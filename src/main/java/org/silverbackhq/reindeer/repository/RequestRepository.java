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

/** Request Repository Class */
public class RequestRepository {

    /**
     * Create a request entity
     *
     * @param requestEntity the request entity
     * @return the new request id
     * @throws Exception if there is error raised
     */
    public Integer createOne(RequestEntity requestEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(requestEntity);
        session.getTransaction().commit();

        return id;
    }

    /**
     * Get requests by endpoint id
     *
     * @param endpointId the endpoint id
     * @return a list of requests
     * @throws Exception if there is error raised
     */
    public List<RequestEntity> getManyByEndpointId(Integer endpointId) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        Query query =
                session.createQuery(
                        String.format(
                                "from %s where endpoint_id=:endpoint_id",
                                RequestEntity.class.getSimpleName()));
        query.setParameter("endpoint_id", endpointId);
        List<RequestEntity> list = query.list();
        session.getTransaction().commit();

        return list;
    }

    /**
     * Get request by id
     *
     * @param id the request id
     * @return the request entity
     * @throws Exception if there is error raised
     */
    public RequestEntity getOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        RequestEntity requestEntity = session.get(RequestEntity.class, id);
        session.getTransaction().commit();

        return requestEntity;
    }

    /**
     * Update request
     *
     * @param requestEntity the request entity
     * @return whether updated or not
     * @throws Exception if there is error raised
     */
    public Boolean update(RequestEntity requestEntity) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(requestEntity);
        session.getTransaction().commit();

        return true;
    }

    /**
     * Delete request by id
     *
     * @param id the request id
     * @return whether deleted or not
     * @throws Exception if there is error raised
     */
    public Boolean deleteOneById(Integer id) throws Exception {

        Session session = ORM.getSessionFactory().openSession();
        session.beginTransaction();
        RequestEntity requestEntity = session.get(RequestEntity.class, id);

        if (requestEntity != null) {
            session.delete(requestEntity);
            session.getTransaction().commit();
            return true;
        }

        session.getTransaction().commit();

        return false;
    }
}
