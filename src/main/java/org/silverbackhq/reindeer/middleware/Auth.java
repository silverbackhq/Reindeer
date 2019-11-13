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
package org.silverbackhq.reindeer.middleware;

import io.vertx.ext.web.RoutingContext;
import org.silverbackhq.reindeer.config.Config;
import org.silverbackhq.reindeer.util.ContentType;
import org.silverbackhq.reindeer.util.JSON;
import org.silverbackhq.reindeer.util.StatusCode;
import org.tinylog.Logger;

/** Auth Class */
public class Auth {

    /**
     * Run Middleware
     *
     * @param context request object
     */
    public void run(RoutingContext context) {
        if (!Config.getConfig().getString("APP_AUTH_TOKEN", "").equals("")) {
            if (context.request().getHeader("X-Auth-Token") == null
                    || !context.request()
                            .getHeader("X-Auth-Token")
                            .equals(Config.getConfig().getString("APP_AUTH_TOKEN", ""))) {
                context.response()
                        .setStatusCode(StatusCode.FORBIDDEN)
                        .putHeader("content-type", ContentType.JSON)
                        .end(
                                new JSON()
                                        .put("error", "Access denied")
                                        .put(
                                                "corrlationId",
                                                (String) context.get("X-Correlation-ID"))
                                        .toString());
                return;
            }
        }

        Logger.info("Incoming Request corrlationId={}", (String) context.get("X-Correlation-ID"));
        context.next();
    }
}
