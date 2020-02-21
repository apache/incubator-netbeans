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

package org.netbeans.modules.mercurial.remote.ui.status;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.mercurial.remote.util.HgUtils;
import org.netbeans.modules.remotefs.versioning.api.VCSFileProxySupport;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.openide.cookies.EditCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.cookies.ViewCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 * Opens the file under {@link SyncFileNode} in editor.
 *
 * 
 */
public class OpenInEditorAction extends AbstractAction {

    public OpenInEditorAction() {
        super(NbBundle.getBundle(OpenInEditorAction.class).getString("CTL_Synchronize_Popup_OpenInEditor")); // NOI18N
        setEnabled(isActionEnabled());
    }

    private boolean isActionEnabled() {
        for (VCSFileProxy file : HgUtils.getCurrentContext(null).getRootFiles()) {
            if (VCSFileProxySupport.canRead(file)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (VCSFileProxy file : HgUtils.getCurrentContext(null).getRootFiles()) {
            FileObject fo = file.toFileObject();
            if (fo != null) {
                try {
                    openDataObjectByCookie(DataObject.find(fo));
                } catch (DataObjectNotFoundException ex) {
                    // ignore this error and try to open other files too
                }
            }
        }
    }
    
    private boolean openDataObjectByCookie(DataObject dataObject) {
        Node.Cookie cookie;
 
        if ((cookie = dataObject.getLookup().lookup(EditorCookie.Observable.class)) != null) {
            return openByCookie(cookie, EditorCookie.Observable.class);
        }
        if ((cookie = dataObject.getLookup().lookup(EditorCookie.class)) != null) {
            return openByCookie(cookie, EditorCookie.class);
        }
        if ((cookie = dataObject.getLookup().lookup(OpenCookie.class)) != null) {
            return openByCookie(cookie, OpenCookie.class);
        }
        if ((cookie = dataObject.getLookup().lookup(EditCookie.class)) != null) {
            return openByCookie(cookie, EditCookie.class);
        }
        if ((cookie = dataObject.getLookup().lookup(ViewCookie.class)) != null) {
            return openByCookie(cookie, ViewCookie.class);
        }
        
        return false;
    }
    
    private boolean openByCookie(Node.Cookie cookie, Class cookieClass) {
        if ((cookieClass == EditorCookie.class)
                || (cookieClass == EditorCookie.Observable.class)) {
            ((EditorCookie) cookie).open();
        } else if (cookieClass == OpenCookie.class) {
            ((OpenCookie) cookie).open();
        } else if (cookieClass == EditCookie.class) {
            ((EditCookie) cookie).edit();
        } else if (cookieClass == ViewCookie.class) {
            ((ViewCookie) cookie).view();
        } else {
            throw new IllegalArgumentException("Reopen #58766: " + cookieClass); // NOI18N
        }
        return true;
    }
}
