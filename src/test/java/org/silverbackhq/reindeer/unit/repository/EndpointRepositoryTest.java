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
package org.silverbackhq.reindeer.unit.repository;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.silverbackhq.reindeer.TestUtils;
import org.silverbackhq.reindeer.entity.*;
import org.silverbackhq.reindeer.repository.*;

/** Endpoint Repository Test Cases */
@ExtendWith(VertxExtension.class)
public class EndpointRepositoryTest {

    EndpointRepository endpointRepository;

    public EndpointRepositoryTest() throws Exception {
        TestUtils.init();
    }

    @Test
    void test_all(Vertx vertx) {

        TestOptions options = new TestOptions().addReporter(new ReportOptions().setTo("console"));
        TestSuite suite = TestSuite.create(EndpointRepositoryTest.class.getName());

        suite.before(
                context -> {
                    this.endpointRepository = new EndpointRepository();
                });

        suite.after(context -> {});

        suite.test(
                String.format("%s Test %s", TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    context.assertEquals("Test", "Test");
                });

        suite.run(options);
    }
}
