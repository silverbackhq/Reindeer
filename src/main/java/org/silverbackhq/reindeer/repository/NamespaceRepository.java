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

/** Namespace Repository Class */
public class NamespaceRepository {

    /**
     * Create a new namespace item
     *
     * @param namespaceEntity the namespace entity
     * @return the new namespace ID
     * @throws Exception if there is error raised
     */
    public Integer createOne(NamespaceEntity namespaceEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(namespaceEntity);
        session.getTransaction().commit();

        return id;
    }

    /**
     * Get All Namespaces
     *
     * @return a list of all namespaces
     * @throws Exception if there is error raised
     */
    public List<NamespaceEntity> getMany() throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format("from %s", NamespaceEntity.class.getSimpleName()));
        List<NamespaceEntity> list = query.list();
        session.getTransaction().commit();

        return list;
    }

    /**
     * Get namespace by id
     *
     * @param id the namespace id
     * @return the namespace entity
     * @throws Exception if there is error raised
     */
    public NamespaceEntity getOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        NamespaceEntity namespaceEntity = session.get(NamespaceEntity.class, id);
        session.getTransaction().commit();

        return namespaceEntity;
    }

    /**
     * Get namespace by slug
     *
     * @param slug the namespace slug
     * @return the namespace entity
     * @throws Exception if there is error raised
     */
    public NamespaceEntity getOneBySlug(String slug) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format(
                                "from %s where slug=:slug", NamespaceEntity.class.getSimpleName()));
        query.setParameter("slug", slug);
        NamespaceEntity namespaceEntity = (NamespaceEntity) query.uniqueResult();
        session.getTransaction().commit();

        return namespaceEntity;
    }

    /**
     * Update namespace
     *
     * @param namespaceEntity the namespace entity
     * @return whether updated or not
     * @throws Exception if there is error raised
     */
    public Boolean update(NamespaceEntity namespaceEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(namespaceEntity);
        session.getTransaction().commit();

        return true;
    }

    /**
     * Delete a namespace by id
     *
     * @param id the namespace id
     * @return whether deleted or not
     * @throws Exception if there is error raised
     */
    public Boolean deleteOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        NamespaceEntity namespaceEntity = session.get(NamespaceEntity.class, id);

        if (namespaceEntity != null) {
            session.delete(namespaceEntity);
            session.getTransaction().commit();
            return true;
        }

        session.getTransaction().commit();

        return false;
    }
}
