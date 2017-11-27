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

package org.netbeans.qa.form;

import org.netbeans.jemmy.operators.*;

/**
 *Class implementing all necessary methods for handling "New File" NbDialog.
 * Most parts of code are generated by jelly tools.* 
 *
 * @author Jiri Vagner
 */
public class NewBeanFormSuperclassOperator extends JDialogOperator {

    /** Creates new NewFile that can handle it.
     */
    public NewBeanFormSuperclassOperator() {
        super("New File"); // NOI18N
    }

    private JLabelOperator _lblSteps;
    private JListOperator _lstSteps;
    public static final String ITEM_3 = "3."; // NOI18N
    private JLabelOperator _lblFormSuperclass;
    private JLabelOperator _lblSuperclass;
    private JTextFieldOperator _txtSuperclass;
    private JLabelOperator _lblWizardDescriptor$FixedHeightLabel;
    private JButtonOperator _btBack;
    private JButtonOperator _btNext;
    private JButtonOperator _btFinish;
    private JButtonOperator _btCancel;
    private JButtonOperator _btHelp;


    //******************************
    // Subcomponents definition part
    //******************************

    /** Tries to find "Steps" JLabel in this dialog.
     * @return JLabelOperator
     */
    public JLabelOperator lblSteps() {
        if (_lblSteps==null) {
            _lblSteps = new JLabelOperator(this, "Steps"); // NOI18N
        }
        return _lblSteps;
    }

    /** Tries to find null JList in this dialog.
     * @return JListOperator
     */
    public JListOperator lstSteps() {
        if (_lstSteps==null) {
            _lstSteps = new JListOperator(this);
        }
        return _lstSteps;
    }

    /** Tries to find "Form Superclass" JLabel in this dialog.
     * @return JLabelOperator
     */
    public JLabelOperator lblFormSuperclass() {
        if (_lblFormSuperclass==null) {
            _lblFormSuperclass = new JLabelOperator(this, "Form Superclass"); // NOI18N
        }
        return _lblFormSuperclass;
    }

    /** Tries to find "Superclass:" JLabel in this dialog.
     * @return JLabelOperator
     */
    public JLabelOperator lblSuperclass() {
        if (_lblSuperclass==null) {
            _lblSuperclass = new JLabelOperator(this, "Superclass:"); // NOI18N
        }
        return _lblSuperclass;
    }

    /** Tries to find null JTextField in this dialog.
     * @return JTextFieldOperator
     */
    public JTextFieldOperator txtSuperclass() {
        if (_txtSuperclass==null) {
            _txtSuperclass = new JTextFieldOperator(this);
        }
        return _txtSuperclass;
    }

    /** Tries to find " " WizardDescriptor$FixedHeightLabel in this dialog.
     * @return JLabelOperator
     */
    public JLabelOperator lblWizardDescriptor$FixedHeightLabel() {
        if (_lblWizardDescriptor$FixedHeightLabel==null) {
            _lblWizardDescriptor$FixedHeightLabel = new JLabelOperator(this, " ", 1); // NOI18N
        }
        return _lblWizardDescriptor$FixedHeightLabel;
    }

    /** Tries to find "< Back" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btBack() {
        if (_btBack==null) {
            _btBack = new JButtonOperator(this, "< Back"); // NOI18N
        }
        return _btBack;
    }

    /** Tries to find "Next >" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btNext() {
        if (_btNext==null) {
            _btNext = new JButtonOperator(this, "Next >"); // NOI18N
        }
        return _btNext;
    }

    /** Tries to find "Finish" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btFinish() {
        if (_btFinish==null) {
            _btFinish = new JButtonOperator(this, "Finish"); // NOI18N
        }
        return _btFinish;
    }

    /** Tries to find "Cancel" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btCancel() {
        if (_btCancel==null) {
            _btCancel = new JButtonOperator(this, "Cancel"); // NOI18N
        }
        return _btCancel;
    }

    /** Tries to find "Help" JButton in this dialog.
     * @return JButtonOperator
     */
    public JButtonOperator btHelp() {
        if (_btHelp==null) {
            _btHelp = new JButtonOperator(this, "Help"); // NOI18N
        }
        return _btHelp;
    }


    //****************************************
    // Low-level functionality definition part
    //****************************************

    /** gets text for txtSuperclass
     * @return String text
     */
    public String getSuperclass() {
        return txtSuperclass().getText();
    }

    /** sets text for txtSuperclass
     * @param text String text
     */
    public void setSuperclass(String text) {
        txtSuperclass().setText(text);
    }

    /** types text for txtSuperclass
     * @param text String text
     */
    public void typeSuperclass(String text) {
        txtSuperclass().typeText(text);
    }

    /** clicks on "< Back" JButton
     */
    public void back() {
        btBack().push();
    }

    /** clicks on "Next >" JButton
     */
    public void next() {
        btNext().push();
    }

    /** clicks on "Finish" JButton
     */
    public void finish() {
        btFinish().push();
    }

    /** clicks on "Cancel" JButton
     */
    public void cancel() {
        btCancel().push();
    }

    /** clicks on "Help" JButton
     */
    public void help() {
        btHelp().push();
    }
}

