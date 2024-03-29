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
package org.silverbackhq.reindeer.migration;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.silverbackhq.reindeer.config.Config;
import org.silverbackhq.reindeer.entity.*;
import org.tinylog.Logger;

/** ORM Class */
public class ORM {

    private static SessionFactory sessionFactory;

    /**
     * Get Session Factory
     *
     * @return the SessionFactory instance
     * @throws Exception if there is error raised
     */
    public static SessionFactory getSessionFactory() throws Exception {

        if (sessionFactory != null) {
            return sessionFactory;
        }

        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();

            if (Config.getConfig().getString("DB_CONNECTION", "h2").equals(Migrate.DB_MySQL)) {
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            }

            if (Config.getConfig().getString("DB_CONNECTION", "h2").equals(Migrate.DB_H2)) {
                settings.put(Environment.DRIVER, "org.h2.Driver");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            }

            settings.put(Environment.URL, new Migrate().getConnectionString());
            settings.put(Environment.USER, Config.getConfig().getString("DB_USERNAME", "root"));
            settings.put(Environment.PASS, Config.getConfig().getString("DB_PASSWORD", "secret"));

            // TODO allow optional configurations of these values
            settings.put(Environment.SHOW_SQL, Config.getConfig().getString("APP_DEBUG", "false"));
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(
                    "connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
            settings.put(
                    "hibernate.jdbc.time_zone",
                    Config.getConfig().getString("APP_TIMEZONE", "UTC"));
            settings.put("hibernate.c3p0.acquire_increment", "1");
            settings.put("hibernate.c3p0.idle_test_period", "60");
            settings.put("hibernate.c3p0.min_size", "1");
            settings.put("hibernate.c3p0.max_size", "2");
            settings.put("hibernate.c3p0.max_statements", "50");
            settings.put("hibernate.c3p0.timeout", "0");
            settings.put("hibernate.c3p0.acquireRetryAttempts", "1");
            settings.put("hibernate.c3p0.acquireRetryDelay", "250");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(ItemEntity.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            Logger.error("Unable to connect to database: {}", e.getMessage());
            throw new Exception("Unable to connect to database.");
        }

        return sessionFactory;
    }
}
