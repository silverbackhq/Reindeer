/*
 * Copyright (C) 2019 Clivern <http://clivern.com>
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

import io.vertx.ext.web.RoutingContext;

/** Namespace Class */
public class Namespace {

    /**
     * Get namespaces endpoint action
     *
     * @param context request object
     */
    public void getAll(RoutingContext context) {
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end("{\"action\":\"getAll\"}");
    }

    /**
     * Create namespace endpoint action
     *
     * @param context request object
     */
    public void createOne(RoutingContext context) {
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end("{\"action\":\"createOne\"}");
    }

    /**
     * Get namespace endpoint action
     *
     * @param context request object
     */
    public void getOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        String.format(
                                "{\"namespaceId\":\"%s\", \"action\": \"getOne\"}", namespaceId));
    }

    /**
     * Delete namespace endpoint action
     *
     * @param context request object
     */
    public void deleteOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        String.format(
                                "{\"namespaceId\":\"%s\", \"action\": \"deleteOne\"}",
                                namespaceId));
    }

    /**
     * Update namespace endpoint action
     *
     * @param context request object
     */
    public void updateOne(RoutingContext context) {
        String namespaceId = context.request().getParam("namespaceId");
        context.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(
                        String.format(
                                "{\"namespaceId\":\"%s\", \"action\": \"updateOne\"}",
                                namespaceId));
    }
}
