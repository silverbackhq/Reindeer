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
package com.clivern.reindeer.command;

import org.flywaydb.core.Flyway;

/** Migrate Class */
public class Migrate {

    /** Run Migration */
    public void run() {
        Flyway flyway =
                Flyway.configure()
                        .dataSource(
                                "jdbc:h2:file:/Users/Ahmed/Projects/Stuff/Reindeer/db", "sa", null)
                        .load();
        flyway.migrate();
    }
}
