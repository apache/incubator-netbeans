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
package org.netbeans.modules.websvc.rest.wizard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.modules.j2ee.persistence.wizard.fromdb.RelatedCMPHelper;
import org.netbeans.modules.j2ee.persistence.wizard.fromdb.RelatedCMPWizard;
import org.netbeans.modules.websvc.rest.codegen.EntityResourcesGenerator;
import org.netbeans.modules.websvc.rest.support.SourceGroupSupport;
import org.netbeans.modules.websvc.rest.wizard.fromdb.DatabaseResourceWizardIterator;
import org.netbeans.spi.java.project.support.ui.PackageView;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.util.ChangeSupport;

/**
 *
 * @author  Pavel Buzek
 */
public class EntityResourcesSetupPanelVisual extends JPanel 
        implements AbstractPanel.Settings, SourcePanel 
{

    private static final long serialVersionUID = -2519548265504277682L;
    private Project project;
    private WizardDescriptor wizard;
    private ChangeSupport changeSupport = new ChangeSupport(this);
    private boolean noController;

    /** Creates new form CrudSetupPanel */
    public EntityResourcesSetupPanelVisual(String name, boolean noController) {
        this.noController = noController;
        initComponents();
        setName(name);
        resourcePackageComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                changeSupport.fireChange();
            }
        });
        if (noController) {
            controllerPackageLabel.setVisible(false);
            controllerPackageComboBox.setVisible(false);
        } else {
            controllerPackageComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    changeSupport.fireChange();
                }
            });
        }
        ((JTextComponent) resourcePackageComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(
                new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSupport.fireChange();
            }
        });
    }
    
    public SourceGroup getSourceGroup() {
        return (SourceGroup) locationComboBox.getSelectedItem();
    }

    @Override
    public String getPackageName() {
        return ((JTextComponent) resourcePackageComboBox.getEditor().getEditorComponent()).getText();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectLabel = new javax.swing.JLabel();
        projectTextField = new javax.swing.JTextField();
        locationLabel = new javax.swing.JLabel();
        locationComboBox = new javax.swing.JComboBox();
        resourcePackageLabel = new javax.swing.JLabel();
        resourcePackageComboBox = new javax.swing.JComboBox();
        controllerPackageLabel = new javax.swing.JLabel();
        controllerPackageComboBox = new javax.swing.JComboBox();

        setPreferredSize(new java.awt.Dimension(450, 100));

        projectLabel.setLabelFor(projectTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectLabel, org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "LBL_Project")); // NOI18N

        projectTextField.setEditable(false);
        projectTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectTextFieldActionPerformed(evt);
            }
        });

        locationLabel.setLabelFor(locationComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(locationLabel, org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "LBL_SrcLocation")); // NOI18N

        locationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationComboBoxActionPerformed(evt);
            }
        });

        resourcePackageLabel.setLabelFor(resourcePackageComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(resourcePackageLabel, org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "LBL_Package")); // NOI18N

        resourcePackageComboBox.setEditable(true);
        resourcePackageComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                resourcePackageComboBoxPropertyChange(evt);
            }
        });

        controllerPackageLabel.setLabelFor(controllerPackageComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(controllerPackageLabel, org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "LBL_ControllerDir")); // NOI18N

        controllerPackageComboBox.setEditable(true);
        controllerPackageComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                controllerPackageComboBoxPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resourcePackageLabel)
                            .addComponent(controllerPackageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectLabel)
                            .addComponent(locationLabel))
                        .addGap(79, 79, 79)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(locationComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 320, Short.MAX_VALUE)
                    .addComponent(projectTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(resourcePackageComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 320, Short.MAX_VALUE)
                    .addComponent(controllerPackageComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 320, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectLabel)
                    .addComponent(projectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locationLabel)
                    .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resourcePackageLabel)
                    .addComponent(resourcePackageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(controllerPackageLabel)
                    .addComponent(controllerPackageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        projectLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "Project")); // NOI18N
        projectLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_Project")); // NOI18N
        projectTextField.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "Project")); // NOI18N
        projectTextField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_Project")); // NOI18N
        locationLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "Location")); // NOI18N
        locationLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_Location")); // NOI18N
        locationComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "Location")); // NOI18N
        locationComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_Location")); // NOI18N
        resourcePackageLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "ResourcePackage")); // NOI18N
        resourcePackageLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_ResourcePackage")); // NOI18N
        resourcePackageComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "ResourcePackage")); // NOI18N
        resourcePackageComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_ResourcePackage")); // NOI18N
        controllerPackageLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "ControllerPackage")); // NOI18N
        controllerPackageLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(EntityResourcesSetupPanelVisual.class, "DESC_ControllerPackage")); // NOI18N
        controllerPackageComboBox.getAccessibleContext().setAccessibleName(controllerPackageLabel.getAccessibleContext().getAccessibleName());
        controllerPackageComboBox.getAccessibleContext().setAccessibleDescription(controllerPackageLabel.getAccessibleContext().getAccessibleDescription());
    }// </editor-fold>//GEN-END:initComponents

    private void locationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationComboBoxActionPerformed
        locationChanged();
    }//GEN-LAST:event_locationComboBoxActionPerformed

    private void resourcePackageComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_resourcePackageComboBoxPropertyChange
        changeSupport.fireChange();
    }//GEN-LAST:event_resourcePackageComboBoxPropertyChange

    private void controllerPackageComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_controllerPackageComboBoxPropertyChange
        changeSupport.fireChange();
    }//GEN-LAST:event_controllerPackageComboBoxPropertyChange

    private void projectTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_projectTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox controllerPackageComboBox;
    private javax.swing.JLabel controllerPackageLabel;
    private javax.swing.JComboBox locationComboBox;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JLabel projectLabel;
    private javax.swing.JTextField projectTextField;
    private javax.swing.JComboBox resourcePackageComboBox;
    private javax.swing.JLabel resourcePackageLabel;
    // End of variables declaration//GEN-END:variables

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public boolean valid(WizardDescriptor wizard) {
        AbstractPanel.clearErrorMessage(wizard);

        if (getSourceGroup() == null) {
            AbstractPanel.setErrorMessage(wizard, "MSG_NoJavaSourceRoots");
        } else if (!Util.isValidPackageName(getResourcePackage())) {
            AbstractPanel.setErrorMessage(wizard, "MSG_InvalidResourcePackageName");
            return false;
        } else if (!noController && !Util.isValidPackageName(getControllerPackage())) {
            AbstractPanel.setErrorMessage(wizard, "MSG_InvalidControllerPackageName");
            return false;
        }
        return true;
    }

    public String getResourcePackage() {
        return ((JTextComponent) resourcePackageComboBox.getEditor().getEditorComponent()).getText();
    }

    public String getControllerPackage() {
        return ((JTextComponent) controllerPackageComboBox.getEditor().getEditorComponent()).getText();
    }

    private void setResourcePackage(String text) {
        ((JTextComponent) resourcePackageComboBox.getEditor().getEditorComponent()).setText(text);
    }

    private void setControllerPackage(String text) {
        ((JTextComponent) controllerPackageComboBox.getEditor().getEditorComponent()).setText(text);
    }

    private void locationChanged() {
        updateSourceGroupPackages();
        changeSupport.fireChange();
    }

    public void read(WizardDescriptor settings) {
        if (project != null) {
            return;
        }

        this.wizard = settings;

        project = Templates.getProject(settings);
        projectTextField.setText(ProjectUtils.getInformation(project).getDisplayName());

        SourceGroup[] sourceGroups = SourceGroupSupport.getJavaSourceGroups(project);
        SourceGroupUISupport.connect(locationComboBox, sourceGroups);

        resourcePackageComboBox.setRenderer(PackageView.listRenderer());
        if (!noController) {
            controllerPackageComboBox.setRenderer(PackageView.listRenderer());
        }
        updateSourceGroupPackages();

        FileObject targetFolder = Templates.getTargetFolder(settings);
        SourceGroup targetSourceGroup = null;
        String targetPackage = "";

        // use package name for entities as a base package name for services:
        RelatedCMPHelper helper = (RelatedCMPHelper)wizard.getProperty(
                DatabaseResourceWizardIterator.PROP_HELPER);
        if (helper != null) {
            targetPackage = helper.getPackageName();
        }

        if (targetFolder != null && targetPackage.isEmpty()) {
            // set default source group and package cf. targetFolder
            targetSourceGroup = SourceGroupSupport.findSourceGroupForFile(sourceGroups, targetFolder);
            if (targetSourceGroup != null) {
                locationComboBox.setSelectedItem(targetSourceGroup);
                targetPackage = SourceGroupSupport.getPackageForFolder(targetSourceGroup, targetFolder);
            }
        }

        targetPackage = (targetPackage.length() == 0) ? "" : targetPackage + ".";
        String resourcePackage = targetPackage + EntityResourcesGenerator.RESOURCE_FOLDER;
        setResourcePackage(resourcePackage);
        addComboBoxListener(resourcePackageComboBox);

        if (!noController) {
            String controllerPackage = targetPackage + EntityResourcesGenerator.CONTROLLER_FOLDER;
            setControllerPackage(controllerPackage);
            addComboBoxListener(controllerPackageComboBox);
        }
    }

    public void store(WizardDescriptor settings) {
        settings.putProperty(WizardProperties.RESOURCE_PACKAGE, getResourcePackage());
        settings.putProperty(WizardProperties.CONTROLLER_PACKAGE, getControllerPackage());
        settings.putProperty(WizardProperties.TARGET_SRC_ROOT, getSourceGroup().getRootFolder());
    }

    private void addComboBoxListener(JComboBox comboBox) {
        JTextComponent text = ((JTextComponent) comboBox.getEditor().getEditorComponent());
        text.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent event) {
                //updatePreview();
            }
        });
    }

    private void updateSourceGroupPackages() {
        SourceGroup sourceGroup = (SourceGroup) locationComboBox.getSelectedItem();
        if (sourceGroup != null) {
            ComboBoxModel model = PackageView.createListView(sourceGroup);
            if (model.getSize() > 0) {
                model.setSelectedItem(model.getElementAt(0));
            }
            resourcePackageComboBox.setModel(model);
            model = PackageView.createListView(sourceGroup);
            if (model.getSize() > 0) {
                model.setSelectedItem(model.getElementAt(0));
            }
            controllerPackageComboBox.setModel(model);
        }
    }

}
