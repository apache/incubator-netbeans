/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
package org.netbeans.core.multiview.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.core.multiview.Splitable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Tools",
        id = "org.netbeans.core.multiview.ClearSplitAction"
)
@ActionRegistration(
        displayName = "#LBL_ClearSplitAction"
)
@ActionReference(path = "Shortcuts", name = "DOS-C")
@NbBundle.Messages({
    "LBL_ClearSplitAction=&Clear",
    "LBL_ValueClearSplit=clearSplit"
})
public final class ClearSplitAction extends AbstractAction {

    public void initTopComponent(TopComponent tc) {
        putValue(Action.NAME, Bundle.LBL_ClearSplitAction());
        //hack to insert extra actions into JDev's popup menu
        putValue("_nb_action_id_", Bundle.LBL_ValueClearSplit()); //NOI18N
        if (tc instanceof Splitable) {
            setEnabled(((Splitable) tc).getSplitOrientation() != -1);
        } else {
            setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        final TopComponent tc = WindowManager.getDefault().getRegistry().getActivated();

        if (tc != null && ((Splitable) tc).getSplitOrientation() != -1) {
            clearSplit(tc, -1);
        }
    }

    public static void clearSplit(TopComponent tc, int elementToActivate) {
        if (tc instanceof Splitable) {
            TopComponent original = ((Splitable) tc).clearSplit(elementToActivate);

            original.open();
            original.requestActive();
            original.invalidate();
            original.revalidate();
            original.repaint();
            original.requestFocusInWindow();
        }
    }
}
