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
package org.silverbackhq.reindeer.util;

import org.json.simple.JSONObject;

/** JSON Class */
public class JSON {

    private JSONObject obj = new JSONObject();

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, String value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, Integer value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, Double value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, Boolean value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, Float value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Add Item
     *
     * @param key Item Key
     * @param value Item Value
     * @return JSON Instance
     */
    public JSON put(String key, JSONObject value) {
        this.obj.put(key, value);
        return this;
    }

    /**
     * Get JSONObject
     *
     * @return the JSONObject
     */
    public JSONObject get() {
        return this.obj;
    }

    @Override
    public String toString() {
        return this.obj.toString();
    }
}
