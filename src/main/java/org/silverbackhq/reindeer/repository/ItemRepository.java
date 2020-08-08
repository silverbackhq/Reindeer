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
package org.silverbackhq.reindeer.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.silverbackhq.reindeer.entity.*;
import org.silverbackhq.reindeer.migration.ORM;

/** Item Repository Class */
public class ItemRepository {

    /**
     * Create a new item item
     *
     * @param itemEntity the item entity
     * @return the new item ID
     * @throws Exception if there is error raised
     */
    public Integer createOne(ItemEntity itemEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(itemEntity);
        session.getTransaction().commit();

        return id;
    }

    /**
     * Get All Items
     *
     * @return a list of all items
     * @throws Exception if there is error raised
     */
    public List<ItemEntity> getMany() throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(String.format("from %s", ItemEntity.class.getSimpleName()));
        List<ItemEntity> list = query.list();
        session.getTransaction().commit();

        return list;
    }

    /**
     * Get item by id
     *
     * @param id the item id
     * @return the item entity
     * @throws Exception if there is error raised
     */
    public ItemEntity getOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        ItemEntity itemEntity = session.get(ItemEntity.class, id);
        session.getTransaction().commit();

        return itemEntity;
    }

    /**
     * Get item by slug
     *
     * @param slug the item slug
     * @return the item entity
     * @throws Exception if there is error raised
     */
    public ItemEntity getOneBySlug(String slug) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format(
                                "from %s where slug=:slug", ItemEntity.class.getSimpleName()));
        query.setParameter("slug", slug);
        ItemEntity itemEntity = (ItemEntity) query.uniqueResult();
        session.getTransaction().commit();

        return itemEntity;
    }

    /**
     * Update item
     *
     * @param itemEntity the item entity
     * @return whether updated or not
     * @throws Exception if there is error raised
     */
    public Boolean update(ItemEntity itemEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(itemEntity);
        session.getTransaction().commit();

        return true;
    }

    /**
     * Delete a item by id
     *
     * @param id the item id
     * @return whether deleted or not
     * @throws Exception if there is error raised
     */
    public Boolean deleteOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        ItemEntity itemEntity = session.get(ItemEntity.class, id);

        if (itemEntity != null) {
            session.delete(itemEntity);
            session.getTransaction().commit();
            return true;
        }

        session.getTransaction().commit();

        return false;
    }
}
