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
import com.clivern.reindeer.config.Container;
import com.clivern.reindeer.config.Logging;
import com.clivern.reindeer.controller.*;
import com.clivern.reindeer.daemon.Worker;
import com.clivern.reindeer.middleware.Before;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import java.util.List;
import org.tinylog.Logger;

/** Main App Class */
public class App extends AbstractVerticle {

    private Injector injector;

    @Override
    public void start(Promise<Void> promise) {

        this.injector = Guice.createInjector(new Container());

        Future<Void> steps =
                bootstrapEnv().compose(v -> prepareDatabase()).compose(v -> startHttpServer());

        steps.setHandler(
                result -> {
                    if (result.succeeded()) {
                        promise.complete();
                    } else {
                        promise.fail(result.cause());
                    }
                });
    }

    @Override
    public void stop() throws Exception {
        Logger.info("Application main verticle stopped.");
    }

    /**
     * Bootstrap Environment
     *
     * @return an instance of future object
     */
    private Future<Void> bootstrapEnv() {

        Promise<Void> promise = Promise.promise();

        List<String> args = this.processArgs();

        if (args == null) {
            promise.complete();
            return promise.future();
        }

        if (!Config.loadFromConfig()) {
            promise.complete();
            return promise.future();
        }

        Boolean envLoaded = false;

        try {
            for (String arg : args) {
                if (arg.startsWith("--env=")) {
                    String path = arg.replace("--env=", "");
                    Config.getConfig().load(path);
                    envLoaded = true;
                    break;
                }
            }
        } catch (Exception e) {
            promise.fail(e.getMessage());
            return promise.future();
        }

        if (!envLoaded) {
            promise.fail("ERROR! Environment file path not provided.");
            return promise.future();
        }

        // Config Logging
        Logging.config();

        promise.complete();

        return promise.future();
    }

    /**
     * Prepare The Database
     *
     * @return an instance of future object
     */
    private Future<Void> prepareDatabase() {

        Promise<Void> promise = Promise.promise();

        Logger.info("Prepare the database for application.");

        promise.complete();

        return promise.future();
    }

    /**
     * Start The HTTP Server
     *
     * @return an instance of future object
     */
    private Future<Void> startHttpServer() {

        Promise<Void> promise = Promise.promise();

        Logger.info("Application main verticle started.");

        Router router = Router.router(vertx);

        Logger.debug("Build application routes.");

        router.route()
                .handler(
                        context -> {
                            this.injector.getInstance(Before.class).run(context);
                        });

        router.get("/")
                .handler(
                        context -> {
                            this.injector.getInstance(Health.class).check(vertx, context);
                        });

        router.get("/_health")
                .handler(
                        context -> {
                            this.injector.getInstance(Health.class).check(vertx, context);
                        });

        router.get("/api/v1/namespace")
                .handler(
                        context -> {
                            this.injector.getInstance(Namespace.class).getAll(vertx, context);
                        });

        router.post("/api/v1/namespace")
                .handler(
                        context -> {
                            this.injector.getInstance(Namespace.class).createOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            this.injector.getInstance(Namespace.class).getOne(vertx, context);
                        });

        router.delete("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            this.injector.getInstance(Namespace.class).deleteOne(vertx, context);
                        });

        router.put("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            this.injector.getInstance(Namespace.class).updateOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            this.injector.getInstance(Endpoint.class).getAll(vertx, context);
                        });

        router.post("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            this.injector.getInstance(Endpoint.class).createOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            this.injector.getInstance(Endpoint.class).getOne(vertx, context);
                        });

        router.delete("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            this.injector.getInstance(Endpoint.class).deleteOne(vertx, context);
                        });

        router.put("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            this.injector.getInstance(Endpoint.class).updateOne(vertx, context);
                        });

        Logger.debug("Starting HTTP server.");

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        Config.getConfig().getInt("APP_PORT", 8888),
                        http -> {
                            if (http.succeeded()) {
                                promise.complete();
                                Logger.info(
                                        "HTTP server started on port {0}",
                                        Config.getConfig().getInt("APP_PORT", 8888));
                            } else {
                                Logger.error(http.cause());
                                promise.fail(http.cause());
                            }
                        });

        Logger.info("Deploying worker verticle {0}.", Worker.class.getName());
        vertx.deployVerticle(this.injector.getInstance(Worker.class));

        return promise.future();
    }
}
