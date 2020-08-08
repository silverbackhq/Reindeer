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

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;

/** Config Class */
public class Config {

    private static final Config instance = new Config();

    private Dotenv env;

    /**
     * Class Constructor
     *
     * @return an instance of config class
     */
    public static Config getConfig() {
        return instance;
    }

    /**
     * Check whether to load from env file or env variables
     *
     * @return Whether to load from env file or env variables
     */
    public static Boolean loadFromConfig() {
        String loadFrom = System.getenv("REINDEER_LOAD_FROM");

        return (loadFrom != null && loadFrom.equals("system")) ? false : true;
    }

    /**
     * Load .env file
     *
     * @param path .env file path
     * @throws Exception when unable to load the configs
     */
    public void load(String path) throws Exception {
        if (path.equals("")) {
            throw new Exception("ERROR! Environment file path is invalid.");
        }

        File f = new File(path);

        if (!f.exists() || !f.isFile()) {
            throw new Exception("ERROR! Environment file path is invalid.");
        }

        System.out.println(
                String.format(
                        "[INFO] Load config file %s/%s",
                        (f.getParent() == null) ? "./" : f.getParent(), f.getName()));

        this.env =
                Dotenv.configure()
                        .directory((f.getParent() == null) ? "./" : f.getParent())
                        .filename(f.getName())
                        .load();
    }

    /**
     * Get env record
     *
     * @param variable the key
     * @param def the default value
     * @return the value
     */
    public String getString(String variable, String def) {
        String value = this.get(variable);

        if (value == null) {
            return def;
        }

        if (value.trim().equals("")) {
            return "";
        }

        return value.trim();
    }

    /**
     * Get env record
     *
     * @param variable the key
     * @param def the default value
     * @return the value
     */
    public Integer getInt(String variable, Integer def) {
        String value = this.get(variable);

        if (value == null) {
            return def;
        }

        if (value.trim().equals("")) {
            return new Integer(0);
        }

        return Integer.parseInt(value.trim());
    }

    /**
     * Get Variable whether from config or environment variables
     *
     * @param variable the key
     * @return the value
     */
    private String get(String variable) {
        if (Config.loadFromConfig()) {
            return this.env.get(variable);
        } else {
            return System.getenv(variable);
        }
    }
}
