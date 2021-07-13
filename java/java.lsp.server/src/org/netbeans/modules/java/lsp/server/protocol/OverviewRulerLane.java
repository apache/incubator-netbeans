/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.java.lsp.server.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Martin Entlicher
 */
public enum OverviewRulerLane {

    Center(2),
    Full(7),
    Left(1),
    Right(4);
    
    private final int intValue;

    OverviewRulerLane(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    private static final Map<Integer, OverviewRulerLane> lookup = new HashMap<>();

    static {
        for (OverviewRulerLane value : OverviewRulerLane.values()) {
            lookup.put(value.getIntValue(), value);
        }
    }

    public static OverviewRulerLane get(Integer intValue) {
        return lookup.get(intValue);
    }

}
