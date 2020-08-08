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

/** Item Repository Test Cases */
@ExtendWith(VertxExtension.class)
public class ItemRepositoryTest {

    ItemRepository itemRepository;

    public ItemRepositoryTest() throws Exception {
        TestUtils.init();
    }

    @Test
    void test_all(Vertx vertx) {

        TestOptions options = new TestOptions().addReporter(new ReportOptions().setTo("console"));
        TestSuite suite = TestSuite.create(ItemRepositoryTest.class.getName());

        suite.before(
                context -> {
                    this.itemRepository = new ItemRepository();
                });

        suite.after(context -> {});

        suite.test(
                String.format(
                        "%s Test ItemRepository::createOne %s", TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.itemRepository.createOne(
                                        new ItemEntity().setName("TestName").setSlug("test_slug")),
                                new Integer(1));
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::createOne test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test ItemRepository::getMany %s", TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.itemRepository.getMany().get(0).getName(), "TestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::getMany test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test ItemRepository::getOneById %s", TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.itemRepository.getOneById(new Integer(1)).getName(),
                                "TestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::getOneById test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test ItemRepository::getOneBySlug %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.itemRepository.getOneBySlug("test_slug").getName(),
                                "TestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::getOneBySlug test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test ItemRepository::update %s", TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        ItemEntity itemEntity = this.itemRepository.getOneById(new Integer(1));
                        itemEntity.setName("NewTestName");
                        context.assertEquals(this.itemRepository.update(itemEntity), true);
                        context.assertEquals(
                                this.itemRepository.getOneBySlug("test_slug").getName(),
                                "NewTestName");
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::update test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.test(
                String.format(
                        "%s Test ItemRepository::deleteOneById %s",
                        TestUtils.CYAN, TestUtils.DEFAULT),
                context -> {
                    try {
                        context.assertEquals(
                                this.itemRepository.deleteOneById(new Integer(1)), true);
                        context.assertEquals(
                                this.itemRepository.deleteOneById(new Integer(2)), false);
                    } catch (Exception e) {
                        context.fail(
                                String.format(
                                        "Error while running ItemRepository::deleteOneById test case: %s",
                                        e.getMessage()));
                    }
                });

        suite.run(options);
    }
}
