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

import org.silverbackhq.reindeer.model.*;

/** Endpoint Repository Class */
public class EndpointRepository {

    public Integer createOne(EndpointEntity endpointEntity) {
        return null;
    }

    public EndpointEntity[] getManyByNamespaceId(Integer namespaceId) {
        return null;
    }

    public EndpointEntity getOneById(Integer id) {
        return null;
    }

    public EndpointEntity getOneByMethodAndURI(String method, String URI) {
        return null;
    }

    public Boolean UpdateById(EndpointEntity endpointEntity) {
        return null;
    }

    public Boolean deleteOneById(Integer id) {
        return null;
    }
}
