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
import com.clivern.reindeer.controller.*;
import com.clivern.reindeer.daemon.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import java.util.List;

/** Main App Class */
public class App extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("[INFO] App Verticle Started.");

        this.loadEnvironment(this.processArgs());

        Router router = Router.router(vertx);

        router.get("/")
                .handler(
                        context -> {
                            new Health().check(vertx, context);
                        });

        router.get("/_health")
                .handler(
                        context -> {
                            new Health().check(vertx, context);
                        });

        router.get("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace().getAll(vertx, context);
                        });

        router.post("/api/v1/namespace")
                .handler(
                        context -> {
                            new Namespace().createOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().getOne(vertx, context);
                        });

        router.delete("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().deleteOne(vertx, context);
                        });

        router.put("/api/v1/namespace/:namespaceId")
                .handler(
                        context -> {
                            new Namespace().updateOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            new Endpoint().getAll(vertx, context);
                        });

        router.post("/api/v1/namespace/:namespaceId/endpoint")
                .handler(
                        context -> {
                            new Endpoint().createOne(vertx, context);
                        });

        router.get("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint().getOne(vertx, context);
                        });

        router.delete("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint().deleteOne(vertx, context);
                        });

        router.put("/api/v1/namespace/:namespaceId/endpoint/:endpointId")
                .handler(
                        context -> {
                            new Endpoint().updateOne(vertx, context);
                        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        Config.getConfig().getInt("APP_PORT", 8888),
                        http -> {
                            if (http.succeeded()) {
                                startPromise.complete();
                                System.out.println(
                                        String.format(
                                                "HTTP server started on port %d",
                                                Config.getConfig().getInt("APP_PORT", 8888)));
                            } else {
                                startPromise.fail(http.cause());
                            }
                        });

        vertx.deployVerticle(new Worker());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("[INFO] App Verticle Stopped.");
    }

    /**
     * Load Environment Configs
     *
     * @param args the process args
     * @throws Exception when unable to load the configs
     */
    private void loadEnvironment(List<String> args) throws Exception {
        if (args == null) {
            System.out.println("[INFO] Running App on Test Mode");
            return;
        }

        if (!Config.loadFromConfig()) {
            System.out.println("[INFO] App is reading the configs from environment variables.");
            return;
        }

        System.out.println("[INFO] App is reading the configs from config file.");

        Boolean envLoaded = false;

        for (String arg : args) {
            if (arg.startsWith("--env=")) {
                String path = arg.replace("--env=", "");
                Config.getConfig().load(path);
                envLoaded = true;
                break;
            }
        }
        if (!envLoaded) {
            throw new Exception("ERROR! Environment file path not provided.");
        }
    }
}
