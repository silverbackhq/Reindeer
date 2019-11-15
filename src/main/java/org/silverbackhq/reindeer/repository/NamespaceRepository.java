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

import org.hibernate.Session;
import org.silverbackhq.reindeer.migration.ORM;
import org.silverbackhq.reindeer.model.*;

/** Namespace Repository Class */
public class NamespaceRepository {

    /**
     * Create a new namespace item
     *
     * @param namespaceEntity the namespace model
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

    public NamespaceEntity[] getMany() {
        return null;
    }

    public NamespaceEntity getOneById(Integer id) {
        return null;
    }

    public NamespaceEntity getOneBySlug(String slug) {
        return null;
    }

    public Boolean UpdateById(NamespaceEntity namespaceEntity) {
        return null;
    }

    public Boolean UpdateBySlug(NamespaceEntity namespaceEntity) {
        return null;
    }

    public Boolean deleteOneById(Integer id) {
        return null;
    }

    public Boolean deleteOneBySlug(String slug) {
        return null;
    }
}
