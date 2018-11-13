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

package org.netbeans.modules.maven.junit.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.api.extexecution.print.LineConvertors;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gsf.testrunner.api.Testcase;
import org.netbeans.modules.gsf.testrunner.api.TestMethodNodeAction;
import org.netbeans.modules.junit.ui.api.JUnitTestMethodNode;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.SingleMethod;
import static org.netbeans.spi.project.SingleMethod.COMMAND_DEBUG_SINGLE_METHOD;
import static org.netbeans.spi.project.SingleMethod.COMMAND_RUN_SINGLE_METHOD;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

/**
 * mkleint: copied from junit module
 *
 * @author answer
 */
public class MavenJUnitTestMethodNode extends JUnitTestMethodNode {

    public MavenJUnitTestMethodNode(Testcase testcase, Project project, Lookup lookup, String projectType, String testingFramework) {
        super(testcase, project, lookup, projectType, testingFramework);
    }

    public MavenJUnitTestMethodNode(Testcase testcase, Project project, String projectType, String testingFramework) {
        super(testcase, project, projectType, testingFramework);
    }

    @Messages({
        "LBL_RerunTest=Run Again",
        "LBL_DebugTest=Debug"
    })
    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<Action>();
        Action preferred = getPreferredAction();
        if (preferred != null) {
            actions.add(preferred);
        }
        FileObject testFO = getTestcaseFileObject();                            
        if (testFO != null){
            Project suiteProject = FileOwnerQuery.getOwner(testFO);
            if (suiteProject != null) {
                ActionProvider actionProvider = suiteProject.getLookup().lookup(ActionProvider.class);
                if (actionProvider != null) {
                    boolean runSupported = false;
                    boolean debugSupported = false;
                    for (String action : actionProvider.getSupportedActions()) {
                        if (!runSupported && action.equals(COMMAND_RUN_SINGLE_METHOD)) {
                            runSupported = true;
                            if (debugSupported) {
                                break;
                            }
                        }
                        if (!debugSupported && action.equals(COMMAND_DEBUG_SINGLE_METHOD)) {
                            debugSupported = true;
                            if (runSupported) {
                                break;
                            }
                        }
                    }

                    SingleMethod methodSpec = new SingleMethod(testFO, testcase.getClassName(), testcase.getName());
                    Lookup nodeContext = Lookups.singleton(methodSpec);
                    if (runSupported && actionProvider.isActionEnabled(COMMAND_RUN_SINGLE_METHOD,
                            nodeContext)) {
                        actions.add(new TestMethodNodeAction(actionProvider,
                                nodeContext,
                                COMMAND_RUN_SINGLE_METHOD,
                                Bundle.LBL_RerunTest()));
                    }
                    if (debugSupported && actionProvider.isActionEnabled(COMMAND_DEBUG_SINGLE_METHOD,
                            nodeContext)) {
                        actions.add(new TestMethodNodeAction(actionProvider,
                                nodeContext,
                                COMMAND_DEBUG_SINGLE_METHOD,
                                Bundle.LBL_DebugTest()));
                    }
                }
            }
        }
        actions.addAll(Arrays.asList(super.getActions(context)));

        return actions.toArray(new Action[actions.size()]);
    }

    public FileObject getTestcaseFileObject() {
        LineConvertors.FileLocator fileLocator = getProject().getLookup().lookup(LineConvertors.FileLocator.class);
        if(fileLocator == null) {
            Logger.getLogger(MavenJUnitTestMethodNode.class.getName()).log(Level.WARNING, "no LineConvertors.FileLocator available for project {0}", getProject().getProjectDirectory());
        }
        if(testcase == null) {
            Logger.getLogger(MavenJUnitTestMethodNode.class.getName()).log(Level.WARNING, "null testcase in MavenJUnitTestMethodNode for project {0}", getProject().getProjectDirectory());            
        }
        String location = testcase != null ? testcase.getLocation() : null;
        return fileLocator != null && location != null ? fileLocator.find(location) : null;
    }
}
