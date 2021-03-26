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
package org.netbeans.modules.java.source.remote.projects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;

public class EditorProjectPanel extends javax.swing.JPanel implements ActionListener {

    private final Project prj;

    public EditorProjectPanel(Lookup context) {
        initComponents();
        prj = context.lookup(Project.class);
        runOnTarget.setSelected(RemotePlatformImpl.isEnabled(prj));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        runOnTarget = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(runOnTarget, org.openide.util.NbBundle.getMessage(EditorProjectPanel.class, "EditorProjectPanel.runOnTarget.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(runOnTarget)
                .addGap(0, 63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(runOnTarget)
                .addGap(0, 277, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox runOnTarget;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            public @Override void run() {
                RemotePlatformImpl.setEnabled(prj, runOnTarget.isSelected());
            }
        });
    }
    
    @Messages("LBL_CategoryEditor=Editor")
    public static class Factory implements ProjectCustomizer.CompositeCategoryProvider {
 
        private static final String CATEGORY_EDITOR = "Editor"; // NOI18N

        public ProjectCustomizer.Category createCategory(Lookup context) {
            return context.lookup(Project.class) == null ? null : ProjectCustomizer.Category.create(
                    CATEGORY_EDITOR, 
                    Bundle.LBL_CategoryEditor(), //NOI18N
                    null);
        }

        public JComponent createComponent(ProjectCustomizer.Category category, Lookup context) {
            EditorProjectPanel customizerPanel = new EditorProjectPanel(context);
            category.setStoreListener(customizerPanel);
            return customizerPanel;
        }
    }

}
