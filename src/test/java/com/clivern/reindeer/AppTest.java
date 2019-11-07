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
package com.clivern.reindeer;

import static org.junit.Assert.*;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class AppTest {

    @BeforeEach
    @DisplayName("Deploy a verticle")
    void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new App(), testContext.succeeding(id -> testContext.completeNow()));
    }

    @Test
    @DisplayName("Check that the verticle is deployed")
    void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
        testContext.completeNow();
    }

    @Test
    @DisplayName("Silly Unit Test")
    void test_case(Vertx vertx) {
        assertEquals("Hello", "Hello");
    }
}
