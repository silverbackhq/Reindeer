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

/** Endpoint Repository Class */
public class EndpointRepository {

    /**
     * Create an endpoint
     *
     * @param endpointEntity the endpoint entity
     * @return the new entity Id
     * @throws Exception if there is error raised
     */
    public Integer createOne(EndpointEntity endpointEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(endpointEntity);
        session.getTransaction().commit();

        return id;
    }

    /**
     * Get endpoints by namespace id
     *
     * @param namespaceId the namespace id
     * @return a list of endpoints
     * @throws Exception if there is error raised
     */
    public List<EndpointEntity> getManyByNamespaceId(Integer namespaceId) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format(
                                "from %s where namespace_id=:namespace_id",
                                EndpointEntity.class.getSimpleName()));
        query.setParameter("namespace_id", namespaceId);
        List<EndpointEntity> list = query.list();
        session.getTransaction().commit();

        return list;
    }

    /**
     * Get endpoint by id
     *
     * @param id the endpoint id
     * @return the endpoint instance
     * @throws Exception if there is error raised
     */
    public EndpointEntity getOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        EndpointEntity endpointEntity = session.get(EndpointEntity.class, id);
        session.getTransaction().commit();

        return endpointEntity;
    }

    /**
     * Get endpoint by method and URI
     *
     * @param namespaceId the namespace id
     * @param method the endpoint method
     * @param URI the endpoint URI
     * @return the endpoint instance
     * @throws Exception if there is error raised
     */
    public EndpointEntity getOneByMethodAndURI(Integer namespaceId, String method, String URI)
            throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format(
                                "from %s where namespace_id=:namespace_id uri=:uri and method=:method",
                                EndpointEntity.class.getSimpleName()));
        query.setParameter("namespace_id", namespaceId);
        query.setParameter("method", method);
        query.setParameter("uri", URI);
        EndpointEntity endpointEntity = (EndpointEntity) query.uniqueResult();
        session.getTransaction().commit();

        return endpointEntity;
    }

    /**
     * Update Endpoint
     *
     * @param endpointEntity the endpoint entity
     * @return whether updated or not
     * @throws Exception if there is error raised
     */
    public Boolean update(EndpointEntity endpointEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(endpointEntity);
        session.getTransaction().commit();

        return true;
    }

    /**
     * Delete endpoint by id
     *
     * @param id the endpoint id
     * @return whether deleted or not
     * @throws Exception if there is error raised
     */
    public Boolean deleteOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        EndpointEntity endpointEntity = session.get(EndpointEntity.class, id);

        if (endpointEntity != null) {
            session.delete(endpointEntity);
            session.getTransaction().commit();
            return true;
        }

        session.getTransaction().commit();

        return false;
    }
}
