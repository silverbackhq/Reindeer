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
import com.clivern.reindeer.controller.Namespace;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/** Main Class */
public class App extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Config.getConfig().load("~~");

        Router router = Router.router(vertx);

        router.route("/").handler(this::healthCheck);

        router.route("/_health").handler(this::healthCheck);

        router.get("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace().getAll(context);
                        });

        router.post("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace().createOne(context);
                        });

        router.get("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().getOne(context);
                        });

        router.delete("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().deleteOne(context);
                        });

        router.put("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().updateOne(context);
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
    }

    /**
     * Health check endpoint action
     *
     * @param context request object
     */
    public void healthCheck(RoutingContext context) {
        context.response().putHeader("content-type", "application/json").end("{\"status\":\"ok\"}");
    }
}
