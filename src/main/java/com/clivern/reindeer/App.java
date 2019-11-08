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

import com.clivern.reindeer.config.Config;
import com.clivern.reindeer.controller.Endpoint;
import com.clivern.reindeer.controller.Namespace;
import com.clivern.reindeer.util.ContentType;
import com.clivern.reindeer.util.JSON;
import com.clivern.reindeer.util.StatusCode;
import com.clivern.reindeer.verticle.TestVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/** Main Class */
public class App extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("[INFO] App Verticle Started.");

        Config.getConfig().load("~~");

        Router router = Router.router(vertx);

        router.route("/").handler(this::healthCheck);

        router.route("/_health").handler(this::healthCheck);

        router.get("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace(vertx).getAll(context);
                        });

        router.post("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace(vertx).createOne(context);
                        });

        router.get("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace(vertx).getOne(context);
                        });

        router.delete("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace(vertx).deleteOne(context);
                        });

        router.put("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace(vertx).updateOne(context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            new Endpoint(vertx).getAll(context);
                        });

        router.post("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            new Endpoint(vertx).createOne(context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint(vertx).getOne(context);
                        });

        router.delete("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint(vertx).deleteOne(context);
                        });

        router.put("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint(vertx).updateOne(context);
                        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        8888,
                        http -> {
                            if (http.succeeded()) {
                                startPromise.complete();
                                System.out.println("HTTP server started on port 8888");
                            } else {
                                startPromise.fail(http.cause());
                            }
                        });

        vertx.deployVerticle(new TestVerticle());
    }

    /**
     * Health check endpoint action
     *
     * @param context request object
     */
    public void healthCheck(RoutingContext context) {
        context.response()
                .setStatusCode(StatusCode.OK)
                .putHeader("content-type", ContentType.JSON)
                .end(new JSON().put("status", "ok").toString());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("[INFO] App Verticle Stopped.");
    }
}
