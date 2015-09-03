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
package mage.client.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import mage.client.MageFrame;
import mage.client.components.MageTextArea;
import mage.client.game.FeedbackPanel.FeedbackMode;
import static mage.client.game.FeedbackPanel.FeedbackMode.QUESTION;
import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_ID_NO;
import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_ID_YES;
import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL;
import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_TEXT_NO;
import static mage.constants.PlayerAction.REQUEST_AUTO_ANSWER_TEXT_YES;
import mage.remote.Session;

/**
 * Panel with buttons that copy the state of feedback panel.
 *
 * @author ayrat
 */
public class HelperPanel extends JPanel {

    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSpecial;
    private javax.swing.JButton btnUndo;
    //private javax.swing.JButton btnEndTurn;
    //private javax.swing.JButton btnStopTimer;

    private MageTextArea textArea;

    private javax.swing.JButton linkLeft;
    private javax.swing.JButton linkRight;
    private javax.swing.JButton linkSpecial;
    private javax.swing.JButton linkUndo;

    private final int defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();
    private final Object tooltipBackground = UIManager.get("info");

    private static final String CMD_AUTO_ANSWER_ID_YES = "cmdAutoAnswerIdYes";
    private static final String CMD_AUTO_ANSWER_ID_NO = "cmdAutoAnswerIdNo";
    private static final String CMD_AUTO_ANSWER_NAME_YES = "cmdAutoAnswerNameYes";
    private static final String CMD_AUTO_ANSWER_NAME_NO = "cmdAutoAnswerNameNo";
    private static final String CMD_AUTO_ANSWER_RESET_ALL = "cmdAutoAnswerResetAll";

    // popup menu for set automatic answers
    private JPopupMenu popupMenuAskYes;
    private JPopupMenu popupMenuAskNo;

    // originalId of feedback causing ability
    private UUID originalId;
    private String message;

    private UUID gameId;
    private Session session;

    public HelperPanel() {
        initComponents();
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
        session = MageFrame.getSession();
    }

    private void initComponents() {
        initPopupMenuTriggerOrder();
        setBackground(new Color(0, 0, 0, 100));
        //setLayout(new GridBagLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(false);

        JPanel container = new JPanel();

        container.setPreferredSize(new Dimension(100, 30));
        container.setMinimumSize(new Dimension(20, 20));
        container.setMaximumSize(new Dimension(2000, 100));
        container.setLayout(new GridBagLayout());
        container.setOpaque(false);

        JPanel jPanel = new JPanel();

        textArea = new MageTextArea();
        textArea.setText("<Empty>");

        jPanel.setOpaque(false);
        jPanel.setBackground(new Color(0, 0, 0, 80));
        jPanel.add(textArea);
        add(jPanel);

        add(container);

        btnSpecial = new JButton("Special");
        btnSpecial.setVisible(false);
        container.add(btnSpecial);
        btnLeft = new JButton("OK");
        btnLeft.setVisible(false);
        container.add(btnLeft);
        btnRight = new JButton("Cancel");
        btnRight.setVisible(false);
        container.add(btnRight);
        btnUndo = new JButton("Undo");
        btnUndo.setVisible(false);
        container.add(btnUndo);

        MouseListener checkPopupAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                checkPopupMenu(me);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                checkPopupMenu(me);
            }

        };

        btnLeft.addMouseListener(checkPopupAdapter);
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkLeft != null) {
                    clickButton(linkLeft);
                }
            }
        });

        btnRight.addMouseListener(checkPopupAdapter);
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkRight != null) {
                    clickButton(linkRight);
                }
            }
        });

        btnSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkSpecial != null) {
                    clickButton(linkSpecial);
                }
            }
        });

        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkUndo != null) {
                    {
                        Thread worker = new Thread() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        linkUndo.doClick();
                                    }
                                });
                            }
                        };
                        worker.start();
                    }
                }
            }
        });

        // sets a darker background and higher simiss time fur tooltip in the feedback / helper panel
        textArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(100000);
                UIManager.put("info", Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);
                UIManager.put("info", tooltipBackground);
            }
        });
    }

    private void checkPopupMenu(MouseEvent me) {
        if (me.isPopupTrigger()
                && originalId != null) { // only Yes/No requests from abilities can be automated
            JButton source = (JButton) me.getSource();
            if (source.getActionCommand().startsWith(QUESTION.toString())) {
                showPopupMenu(me.getComponent(), source.getActionCommand());
                me.consume();
            }
        }
    }

    private void clickButton(final JButton button) {
        Thread worker = new Thread() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setState("", false, "", false, null);
                        setSpecial("", false);
                        button.doClick();
                    }
                });
            }
        };
        worker.start();
    }

    public void setState(String txtLeft, boolean leftVisible, String txtRight, boolean rightVisible, FeedbackMode mode) {
        this.btnLeft.setVisible(leftVisible);
        if (!txtLeft.isEmpty()) {
            this.btnLeft.setText(txtLeft);
            if (mode != null) {
                this.btnLeft.setActionCommand(mode.toString() + txtLeft);
            }
        }
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
            if (mode != null) {
                this.btnRight.setActionCommand(mode.toString() + txtRight);
            }
        }

    }

    public void setSpecial(String txtSpecial, boolean specialVisible) {
        this.btnSpecial.setVisible(specialVisible);
        this.btnSpecial.setText(txtSpecial);
    }

    public void setUndoEnabled(boolean enabled) {
        this.btnUndo.setVisible(enabled);
    }

    public void setRight(String txtRight, boolean rightVisible) {
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
        }
    }

    public void setLinks(JButton left, JButton right, JButton special, JButton undo) {
        this.linkLeft = left;
        this.linkRight = right;
        this.linkSpecial = special;
        this.linkUndo = undo;
    }

    public void setOriginalId(UUID originalId) {
        this.originalId = originalId;
    }

    public void setBasicMessage(String message) {
        this.message = message;
        this.textArea.setText(message, this.getWidth());
    }

    public void setTextArea(String message) {
        this.textArea.setText(message, this.getWidth());
    }

    @Override
    public void requestFocus() {
        this.btnRight.requestFocus();
    }

    private void initPopupMenuTriggerOrder() {

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAutoAnswerPopupMenuEvent(e);
            }
        };

        popupMenuAskYes = new JPopupMenu();
        popupMenuAskNo = new JPopupMenu();

        // String tooltipText = "";
        JMenuItem menuItem;
        menuItem = new JMenuItem("Always Yes for the same text and ability");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_ID_YES);
        menuItem.addActionListener(actionListener);
        menuItem.setToolTipText("<HTML>If the same question from the same ability would<br/>be asked again, it's automatically answered with <b>Yes</b>.");
        popupMenuAskYes.add(menuItem);

        menuItem = new JMenuItem("Always No for the same text and ability");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_ID_NO);
        menuItem.setToolTipText("<HTML>If the same question from the same ability would<br/>be asked again, it's automatically answered with <b>No</b>.");
        menuItem.addActionListener(actionListener);
        popupMenuAskNo.add(menuItem);

        menuItem = new JMenuItem("Always Yes for the same text");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_NAME_YES);
        menuItem.setToolTipText("<HTML>If the same question would be asked again (regardless from which source),<br/> it's automatically answered with <b>Yes</b>.");
        menuItem.addActionListener(actionListener);
        popupMenuAskYes.add(menuItem);

        menuItem = new JMenuItem("Always No for the same text");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_NAME_NO);
        menuItem.setToolTipText("<HTML>If the same question would be asked again (regardless from which source),<br/> it's automatically answered with <b>No</b>.");
        menuItem.addActionListener(actionListener);
        popupMenuAskNo.add(menuItem);

        menuItem = new JMenuItem("Delete all automatic Yes/No settings");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_RESET_ALL);
        menuItem.addActionListener(actionListener);
        popupMenuAskYes.add(menuItem);

        menuItem = new JMenuItem("Delete all automatic Yes/No settings");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_RESET_ALL);
        menuItem.addActionListener(actionListener);
        popupMenuAskNo.add(menuItem);
    }

    public void handleAutoAnswerPopupMenuEvent(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CMD_AUTO_ANSWER_ID_YES:
                session.sendPlayerAction(REQUEST_AUTO_ANSWER_ID_YES, gameId, originalId.toString() + "#" + message);
                clickButton(btnLeft);
                break;
            case CMD_AUTO_ANSWER_ID_NO:
                session.sendPlayerAction(REQUEST_AUTO_ANSWER_ID_NO, gameId, originalId.toString() + "#" + message);
                clickButton(btnRight);
                break;
            case CMD_AUTO_ANSWER_NAME_YES:
                session.sendPlayerAction(REQUEST_AUTO_ANSWER_TEXT_YES, gameId, message);
                clickButton(btnLeft);
                break;
            case CMD_AUTO_ANSWER_NAME_NO:
                session.sendPlayerAction(REQUEST_AUTO_ANSWER_TEXT_NO, gameId, message);
                clickButton(btnRight);
                break;
            case CMD_AUTO_ANSWER_RESET_ALL:
                session.sendPlayerAction(REQUEST_AUTO_ANSWER_RESET_ALL, gameId, null);
                break;
        }
    }

    private void showPopupMenu(Component callingComponent, String actionCommand) {
        // Get the location of the point 'on the screen'
        Point p = callingComponent.getLocationOnScreen();
        // Show the JPopupMenu via program
        // Parameter desc
        // ----------------
        // this - represents current frame
        // 0,0 is the co ordinate where the popup
        // is shown
        JPopupMenu menu;
        if (actionCommand.endsWith("Yes")) {
            menu = popupMenuAskYes;
        } else {
            menu = popupMenuAskNo;
        }
        menu.show(this, 0, 0);

        // Now set the location of the JPopupMenu
        // This location is relative to the screen
        menu.setLocation(p.x, p.y + callingComponent.getHeight());
    }
}
