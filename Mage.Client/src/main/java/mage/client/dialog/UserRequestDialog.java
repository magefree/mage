package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.util.GUISizeHelper;
import mage.constants.PlayerAction;
import mage.view.UserRequestMessage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;

/**
 * App GUI: global window message with additional action to choose (example: close the app)
 * Can be used in any places (in games, in app, etc)
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UserRequestDialog extends MageDialog {

    private UserRequestMessage userRequestMessage;

    public UserRequestDialog() {
        initComponents();
        setGUISize();
    }

    private void setGUISize() {
        Font font = GUISizeHelper.dialogFont;
        lblText.setFont(font);
        lblText.setMaximumSize(new Dimension(300 + font.getSize() * 15, 200 + font.getSize() * 5));
        lblText.setMinimumSize(new Dimension(300 + font.getSize() * 15, 20 + font.getSize() * 5));
        lblText.setPreferredSize(new Dimension(300 + font.getSize() * 15, 20 + font.getSize() * 5));
        btn1.setFont(font);
        btn1.setMinimumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn1.setMaximumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn1.setPreferredSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn2.setFont(font);
        btn2.setMinimumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn2.setMaximumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn2.setPreferredSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn3.setFont(font);
        btn3.setMinimumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn3.setMaximumSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        btn3.setPreferredSize(new Dimension(50 + 4 * font.getSize(), 2 * font.getSize() + 10));
        JComponent c = ((BasicInternalFrameUI) this.getUI()).getNorthPane();
        c.setMinimumSize(new Dimension(c.getMinimumSize().width, font.getSize() + 10));
        c.setMaximumSize(new Dimension(c.getMaximumSize().width, font.getSize() + 10));
        c.setPreferredSize(new Dimension(c.getPreferredSize().width, font.getSize() + 10));
        c.setFont(font);
    }

    public void showDialog(UserRequestMessage userRequestMessage) {
        this.userRequestMessage = userRequestMessage;
        this.setTitle(userRequestMessage.getTitle());
        String text = "<html><p style=\"text-align:center; margin-left:10px; margin-right:10px\">" + userRequestMessage.getMessage() + "</p></html>";
        this.lblText.setText(text);
        if (userRequestMessage.getButton1Text() != null) {
            this.btn1.setText(userRequestMessage.getButton1Text());
            this.btn1.setFocusable(false);
        } else {
            this.btn1.setVisible(false);
        }
        if (userRequestMessage.getButton2Text() != null) {
            this.btn2.setText(userRequestMessage.getButton2Text());
            this.btn2.setFocusable(false);
        } else {
            this.btn2.setVisible(false);
        }
        if (userRequestMessage.getButton3Text() != null) {
            this.btn3.setText(userRequestMessage.getButton3Text());
            this.btn3.setFocusable(false);
        } else {
            this.btn3.setVisible(false);
        }

        // improved auto-size
        // height looks bad, so change only width
        this.pack();
        Dimension newPreferedSize = new Dimension(this.getPreferredSize());
        newPreferedSize.setSize(
                newPreferedSize.width * userRequestMessage.getWindowSizeRatio(),
                newPreferedSize.height/* * userRequestMessage.getWindowSizeRatio()*/
        );
        this.setPreferredSize(newPreferedSize);

        this.pack();
        this.revalidate();
        this.repaint();
        this.setModal(true);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblText = new javax.swing.JLabel();
        btn3 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();

        setResizable(true);
        setTitle("UserRequestMessage");
        setModal(true);

        lblText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblText.setText("message to the user");
        lblText.setMaximumSize(new java.awt.Dimension(1000, 500));
        lblText.setMinimumSize(new java.awt.Dimension(400, 60));
        lblText.setPreferredSize(new java.awt.Dimension(400, 60));

        btn3.setText("btn3");
        btn3.setMaximumSize(new java.awt.Dimension(150, 50));
        btn3.setMinimumSize(new java.awt.Dimension(75, 25));
        btn3.setPreferredSize(new java.awt.Dimension(150, 50));
        btn3.addActionListener(evt -> btn3ActionPerformed(evt));

        btn2.setText("btn2");
        btn2.setMaximumSize(new java.awt.Dimension(150, 50));
        btn2.setMinimumSize(new java.awt.Dimension(75, 25));
        btn2.setPreferredSize(new java.awt.Dimension(150, 50));
        btn2.addActionListener(evt -> btn2ActionPerformed(evt));

        btn1.setText("btn1");
        btn1.setMaximumSize(new java.awt.Dimension(150, 50));
        btn1.setMinimumSize(new java.awt.Dimension(75, 25));
        btn1.setPreferredSize(new java.awt.Dimension(150, 50));
        btn1.addActionListener(evt -> btn1ActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        if (userRequestMessage.getButton1Action() != null) {
            sendUserReplay(userRequestMessage.getButton1Action());
        }
        this.removeDialog();
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        if (userRequestMessage.getButton2Action() != null) {
            sendUserReplay(userRequestMessage.getButton2Action());
        }
        this.removeDialog();
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        if (userRequestMessage.getButton3Action() != null) {
            sendUserReplay(userRequestMessage.getButton3Action());
        }
        this.removeDialog();
    }//GEN-LAST:event_btn3ActionPerformed

    private void sendUserReplay(PlayerAction playerAction) {
        SwingUtilities.invokeLater(() ->MageFrame.getInstance().sendUserReplay(playerAction, userRequestMessage));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JLabel lblText;
    // End of variables declaration//GEN-END:variables

}
