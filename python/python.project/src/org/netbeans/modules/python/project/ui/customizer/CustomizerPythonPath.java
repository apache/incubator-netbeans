/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CustomizerPythonPath.java
 *
 * Created on Sep 7, 2008, 6:56:16 PM
 */
package org.netbeans.modules.python.project.ui.customizer;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

import org.netbeans.modules.python.api.PlatformComponentFactory;
import org.netbeans.modules.python.api.PythonPlatform;
import org.netbeans.modules.python.api.PythonPlatformManager;
import org.netbeans.modules.python.core.ui.models.PathListModel;

import org.netbeans.modules.python.project.ui.Utils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.InstanceDataObject;
import org.openide.util.Exceptions;
import org.openide.util.actions.CallableSystemAction;

public class CustomizerPythonPath extends javax.swing.JPanel {
    private final PythonProjectProperties uiProperties;
    private PlatformComponentFactory.PlatformChangeListener platformListener;

    /** Creates new form CustomizerPythonPath */
    public CustomizerPythonPath(PythonProjectProperties properties) {
        this.uiProperties = properties;
        initComponents();
        pythonPathModel.setModel(uiProperties.getPythonPath());
        final PythonPlatformManager manager = PythonPlatformManager.getInstance();
        String pid = uiProperties.getActivePlatformId();
        if (pid == null) {
            pid = manager.getDefaultPlatform();
        }
        final PythonPlatform activePlatform = manager.getPlatform(pid);
        if (activePlatform != null) {
            platforms.setSelectedItem(activePlatform);
        }
    }

    public 
    @Override
    void addNotify() {
        super.addNotify();
        platformListener = new PlatformComponentFactory.PlatformChangeListener() {
            @Override
            public void platformChanged() {
                PythonPlatform platform = (PythonPlatform)platforms.getSelectedItem();
                if (platform != null) {
                    uiProperties.setActivePlatformId(platform.getId());
                    // When we support configurations:
                    //configs.get(getSelectedConfig()).put(PythonProjectProperties.ACTIVE_PLATFORM, platform.getName());
                }
            }
        };
        PlatformComponentFactory.addPlatformChangeListener(platforms, platformListener);
    }

    public 
    @Override
    void removeNotify() {
        PlatformComponentFactory.removePlatformChangeListener(platforms, platformListener);
        super.removeNotify();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        pythonPath = new javax.swing.JList();
        addPythonPath = new javax.swing.JButton();
        removePythonPath = new javax.swing.JButton();
        moveUpPythonPath = new javax.swing.JButton();
        moveDownPythonPath = new javax.swing.JButton();
        manage = new javax.swing.JButton();
        platforms = org.netbeans.modules.python.api.PlatformComponentFactory.getPythonPlatformsComboxBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        pythonPath.setModel(pythonPathModel);
        pythonPath.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(pythonPath);

        addPythonPath.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.addPythonPath.text_1")); // NOI18N
        addPythonPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPythonPathActionPerformed(evt);
            }
        });

        removePythonPath.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.removePythonPath.text_1")); // NOI18N
        removePythonPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePythonPathActionPerformed(evt);
            }
        });

        moveUpPythonPath.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.moveUpPythonPath.text_1")); // NOI18N
        moveUpPythonPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpPythonPathActionPerformed(evt);
            }
        });

        moveDownPythonPath.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.moveDownPythonPath.text_1")); // NOI18N
        moveDownPythonPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownPythonPathActionPerformed(evt);
            }
        });

        manage.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.manage.text")); // NOI18N
        manage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageActionPerformed(evt);
            }
        });

        platforms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                platformsActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(CustomizerPythonPath.class, "CustomizerPythonPath.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(platforms, 0, 244, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manage)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moveDownPythonPath, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(moveUpPythonPath, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(removePythonPath, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(addPythonPath, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(platforms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(manage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addPythonPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removePythonPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveUpPythonPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveDownPythonPath))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addPythonPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPythonPathActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setFileHidingEnabled(false);
        fc.setDialogTitle("Select Python Egg or Python Lib Directory");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            for (File file : files) {
                String cmd = file.getAbsolutePath();
                pythonPathModel.add(cmd);
            }
        }

}//GEN-LAST:event_addPythonPathActionPerformed

    private void removePythonPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePythonPathActionPerformed
        int selectedIndex = pythonPath.getSelectedIndex();
        if (selectedIndex != -1) {
            pythonPathModel.remove(selectedIndex);
        }
}//GEN-LAST:event_removePythonPathActionPerformed

    private void moveUpPythonPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpPythonPathActionPerformed
        int selectedIndex = pythonPath.getSelectedIndex();
        if (selectedIndex != -1) {
            pythonPathModel.moveUp(selectedIndex);
        }
}//GEN-LAST:event_moveUpPythonPathActionPerformed

    private void moveDownPythonPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownPythonPathActionPerformed
        pythonPathModel.moveDown(pythonPath.getSelectedIndex());
}//GEN-LAST:event_moveDownPythonPathActionPerformed

    private void manageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageActionPerformed
        //Workaround, Needs an API to display platform customizer
        final FileObject fo = FileUtil.getConfigFile("Actions/Python/org-netbeans-modules-python-platform-PythonManagerAction.instance");  //NOI18N
        if (fo != null) {
            try {
                InstanceDataObject ido = (InstanceDataObject)DataObject.find(fo);
                CallableSystemAction action = (CallableSystemAction)ido.instanceCreate();
                action.performAction();
                platforms.setModel(Utils.createPlatformModel()); //Currentl the PythonManager doesn't fire events, we need to replace model.
            } catch (IOException | ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
}//GEN-LAST:event_manageActionPerformed

    private void platformsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_platformsActionPerformed
        PythonPlatform selectedPlatform = (PythonPlatform)platforms.getSelectedItem();
        if (selectedPlatform != null) {
            uiProperties.setActivePlatformId(selectedPlatform.getId());
        }
    }//GEN-LAST:event_platformsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPythonPath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton manage;
    private javax.swing.JButton moveDownPythonPath;
    private javax.swing.JButton moveUpPythonPath;
    private javax.swing.JComboBox platforms;
    private javax.swing.JList pythonPath;
    private javax.swing.JButton removePythonPath;
    // End of variables declaration//GEN-END:variables
    private PathListModel pythonPathModel = new PathListModel();
}
