/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.mode.repository.cluster.consul.lock;

import com.ecwid.consul.v1.ConsulClient;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.mode.repository.cluster.consul.props.ConsulProperties;
import org.apache.shardingsphere.mode.repository.cluster.lock.DistributedLock;
import org.apache.shardingsphere.mode.repository.cluster.lock.DistributedLockHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Consul distributed lock holder.
 */
@RequiredArgsConstructor
public class ConsulDistributedLockHolder implements DistributedLockHolder {
    
    private final Map<String, ConsulDistributedLock> locks = new ConcurrentHashMap<>();
    
    private final ConsulClient client;
    
    private final ConsulProperties props;
    
    @Override
    public DistributedLock getDistributedLock(final String lockKey) {
        ConsulDistributedLock result = locks.get(lockKey);
        if (null == result) {
            result = new ConsulDistributedLock(lockKey, client, props);
            locks.put(lockKey, result);
        }
        return result;
    }
}
