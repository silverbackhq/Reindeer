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
package org.silverbackhq.reindeer.config;

import java.util.HashMap;
import org.tinylog.configuration.Configuration;

/** Logging Class */
public class Logging {

    public static void config() {
        HashMap<String, String> configs = new HashMap<String, String>();
        configs.put("writer", Config.getConfig().getString("APP_LOGGING_HANDLERS", "console"));
        configs.put("writer.level", Config.getConfig().getString("APP_LOGGING_LEVEL", "debug"));
        configs.put(
                "writer.format",
                Config.getConfig()
                        .getString(
                                "APP_LOGGING_FORMAT",
                                "{date} [{level}] {class}.{method}() {message}"));
        Configuration.replace(configs);
    }
}
