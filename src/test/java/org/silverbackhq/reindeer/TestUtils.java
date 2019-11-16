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

import static org.junit.Assert.*;

import org.silverbackhq.reindeer.config.Config;
import org.silverbackhq.reindeer.entity.*;
import org.silverbackhq.reindeer.migration.Migrate;

/** Test Utils */
public class TestUtils {

    public static final String CYAN = "\033[0;36m";
    public static final String DEFAULT = "\033[0m";
    public static Boolean configured = false;

    /**
     * Init App
     *
     * @param Exception when error raised
     */
    public static void init() throws Exception {

        if (TestUtils.configured) {
            return;
        }
        TestUtils.configured = true;
        Config.getConfig().load("src/test/resources/.env.test");
        new Migrate().run();
    }

    /**
     * Print a message
     *
     * @param message the message
     */
    public static void print(String message) {
        System.out.println(
                String.format("%s===> %s <===%s", TestUtils.CYAN, message, TestUtils.DEFAULT));
    }
}
