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

package org.netbeans.modules.mercurial.ui.wizards;

public final class ClonePathsPanel extends javax.swing.JPanel {
    
    /** Creates new form CloneVisualPanel2 */
    public ClonePathsPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        defaultPullPathLabel = new javax.swing.JLabel();
        defaultPushPathLabel = new javax.swing.JLabel();
        defaultValuesButton = new javax.swing.JButton();

        setName(org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "pathsPanel.Name")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "defaultLabel.Name")); // NOI18N

        defaultPullPathLabel.setLabelFor(defaultPullPathField);
        org.openide.awt.Mnemonics.setLocalizedText(defaultPullPathLabel, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "defaultPullLabel.Name")); // NOI18N

        defaultPullPathField.setColumns(30);
        defaultPullPathField.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(changePullPathButton, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "changePullPushPath.Name")); // NOI18N
        changePullPathButton.setDefaultCapable(false);

        defaultPushPathLabel.setLabelFor(defaultPushPathField);
        org.openide.awt.Mnemonics.setLocalizedText(defaultPushPathLabel, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "defaultPushLabel.Name")); // NOI18N

        defaultPushPathField.setColumns(30);
        defaultPushPathField.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(changePushPathButton, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "changePullPushPath.Name")); // NOI18N
        changePushPathButton.setDefaultCapable(false);

        org.openide.awt.Mnemonics.setLocalizedText(defaultValuesButton, org.openide.util.NbBundle.getMessage(ClonePathsPanel.class, "setDefaultValues.Name")); // NOI18N
        defaultValuesButton.setDefaultCapable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defaultPullPathLabel)
                    .addComponent(defaultPushPathLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defaultPullPathField)
                    .addComponent(defaultPushPathField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changePushPathButton)
                    .addComponent(changePullPathButton)))
            .addComponent(defaultValuesButton)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultPullPathLabel)
                    .addComponent(defaultPullPathField)
                    .addComponent(changePullPathButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultPushPathLabel)
                    .addComponent(defaultPushPathField)
                    .addComponent(changePushPathButton))
                .addGap(18, 18, 18)
                .addComponent(defaultValuesButton))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JButton changePullPathButton = new javax.swing.JButton();
    final javax.swing.JButton changePushPathButton = new javax.swing.JButton();
    final javax.swing.JTextField defaultPullPathField = new javax.swing.JTextField();
    private javax.swing.JLabel defaultPullPathLabel;
    final javax.swing.JTextField defaultPushPathField = new javax.swing.JTextField();
    private javax.swing.JLabel defaultPushPathLabel;
    javax.swing.JButton defaultValuesButton;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
}

