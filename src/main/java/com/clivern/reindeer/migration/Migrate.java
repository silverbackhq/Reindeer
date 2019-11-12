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
package com.clivern.reindeer.migration;

import com.clivern.reindeer.config.Config;
import org.flywaydb.core.Flyway;

/** Migrate Class */
public class Migrate {

    private static final String DB_H2 = "h2";
    private static final String DB_MySQL = "mysql";

    /**
     * Run Migration
     *
     * @throws Exception when unsupported database found
     */
    public void run() throws Exception {
        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                this.getConnectionString(),
                                Config.getConfig().getString("DB_USERNAME", "root"),
                                Config.getConfig().getString("DB_PASSWORD", "secret"))
                        .load();
        flyway.migrate();
    }

    /**
     * Get DB Connection String
     *
     * @return the DB Connection String
     * @throws Exception when unsupported database found
     */
    private String getConnectionString() throws Exception {
        if (Config.getConfig().getString("DB_CONNECTION", "h2").equals(Migrate.DB_H2)) {
            return String.format(
                    "jdbc:h2:file:%s", Config.getConfig().getString("DB_DATABASE", "./db"));
        }
        if (Config.getConfig().getString("DB_CONNECTION", "h2").equals(Migrate.DB_MySQL)) {
            return String.format(
                    "jdbc:mysql://%s:%d/%s",
                    Config.getConfig().getString("DB_HOST", "127.0.0.1"),
                    Config.getConfig().getInt("DB_PORT", 3306),
                    Config.getConfig().getString("DB_DATABASE", "reindeer"));
        }

        throw new Exception(
                String.format(
                        "Unsupported database %s",
                        Config.getConfig().getString("DB_CONNECTION", "h2")));
    }
}
