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
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import mage.client.components.MageTextArea;

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

    public HelperPanel() {
        initComponents();
    }

    private void initComponents() {

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

        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkLeft != null) {
                    {
                        Thread worker = new Thread() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        setState("", false, "", false);
                                        setSpecial("", false);
                                        linkLeft.doClick();
                                    }
                                });
                            }
                        };
                        worker.start();
                    }
                }
            }
        });

        btnRight.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkRight != null) {
                    Thread worker = new Thread() {
                        @Override
                        public void run() {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    setState("", false, "", false);
                                    setSpecial("", false);
                                    linkRight.doClick();
                                }
                            });
                        }
                    };
                    worker.start();
                }
            }
        });

        btnSpecial.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (linkSpecial != null) {
                    {
                        Thread worker = new Thread() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        setState("", false, "", false);
                                        setSpecial("", false);
                                        linkSpecial.doClick();
                                    }
                                });
                            }
                        };
                        worker.start();
                    }
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

    public void setState(String txtLeft, boolean leftVisible, String txtRight, boolean rightVisible) {
        this.btnLeft.setVisible(leftVisible);
        if (!txtLeft.isEmpty()) {
            this.btnLeft.setText(txtLeft);
        }
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
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

    public void setMessage(String message) {
//        if (message.startsWith("Use alternative cost")) {
//            message = "Use alternative cost?";
//        } else if (message.contains("Use ")) {
//            if (message.length() < this.getWidth() / 10) {
//                message = getSmallText(message);
//            } else {
//                message = "Use ability?" + getSmallText(message.substring(0, this.getWidth() / 10));
//            }
//        }
        textArea.setText(message, this.getWidth());
    }

    protected String getSmallText(String text) {
        return "<div style='font-size:11pt'>" + text + "</div>";
    }

    @Override
    public void requestFocus() {
        this.btnRight.requestFocus();
    }
}
