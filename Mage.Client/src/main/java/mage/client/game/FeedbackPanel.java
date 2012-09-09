/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * FeedbackPanel.java
 *
 * Created on 23-Dec-2009, 9:54:01 PM
 */

package mage.client.game;

import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.components.MageTextArea;
import mage.client.dialog.MageDialog;
import mage.client.util.AudioManager;
import mage.client.util.gui.ArrowBuilder;
import mage.remote.Session;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FeedbackPanel extends javax.swing.JPanel {

    private final static Logger logger = Logger.getLogger(FeedbackPanel.class);

    public enum FeedbackMode {
        INFORM, QUESTION, CONFIRM, CANCEL, SELECT, END
    }

    private boolean selected = false;
    private UUID gameId;
    private Session session;
    private FeedbackMode mode;
    private MageDialog connectedDialog;
    private ChatPanel connectedChatPanel;

    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    /** Creates new form FeedbackPanel */
    public FeedbackPanel() {
        //initComponents();
        customInitComponents();
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
        session = MageFrame.getSession();
    }

    public void getFeedback(FeedbackMode mode, String message, boolean special, Map<String, Serializable> options) {
        logger.info("text: " + message);
        this.lblMessage.setText(message);
        this.helper.setMessage(message);
        this.selected = false;
        this.mode = mode;
        switch (this.mode) {
            case INFORM:
                this.btnLeft.setVisible(false);
                this.btnRight.setVisible(false);
                this.helper.setState("", false, "", false);
                break;
            case QUESTION:
                this.btnLeft.setVisible(true);
                this.btnLeft.setText("Yes");
                this.btnRight.setVisible(true);
                this.btnRight.setText("No");
                this.helper.setState("Yes", true, "No", true);
                break;
            case CONFIRM:
                this.btnLeft.setVisible(true);
                this.btnLeft.setText("OK");
                this.btnRight.setVisible(true);
                this.btnRight.setText("Cancel");
                this.helper.setState("Ok", true, "Cancel", true);
                break;
            case CANCEL:
                this.btnLeft.setVisible(false);
                this.btnRight.setVisible(true);
                this.btnRight.setText("Cancel");
                this.helper.setState("", false, "Cancel", true);
                break;
            case SELECT:
                this.btnLeft.setVisible(false);
                this.btnRight.setVisible(true);
                this.btnRight.setText("Done");
                this.helper.setState("", false, "Done", true);
                break;
            case END:
                this.btnLeft.setVisible(false);
                this.btnRight.setVisible(true);
                this.btnRight.setText("OK");
                this.helper.setState("", false, "OK", true);
                ArrowBuilder.removeAllArrows();
                endWithTimeout();
                break;
        }
        this.btnSpecial.setVisible(special);
        this.btnSpecial.setText("Special");
        this.helper.setSpecial("Special", special);
        if (message.contains("P}")) {
            this.btnSpecial.setVisible(true);
            this.btnSpecial.setText("Pay 2 life");
            this.helper.setSpecial("Pay 2 life", true);
        }

        //boolean yourTurn = options != null && options.containsKey("your_turn");

        requestFocusIfPossible();
        handleOptions(options);

        this.revalidate();
        this.repaint();
        this.helper.setLinks(btnLeft, btnRight, btnSpecial);

        this.helper.setVisible(true);
    }

    /**
     * Close game window by pressing OK button after 5 seconds
     */
    private void endWithTimeout() {
        Runnable task = new Runnable() {
            public void run() {
                logger.info("Ending game...");
                FeedbackPanel.this.btnRight.doClick();
            }
        };
        worker.schedule(task, 8, TimeUnit.SECONDS);
    }

    private void handleOptions(Map<String, Serializable> options) {
        if (options != null) {
            if (options.containsKey("UI.right.btn.text")) {
                this.btnRight.setText((String)options.get("UI.right.btn.text"));
                this.helper.setRight((String)options.get("UI.right.btn.text"), true);
            }
            if (options.containsKey("dialog")) {
                connectedDialog = (MageDialog) options.get("dialog");
            }
        }
    }

    // Issue 256: Chat+Feedback panel: request focus prevents players from chatting
    private void requestFocusIfPossible() {
        boolean requestFocusAllowed = true;
        if (connectedChatPanel != null && connectedChatPanel.getTxtMessageInputComponent() != null) {
            if (connectedChatPanel.getTxtMessageInputComponent().hasFocus()) {
                requestFocusAllowed = false;
            }
        }
        if (requestFocusAllowed) {
            this.btnRight.requestFocus();
            this.helper.requestFocus();
        }
    }

    public void doClick() {
        this.btnRight.doClick();
    }

    public void clear() {
//        stopModal();
        this.btnLeft.setVisible(false);
        this.btnRight.setVisible(false);
        this.btnSpecial.setVisible(false);
        this.lblMessage.setText("");
        logger.debug("feedback - clear");
    }

//    public void clear0() {
//        stopModal();
//    }

//    private synchronized void startModal() {
//
//        try {
//            if (SwingUtilities.isEventDispatchThread()) {
//                EventQueue theQueue = getToolkit().getSystemEventQueue();
//                while (!selected) {
//                    AWTEvent event = theQueue.getNextEvent();
//                    Object source = event.getSource();
//                    boolean dispatch = true;
//
//                    if (event instanceof MouseEvent) {
//                        MouseEvent e = (MouseEvent) event;
//                        MouseEvent m = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this);
//                        if (!this.contains(m.getPoint()) && e.getID() != MouseEvent.MOUSE_DRAGGED) {
//                            dispatch = false;
//                        }
//                    }
//
//                    if (dispatch) {
//                        if (event instanceof ActiveEvent) {
//                            ((ActiveEvent) event).dispatch();
//                        } else if (source instanceof Component) {
//                            ((Component) source).dispatchEvent(event);
//                        } else if (source instanceof MenuComponent) {
//                            ((MenuComponent) source).dispatchEvent(event);
//                        } else {
//                            logger.warn("Unable to dispatch: " + event);
//                        }
//                    }
//                }
//            } else {
//                while (!selected) {
//                    wait();
//                }
//            }
//        } catch (InterruptedException ignored) {
//        }
//
//    }
//
//    private synchronized void stopModal() {
//        notifyAll();
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRight = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        //lblMessage = new javax.swing.JTextArea();
        lblMessage = new MageTextArea();
        btnSpecial = new javax.swing.JButton();

        setBackground(new java.awt.Color(255,255,255,200));

        btnRight.setText("Cancel");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnLeft.setText("OK");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //lblMessage.setBackground(new java.awt.Color(204, 204, 204));
        /*lblMessage.setColumns(20);
        lblMessage.setEditable(false);
        lblMessage.setLineWrap(true);
        lblMessage.setRows(2);
        lblMessage.setWrapStyleWord(true);*/

        lblMessage.setBorder(null);
        jScrollPane1.setViewportView(lblMessage);
        jScrollPane1.setBorder(null);

        btnSpecial.setText("Special");
        btnSpecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpecialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSpecial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnLeft)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRight))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRight)
                    .addComponent(btnLeft)
                    .addComponent(btnSpecial)))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void customInitComponents() {
        btnRight = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblMessage = new MageTextArea();
        btnSpecial = new javax.swing.JButton();

        setBackground(new java.awt.Color(0,0,0,80));

        btnRight.setText("Cancel");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnLeft.setText("OK");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        lblMessage.setBorder(null);
        jScrollPane1.setViewportView(lblMessage);
        jScrollPane1.setBorder(null);

        btnSpecial.setText("Special");
        btnSpecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpecialActionPerformed(evt);
            }
        });

        JLabel jlabel = new JLabel();
        jlabel.setLayout(new BorderLayout());
        jlabel.add(jScrollPane1, BorderLayout.CENTER);

        setLayout(new BorderLayout());

        JLabel jlabel2 = new JLabel();
        jlabel2.setLayout(new FlowLayout());
        jlabel2.add(btnSpecial);
        jlabel2.add(btnLeft);
        jlabel2.add(btnRight);
        jlabel2.setPreferredSize(new Dimension(0, 35));

        add(jlabel, BorderLayout.CENTER);
        add(jlabel2, BorderLayout.PAGE_END);
    }

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        this.selected = true;
//        clear0();
        if (connectedDialog != null) {
            connectedDialog.hideDialog();
        }
        if (mode == FeedbackMode.SELECT && (evt.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            session.sendPlayerInteger(gameId, 0);
        } else if (mode == FeedbackMode.END) {
            MageFrame.getGame(gameId).hideGame();
        } else {
            session.sendPlayerBoolean(gameId, false);
        }
        //AudioManager.playButtonOk();
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        this.selected = true;
//        clear0();
        session.sendPlayerBoolean(gameId, true);
        AudioManager.playButtonCancel();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnSpecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpecialActionPerformed
        session.sendPlayerString(gameId, "special");
    }//GEN-LAST:event_btnSpecialActionPerformed

    public void setHelperPanel(HelperPanel helper) {
        this.helper = helper;
    }

    public FeedbackMode getMode() {
        return this.mode;
    }

    public void setConnectedChatPanel(ChatPanel chatPanel) {
        this.connectedChatPanel = chatPanel;
    }

    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSpecial;
    private javax.swing.JScrollPane jScrollPane1;
    //private javax.swing.JTextArea lblMessage;
    private MageTextArea lblMessage;
    private HelperPanel helper;
}
