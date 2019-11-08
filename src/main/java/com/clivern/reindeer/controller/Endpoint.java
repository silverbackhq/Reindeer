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
package com.clivern.reindeer.controller;

import com.clivern.reindeer.util.JSON;
import com.clivern.reindeer.verticle.TestVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

/** Endpoint Class */
public class Endpoint {

    private Vertx vertx;

    public Endpoint(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Get namespace endpoints endpoint action
     *
     * @param context request object
     */
    public void getAll(RoutingContext context) {
        this.vertx.eventBus().send(TestVerticle.ADDRESS, "Endpoint::getAll");

        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(new JSON().put("action", "getAll").put("namespaceId", namespaceId).toString());
    }

    /**
     * Create namespace endpoint endpoint action
     *
     * @param context request object
     */
    public void createOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        new JSON()
                                .put("action", "createOne")
                                .put("namespaceId", namespaceId)
                                .toString());
    }

    /**
     * Get namespace endpoint endpoint action
     *
     * @param context request object
     */
    public void getOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        String endpointId = context.request().getParam("endpointId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        new JSON()
                                .put("action", "getOne")
                                .put("namespaceId", namespaceId)
                                .put("endpointId", endpointId)
                                .toString());
    }

    /**
     * Delete namespace endpoint endpoint action
     *
     * @param context request object
     */
    public void deleteOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        String endpointId = context.request().getParam("endpointId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        new JSON()
                                .put("action", "deleteOne")
                                .put("namespaceId", namespaceId)
                                .put("endpointId", endpointId)
                                .toString());
    }

    /**
     * Update namespace endpoint endpoint action
     *
     * @param context request object
     */
    public void updateOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        String endpointId = context.request().getParam("endpointId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        new JSON()
                                .put("action", "updateOne")
                                .put("namespaceId", namespaceId)
                                .put("endpointId", endpointId)
                                .toString());
    }
}
