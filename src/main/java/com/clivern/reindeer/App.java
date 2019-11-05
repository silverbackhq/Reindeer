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
package com.clivern.reindeer;

import io.vertx.core.Vertx;

/** Main Class */
public class App {

    public String getGreeting() {
        return "Hello From Reindeer!";
    }

    public static void main(String[] args) {
        Vertx.vertx()
                .createHttpServer()
                .requestHandler(
                        req -> {
                            req.response()
                                    .putHeader("content-type", "text/plain")
                                    .end(new App().getGreeting());
                        })
                .listen(8080);
    }
}
