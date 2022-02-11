package mage.client.game;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.chat.ChatPanelBasic;
import mage.client.dialog.MageDialog;
import mage.client.util.GUISizeHelper;
import mage.client.util.audio.AudioManager;
import mage.client.util.gui.ArrowBuilder;
import mage.constants.PlayerAction;
import mage.constants.TurnPhase;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static mage.constants.Constants.Option.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FeedbackPanel extends javax.swing.JPanel {

    private static final Logger LOGGER = Logger.getLogger(FeedbackPanel.class);

    public enum FeedbackMode {
        INFORM, QUESTION, CONFIRM, CANCEL, SELECT, END
    }

    private UUID gameId;
    private FeedbackMode mode;
    private MageDialog connectedDialog;
    private ChatPanelBasic connectedChatPanel;
    private int lastMessageId;
    private Map<String, Serializable> lastOptions = new HashMap<>();

    private static final ScheduledExecutorService WORKER = Executors.newSingleThreadScheduledExecutor();

    /**
     * Creates new form FeedbackPanel
     */
    public FeedbackPanel() {
        //initComponents();
        customInitComponents();
    }

    public void init(UUID gameId) {
        this.gameId = gameId;
        helper.init(gameId);
        setGUISize();
    }

    public void changeGUISize() {
        setGUISize();
        helper.changeGUISize();
    }

    private void setGUISize() {
    }

    public void prepareFeedback(FeedbackMode mode, String message, boolean special, Map<String, Serializable> options,
                                int messageId, boolean gameNeedUserFeedback, TurnPhase gameTurnPhase) {
        synchronized (this) {
            if (messageId < this.lastMessageId) {
                // if too many warning messages here then look at GAME_REDRAW_GUI event logic
                LOGGER.warn("catch un-synced message from later source (possible reason: connection or performance problems): " + messageId + ", text=" + message);
                return;
            }
            this.lastMessageId = messageId;
            this.lastOptions = options;
            this.mode = mode;
        }

        this.helper.setBasicMessage(message);
        this.helper.setOriginalId(null); // reference to the feedback causing ability
        String lblText = addAdditionalText(message, options);
        this.helper.setTextArea(lblText);


        switch (this.mode) {
            case INFORM:
                setButtonState("", "", mode);
                break;
            case QUESTION:
                setButtonState("Yes", "No", mode);
                if (options != null && options.containsKey(ORIGINAL_ID)) {
                    // allows yes/no auto-answers for ability related
                    this.helper.setOriginalId((UUID) options.get(ORIGINAL_ID));
                }
                break;
            case CONFIRM:
                setButtonState("OK", "Cancel", mode);
                break;
            case CANCEL:
                setButtonState("", "Cancel", mode);
                this.helper.setUndoEnabled(false);
                break;
            case SELECT:
                setButtonState("", "Done", mode);
                break;
            case END:
                setButtonState("", "Close game", mode);
                ArrowBuilder.getBuilder().removeAllArrows(gameId);
                endWithTimeout();
                break;
        }
        if (options != null && options.containsKey(SPECIAL_BUTTON)) {
            this.setSpecial((String) options.get(SPECIAL_BUTTON), true);
        } else {
            this.setSpecial("Special", special);
        }

        requestFocusIfPossible();
        updateOptions(options);

        this.revalidate();
        this.repaint();
        this.helper.setLinks(btnLeft, btnRight, btnSpecial, btnUndo);

        this.helper.setVisible(true);
        this.helper.setGameNeedFeedback(gameNeedUserFeedback, gameTurnPhase);
        this.helper.autoSizeButtonsAndFeedbackState();
    }

    private void setButtonState(String leftText, String rightText, FeedbackMode mode) {
        btnLeft.setVisible(!leftText.isEmpty());
        btnLeft.setText(leftText);
        btnRight.setVisible(!rightText.isEmpty());
        btnRight.setText(rightText);
        this.helper.setState(leftText, !leftText.isEmpty(), rightText, !rightText.isEmpty(), mode);
    }

    private String addAdditionalText(String message, Map<String, Serializable> options) {
        if (options != null && options.containsKey(SECOND_MESSAGE)) {
            return message + getSmallText((String) options.get(SECOND_MESSAGE));
        } else {
            return message;
        }
    }

    protected static String getSmallText(String text) {
        return "<div style='font-size:" + GUISizeHelper.gameDialogAreaFontSizeSmall + "pt'>" + text + "</div>";
    }

    private void setSpecial(String text, boolean visible) {
        this.btnSpecial.setText(text);
        this.btnSpecial.setVisible(visible);
        this.helper.setSpecial(text, visible);
    }

    /**
     * Close game window by pressing OK button after 8 seconds
     */
    private void endWithTimeout() {
        Runnable task = () -> {
            LOGGER.info("Ending game...");
            Component c = MageFrame.getGame(gameId);
            while (c != null && !(c instanceof GamePane)) {
                c = c.getParent();
            }
            if (c != null && c.isVisible()) { // check if GamePanel still visible
                FeedbackPanel.this.btnRight.doClick();
            }
        };
        WORKER.schedule(task, 8, TimeUnit.SECONDS);
    }

    public void updateOptions(Map<String, Serializable> options) {
        this.lastOptions = options;

        if (this.lastOptions != null) {
            if (this.lastOptions.containsKey("UI.left.btn.text")) {
                String text = (String) this.lastOptions.get("UI.left.btn.text");
                this.btnLeft.setText(text);
                this.helper.setLeft(text, !text.isEmpty());
            }
            if (this.lastOptions.containsKey("UI.right.btn.text")) {
                String text = (String) this.lastOptions.get("UI.right.btn.text");
                this.btnRight.setText(text);
                this.helper.setRight(text, !text.isEmpty());
            }
            updateConnectedDialog((MageDialog) this.lastOptions.getOrDefault("dialog", null));
            this.helper.autoSizeButtonsAndFeedbackState();
        } else {
            updateConnectedDialog(null);
        }
    }

    private void updateConnectedDialog(MageDialog newDialog) {
        if (this.connectedDialog != null && this.connectedDialog != newDialog) {
            // remove old
            this.connectedDialog.removeDialog();
        }
        this.connectedDialog = newDialog;
        if (this.connectedDialog != null) {
            this.connectedDialog.setVisible(true);
        }
    }

    // Issue 256: Chat+Feedback panel: request focus prevents players from chatting
    // Issue #1054: XMage steals window focus whenever the screen updates
    private void requestFocusIfPossible() {
        boolean requestFocusAllowed = true;
        if (MageFrame.getInstance().getFocusOwner() == null) {
            requestFocusAllowed = false;
        } else if (connectedChatPanel != null && connectedChatPanel.getTxtMessageInputComponent() != null) {
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
        this.btnLeft.setVisible(false);
        this.btnRight.setVisible(false);
        this.btnSpecial.setVisible(false);
    }

    private void customInitComponents() {
        btnRight = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnSpecial = new javax.swing.JButton();
        btnUndo = new javax.swing.JButton();
        btnUndo.setVisible(true);

        setBackground(new java.awt.Color(0, 0, 0, 80));

        btnRight.setText("Cancel");
        btnRight.addActionListener(evt -> btnRightActionPerformed(evt));

        btnLeft.setText("OK");
        btnLeft.addActionListener(evt -> btnLeftActionPerformed(evt));

        btnSpecial.setText("Special");
        btnSpecial.addActionListener(evt -> btnSpecialActionPerformed(evt));

        btnUndo.setText("Undo");
        btnUndo.addActionListener(evt -> btnUndoActionPerformed(evt));

    }

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        updateConnectedDialog(null);
        if (mode == FeedbackMode.SELECT && (evt.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            SessionHandler.sendPlayerInteger(gameId, 0);
        } else if (mode == FeedbackMode.END) {
            GamePanel gamePanel = MageFrame.getGame(gameId);
            if (gamePanel != null) {
                gamePanel.removeGame();
            }
        } else {
            SessionHandler.sendPlayerBoolean(gameId, false);
        }
        //AudioManager.playButtonOk();
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        SessionHandler.sendPlayerBoolean(gameId, true);
        AudioManager.playButtonCancel();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnSpecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpecialActionPerformed
        SessionHandler.sendPlayerString(gameId, "special");
    }//GEN-LAST:event_btnSpecialActionPerformed

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.UNDO, gameId, null);
    }

    public void setHelperPanel(HelperPanel helper) {
        this.helper = helper;
    }

    public FeedbackMode getMode() {
        return this.mode;
    }

    public void setConnectedChatPanel(ChatPanelBasic chatPanel) {
        this.connectedChatPanel = chatPanel;
    }

    public void pressOKYesOrDone() {
        if (btnLeft.getText().equals("OK") || btnLeft.getText().equals("Yes")) {
            btnLeft.doClick();
        } else if (btnRight.getText().equals("OK") || btnRight.getText().equals("Yes") || btnRight.getText().equals("Done")) {
            btnRight.doClick();
        }
    }

    public void allowUndo(int bookmark) {
        this.helper.setUndoEnabled(true);
    }

    public void disableUndo() {
        this.helper.setUndoEnabled(false);
    }

    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSpecial;
    private javax.swing.JButton btnUndo;
    private HelperPanel helper;
}
