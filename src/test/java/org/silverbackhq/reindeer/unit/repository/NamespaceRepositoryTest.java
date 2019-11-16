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

/** Namespace Repository Test Cases */
@ExtendWith(VertxExtension.class)
public class NamespaceRepositoryTest {

    NamespaceRepository namespaceRepository;

    public NamespaceRepositoryTest() throws Exception {
        TestUtils.init();
    }

    @Test
    void test_all(Vertx vertx) {

        TestOptions options = new TestOptions().addReporter(new ReportOptions().setTo("console"));
        TestSuite suite = TestSuite.create(NamespaceRepositoryTest.class.getName());

        suite.before(
                context -> {
                    this.namespaceRepository = new NamespaceRepository();
                });

        suite.after(context -> {});

        suite.test(
                String.format(
                        "%s Test NamespaceRepository::createOne %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.namespaceRepository.createOne(
                                        new NamespaceEntity()
                                                .setName("TestName")
                                                .setSlug("test_slug")),
                                new Integer(1));
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Exception while running NamespaceRepository::createOne test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test NamespaceRepository::getOneById %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.namespaceRepository.getOneById(new Integer(1)).getName(),
                                "TestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Exception while running NamespaceRepository::getOneById test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test NamespaceRepository::getOneBySlug %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.namespaceRepository.getOneBySlug("test_slug").getName(),
                                "TestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Exception while running NamespaceRepository::getOneBySlug test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test NamespaceRepository::update %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        NamespaceEntity namespaceEntity =
                                this.namespaceRepository.getOneById(new Integer(1));
                        namespaceEntity.setName("NewTestName");
                        context.assertEquals(
                                this.namespaceRepository.update(namespaceEntity), true);
                        context.assertEquals(
                                this.namespaceRepository.getOneBySlug("test_slug").getName(),
                                "NewTestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Exception while running NamespaceRepository::update test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test NamespaceRepository::deleteOneById %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.namespaceRepository.deleteOneById(new Integer(1)), true);
                        context.assertEquals(
                                this.namespaceRepository.deleteOneById(new Integer(2)), false);
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Exception while running NamespaceRepository::deleteOneById test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.run(options);
    }
}
