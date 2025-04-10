/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.file;

import java.util.UUID;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

public class FileConsumerIdempotentOnExceptionHandledTest extends ContextTestSupport {
    private static final String TEST_FILE_NAME = "hello" + UUID.randomUUID() + ".txt";

    @Test
    public void testIdempotent() throws Exception {
        getMockEndpoint("mock:invalid").expectedMessageCount(1);

        template.sendBodyAndHeader(fileUri(), "Hello World", Exchange.FILE_NAME, TEST_FILE_NAME);

        oneExchangeDone.matchesWaitTime();

        assertMockEndpointsSatisfied();

        // the error is handled and the file is regarded as success and
        // therefore moved to .camel
        assertFileNotExists(testFile(TEST_FILE_NAME));
        assertFileExists(testFile(".camel/" + TEST_FILE_NAME));
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                onException(Exception.class).handled(true).to("mock:invalid");

                // our route logic to process files from the input folder
                from(fileUri("?initialDelay=0&delay=10&idempotent=true")).to("mock:input")
                        .throwException(new IllegalArgumentException("Forced"));
            }
        };
    }

}
