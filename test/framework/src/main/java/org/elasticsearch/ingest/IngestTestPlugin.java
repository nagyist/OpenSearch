/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.ingest;

import org.elasticsearch.node.NodeModule;
import org.elasticsearch.plugins.Plugin;

/**
 * Adds an ingest processor to be used in tests.
 */
public class IngestTestPlugin extends Plugin {

    @Override
    public String name() {
        return "ingest-test";
    }

    @Override
    public String description() {
        return "Contains an ingest processor to be used in tests";
    }

    public void onModule(NodeModule nodeModule) {
        nodeModule.registerProcessor("test", (registry) -> config ->
                new TestProcessor("id", "test", doc -> {
                    doc.setFieldValue("processed", true);
                    if (doc.hasField("fail") && doc.getFieldValue("fail", Boolean.class)) {
                        throw new IllegalArgumentException("test processor failed");
                    }
                })
        );
    }
}
