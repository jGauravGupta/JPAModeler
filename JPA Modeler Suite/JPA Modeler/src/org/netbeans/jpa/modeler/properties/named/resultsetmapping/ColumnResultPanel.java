/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.properties.named.resultsetmapping;

import javax.swing.JOptionPane;
import org.netbeans.jpa.modeler.spec.ColumnResult;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.internal.EntityComponent;

public class ColumnResultPanel extends EntityComponent<ColumnResult> {

    public ColumnResultPanel() {
        super("", true);
        initComponents();
    }

    @Override
    public void init() {
    }

    @Override
    public void createEntity(Class<? extends Entity> entityWrapperType) {
        this.setTitle("Create new Column Result");
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[4]));
        }
        name_TextField.setText("");
        class_TextField.setText("");
    }

    @Override
    public void updateEntity(Entity<ColumnResult> entityValue) {
        this.setTitle("Update Column Result");
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            ColumnResult columnResult = (ColumnResult) row[0];
            name_TextField.setText(columnResult.getName());
            class_TextField.setText(columnResult.getClazz());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        root_LayeredPane = new javax.swing.JLayeredPane();
        name_LayeredPane = new javax.swing.JLayeredPane();
        name_Label = new javax.swing.JLabel();
        name_TextField = new javax.swing.JTextField();
        class_LayeredPane = new javax.swing.JLayeredPane();
        class_Label = new javax.swing.JLabel();
        class_TextField = new javax.swing.JTextField();
        action_jLayeredPane = new javax.swing.JLayeredPane();
        save_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.openide.awt.Mnemonics.setLocalizedText(name_Label, org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.name_Label.text")); // NOI18N

        name_TextField.setText(org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.name_TextField.text")); // NOI18N

        javax.swing.GroupLayout name_LayeredPaneLayout = new javax.swing.GroupLayout(name_LayeredPane);
        name_LayeredPane.setLayout(name_LayeredPaneLayout);
        name_LayeredPaneLayout.setHorizontalGroup(
            name_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(name_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        name_LayeredPaneLayout.setVerticalGroup(
            name_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(name_LayeredPaneLayout.createSequentialGroup()
                .addGroup(name_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        name_LayeredPane.setLayer(name_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        name_LayeredPane.setLayer(name_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(class_Label, org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.class_Label.text")); // NOI18N

        class_TextField.setText(org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.class_TextField.text")); // NOI18N

        javax.swing.GroupLayout class_LayeredPaneLayout = new javax.swing.GroupLayout(class_LayeredPane);
        class_LayeredPane.setLayout(class_LayeredPaneLayout);
        class_LayeredPaneLayout.setHorizontalGroup(
            class_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(class_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(class_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(class_TextField)
                .addContainerGap())
        );
        class_LayeredPaneLayout.setVerticalGroup(
            class_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(class_LayeredPaneLayout.createSequentialGroup()
                .addGroup(class_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(class_Label)
                    .addComponent(class_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        class_LayeredPane.setLayer(class_Label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        class_LayeredPane.setLayer(class_TextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.openide.awt.Mnemonics.setLocalizedText(save_Button, org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.save_Button.text")); // NOI18N
        save_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.save_Button.toolTipText")); // NOI18N
        save_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ButtonActionPerformed(evt);
            }
        });
        action_jLayeredPane.add(save_Button);
        save_Button.setBounds(0, 0, 70, 23);

        org.openide.awt.Mnemonics.setLocalizedText(cancel_Button, org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.cancel_Button.text")); // NOI18N
        cancel_Button.setToolTipText(org.openide.util.NbBundle.getMessage(ColumnResultPanel.class, "ColumnResultPanel.cancel_Button.toolTipText")); // NOI18N
        cancel_Button.setPreferredSize(new java.awt.Dimension(60, 23));
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });
        action_jLayeredPane.add(cancel_Button);
        cancel_Button.setBounds(80, 0, 70, 23);

        javax.swing.GroupLayout root_LayeredPaneLayout = new javax.swing.GroupLayout(root_LayeredPane);
        root_LayeredPane.setLayout(root_LayeredPaneLayout);
        root_LayeredPaneLayout.setHorizontalGroup(
            root_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(root_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(root_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(name_LayeredPane)
                    .addComponent(class_LayeredPane))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, root_LayeredPaneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(action_jLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        root_LayeredPaneLayout.setVerticalGroup(
            root_LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(root_LayeredPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(name_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(class_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(action_jLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        root_LayeredPane.setLayer(name_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        root_LayeredPane.setLayer(class_LayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        root_LayeredPane.setLayer(action_jLayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(root_LayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(root_LayeredPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateField() {
        if (this.name_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Name field can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        if (this.class_TextField.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Value field can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }//I18n
        return true;
    }

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ButtonActionPerformed
        if (!validateField()) {
            return;
        }
        ColumnResult columnResult = null;
        if (this.getEntity().getClass() == RowValue.class) {
            Object[] row = ((RowValue) this.getEntity()).getRow();
            if (row[0] == null) {
                columnResult = new ColumnResult();
            } else {
                columnResult = (ColumnResult) row[0];
            }
        }
        columnResult.setName(name_TextField.getText());
        columnResult.setClazz(class_TextField.getText());
        if (this.getEntity().getClass() == RowValue.class) {
            Object[] row = ((RowValue) this.getEntity()).getRow();
            row[0] = columnResult;
            row[1] = columnResult.getName();
            row[2] = columnResult.getClazz();
        }
        saveActionPerformed(evt);
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane action_jLayeredPane;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JLabel class_Label;
    private javax.swing.JLayeredPane class_LayeredPane;
    private javax.swing.JTextField class_TextField;
    private javax.swing.JLabel name_Label;
    private javax.swing.JLayeredPane name_LayeredPane;
    private javax.swing.JTextField name_TextField;
    private javax.swing.JLayeredPane root_LayeredPane;
    private javax.swing.JButton save_Button;
    // End of variables declaration//GEN-END:variables
}
