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
package com.clivern.reindeer.daemon;

import com.clivern.reindeer.task.*;
import com.clivern.reindeer.util.Serializer;
import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import org.tinylog.Logger;

/** Worker Class */
public class Worker extends AbstractVerticle {

    private Serializer serializer;

    public static final int POOL_SIZE = 5;

    public static final String POOL_NAME = "daemon_worker";

    /**
     * Class Constructor
     *
     * @param serializer an instance of serializer
     */
    @Inject
    public Worker(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void start() throws Exception {
        Logger.info("Worker verticle {} started.", Worker.class.getName());
        vertx.eventBus().consumer(Worker.class.getName(), this::invoke);
    }

    @Override
    public void stop() throws Exception {
        Logger.info("Worker verticle {} stopped.", Worker.class.getName());
    }

    /**
     * Invoke Tasks
     *
     * @param signal incoming signal
     */
    private void invoke(Message<String> signal) {
        Signal signalObj = this.serializer.unserialize(signal.body().toString(), Signal.class);

        if (signalObj.task == null) {
            return;
        }

        // Invoke com.clivern.reindeer.task.Log Task
        if (signalObj.task.equals(Log.class.getName())) {
            new Log().run(signalObj.args);
        }
    }
}
