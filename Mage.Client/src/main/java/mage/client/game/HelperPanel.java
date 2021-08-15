package mage.client.game;

import mage.client.SessionHandler;
import mage.client.components.MageTextArea;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.util.AppUtil;
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.constants.TurnPhase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
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

    // popup menu for keep auto-answer in yes/no dialogs
    private JPopupMenu popupMenuAskYes;
    private JMenuItem popupItemYesAsText;
    private JMenuItem popupItemYesAsTextAndAbility;
    private JPopupMenu popupMenuAskNo;
    private JMenuItem popupItemNoAsText;
    private JMenuItem popupItemNoAsTextAndAbility;

    // originalId of feedback causing ability
    private UUID originalId;
    private String message;

    private UUID gameId;
    private boolean gameNeedFeedback = false;
    private TurnPhase gameTurnPhase = null;

    private Timer needFeedbackTimer;

    {
        // start timer to inform user about needed feedback (example: inform by sound play)
        needFeedbackTimer = new Timer(100, evt -> SwingUtilities.invokeLater(() -> {
            needFeedbackTimer.stop();
            if (!AppUtil.isAppActive() || !AppUtil.isGameActive(this.gameId)) {
                // sound notification
                AudioManager.playFeedbackNeeded();
                // tray notification (baloon + icon blinking)
                //MageTray.instance.displayMessage("Game needs your action.");
                //MageTray.instance.blink();
            }
        }));
    }

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
            public void mousePressed(MouseEvent e) {
                checkPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkPopupMenu(e);
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
            public void mouseEntered(MouseEvent e) {
                ToolTipManager.sharedInstance().setDismissDelay(100 * 1000);
                UIManager.put("info", Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ToolTipManager.sharedInstance().setDismissDelay(Constants.TOOLTIPS_DELAY_MS);
                UIManager.put("info", tooltipBackground);
            }
        });
    }

    private void checkPopupMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            // allows any yes/no dialogs
            JButton source = (JButton) e.getSource();
            if (source.getActionCommand().startsWith(QUESTION.toString())) {
                showPopupMenu(e.getComponent(), source.getActionCommand());
                e.consume();
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
                this.btnLeft.setActionCommand(mode + txtLeft);
            }
        }

        this.btnRight.setVisible(rightVisible);
        if (!txtRight.isEmpty()) {
            this.btnRight.setText(txtRight);
            if (mode != null) {
                this.btnRight.setActionCommand(mode + txtRight);
            }
        }

        // auto-answer hints
        String buttonTooltip = null;
        if (mode == QUESTION) {
            buttonTooltip = "Right click on button to make auto-answer.";
        }
        this.btnLeft.setToolTipText(buttonTooltip);
        this.btnRight.setToolTipText(buttonTooltip);

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

        if (this.gameNeedFeedback) {
            // start notification sound timer
            this.needFeedbackTimer.restart();
        } else {
            // stop notification sound timer
            this.needFeedbackTimer.stop();
        }
    }

    public void autoSizeButtonsAndFeedbackState() {
        // two mode: same size for small texts (flow), different size for long texts (grid)
        // also colorize feedback panel on player's priority and enable sound notification

        int BUTTONS_H_GAP = 15;
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_MAIN = new Color(0, 0, 255, 50);
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_BATTLE = new Color(255, 0, 0, 50);
        Color ACTIVE_FEEDBACK_BACKGROUND_COLOR_OTHER = new Color(0, 255, 0, 50);
        int FEEDBACK_COLORIZING_MODE = PreferencesDialog.getBattlefieldFeedbackColorizingMode();

        // cleanup current settings to default (flow layout - different sizes)
        this.buttonGrid.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTONS_H_GAP, 0));
        this.buttonGrid.setPreferredSize(null);

        List<JButton> buttons = new ArrayList<>();
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
        popupItemYesAsTextAndAbility = new JMenuItem("Auto-answer YES for the same TEXT and ABILITY");
        popupItemYesAsTextAndAbility.setActionCommand(CMD_AUTO_ANSWER_ID_YES);
        popupItemYesAsTextAndAbility.addActionListener(actionListener);
        popupItemYesAsTextAndAbility.setToolTipText("<HTML>If the same question from the same ability would<br/>be asked again, it's automatically answered with <b>Yes</b>.<br/>You can reset it by battlefield right click menu.");
        popupMenuAskYes.add(popupItemYesAsTextAndAbility);

        popupItemNoAsTextAndAbility = new JMenuItem("Auto-answer NO for the same TEXT and ABILITY");
        popupItemNoAsTextAndAbility.setActionCommand(CMD_AUTO_ANSWER_ID_NO);
        popupItemNoAsTextAndAbility.setToolTipText("<HTML>If the same question from the same ability would<br/>"
                + "be asked again, it's automatically answered with <b>No</b>.<br/>"
                + "You can reset it by battlefield right click menu.");
        popupItemNoAsTextAndAbility.addActionListener(actionListener);
        popupMenuAskNo.add(popupItemNoAsTextAndAbility);

        popupItemYesAsText = new JMenuItem("Auto-answer YES for the same TEXT");
        popupItemYesAsText.setActionCommand(CMD_AUTO_ANSWER_NAME_YES);
        popupItemYesAsText.setToolTipText("<HTML>If the same question would be asked again (regardless from which source),<br/>"
                + "it's automatically answered with <b>Yes</b>.<br/>"
                + "You can reset it by battlefield right click menu.");
        popupItemYesAsText.addActionListener(actionListener);
        popupMenuAskYes.add(popupItemYesAsText);

        popupItemNoAsText = new JMenuItem("Auto-answer NO for the same TEXT");
        popupItemNoAsText.setActionCommand(CMD_AUTO_ANSWER_NAME_NO);
        popupItemNoAsText.setToolTipText("<HTML>If the same question would be asked again (regardless from which source),<br/>"
                + "it's automatically answered with <b>No</b>.<br/>"
                + "You can reset it by battlefield right click menu.");
        popupItemNoAsText.addActionListener(actionListener);
        popupMenuAskNo.add(popupItemNoAsText);

        JMenuItem menuItem = new JMenuItem("Reset all YES/NO auto-answers");
        menuItem.setActionCommand(CMD_AUTO_ANSWER_RESET_ALL);
        menuItem.addActionListener(actionListener);
        popupMenuAskYes.add(menuItem);

        menuItem = new JMenuItem("Reset all YES/NO auto-answers");
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
        // keep auto-answer for yes/no

        // two modes:
        // - remember text for all (example: commander zone change);
        // - remember text + ability for source only (example: any optional ability)

        // yes
        popupItemYesAsText.setEnabled(true);
        popupItemYesAsTextAndAbility.setEnabled(originalId != null);
        popupItemYesAsText.setEnabled(true);
        popupItemYesAsTextAndAbility.setEnabled(originalId != null);
        // no
        popupItemNoAsText.setEnabled(true);
        popupItemNoAsTextAndAbility.setEnabled(originalId != null);
        popupItemNoAsText.setEnabled(true);
        popupItemNoAsTextAndAbility.setEnabled(originalId != null);

        Point p = callingComponent.getLocationOnScreen();
        // Show the JPopupMenu via program
        // Parameter desc
        // ----------------
        // this - represents current frame
        // 0,0 is the coordinate where the popup
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
