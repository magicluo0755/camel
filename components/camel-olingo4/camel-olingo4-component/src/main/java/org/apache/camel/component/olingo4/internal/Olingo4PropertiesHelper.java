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
package org.apache.camel.component.olingo4.internal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.camel.CamelContext;
import org.apache.camel.component.olingo4.Olingo4Configuration;
import org.apache.camel.support.component.ApiMethodPropertiesHelper;

/**
 * Singleton {@link ApiMethodPropertiesHelper} for Olingo4 component.
 */
public final class Olingo4PropertiesHelper extends ApiMethodPropertiesHelper<Olingo4Configuration> {

    private static final Lock LOCK = new ReentrantLock();
    private static Olingo4PropertiesHelper helper;

    private Olingo4PropertiesHelper(CamelContext context) {
        super(context, Olingo4Configuration.class, Olingo4Constants.PROPERTY_PREFIX);
    }

    public static Olingo4PropertiesHelper getHelper(CamelContext context) {
        LOCK.lock();
        try {
            if (helper == null) {
                helper = new Olingo4PropertiesHelper(context);
            }
            return helper;
        } finally {
            LOCK.unlock();
        }
    }
}
