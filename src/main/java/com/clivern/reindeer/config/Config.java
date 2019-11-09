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
package com.clivern.reindeer.config;

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
     * Load .env file inside a directory
     *
     * @param directory .env file directory
     * @throws Exception when unable to load the configs
     */
    public void load(String directory) throws Exception {

        if (directory.equals("")) {
            throw new Exception("ERROR! Environment file path is invalid.");
        }

        File f = new File(directory.replace("/.env", ""));

        if (!f.isDirectory()) {
            throw new Exception("ERROR! Environment file path is invalid.");
        }

        this.env =
                Dotenv.configure()
                        .directory(directory.replace("/.env", ""))
                        .filename(".env")
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
        String value = this.env.get(variable);

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
        String value = this.env.get(variable);

        if (value == null) {
            return def;
        }

        if (value.trim().equals("")) {
            return new Integer(0);
        }

        return Integer.parseInt(value.trim());
    }
}
