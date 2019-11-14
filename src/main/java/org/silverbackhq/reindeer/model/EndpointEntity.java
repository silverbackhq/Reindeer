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
package org.silverbackhq.reindeer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "endpoint")
@DynamicUpdate
public class EndpointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    /**
     * Get id column value
     *
     * @return the id column value
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Get created_at column value
     *
     * @return the created_at column value
     */
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Get updated_at column value
     *
     * @return the updated_at column value
     */
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Set id column value
     *
     * @param id the id column value
     * @return an instance of the model
     */
    public EndpointEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Set created_at column value
     *
     * @param createdAt the created_at column value
     * @return an instance of the model
     */
    public EndpointEntity setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Set updated_at column value
     *
     * @param updatedAt the updated_at column value
     * @return an instance of the model
     */
    public EndpointEntity setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
