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
package org.silverbackhq.reindeer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import java.util.List;
import org.silverbackhq.reindeer.config.Config;
import org.silverbackhq.reindeer.config.Container;
import org.silverbackhq.reindeer.config.Logging;
import org.silverbackhq.reindeer.controller.*;
import org.silverbackhq.reindeer.daemon.Worker;
import org.silverbackhq.reindeer.middleware.Auth;
import org.silverbackhq.reindeer.middleware.Correlation;
import org.silverbackhq.reindeer.migration.Migrate;
import org.tinylog.Logger;

/** Main Application Class */
public class Application extends AbstractVerticle {

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

        try {
            Logger.info("Start migrating the database.");

            new Migrate().run();
        } catch (Exception e) {
            Logger.error(e.getMessage());

            promise.fail(e.getMessage());

            return promise.future();
        }

        Logger.info("Finished migrating the database.");

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
                            this.injector.getInstance(Correlation.class).run(context);
                        });

        router.route()
                .handler(
                        context -> {
                            this.injector.getInstance(Auth.class).run(context);
                        });

        router.get("/")
                .handler(
                        context -> {
                            this.injector.getInstance(HomeController.class).index(vertx, context);
                        });

        router.get("/_health")
                .handler(
                        context -> {
                            this.injector.getInstance(HealthController.class).check(vertx, context);
                        });

        router.get("/api/v1/item")
                .handler(
                        context -> {
                            this.injector.getInstance(ItemController.class).getAll(vertx, context);
                        });

        router.post("/api/v1/item")
                .handler(
                        context -> {
                            this.injector
                                    .getInstance(ItemController.class)
                                    .createOne(vertx, context);
                        });

        router.get("/api/v1/item/:id")
                .handler(
                        context -> {
                            this.injector.getInstance(ItemController.class).getOne(vertx, context);
                        });

        router.delete("/api/v1/item/:id")
                .handler(
                        context -> {
                            this.injector
                                    .getInstance(ItemController.class)
                                    .deleteOne(vertx, context);
                        });

        router.put("/api/v1/item/:id")
                .handler(
                        context -> {
                            this.injector
                                    .getInstance(ItemController.class)
                                    .updateOne(vertx, context);
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
                                        "HTTP server started on port {}",
                                        Config.getConfig().getInt("APP_PORT", 8888));
                            } else {
                                Logger.error(http.cause());
                                promise.fail(http.cause());
                            }
                        });

        Logger.info("Deploying worker verticle {}.", Worker.class.getName().toString());

        vertx.deployVerticle(
                this.injector.getInstance(Worker.class),
                new DeploymentOptions()
                        .setWorker(true)
                        .setWorkerPoolName(Worker.POOL_NAME)
                        .setWorkerPoolSize(Worker.POOL_SIZE),
                res -> {
                    if (res.succeeded()) {
                        Logger.info(
                                "{} verticle deployed, deploymentId {}",
                                Worker.class.getName().toString(),
                                res.result());
                    } else {
                        promise.fail(res.cause());
                        Logger.error(
                                "{} verticle failed to deploy: {}",
                                Worker.class.getName().toString(),
                                res.cause());
                    }
                });

        return promise.future();
    }
}
