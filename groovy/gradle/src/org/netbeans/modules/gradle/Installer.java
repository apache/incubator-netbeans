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

package org.netbeans.modules.gradle;

import org.openide.modules.ModuleInfo;
import org.openide.modules.ModuleInstall;
import org.openide.modules.Modules;
import org.openide.util.NbBundle.Messages;

public final class Installer extends ModuleInstall {

    private static final String CONFLICTING_PROJECT = "org.netbeans.gradle.project";
    
    @Override
    @Messages(
            "MSG_MODULE_CONFLICT=Gradle Projects module conflicts with the Gradle Support module.\n"
                    + "In order to be able to activate the Gradle Project module (and modules depending on that):\n"
                    + "Please go to Tools > Plugins > Installed, search for Gradle Support, disable the plugin\n"
                    + "then restart NetBeans and enable Gradle Projects plugin."
    )
    public void validate() throws IllegalStateException {
        ModuleInfo module = Modules.getDefault().findCodeNameBase(CONFLICTING_PROJECT);
        if ((module != null) && module.isEnabled()) {
            throw new IllegalStateException(Bundle.MSG_MODULE_CONFLICT());
        }
    }
}
