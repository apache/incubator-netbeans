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
package org.netbeans.modules.cnd.repository.spi;

import org.netbeans.modules.cnd.repository.api.UnitDescriptor;
import org.openide.filesystems.FileSystem;

/**
 *
 */
public interface UnitDescriptorsMatcherImplementation {

    /**
     * @param descriptor1
     * @param descriptor2
     * @return 
     */
    public boolean matches(UnitDescriptor descriptor1, UnitDescriptor descriptor2);
    
    /**
     * For native projects: we should find all native projects
     * and find the project where old name is the same as <code>layerDescriptor</code>
     * and return the new unit descriptor depending of the new project name
     * @param targetFileSystem
     * @param sourceUnitDescriptor
     * @return 
     */
    public UnitDescriptor destinationDescriptor(FileSystem targetFileSystem, UnitDescriptor sourceUnitDescriptor);
    
    /**
     * For native projects: we should find all native projects
     * and find the project where old name is the same as <code>layerDescriptor</code>
     * and return the new unit descriptor depending of the new project name
     * @param targetFileSystem
     * @param destinationDescriptor
     * @return 
     */
    public UnitDescriptor sourceDescriptor(FileSystem targetFileSystem, UnitDescriptor destinationDescriptor);    
}
