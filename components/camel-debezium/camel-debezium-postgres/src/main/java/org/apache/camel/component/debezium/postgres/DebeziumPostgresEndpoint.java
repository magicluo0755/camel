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
package org.apache.camel.component.debezium.postgres;

import java.util.Map;

import org.apache.camel.Category;
import org.apache.camel.component.debezium.DebeziumConstants;
import org.apache.camel.component.debezium.DebeziumEndpoint;
import org.apache.camel.component.debezium.postgres.configuration.PostgresConnectorEmbeddedDebeziumConfiguration;
import org.apache.camel.spi.EndpointServiceLocation;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

/**
 * Capture changes from a PostgresSQL database.
 */
@UriEndpoint(firstVersion = "3.0.0", scheme = "debezium-postgres", title = "Debezium PostgresSQL Connector",
             syntax = "debezium-postgres:name", category = { Category.DATABASE }, consumerOnly = true,
             headersClass = DebeziumConstants.class)
public final class DebeziumPostgresEndpoint extends DebeziumEndpoint<PostgresConnectorEmbeddedDebeziumConfiguration>
        implements EndpointServiceLocation {

    @UriParam
    private PostgresConnectorEmbeddedDebeziumConfiguration configuration;

    public DebeziumPostgresEndpoint(final String uri, final DebeziumPostgresComponent component,
                                    final PostgresConnectorEmbeddedDebeziumConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
    }

    public DebeziumPostgresEndpoint() {
    }

    @Override
    public String getServiceUrl() {
        return configuration.getDatabaseHostname() + ":" + configuration.getDatabasePort();
    }

    @Override
    public String getServiceProtocol() {
        return "jdbc";
    }

    @Override
    public Map<String, String> getServiceMetadata() {
        if (configuration.getDatabaseUser() != null) {
            return Map.of("username", configuration.getDatabaseUser());
        }
        return null;
    }

    @Override
    public PostgresConnectorEmbeddedDebeziumConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(final PostgresConnectorEmbeddedDebeziumConfiguration configuration) {
        this.configuration = configuration;
    }
}
