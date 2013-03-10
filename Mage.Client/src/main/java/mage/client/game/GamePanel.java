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

import mage.Constants;
import mage.cards.action.ActionCallback;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.chat.ChatPanel;
import mage.client.combat.CombatManager;
import mage.client.components.HoverButton;
import mage.client.components.MageComponents;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.components.layout.RelativeLayout;
import mage.client.dialog.*;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.GameManager;
import mage.client.util.PhaseManager;
import mage.client.util.gui.ArrowBuilder;
import mage.remote.Session;
import mage.view.*;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko8
 */
public final class GamePanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(GamePanel.class);
    private static final String YOUR_HAND = "Your hand";
	private static final int X_PHASE_WIDTH = 55;
    private Map<UUID, PlayAreaPanel> players = new HashMap<UUID, PlayAreaPanel>();
    private Map<UUID, ExileZoneDialog> exiles = new HashMap<UUID, ExileZoneDialog>();
    private Map<String, ShowCardsDialog> revealed = new HashMap<String, ShowCardsDialog>();
    private Map<String, ShowCardsDialog> lookedAt = new HashMap<String, ShowCardsDialog>();
    private UUID gameId;
    private UUID playerId;
    private Session session;
    private ReplayTask replayTask;
    private CombatDialog combat;
    private PickNumberDialog pickNumber;
    private JLayeredPane jLayeredPane;
    private String chosenHandKey = "You";
    private boolean smallMode = false;
    private boolean initialized = false;

    private int storedHeight;
    
    private Map<String, HoverButton> hoverButtons;

    public GamePanel() {
        initComponents();

        combat = new CombatDialog();
        pickNumber = new PickNumberDialog();
        MageFrame.getDesktop().add(combat, JLayeredPane.POPUP_LAYER);
        combat.hideDialog();
        MageFrame.getDesktop().add(pickNumber, JLayeredPane.POPUP_LAYER);

        this.feedbackPanel.setConnectedChatPanel(this.userChatPanel);

        // Override layout (I can't edit generated code)
        this.setLayout(new BorderLayout());
        final JLayeredPane j = new JLayeredPane();
        j.setSize(1024, 768);
        this.add(j);
        j.add(jSplitPane0, JLayeredPane.DEFAULT_LAYER);

        Map<String, JComponent> myUi = getUIComponents(j);
        Plugins.getInstance().updateGamePanel(myUi);

        // Enlarge jlayeredpane on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = ((JComponent) e.getSource()).getWidth();
                int height = ((JComponent) e.getSource()).getHeight();
                j.setSize(width, height);
                jSplitPane0.setSize(width, height);

                if (height < storedHeight) {
                    pnlBattlefield.setSize(0, 200);
                }
                storedHeight = height;

                sizeToScreen();

                if (!initialized) {
                    String state = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_BIG_CARD_TOGGLED, null);
                    if (state != null && state.equals("down")) {
                        jSplitPane0.setDividerLocation(1.0);
                    }
                    initialized = true;
                }

            }
        });
    }

    private Map<String, JComponent> getUIComponents(JLayeredPane jLayeredPane) {
        Map<String, JComponent> components = new HashMap<String, JComponent>();

        components.put("jSplitPane1", jSplitPane1);
        components.put("pnlBattlefield", pnlBattlefield);
        components.put("jPanel3", jPanel3);
        components.put("hand", handContainer);
        components.put("gameChatPanel", gameChatPanel);
        components.put("userChatPanel", userChatPanel);
        components.put("jLayeredPane", jLayeredPane);
        components.put("gamePanel", this);

        return components;
    }

    public void cleanUp() {
        saveDividerLocations();
        this.gameChatPanel.disconnect();
        this.players.clear();
        logger.debug("players clear.");
        this.pnlBattlefield.removeAll();
        combat.hideDialog();
        pickNumber.hideDialog();
        for (ExileZoneDialog exile: exiles.values()) {
            exile.hideDialog();
        }
        for (ShowCardsDialog reveal: revealed.values()) {
            reveal.hideDialog();
        }
        try {
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (InterruptedException ex) {
            logger.fatal("popupContainer error:", ex);
        }
    }

    private void saveDividerLocations() {
        // save panel sizes and divider locations.
        Rectangle rec = MageFrame.getDesktop().getBounds();
        StringBuilder sb = new StringBuilder(Double.toString(rec.getWidth())).append("x").append(Double.toString(rec.getHeight()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_LAST_SIZE, sb.toString());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_0, Integer.toString(this.jSplitPane0.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_1, Integer.toString(this.jSplitPane1.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_2, Integer.toString(this.jSplitPane2.getDividerLocation()));
    }

    public void restoreDividerLocations() {
        Rectangle rec = MageFrame.getDesktop().getBounds();
        if (rec != null) {
            String size = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAMEPANEL_LAST_SIZE, null);
            StringBuilder sb = new StringBuilder(Double.toString(rec.getWidth())).append("x").append(Double.toString(rec.getHeight()));
            // use divider positions only if screen size is the same as it was the time the settings were saved
            if (size != null && size.equals(sb.toString())) {

                String location = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_0, null);
                if (location != null && jSplitPane0 != null) {
                    jSplitPane0.setDividerLocation(Integer.parseInt(location));
                }
                location = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_1, null);
                if (location != null && jSplitPane1 != null) {
                    jSplitPane1.setDividerLocation(Integer.parseInt(location));
                }
                location = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_2, null);
                if (location != null && jSplitPane2 != null) {
                    jSplitPane2.setDividerLocation(Integer.parseInt(location));
                }
            }
        }
    }

    private void sizeToScreen() {
        Rectangle rect = this.getBounds();

        if (rect.height < 720) {
            if (!smallMode) {
                smallMode = true;
                Dimension bbDimension = new Dimension(128, 184);
                bigCard.setMaximumSize(bbDimension);
                bigCard.setMinimumSize(bbDimension);
                bigCard.setPreferredSize(bbDimension);
                pnlGameInfo.revalidate();
                pnlGameInfo.repaint();
                this.handContainer.sizeHand(0.8, smallMode);
                for (PlayAreaPanel p: players.values()) {
                    p.sizePlayer(smallMode);
                }
            }
        }
        else {
            if (smallMode) {
                smallMode = false;
                Dimension bbDimension = new Dimension(256, 367);
                bigCard.setMaximumSize(bbDimension);
                bigCard.setMinimumSize(bbDimension);
                bigCard.setPreferredSize(bbDimension);
                pnlGameInfo.revalidate();
                pnlGameInfo.repaint();
                this.handContainer.sizeHand(1, smallMode);
                for (PlayAreaPanel p: players.values()) {
                    p.sizePlayer(smallMode);
                }
            }
        }

        ArrowBuilder.getBuilder().setSize(rect.width, rect.height);

        DialogManager.getManager(gameId).setScreenWidth(rect.width);
        DialogManager.getManager(gameId).setScreenHeight(rect.height);
        DialogManager.getManager(gameId).setBounds(0, 0, rect.width, rect.height);
    }

    public synchronized void showGame(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
        session = MageFrame.getSession();
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();
        this.abilityPicker.init(session, gameId);
        this.btnConcede.setVisible(true);
        this.btnEndTurn.setVisible(true);
        this.btnSwitchHands.setVisible(false);
        this.pnlReplay.setVisible(false);
        this.btnStopWatching.setVisible(false);
        this.gameChatPanel.clear();
        this.gameChatPanel.connect(session.getGameChatId(gameId));
        if (!session.joinGame(gameId)) {
            hideGame();
        }
    }

    public synchronized void watchGame(UUID gameId) {
        this.gameId = gameId;
        this.playerId = null;
        session = MageFrame.getSession();
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();
        this.btnConcede.setVisible(false);
        this.btnEndTurn.setVisible(false);
        this.btnSwitchHands.setVisible(false);
        this.btnStopWatching.setVisible(true);
        this.pnlReplay.setVisible(false);
        this.gameChatPanel.clear();
        this.gameChatPanel.connect(session.getGameChatId(gameId));
        if (!session.watchGame(gameId)) {
            hideGame();
        }
    }

    public synchronized void replayGame(UUID gameId) {
        this.gameId = gameId;
        this.playerId = null;
        session = MageFrame.getSession();
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();
        this.btnConcede.setVisible(false);
        this.btnEndTurn.setVisible(false);
        this.btnSwitchHands.setVisible(false);
        this.btnStopWatching.setVisible(false);
        this.pnlReplay.setVisible(true);
        this.gameChatPanel.clear();
        if (!session.startReplay(gameId)) {
            hideGame();
        }
    }

    public void hideGame() {
        cleanUp();
        Component c = this.getParent();
        while (c != null && !(c instanceof GamePane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((GamePane)c).hideFrame();
        }
    }

    public synchronized void init(GameView game) {
        logger.warn("init.");
        combat.init(gameId, bigCard);
        combat.setLocation(500, 300);
        addPlayers(game);
        logger.warn("added players.");
        updateGame(game);
    }

    private void addPlayers(GameView game) {
        this.players.clear();
        this.pnlBattlefield.removeAll();
        //arrange players in a circle with the session player at the bottom left
        int numSeats = game.getPlayers().size();
        int numColumns = (numSeats + 1) / 2;
        boolean oddNumber = (numColumns > 1 && numSeats % 2 == 1);
        int col = 0;
        int row = 1;
        int playerSeat = 0;
        if (playerId != null) {
            for (PlayerView player: game.getPlayers()) {
                if (playerId.equals(player.getPlayerId())) {
                    break;
                }
                playerSeat++;
            }
        }
        PlayerView player = game.getPlayers().get(playerSeat);
        PlayAreaPanel sessionPlayer = new PlayAreaPanel(player, bigCard, gameId, true);
        players.put(player.getPlayerId(), sessionPlayer);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        if (oddNumber) {
            c.gridwidth = 2;
        }
        c.gridx = col;
        c.gridy = row;
        this.pnlBattlefield.add(sessionPlayer, c);
        sessionPlayer.setVisible(true);
        if (oddNumber) {
            col++;
        }
        int playerNum = playerSeat + 1;
        if (playerNum >= numSeats) {
            playerNum = 0;
        }
        while (true) {
            if (row == 1) {
                col++;
            }
            else {
                col--;
            }
            if (col >= numColumns) {
                row = 0;
                col = numColumns - 1;
            }
            player = game.getPlayers().get(playerNum);
            PlayAreaPanel playerPanel = new PlayAreaPanel(player, bigCard, gameId, false);
            players.put(player.getPlayerId(), playerPanel);
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0.5;
            c.weighty = 0.5;
            c.gridx = col;
            c.gridy = row;
            this.pnlBattlefield.add(playerPanel, c);
            playerPanel.setVisible(true);
            playerNum++;
            if (playerNum >= numSeats) {
                playerNum = 0;
            }
            if (playerNum == playerSeat) {
                break;
            }
        }
        for (PlayAreaPanel p: players.values()) {
            p.sizePlayer(smallMode);
        }
    }

    public synchronized void updateGame(GameView game) {
        if (playerId == null || game.getHand() == null) {
            this.handContainer.setVisible(false);
        } else {
            handCards.clear();
            handCards.put(YOUR_HAND, game.getHand());

            // Get opponents hand cards if available
            if (game.getOpponentHands() != null) {
                for (Map.Entry<String, SimpleCardsView> hand: game.getOpponentHands().entrySet()) {
                    handCards.put(hand.getKey(), hand.getValue());
                }
            }

            if (!handCards.containsKey(chosenHandKey)) {
                chosenHandKey = YOUR_HAND;
            }
            handContainer.loadCards(handCards.get(chosenHandKey), bigCard, gameId);

            hideAll();

            // set visible only if we have any other hand visible than ours
            boolean previous = btnSwitchHands.isVisible();
            boolean visible = handCards.size() > 1;
            if (previous != visible) {
                btnSwitchHands.setVisible(visible);
                if (visible) {
                    JOptionPane.showMessageDialog(null, "You control other player's turn. \nUse \"Switch Hand\" on the bottom to switch between cards in different hands.");
                } else {
                    JOptionPane.showMessageDialog(null, "You lost control on other player's turn.");
                }
            }
        }

        if (game.getPhase() != null) {
            this.txtPhase.setText(game.getPhase().toString());
        } else {
            this.txtPhase.setText("");
        }
        updatePhases(game.getStep());

        if (game.getStep() != null) {
            this.txtStep.setText(game.getStep().toString());
        }
        else {
            this.txtStep.setText("");
        }
        this.txtActivePlayer.setText(game.getActivePlayerName());
        this.txtPriority.setText(game.getPriorityPlayerName());
        this.txtTurn.setText(Integer.toString(game.getTurn()));
        for (PlayerView player: game.getPlayers()) {
            if (players.containsKey(player.getPlayerId())) {
                players.get(player.getPlayerId()).update(player);
            } else {
                logger.warn("Couldn't find player.");
                logger.warn("   uuid:" + player.getPlayerId());
                logger.warn("   players:");
                for (PlayAreaPanel p : players.values()) {
                    logger.warn(""+p);
                }
            }
        }

        GameManager.getInstance().setStackSize(game.getStack().size());
        displayStack(game, bigCard, feedbackPanel, gameId);

        for (ExileView exile: game.getExile()) {
            if (!exiles.containsKey(exile.getId())) {
                ExileZoneDialog newExile = new ExileZoneDialog();
                exiles.put(exile.getId(), newExile);
                MageFrame.getDesktop().add(newExile, JLayeredPane.MODAL_LAYER);
                newExile.show();
            }
            exiles.get(exile.getId()).loadCards(exile, bigCard, gameId);
        }
        showRevealed(game);
        showLookedAt(game);
        if (game.getCombat().size() > 0) {
            CombatManager.getInstance().showCombat(game.getCombat(), gameId);
        } else {
            CombatManager.getInstance().hideCombat(gameId);
        }
        this.revalidate();
        this.repaint();
    }

    private void displayStack(GameView game, BigCard bigCard, FeedbackPanel feedbackPanel, UUID gameId) {
        this.stack.loadCards(game.getStack(), bigCard, gameId, null);
    }

    /**
     * Update phase buttons\labels.
     */
    private void updatePhases(Constants.PhaseStep step) {
        if (step == null) {
            logger.warn("step is null");
            return;
        }
        if (currentStep != null) {
            currentStep.setLocation(prevPoint);
        }
        switch (step) {
            case UNTAP: updateButton("Untap"); break;
            case UPKEEP: updateButton("Upkeep"); break;
            case DRAW: updateButton("Draw"); break;
            case PRECOMBAT_MAIN: updateButton("Main1"); break;
            case BEGIN_COMBAT: updateButton("Combat_Start"); break;
            case DECLARE_ATTACKERS: updateButton("Combat_Attack"); break;
            case DECLARE_BLOCKERS: updateButton("Combat_Block"); break;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE: updateButton("Combat_Damage"); break;
            case END_COMBAT: updateButton("Combat_End"); break;
            case POSTCOMBAT_MAIN: updateButton("Main2"); break;
            case END_TURN: updateButton("Cleanup"); break;
        }
    }

    private void updateButton(String name) {
        if (hoverButtons.containsKey(name)) {
            currentStep = hoverButtons.get(name);
            prevPoint = currentStep.getLocation();
            currentStep.setLocation(prevPoint.x - 15, prevPoint.y);
        }
    }

    private void showRevealed(GameView game) {
        for (ShowCardsDialog reveal: revealed.values()) {
            reveal.clearReloaded();
        }
        for (RevealedView reveal: game.getRevealed()) {
            if (!revealed.containsKey(reveal.getName())) {
                ShowCardsDialog newReveal = new ShowCardsDialog();
                revealed.put(reveal.getName(), newReveal);
            }
            revealed.get(reveal.getName()).loadCards("Revealed " + reveal.getName(), reveal.getCards(), bigCard, Config.dimensions, gameId, false);
        }
    }

    private void showLookedAt(GameView game) {
        for (ShowCardsDialog looked: lookedAt.values()) {
            looked.clearReloaded();
        }
        for (LookedAtView looked: game.getLookedAt()) {
            if (!lookedAt.containsKey(looked.getName())) {
                ShowCardsDialog newReveal = new ShowCardsDialog();
                lookedAt.put(looked.getName(), newReveal);
            }
            lookedAt.get(looked.getName()).loadCards("Looked at by " + looked.getName(), looked.getCards(), bigCard, Config.dimensions, gameId, false);
        }
    }

    public void ask(String question, GameView gameView) {
        updateGame(gameView);
        this.feedbackPanel.getFeedback(FeedbackMode.QUESTION, question, false, null);
    }

    public void pickTarget(String message, CardsView cardView, GameView gameView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        updateGame(gameView);
        Map<String, Serializable> options0 = options == null ? new HashMap<String, Serializable>() : options;
        if (cardView != null && cardView.size() > 0) {
            ShowCardsDialog dialog = showCards(message, cardView, required, options0);
            options0.put("dialog", dialog);
        }
        this.feedbackPanel.getFeedback(required?FeedbackMode.INFORM:FeedbackMode.CANCEL, message, gameView.getSpecial(), options0);

    }

    public void inform(String information, GameView gameView) {
        updateGame(gameView);
        this.feedbackPanel.getFeedback(FeedbackMode.INFORM, information, gameView.getSpecial(), null);
    }

    public void endMessage(String message) {
        this.feedbackPanel.getFeedback(FeedbackMode.END, message, false, null);
        ArrowBuilder.getBuilder().removeAllArrows(gameId);
    }

    public int modalQuestion(String message, String title) {
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
    }

    public void select(String message, GameView gameView) {
        updateGame(gameView);
        String messageToDisplay = message;
        Map<String, Serializable> options = null;
        for (PlayerView playerView : gameView.getPlayers()) {
            if (playerView.getPlayerId().equals(playerId)) {
                if (playerView.isActive()) {
                    options = new HashMap<String, Serializable>();
                    options.put("your_turn", true);
                    messageToDisplay = message + " <div style='font-size:11pt'>Your turn</div>";
                }
                break;
            }
        }
        this.feedbackPanel.getFeedback(FeedbackMode.SELECT, messageToDisplay, gameView.getSpecial(), options);
        if (PhaseManager.getInstance().isSkip(gameView, message)) {
            this.feedbackPanel.doClick();
        }
    }

    public void playMana(String message, GameView gameView) {
        updateGame(gameView);
        DialogManager.getManager(gameId).fadeOut();
        this.feedbackPanel.getFeedback(FeedbackMode.CANCEL, message, gameView.getSpecial(), null);
    }

    public void playXMana(String message, GameView gameView) {
        updateGame(gameView);
        DialogManager.getManager(gameId).fadeOut();
        this.feedbackPanel.getFeedback(FeedbackMode.CONFIRM, message, gameView.getSpecial(), null);
    }

    public void replayMessage(String message) {
        //TODO: implement this
    }

    public void pickAbility(AbilityPickerView choices) {
        hideAll();
        DialogManager.getManager(gameId).fadeOut();
        this.abilityPicker.show(choices, MageFrame.getDesktop().getMousePosition());
    }

    private void hideAll() {
        ActionCallback callback = Plugins.getInstance().getActionCallback();
        ((MageActionCallback)callback).hideAll(gameId);
    }

    private ShowCardsDialog showCards(String title, CardsView cards, boolean required, Map<String, Serializable> options) {
        hideAll();
        ShowCardsDialog showCards = new ShowCardsDialog();
        showCards.loadCards(title, cards, bigCard, Config.dimensionsEnlarged, gameId, required, options);
        return showCards;
    }

    public void getAmount(int min, int max, String message) {
        pickNumber.showDialog(min, max, message);
        if (pickNumber.isCancel()) {
            session.sendPlayerBoolean(gameId, false);
        }
        else {
            session.sendPlayerInteger(gameId, pickNumber.getAmount());
        }
    }

    public void getChoice(String message, String[] choices) {
        hideAll();
        PickChoiceDialog pickChoice = new PickChoiceDialog();
        pickChoice.showDialog(message, choices);
        session.sendPlayerString(gameId, pickChoice.getChoice());
    }

    public void pickPile(String message, CardsView pile1, CardsView pile2) {
        hideAll();
        PickPileDialog pickPileDialog = new PickPileDialog();
        pickPileDialog.loadCards(message, pile1, pile2, bigCard, Config.dimensions, gameId);
        session.sendPlayerBoolean(gameId, pickPileDialog.isPickedPile1());
    }

    public Map<UUID, PlayAreaPanel> getPlayers() {
        return players;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        abilityPicker = new mage.client.components.ability.AbilityPicker();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane0 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        pnlGameInfo = new javax.swing.JPanel();
        lblPhase = new javax.swing.JLabel();
        txtPhase = new javax.swing.JLabel();
        lblStep = new javax.swing.JLabel();
        txtStep = new javax.swing.JLabel();
        lblTurn = new javax.swing.JLabel();
        txtTurn = new javax.swing.JLabel();
        txtActivePlayer = new javax.swing.JLabel();
        lblActivePlayer = new javax.swing.JLabel();
        txtPriority = new javax.swing.JLabel();
        lblPriority = new javax.swing.JLabel();
        feedbackPanel = new mage.client.game.FeedbackPanel();
        btnConcede = new javax.swing.JButton();
        btnEndTurn = new javax.swing.JButton();
        btnSwitchHands = new javax.swing.JButton();
        btnStopWatching = new javax.swing.JButton();
        bigCard = new mage.client.cards.BigCard();
        stack = new mage.client.cards.Cards();
        pnlReplay = new javax.swing.JPanel();
        btnStopReplay = new javax.swing.JButton();
        btnNextPlay = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        btnSkipForward = new javax.swing.JButton();
        btnPreviousPlay = new javax.swing.JButton();
        pnlBattlefield = new javax.swing.JPanel();
        gameChatPanel = new mage.client.chat.ChatPanel();
        gameChatPanel.useExtendedView(ChatPanel.VIEW_MODE.GAME);
        userChatPanel = new mage.client.chat.ChatPanel();
        userChatPanel.setParentChat(gameChatPanel);
        userChatPanel.useExtendedView(ChatPanel.VIEW_MODE.CHAT);
        gameChatPanel.setConnectedChat(userChatPanel);
        gameChatPanel.disableInput();
        gameChatPanel.setMinimumSize(new java.awt.Dimension(100, 48));
        jSplitPane2 = new javax.swing.JSplitPane();
        handContainer = new HandPanel();

        handCards = new HashMap<String, SimpleCardsView>();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(7);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(26, 48));

        jSplitPane0.setBorder(null);
        jSplitPane0.setDividerSize(7);
        jSplitPane0.setResizeWeight(1.0);
        jSplitPane0.setOneTouchExpandable(true);

        restoreDividerLocations();

        pnlGameInfo.setOpaque(false);

        lblPhase.setLabelFor(txtPhase);
        lblPhase.setText("Phase:");

        txtPhase.setText("Phase");
        txtPhase.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtPhase.setMinimumSize(new java.awt.Dimension(0, 16));

        lblStep.setLabelFor(txtStep);
        lblStep.setText("Step:");

        txtStep.setText("Step");
        txtStep.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtStep.setMinimumSize(new java.awt.Dimension(0, 16));

        lblTurn.setLabelFor(txtTurn);
        lblTurn.setText("Turn:");

        txtTurn.setText("Turn");
        txtTurn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtTurn.setMinimumSize(new java.awt.Dimension(0, 16));

        txtActivePlayer.setText("Active Player");
        txtActivePlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtActivePlayer.setMinimumSize(new java.awt.Dimension(0, 16));

        lblActivePlayer.setLabelFor(txtActivePlayer);
        lblActivePlayer.setText("Active Player:");

        txtPriority.setText("Priority Player");
        txtPriority.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtPriority.setMinimumSize(new java.awt.Dimension(0, 16));

        lblPriority.setLabelFor(txtPriority);
        lblPriority.setText("Priority Player:");

        feedbackPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        feedbackPanel.setMaximumSize(new java.awt.Dimension(208, 121));
        feedbackPanel.setMinimumSize(new java.awt.Dimension(208, 121));

        bigCard.setBorder(new LineBorder(Color.black, 1, true));

        btnConcede.setText("Concede");
        btnConcede.setToolTipText("Concede the current game");
        btnConcede.setFocusable(false);
        btnConcede.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                btnConcedeActionPerformed(null);
            }
        });

        btnEndTurn.setText("End Turn (F4)");
        btnEndTurn.setToolTipText("End This Turn");
        btnEndTurn.setFocusable(false);
        btnEndTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                btnEndTurnActionPerformed(null);
            }
        });

        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);

        this.getInputMap(c).put(ks, "F4_PRESS");
        this.getActionMap().put("F4_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnEndTurnActionPerformed(null);
            }
        });

        KeyStroke ks8 = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0);
        this.getInputMap(c).put(ks8, "F9_PRESS");
        this.getActionMap().put("F9_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (feedbackPanel != null && FeedbackMode.SELECT.equals(feedbackPanel.getMode())) {
                    session.sendPlayerInteger(gameId, -9999);
                }
            }
        });

        KeyStroke ksAltE = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltE, "ENLARGE");
        this.getActionMap().put("ENLARGE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ActionCallback callback = Plugins.getInstance().getActionCallback();
                ((MageActionCallback)callback).enlargeCard();
            }
        });

        KeyStroke ksAltD = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltD, "BIG_IMAGE");
        this.getActionMap().put("BIG_IMAGE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                imagePanelState = !imagePanelState;
                if (!imagePanelState) {
                    jSplitPane0.resetToPreferredSizes();
                    jSplitPane0.setDividerLocation(jSplitPane0.getSize().width - jSplitPane0.getInsets().right - jSplitPane0.getDividerSize() - 260);
                } else {
                    jSplitPane0.setDividerLocation(1.0);
                }
            }
        });

        final BasicSplitPaneUI myUi = (BasicSplitPaneUI) jSplitPane0.getUI();
        final BasicSplitPaneDivider divider = myUi.getDivider();
        final JButton upArrowButton = (JButton) divider.getComponent(0);
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PreferencesDialog.saveValue(PreferencesDialog.KEY_BIG_CARD_TOGGLED, "up");
            }
        });

        final JButton downArrowButton = (JButton) divider.getComponent(1);
        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PreferencesDialog.saveValue(PreferencesDialog.KEY_BIG_CARD_TOGGLED, "down");
            }
        });

        KeyStroke ksAltShiftReleased = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAltShiftReleased, "ENLARGE_RELEASE");
        this.getActionMap().put("ENLARGE_RELEASE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ActionCallback callback = Plugins.getInstance().getActionCallback();
                ((MageActionCallback)callback).hideCard();
            }
        });

        btnSwitchHands.setText("Switch Hands");
        btnSwitchHands.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                btnSwitchHandActionPerformed(null);
            }
        });

        btnStopWatching.setText("Stop Watching");
        btnStopWatching.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopWatchingActionPerformed(evt);
            }
        });

        stack.setPreferredSize(new java.awt.Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 25));
        stack.setBackgroundColor(new Color(0,0,0,0));

        btnStopReplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop.png")));
        btnStopReplay.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopReplayActionPerformed(evt);
            }
        });

        btnNextPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop_right.png")));
        btnNextPlay.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPlayActionPerformed(evt);
            }
        });

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_right.png")));
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnSkipForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_double_stop_right.png")));
        btnSkipForward.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkipForwardActionPerformed(evt);
            }
        });

        btnPreviousPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop_left.png")));
        btnPreviousPlay.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gl_pnlReplay = new javax.swing.GroupLayout(pnlReplay);
        pnlReplay.setLayout(gl_pnlReplay);
        gl_pnlReplay.setHorizontalGroup(
            gl_pnlReplay.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_pnlReplay.createSequentialGroup()
                .addComponent(btnPreviousPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStopReplay, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSkipForward, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        gl_pnlReplay.setVerticalGroup(
            gl_pnlReplay.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSkipForward, 0, 0, Short.MAX_VALUE)
            .addComponent(btnNextPlay, 0, 0, Short.MAX_VALUE)
            .addComponent(btnStopReplay, 0, 0, Short.MAX_VALUE)
            .addComponent(btnPlay, 0, 0, Short.MAX_VALUE)
            .addComponent(btnPreviousPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout gl_pnlGameInfo = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(gl_pnlGameInfo);
        gl_pnlGameInfo.setHorizontalGroup(
                gl_pnlGameInfo.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(gl_pnlGameInfo.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnConcede)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEndTurn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSwitchHands)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnStopWatching)
                                .addContainerGap(62, Short.MAX_VALUE))
                                //.addComponent(bigCard, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                //.addComponent(feedbackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                //.addComponent(stack, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                        .addGroup(gl_pnlGameInfo.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(51, Short.MAX_VALUE))
        );
        gl_pnlGameInfo.setVerticalGroup(
            gl_pnlGameInfo.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_pnlGameInfo.createSequentialGroup()
                    //.addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addGap(1, 1, 1)
                            //.addComponent(feedbackPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                    .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(gl_pnlGameInfo.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConcede)
                            .addComponent(btnEndTurn)
                            .addComponent(btnSwitchHands)
                            .addComponent(btnStopWatching)))
        );

        pnlBattlefield.setLayout(new java.awt.GridBagLayout());

        jPhases = new JPanel();
        jPhases.setBackground(new Color(0, 0, 0, 0));
        jPhases.setLayout(null);
        jPhases.setPreferredSize(new Dimension(X_PHASE_WIDTH, 450));

        String[] phases = {"Untap", "Upkeep", "Draw", "Main1",
                "Combat_Start", "Combat_Attack", "Combat_Block", "Combat_Damage", "Combat_End",
                "Main2", "Cleanup", "Next_Turn"};
        for (String name : phases) {
            createPhaseButton(name);
        }

        int i = 0;
        for (String name : hoverButtons.keySet()) {
            HoverButton hoverButton = hoverButtons.get(name);
            hoverButton.setAlignmentX(LEFT_ALIGNMENT);
            hoverButton.setBounds(X_PHASE_WIDTH - 36, i*36, 36, 36);
            jPhases.add(hoverButton);
            i++;
        }

        pnlReplay.setOpaque(false);
        HelperPanel helper = new HelperPanel();
        helper.setPreferredSize(new Dimension(100, 90));
        feedbackPanel.setHelperPanel(helper);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.setLeftComponent(userChatPanel);
        jSplitPane2.setBottomComponent(gameChatPanel);

        phasesContainer = new JPanel();
        phasesContainer.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS));
        phasesContainer.setBackground(new Color(0, 0, 0, 0));
        Float ratio = new Float(1);
        JPanel empty1 = new JPanel();
        empty1.setBackground(new Color(0, 0, 0, 0));
        phasesContainer.add(empty1, ratio);
        phasesContainer.add(jPhases);
        JPanel empty2 = new JPanel();
        empty2.setBackground(new Color(0, 0, 0, 0));
        phasesContainer.add(empty2, ratio);

        javax.swing.GroupLayout gl_jPanel3 = new javax.swing.GroupLayout(jPanel3);
        gl_jPanel3.setHorizontalGroup(
            gl_jPanel3.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_jPanel3.createSequentialGroup()
                        //.addComponent(pnlGameInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        //.addGap(0)
                        .addGroup(gl_jPanel3.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_jPanel3.createSequentialGroup()
                                        .addGroup(gl_jPanel3.createParallelGroup(Alignment.LEADING)
                                                .addComponent(helper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(handContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        )
                                        .addComponent(stack, 400, 400, 400)

                                )
                                .addGap(0)
                                        //.addComponent(jPhases, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(gl_jPanel3.createSequentialGroup()
                                        .addComponent(pnlBattlefield, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(phasesContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                )))
        );
        gl_jPanel3.setVerticalGroup(
                gl_jPanel3.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_jPanel3.createSequentialGroup()
                                .addGroup(gl_jPanel3.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pnlBattlefield, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(phasesContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                )
                                        //.addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_jPanel3.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_jPanel3.createSequentialGroup()
                                                .addGap(85)
                                                .addComponent(stack, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl_jPanel3.createSequentialGroup()
                                                .addComponent(helper, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(handContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                        )
        );
        jPanel3.setLayout(gl_jPanel3);

        jSplitPane1.setLeftComponent(jPanel3);
        jSplitPane1.setRightComponent(jSplitPane2);

        // Set individual area sizes of big card pane
        GridBagLayout gbl = new GridBagLayout();
        jPanel2.setLayout( gbl );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1; gbc.gridheight = 4; // size 4/5
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbl.setConstraints( bigCard, gbc );
        jPanel2.add( bigCard );

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.NONE;
        gbc2.gridx = 0; gbc2.gridy = GridBagConstraints.RELATIVE;
        gbc2.gridwidth = 1; gbc2.gridheight = 1; //size 1/5
        gbc2.weightx = 0.0; gbc2.weighty = 0.0;
        gbl.setConstraints( pnlGameInfo, gbc2 );
        jPanel2.add( pnlGameInfo );

        jPanel2.setOpaque(false);

        // game pane and chat/log pane
        jSplitPane0.setLeftComponent(jSplitPane1);
        // big card and buttons
        jSplitPane0.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane0, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane0, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
        );
    }

    private void btnConcedeActionPerformed(java.awt.event.ActionEvent evt) {
        if (modalQuestion("Are you sure you want to concede?", "Confirm concede") == JOptionPane.YES_OPTION) {
            session.concedeGame(gameId);
        }
    }

    private void btnEndTurnActionPerformed(java.awt.event.ActionEvent evt) {
        if (feedbackPanel != null && FeedbackMode.SELECT.equals(feedbackPanel.getMode())) {
            session.sendPlayerInteger(gameId, 0);
        }
    }

    private void btnSwitchHandActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String[] choices = handCards.keySet().toArray(new String[0]);

        String newChosenHandKey = (String) JOptionPane.showInputDialog(
                    this,
                    "Choose hand to display:", "Switch between hands",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    choices,
                    this.chosenHandKey);

        if (newChosenHandKey != null && newChosenHandKey.length() > 0) {
            this.chosenHandKey = newChosenHandKey;
            SimpleCardsView cards = handCards.get(chosenHandKey);
            handContainer.loadCards(cards, bigCard, gameId);
        }
    }

    private void btnStopWatchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopWatchingActionPerformed
        if (modalQuestion("Are you sure you want to stop watching?", "Stop watching") == JOptionPane.YES_OPTION) {
            session.stopWatching(gameId);
            this.hideGame();
        }
    }//GEN-LAST:event_btnStopWatchingActionPerformed

    private void btnStopReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopReplayActionPerformed
        if (replayTask != null && !replayTask.isDone()) {
            replayTask.cancel(true);
        }
        else if (modalQuestion("Are you sure you want to stop replay?", "Stop replay") == JOptionPane.YES_OPTION) {
            session.stopReplay(gameId);
        }
    }//GEN-LAST:event_btnStopReplayActionPerformed

    private void btnNextPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPlayActionPerformed
        session.nextPlay(gameId);
    }//GEN-LAST:event_btnNextPlayActionPerformed

    private void btnPreviousPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousPlayActionPerformed
        session.previousPlay(gameId);
    }//GEN-LAST:event_btnPreviousPlayActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        if (replayTask == null || replayTask.isDone()) {
            replayTask = new ReplayTask(session, gameId);
            replayTask.execute();
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnSkipForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipForwardActionPerformed
        session.skipForward(gameId, 10);
    }//GEN-LAST:event_btnSkipForwardActionPerformed

    public void setJLayeredPane(JLayeredPane jLayeredPane) {
        this.jLayeredPane = jLayeredPane;
    }

    public void installComponents() {
        jLayeredPane.setOpaque(false);
        jLayeredPane.add(abilityPicker);
        jLayeredPane.add(DialogManager.getManager(gameId), JLayeredPane.MODAL_LAYER, 0);
        abilityPicker.setVisible(false);
    }

    private void createPhaseButton(String name) {
        if (hoverButtons == null) {
            hoverButtons = new LinkedHashMap<String, HoverButton>();
        }
        Rectangle rect = new Rectangle(36, 36);
        HoverButton button = new HoverButton("", ImageManagerImpl.getInstance().getPhaseImage(name), rect);
        button.setToolTipText(name.replaceAll("_", " "));
        button.setPreferredSize(new Dimension(36, 36));
        hoverButtons.put(name, button);
    }

    private mage.client.components.ability.AbilityPicker abilityPicker;
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnConcede;
    private javax.swing.JButton btnEndTurn;
    private javax.swing.JButton btnSwitchHands;
    private javax.swing.JButton btnNextPlay;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPreviousPlay;
    private javax.swing.JButton btnSkipForward;
    private javax.swing.JButton btnStopReplay;
    private javax.swing.JButton btnStopWatching;
    private mage.client.chat.ChatPanel gameChatPanel;
    private mage.client.game.FeedbackPanel feedbackPanel;
    private mage.client.chat.ChatPanel userChatPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane0;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblActivePlayer;
    private javax.swing.JLabel lblPhase;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblStep;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JPanel pnlBattlefield;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlReplay;
    private javax.swing.JLabel txtActivePlayer;
    private javax.swing.JLabel txtPhase;
    private javax.swing.JLabel txtPriority;
    private javax.swing.JLabel txtStep;
    private javax.swing.JLabel txtTurn;

    private Map<String, SimpleCardsView> handCards;
    private mage.client.cards.Cards stack;
    private HandPanel handContainer;

    private javax.swing.JSplitPane jSplitPane2;
    private JPanel jPhases;
    private JPanel phasesContainer;


    private HoverButton currentStep;
    private Point prevPoint;

    private boolean imagePanelState;

}

class ReplayTask extends SwingWorker<Void, Collection<MatchView>> {

    private Session session;
    private UUID gameId;

    private static final Logger logger = Logger.getLogger(ReplayTask.class);

    ReplayTask(Session session, UUID gameId) {
        this.session = session;
        this.gameId = gameId;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            session.nextPlay(gameId);
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException ex) {
            logger.fatal("Replay Match Task error", ex);
        } catch (ExecutionException ex) {
            logger.fatal("Replay Match Task error", ex);
        } catch (CancellationException ex) {}
    }
}
