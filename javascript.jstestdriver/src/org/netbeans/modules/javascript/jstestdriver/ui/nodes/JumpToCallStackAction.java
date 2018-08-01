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

package org.netbeans.modules.javascript.jstestdriver.ui.nodes;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.modules.javascript.jstestdriver.util.JsTestDriverUtils;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

public class JumpToCallStackAction extends AbstractAction {

    private final String[] stacktraces;
    private final Callback callback;


    @NbBundle.Messages("JumpToCallStackAction.name=&Go to Source")
    public JumpToCallStackAction(String[] stacktraces, Callback callback) {
        assert stacktraces != null;
        this.stacktraces = stacktraces;
        this.callback = callback;

        String name = Bundle.JumpToCallStackAction_name();
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (callback != null) {
            // iterate through stacktraces in order to find the correct test case location
            // if stacktraces.lenght == 1, user probably clicked on a stacktrace node
            // if stacktraces.lenght > 1, user probably clicked on a failed test method node
            for (String callstackFrameInfo : stacktraces) {
                Pair<File, int[]> pair = callback.parseLocation(callstackFrameInfo);
                if (pair != null) {
                    JsTestDriverUtils.openFile(pair.first(), pair.second()[0], pair.second()[1]);
                    return;
                }
            }
        }
    }

    //~ Inner classes

    public interface Callback {
        @CheckForNull
        Pair<File, int[]> parseLocation(String callStack);
    }

}
