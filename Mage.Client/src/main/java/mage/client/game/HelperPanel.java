
package mage.client.game;

import mage.client.SessionHandler;
import mage.client.components.MageTextArea;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.util.GUISizeHelper;
import mage.constants.TurnPhase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

import static mage.client.game.FeedbackPanel.FeedbackMode.QUESTION;
import static mage.constants.PlayerAction.*;

/**
 * Panel with buttons that copy the state of feedback panel.
 *
 * @author ayrat, JayDi85
 */
public class HelperPanel extends JPanel {

    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSpecial;
    private javax.swing.JButton btnUndo;
    //private javax.swing.JButton btnEndTurn;
    //private javax.swing.JButton btnStopTimer;
    private JScrollPane textAreaScrollPane;
    private MageTextArea dialogTextArea;
    JPanel mainPanel;
    JPanel buttonGrid;
    JPanel buttonContainer;

    private javax.swing.JButton linkLeft;
    private javax.swing.JButton linkRight;
    private javax.swing.JButton linkSpecial;
    private javax.swing.JButton linkUndo;

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
    private boolean gameNeedFeedback = false;
    private TurnPhase gameTurnPhase = null;

    public HelperPanel() {
        initComponents();
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        //this.setMaximumSize(new Dimension(getParent().getWidth(), Integer.MAX_VALUE));
        textAreaScrollPane.setMaximumSize(new Dimension(getParent().getWidth(), GUISizeHelper.gameDialogAreaTextHeight));
        textAreaScrollPane.setPreferredSize(new Dimension(getParent().getWidth(), GUISizeHelper.gameDialogAreaTextHeight));

//        dialogTextArea.setMaximumSize(new Dimension(getParent().getWidth(), Integer.MAX_VALUE));
//        dialogTextArea.setPreferredSize(new Dimension(getParent().getWidth(), GUISizeHelper.gameDialogAreaTextHeight));
//        buttonContainer.setPreferredSize(new Dimension(getParent().getWidth(), GUISizeHelper.gameDialogButtonHeight + 4));
//        buttonContainer.setMinimumSize(new Dimension(160, GUISizeHelper.gameDialogButtonHeight + 20));
//        buttonContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, GUISizeHelper.gameDialogButtonHeight + 4));
        btnLeft.setFont(GUISizeHelper.gameDialogAreaFont);
        btnRight.setFont(GUISizeHelper.gameDialogAreaFont);
        btnSpecial.setFont(GUISizeHelper.gameDialogAreaFont);
        btnUndo.setFont(GUISizeHelper.gameDialogAreaFont);

        // update text fonts
        if (message != null) {
            int pos1 = this.message.indexOf("font-size:");

            if (pos1 > 0) {
                int pos2 = this.message.indexOf("font-size:", pos1 + 10);

                String newMessage;
                if (pos2 > 0) {
                    // 2 sizes: big + small // TODO: 2 sizes for compatibility only? On 04.02.2018 can't find two size texts (JayDi85)
                    newMessage = this.message.substring(0, pos1 + 10) + GUISizeHelper.gameDialogAreaFontSizeBig + this.message.substring(pos1 + 12);
                    newMessage = newMessage.substring(0, pos1 + 10) + GUISizeHelper.gameDialogAreaFontSizeSmall + newMessage.substring(pos1 + 12);
                } else {
                    // 1 size: small
                    newMessage = this.message.substring(0, pos1 + 10) + GUISizeHelper.gameDialogAreaFontSizeSmall + this.message.substring(pos1 + 12);
                }
                setBasicMessage(newMessage);
            }
        }

        autoSizeButtonsAndFeedbackState();

        GUISizeHelper.changePopupMenuFont(popupMenuAskNo);
        GUISizeHelper.changePopupMenuFont(popupMenuAskYes);
        revalidate();
        repaint();
    }

    private void initComponents() {
        initPopupMenuTriggerOrder();

        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new GridLayout(0, 1));
        this.setOpaque(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));
        mainPanel.setOpaque(false);
        this.add(mainPanel);

        dialogTextArea = new MageTextArea();
        dialogTextArea.setText("<Empty>");
        dialogTextArea.setOpaque(false);

        textAreaScrollPane = new JScrollPane(dialogTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textAreaScrollPane.setOpaque(false);
        textAreaScrollPane.setBackground(new Color(0, 0, 0, 0));
        textAreaScrollPane.getViewport().setOpaque(false);
        textAreaScrollPane.setBorder(null);
        textAreaScrollPane.setViewportBorder(null);
        mainPanel.add(textAreaScrollPane);

        buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonContainer.setOpaque(false);
        mainPanel.add(buttonContainer);

        buttonGrid = new JPanel(); // buttons layout auto changes by autoSizeButtonsAndFeedbackState
        buttonGrid.setOpaque(false);
        buttonContainer.add(buttonGrid);

        btnSpecial = new JButton("Special");
        btnSpecial.setVisible(false);
        buttonGrid.add(btnSpecial);

        btnLeft = new JButton("OK");
        btnLeft.setVisible(false);
        buttonGrid.add(btnLeft);

        btnRight = new JButton("Cancel");
        btnRight.setVisible(false);
        buttonGrid.add(btnRight);

        btnUndo = new JButton("Undo");
        btnUndo.setVisible(false);
        buttonGrid.add(btnUndo);

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
        btnLeft.addActionListener(evt -> {
            if (linkLeft != null) {
                clickButton(linkLeft);
            }
        });

        btnRight.addMouseListener(checkPopupAdapter);
        btnRight.addActionListener(evt -> {
            if (linkRight != null) {
                clickButton(linkRight);
            }
        });

        btnSpecial.addActionListener(evt -> {
            if (linkSpecial != null) {
                clickButton(linkSpecial);
            }
        });

        btnUndo.addActionListener(evt -> {
            if (linkUndo != null) {
                {
                    Thread worker = new Thread(() -> SwingUtilities.invokeLater(() -> linkUndo.doClick()));
                    worker.start();
                }
            }
        });

        // sets a darker background and higher simiss time fur tooltip in the feedback / helper panel
        dialogTextArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(100 * 1000);
                UIManager.put("info", Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setDismissDelay(Constants.TOOLTIPS_DELAY_MS);
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
        Thread worker = new Thread(() -> SwingUtilities.invokeLater(() -> {
            setState("", false, "", false, null);
            setSpecial("", false);
            button.doClick();
        }));
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
        autoSizeButtonsAndFeedbackState();
    }

    public void setSpecial(String txtSpecial, boolean specialVisible) {
        this.btnSpecial.setVisible(specialVisible);
        this.btnSpecial.setText(txtSpecial);
    }

    public void setUndoEnabled(boolean enabled) {
        this.btnUndo.setVisible(enabled);
        autoSizeButtonsAndFeedbackState();
    }

    public void setLeft(String text, boolean visible) {
        this.btnLeft.setVisible(visible);
        if (!text.isEmpty()) {
            this.btnLeft.setText(text);
        }
        autoSizeButtonsAndFeedbackState();
    }

    public void setRight(String txtRight, boolean rightVisible) {
        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
        }
        autoSizeButtonsAndFeedbackState();
    }

    public void setGameNeedFeedback(boolean need, TurnPhase gameTurnPhase) {
        this.gameNeedFeedback = need;
        this.gameTurnPhase = gameTurnPhase;
    }

    public void autoSizeButtonsAndFeedbackState() {
        // two mode: same size for small texts (flow), different size for long texts (grid)
        // plus colorize feedback panel on player's priority

        int BUTTONS_H_GAP = 15;
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_MAIN = new Color(0, 0, 255, 50);
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_BATTLE = new Color(255, 0, 0, 50);
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_OTHER = new Color(0, 255, 0, 50);
        int FEEDBACK_COLORIZING_MODE = PreferencesDialog.getBattlefieldFeedbackColorizingMode();

        // cleanup current settings to default (flow layout - different sizes)
        this.buttonGrid.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTONS_H_GAP, 0));
        this.buttonGrid.setPreferredSize(null);

        ArrayList<JButton> buttons = new ArrayList<>();
        if (this.btnSpecial.isVisible()) {
            buttons.add(this.btnSpecial);
        }
        if (this.btnLeft.isVisible()) {
            buttons.add(this.btnLeft);
        }
        if (this.btnRight.isVisible()) {
            buttons.add(this.btnRight);
        }
        if (this.btnUndo.isVisible()) {
            buttons.add(this.btnUndo);
        }

        // color panel on player's feedback waiting
        if (this.gameNeedFeedback) {
            // wait player's action
            switch (FEEDBACK_COLORIZING_MODE) {
                case Constants.BATTLEFIELD_FEEDBACK_COLORIZING_MODE_DISABLE:
                    // disabled
                    this.mainPanel.setOpaque(false);
                    this.mainPanel.setBorder(null);
                    break;

                case Constants.BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_ONE_COLOR:
                    // one color
                    this.mainPanel.setOpaque(true);
                    this.mainPanel.setBackground(ACTIVE_FEEDBACK_BACKGROUND_COLOR_OTHER);
                    break;

                case Constants.BATTLEFIELD_FEEDBACK_COLORIZING_MODE_ENABLE_BY_MULTICOLOR:
                    // multicolor
                    this.mainPanel.setOpaque(true);
                    Color backColor = ACTIVE_FEEDBACK_BACKGROUND_COLOR_OTHER;
                    if (this.gameTurnPhase != null) {
                        switch (this.gameTurnPhase) {
                            case PRECOMBAT_MAIN:
                            case POSTCOMBAT_MAIN:
                                backColor = ACTIVE_FEEDBACK_BACKGROUND_COLOR_MAIN;
                                break;
                            case COMBAT:
                                backColor = ACTIVE_FEEDBACK_BACKGROUND_COLOR_BATTLE;
                                break;
                            default:
                                break;
                        }
                    }
                    this.mainPanel.setBackground(backColor);
                    break;
                default:
                    break;
            }
        } else {
            // inform about other players
            this.mainPanel.setOpaque(false);
        }

        if (buttons.isEmpty()) {
            return;
        }

        this.buttonGrid.removeAll();
        for (JButton button : buttons) {
            this.buttonGrid.add(button);
        }

        // random text test (click to any objects to change)
        /*
        Integer i = 1 + RandomUtil.nextInt(50);
        String longText = i.toString() + "-";
        while (longText.length() < i) {longText += "a";}
        this.btnRight.setText(longText);
        //*/

        // search max preferred size to draw full button's text
        int needButtonSizeW = 0;
        for (JButton button : buttons) {
            needButtonSizeW = Math.max(needButtonSizeW, button.getPreferredSize().width);
        }

        // search max const size
        int constButtonSizeW = GUISizeHelper.gameDialogButtonWidth * 200 / 100;
        int constGridSizeW = buttons.size() * constButtonSizeW + BUTTONS_H_GAP * (buttons.size() - 1);
        int constGridSizeH = Math.round(GUISizeHelper.gameDialogButtonHeight * 150 / 100);

        if (needButtonSizeW < constButtonSizeW) {
            // same size mode (grid)
            GridLayout gl = new GridLayout(1, buttons.size(), BUTTONS_H_GAP, 0);
            this.buttonGrid.setLayout(gl);
            this.buttonGrid.setPreferredSize(new Dimension(constGridSizeW, constGridSizeH));
        } else {
            // different size mode (flow) -- already used by default
            //FlowLayout fl = new FlowLayout(FlowLayout.CENTER, BUTTONS_H_GAP, 0);
            //this.buttonGrid.setLayout(fl);
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
        this.dialogTextArea.setText(message, this.getWidth());
    }

    public void setTextArea(String message) {
        this.dialogTextArea.setText(message, this.getWidth());
    }

    @Override
    public void requestFocus() {
        this.btnRight.requestFocus();
    }

    private void initPopupMenuTriggerOrder() {

        ActionListener actionListener = e -> handleAutoAnswerPopupMenuEvent(e);

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
                SessionHandler.sendPlayerAction(REQUEST_AUTO_ANSWER_ID_YES, gameId, originalId.toString() + '#' + message);
                clickButton(btnLeft);
                break;
            case CMD_AUTO_ANSWER_ID_NO:
                SessionHandler.sendPlayerAction(REQUEST_AUTO_ANSWER_ID_NO, gameId, originalId.toString() + '#' + message);
                clickButton(btnRight);
                break;
            case CMD_AUTO_ANSWER_NAME_YES:
                SessionHandler.sendPlayerAction(REQUEST_AUTO_ANSWER_TEXT_YES, gameId, message);
                clickButton(btnLeft);
                break;
            case CMD_AUTO_ANSWER_NAME_NO:
                SessionHandler.sendPlayerAction(REQUEST_AUTO_ANSWER_TEXT_NO, gameId, message);
                clickButton(btnRight);
                break;
            case CMD_AUTO_ANSWER_RESET_ALL:
                SessionHandler.sendPlayerAction(REQUEST_AUTO_ANSWER_RESET_ALL, gameId, null);
                break;
            default:
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
