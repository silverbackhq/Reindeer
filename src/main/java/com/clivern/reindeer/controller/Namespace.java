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

import com.clivern.reindeer.daemon.Worker;
import com.clivern.reindeer.task.Log;
import com.clivern.reindeer.util.ContentType;
import com.clivern.reindeer.util.JSON;
import com.clivern.reindeer.util.StatusCode;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

/** Namespace Class */
public class Namespace {

    /**
     * Get namespaces endpoint action
     *
     * @param context request object
     * @param vertx an instance of Vertx
     */
    public void getAll(Vertx vertx, RoutingContext context) {
        vertx.eventBus()
                .send(
                        Worker.class.getName(),
                        new JSON().put("task", Log.class.getName()).toString());

        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(new JSON().put("action", "getAll").toString());
    }

    /*
     * Create namespace endpoint action
     *
     * @param context request object
     * @param vertx an instance of Vertx
     */
    public void createOne(Vertx vertx, RoutingContext context) {
        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(new JSON().put("action", "createOne").toString());
    }

    /**
     * Get namespace endpoint action
     *
     * @param context request object
     * @param vertx an instance of Vertx
     */
    public void getOne(Vertx vertx, RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(new JSON().put("action", "getOne").put("namespaceId", namespaceId).toString());
    }

    /**
     * Delete namespace endpoint action
     *
     * @param context request object
     * @param vertx an instance of Vertx
     */
    public void deleteOne(Vertx vertx, RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(
                        new JSON()
                                .put("action", "deleteOne")
                                .put("namespaceId", namespaceId)
                                .toString());
    }

    /**
     * Update namespace endpoint action
     *
     * @param context request object
     * @param vertx an instance of Vertx
     */
    public void updateOne(Vertx vertx, RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(
                        new JSON()
                                .put("action", "updateOne")
                                .put("namespaceId", namespaceId)
                                .toString());
    }
}