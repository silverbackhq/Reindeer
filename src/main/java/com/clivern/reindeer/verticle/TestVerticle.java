/*
 * Copyright (C) 2019 Silverbackhq <https://github.com/silverbackhq>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.clivern.reindeer.verticle;

import io.vertx.core.AbstractVerticle;

/** TestVerticle Class */
public class TestVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println("[INFO] Test Verticle Started.");

        vertx.eventBus()
                .consumer(
                        TestVerticle.class.getName(),
                        message -> {
                            System.out.println(
                                    String.format("[INFO] Received message: %s", message.body()));
                        });
    }

    @Override
    public void stop() throws Exception {
        System.out.println("[INFO] Test Verticle Stopped.");
    }
}
