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
package org.netbeans.modules.php.zend.ui.actions;

import org.netbeans.modules.csl.api.UiUtils;
import org.netbeans.modules.php.api.editor.EditorSupport;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.modules.php.api.editor.PhpType;
import org.netbeans.modules.php.spi.framework.actions.GoToActionAction;
import org.netbeans.modules.php.zend.util.ZendUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

public final class ZendGoToActionAction extends GoToActionAction {
    private static final long serialVersionUID = 89756313874L;

    private final FileObject fo;

    public ZendGoToActionAction(FileObject fo) {
        assert ZendUtils.isViewWithAction(fo);
        this.fo = fo;
    }

    @Override
    public boolean goToAction() {
        FileObject action = ZendUtils.getAction(fo);
        if (action != null) {
            UiUtils.open(action, getActionMethodOffset(action));
            return true;
        }
        return false;
    }

    private int getActionMethodOffset(FileObject action) {
        String actionMethodName = ZendUtils.getActionName(fo);
        EditorSupport editorSupport = Lookup.getDefault().lookup(EditorSupport.class);
        for (PhpClass phpClass : editorSupport.getClasses(action)) {
            if (phpClass.getName().endsWith(ZendUtils.CONTROLLER_CLASS_SUFFIX)) {
                if (actionMethodName != null) {
                    for (PhpType.Method method : phpClass.getMethods()) {
                        if (actionMethodName.equals(method.getName())) {
                            return method.getOffset();
                        }
                    }
                }
                return phpClass.getOffset();
            }
        }
        return DEFAULT_OFFSET;
    }
}
