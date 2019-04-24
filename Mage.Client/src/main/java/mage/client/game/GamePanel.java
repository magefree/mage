
package mage.client.game;

import mage.cards.Card;
import mage.cards.action.ActionCallback;
import mage.choices.Choice;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.chat.ChatPanelBasic;
import mage.client.combat.CombatManager;
import mage.client.components.HoverButton;
import mage.client.components.KeyboundButton;
import mage.client.components.MageComponents;
import mage.client.components.ext.dlg.DialogManager;
import mage.client.components.layout.RelativeLayout;
import mage.client.dialog.*;
import mage.client.dialog.CardInfoWindowDialog.ShowType;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.*;
import mage.client.util.Event;
import mage.client.util.audio.AudioManager;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.MageDialogState;
import mage.constants.*;
import mage.game.events.PlayerQueryEvent;
import mage.view.*;
import org.apache.log4j.Logger;
import org.mage.card.arcane.CardPanel;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static mage.client.dialog.PreferencesDialog.*;
import static mage.constants.PlayerAction.*;

/**
 * @author BetaSteward_at_googlemail.com, nantuko8
 */
public final class GamePanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(GamePanel.class);
    private static final String YOUR_HAND = "Your hand";
    private static final int X_PHASE_WIDTH = 55;
    private static final int STACK_MIN_CARDS_OFFSET_Y = 7;  // TODO: Size bui GUISize value

    private static final String CMD_AUTO_ORDER_FIRST = "cmdAutoOrderFirst";
    private static final String CMD_AUTO_ORDER_LAST = "cmdAutoOrderLast";
    private static final String CMD_AUTO_ORDER_NAME_FIRST = "cmdAutoOrderNameFirst";
    private static final String CMD_AUTO_ORDER_NAME_LAST = "cmdAutoOrderNameLast";
    private static final String CMD_AUTO_ORDER_RESET_ALL = "cmdAutoOrderResetAll";

    private final Map<UUID, PlayAreaPanel> players = new HashMap<>();
    private final Map<UUID, Boolean> playersWhoLeft = new HashMap<>();

    // non modal frames
    private final Map<UUID, CardInfoWindowDialog> exiles = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> revealed = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> lookedAt = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> graveyardWindows = new HashMap<>();
    private final Map<String, CardsView> graveyards = new HashMap<>();

    private final ArrayList<ShowCardsDialog> pickTarget = new ArrayList<>();
    private UUID gameId;
    private UUID playerId; // playerId of the player
    GamePane gamePane;
    private ReplayTask replayTask;
    private final PickNumberDialog pickNumber;
    private JLayeredPane jLayeredPane;
    private String chosenHandKey = "You";
    private boolean smallMode = false;
    private boolean initialized = false;

    private boolean menuNameSet = false;
    private boolean handCardsOfOpponentAvailable = false;

    private Map<String, Card> loadedCards = new HashMap<>();

    private int storedHeight;
    private Map<String, HoverButton> hoverButtons;

    private MageDialogState choiceWindowState;

    private boolean initComponents;

    private Timer resizeTimer;

    private enum PopUpMenuType {

        TRIGGER_ORDER
    }

    // CardView popupMenu was invoked last
    private CardView cardViewPopupMenu;

    // popup menu for triggered abilities order
    private JPopupMenu popupMenuTriggerOrder;

    public GamePanel() {
        initComponents = true;
        initComponents();

        pickNumber = new PickNumberDialog();
        MageFrame.getDesktop().add(pickNumber, JLayeredPane.MODAL_LAYER);

        this.feedbackPanel.setConnectedChatPanel(this.userChatPanel);

        this.stackObjects.setMinOffsetY(STACK_MIN_CARDS_OFFSET_Y);

        // Override layout (I can't edit generated code)
        this.setLayout(new BorderLayout());
        final JLayeredPane jLayeredBackgroundPane = new JLayeredPane();
        jLayeredBackgroundPane.setSize(1024, 768);
        this.add(jLayeredBackgroundPane);
        jLayeredBackgroundPane.add(jSplitPane0, JLayeredPane.DEFAULT_LAYER);

        Map<String, JComponent> myUi = getUIComponents(jLayeredBackgroundPane);
        Plugins.instance.updateGamePanel(myUi);

        // Enlarge jlayeredpane on resize of game panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = ((JComponent) e.getSource()).getWidth();
                int height = ((JComponent) e.getSource()).getHeight();
                jLayeredBackgroundPane.setSize(width, height);
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
        // Resize the width of the stack area if the size of the play area is changed
        ComponentAdapter componentAdapterPlayField = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!initComponents) {
                    if (resizeTimer.isRunning()) {
                        resizeTimer.restart();
                    } else {
                        resizeTimer.start();
                    }
                }
            }
        };

        resizeTimer = new Timer(1000, evt -> SwingUtilities.invokeLater(() -> {
            resizeTimer.stop();
            setGUISize();
            feedbackPanel.changeGUISize();

        }));

        pnlHelperHandButtonsStackArea.addComponentListener(componentAdapterPlayField);
        initComponents = false;
    }

    private Map<String, JComponent> getUIComponents(JLayeredPane jLayeredPane) {
        Map<String, JComponent> components = new HashMap<>();

        components.put("jSplitPane1", jSplitPane1);
        components.put("pnlBattlefield", pnlBattlefield);
        components.put("pnlHelperHandButtonsStackArea", pnlHelperHandButtonsStackArea);
        components.put("hand", handContainer);
        components.put("gameChatPanel", gameChatPanel);
        components.put("userChatPanel", userChatPanel);
        components.put("jLayeredPane", jLayeredPane);
        components.put("gamePanel", this);

        return components;
    }

    public void cleanUp() {
        MageFrame.removeGame(gameId);
        saveDividerLocations();
        this.gameChatPanel.disconnect();
        this.userChatPanel.disconnect();

        this.removeListener();

        this.handContainer.cleanUp();
        this.stackObjects.cleanUp();
        for (Map.Entry<UUID, PlayAreaPanel> playAreaPanelEntry : players.entrySet()) {
            playAreaPanelEntry.getValue().CleanUp();
        }
        this.players.clear();
        this.playersWhoLeft.clear();

        if (jLayeredPane != null) {
            jLayeredPane.remove(abilityPicker);
            jLayeredPane.remove(DialogManager.getManager(gameId));
        }
        this.abilityPicker.cleanUp();
        DialogManager.removeGame(gameId);

        if (pickNumber != null) {
            pickNumber.removeDialog();
        }
        for (CardInfoWindowDialog exileDialog : exiles.values()) {
            exileDialog.cleanUp();
            exileDialog.removeDialog();
        }
        for (CardInfoWindowDialog graveyardDialog : graveyardWindows.values()) {
            graveyardDialog.cleanUp();
            graveyardDialog.removeDialog();
        }
        for (CardInfoWindowDialog revealDialog : revealed.values()) {
            revealDialog.cleanUp();
            revealDialog.removeDialog();
        }
        for (CardInfoWindowDialog lookedAtDialog : lookedAt.values()) {
            lookedAtDialog.cleanUp();
            lookedAtDialog.removeDialog();
        }
        for (ShowCardsDialog pickTargetDialog : pickTarget) {
            pickTargetDialog.cleanUp();
            pickTargetDialog.removeDialog();
        }
        Plugins.instance.getActionCallback().hideOpenComponents();
        try {
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (InterruptedException ex) {
            logger.fatal("popupContainer error:", ex);
        }
        jPanel2.remove(bigCard);
        this.bigCard = null;
    }

    public void changeGUISize() {
        initComponents = true;
        setGUISize();
        stackObjects.changeGUISize();
        feedbackPanel.changeGUISize();
        handContainer.changeGUISize();
        for (PlayAreaPanel playAreaPanel : players.values()) {
            playAreaPanel.changeGUISize();
        }

        for (CardInfoWindowDialog cardInfoWindowDialog : exiles.values()) {
            cardInfoWindowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog cardInfoWindowDialog : revealed.values()) {
            cardInfoWindowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog cardInfoWindowDialog : lookedAt.values()) {
            cardInfoWindowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog cardInfoWindowDialog : graveyardWindows.values()) {
            cardInfoWindowDialog.changeGUISize();
        }
        for (ShowCardsDialog showCardsDialog : pickTarget) {
            showCardsDialog.changeGUISize();
        }

        this.revalidate();
        this.repaint();
        initComponents = false;
    }

    private void setGUISize() {
        jSplitPane0.setDividerSize(GUISizeHelper.dividerBarSize);
        jSplitPane1.setDividerSize(GUISizeHelper.dividerBarSize);
        jSplitPane2.setDividerSize(GUISizeHelper.dividerBarSize);
        stackObjects.setCardDimension(GUISizeHelper.handCardDimension);

        txtSpellsCast.setFont(new Font(GUISizeHelper.gameDialogAreaFont.getFontName(), Font.BOLD, GUISizeHelper.gameDialogAreaFont.getSize()));
        txtHoldPriority.setFont(new Font(GUISizeHelper.gameDialogAreaFont.getFontName(), Font.BOLD, GUISizeHelper.gameDialogAreaFont.getSize()));
        GUISizeHelper.changePopupMenuFont(popupMenuTriggerOrder);

        int newStackWidth = pnlHelperHandButtonsStackArea.getWidth() * GUISizeHelper.stackWidth / 100;
        if (newStackWidth < 410) {
            newStackWidth = 410;
        }
        Dimension newDimension = new Dimension(pnlHelperHandButtonsStackArea.getWidth() - newStackWidth, GUISizeHelper.handCardDimension.height + GUISizeHelper.scrollBarSize);
        handContainer.setPreferredSize(newDimension);
        handContainer.setMaximumSize(newDimension);

        newDimension = new Dimension(newStackWidth, STACK_MIN_CARDS_OFFSET_Y + GUISizeHelper.handCardDimension.height + GUISizeHelper.scrollBarSize);
        stackObjects.setPreferredSize(newDimension);
        stackObjects.setMinimumSize(newDimension);
        stackObjects.setMaximumSize(newDimension);

        newDimension = new Dimension(newStackWidth, (int) pnlShortCuts.getPreferredSize().getHeight());
        pnlShortCuts.setPreferredSize(newDimension);
        pnlShortCuts.setMinimumSize(newDimension);
        pnlShortCuts.setMaximumSize(newDimension);
    }

    private void saveDividerLocations() {
        // save panel sizes and divider locations.
        Rectangle rec = MageFrame.getDesktop().getBounds();
        String sb = Double.toString(rec.getWidth()) + 'x' + Double.toString(rec.getHeight());
        PreferencesDialog.saveValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, sb);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_0, Integer.toString(this.jSplitPane0.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_1, Integer.toString(this.jSplitPane1.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATION_2, Integer.toString(this.jSplitPane2.getDividerLocation()));
    }

    private void restoreDividerLocations() {
        Rectangle rec = MageFrame.getDesktop().getBounds();
        if (rec != null) {
            String size = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_MAGE_PANEL_LAST_SIZE, null);
            String sb = Double.toString(rec.getWidth()) + 'x' + Double.toString(rec.getHeight());
            // use divider positions only if screen size is the same as it was the time the settings were saved
            if (size != null && size.equals(sb)) {

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
                pnlShortCuts.revalidate();
                pnlShortCuts.repaint();
                for (PlayAreaPanel p : players.values()) {
                    p.sizePlayer(smallMode);
                }
            }
        } else if (smallMode) {
            smallMode = false;
            Dimension bbDimension = new Dimension(256, 367);
            bigCard.setMaximumSize(bbDimension);
            bigCard.setMinimumSize(bbDimension);
            bigCard.setPreferredSize(bbDimension);
            pnlShortCuts.revalidate();
            pnlShortCuts.repaint();
            for (PlayAreaPanel p : players.values()) {
                p.sizePlayer(smallMode);
            }
        }

        ArrowBuilder.getBuilder().setSize(rect.width, rect.height);

        DialogManager.getManager(gameId).setScreenWidth(rect.width);
        DialogManager.getManager(gameId).setScreenHeight(rect.height);
        DialogManager.getManager(gameId).setBounds(0, 0, rect.width, rect.height);
    }

    public synchronized void showGame(UUID gameId, UUID playerId, GamePane gamePane) {
        this.gameId = gameId;
        this.gamePane = gamePane;
        this.playerId = playerId;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();
        this.abilityPicker.init(gameId);

        this.btnConcede.setVisible(true);
        this.btnStopWatching.setVisible(false);
        this.btnSwitchHands.setVisible(false);
        this.btnCancelSkip.setVisible(true);
        this.btnToggleMacro.setVisible(true);

        this.btnSkipToNextTurn.setVisible(true);
        this.btnSkipToEndTurn.setVisible(true);
        this.btnSkipToNextMain.setVisible(true);
        this.btnSkipStack.setVisible(true);
        this.btnSkipToYourTurn.setVisible(true);
        this.btnSkipToEndStepBeforeYourTurn.setVisible(true);

        this.pnlReplay.setVisible(false);

        this.gameChatPanel.clear();
        SessionHandler.getGameChatId(gameId).ifPresent(uuid -> this.gameChatPanel.connect(uuid));
        if (!SessionHandler.joinGame(gameId)) {
            removeGame();
        } else {
            // play start sound
            AudioManager.playYourGameStarted();
        }
    }

    public synchronized void watchGame(UUID gameId, GamePane gamePane) {
        this.gameId = gameId;
        this.gamePane = gamePane;
        this.playerId = null;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();

        this.btnConcede.setVisible(false);
        this.btnStopWatching.setVisible(true);
        this.btnSwitchHands.setVisible(false);
        this.chosenHandKey = "";
        this.btnCancelSkip.setVisible(false);
        this.btnToggleMacro.setVisible(false);

        this.btnSkipToNextTurn.setVisible(false);
        this.btnSkipToEndTurn.setVisible(false);
        this.btnSkipToNextMain.setVisible(false);
        this.btnSkipStack.setVisible(false);
        this.btnSkipToYourTurn.setVisible(false);
        this.btnSkipToEndStepBeforeYourTurn.setVisible(false);

        this.pnlReplay.setVisible(false);
        this.gameChatPanel.clear();
        SessionHandler.getGameChatId(gameId).ifPresent(uuid
                -> this.gameChatPanel.connect(uuid));
        if (!SessionHandler.watchGame(gameId)) {
            removeGame();
        }
        for (PlayAreaPanel panel : players.values()) {
            panel.setPlayingMode(false);
        }
    }

    public synchronized void replayGame(UUID gameId) {
        this.gameId = gameId;
        this.playerId = null;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId);
        this.feedbackPanel.clear();
        this.btnConcede.setVisible(false);
        this.btnSkipToNextTurn.setVisible(false);
        this.btnSwitchHands.setVisible(false);
        this.btnStopWatching.setVisible(false);
        this.pnlReplay.setVisible(true);
        this.gameChatPanel.clear();
        if (!SessionHandler.startReplay(gameId)) {
            removeGame();
        }
        for (PlayAreaPanel panel : players.values()) {
            panel.setPlayingMode(false);
        }
    }

    /**
     * Closes the game and it's resources
     */
    public void removeGame() {
        Component c = this.getParent();
        while (c != null && !(c instanceof GamePane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((GamePane) c).removeGame();
        }
    }

    public synchronized void init(GameView game) {
        addPlayers(game);
        // default menu states
        setMenuStates(
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                holdingPriority
        );

        updateGame(game);
    }

    private void addPlayers(GameView game) {
        this.players.clear();
        this.playersWhoLeft.clear();
        this.pnlBattlefield.removeAll();
        //arrange players in a circle with the session player at the bottom left
        int numSeats = game.getPlayers().size();
        int numColumns = (numSeats + 1) / 2;
        boolean oddNumber = (numColumns > 1 && numSeats % 2 == 1);
        int col = 0;
        int row = 1;
        int playerSeat = 0;
        if (playerId != null) {
            for (PlayerView player : game.getPlayers()) {
                if (playerId.equals(player.getPlayerId())) {
                    break;
                }
                playerSeat++;
            }
        }
        PlayerView player = game.getPlayers().get(playerSeat);
        PlayAreaPanel playAreaPanel = new PlayAreaPanel(player, bigCard, gameId, game.getPriorityTime(), this,
                new PlayAreaPanelOptions(game.isPlayer(), game.isPlayer(), game.isRollbackTurnsAllowed(), row == 0));
        players.put(player.getPlayerId(), playAreaPanel);
        playersWhoLeft.put(player.getPlayerId(), false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        if (oddNumber) {
            c.gridwidth = 2;
        }
        c.gridx = col;
        c.gridy = 0;

        // Top panel (row=0)
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);

        // Bottom panel (row=1)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        topPanel.setLayout(new GridBagLayout());
        bottomPanel.setLayout(new GridBagLayout());

        bottomPanel.add(playAreaPanel, c);
        playAreaPanel.setVisible(true);
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
            } else {
                col--;
            }
            if (col >= numColumns) {
                row = 0;
                col = numColumns - 1;
            }
            player = game.getPlayers().get(playerNum);
            PlayAreaPanel playerPanel = new PlayAreaPanel(player, bigCard, gameId, game.getPriorityTime(), this,
                    new PlayAreaPanelOptions(game.isPlayer(), false, game.isRollbackTurnsAllowed(), row == 0));
            players.put(player.getPlayerId(), playerPanel);
            playersWhoLeft.put(player.getPlayerId(), false);
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0.5;
            c.weighty = 0.5;
            c.gridx = col;
            c.gridy = 0;

            if (row == 0) {
                topPanel.add(playerPanel, c);
            } else {
                bottomPanel.add(playerPanel, c);
            }

            playerPanel.setVisible(true);
            playerNum++;
            if (playerNum >= numSeats) {
                playerNum = 0;
            }
            if (playerNum == playerSeat) {
                break;
            }
        }
        for (PlayAreaPanel p : players.values()) {
            p.sizePlayer(smallMode);
        }

        GridBagConstraints panelC = new GridBagConstraints();
        panelC.fill = GridBagConstraints.BOTH;
        panelC.weightx = 0.5;
        panelC.weighty = 0.5;
        panelC.gridwidth = 1;
        panelC.gridy = 0;
        this.pnlBattlefield.add(topPanel, panelC);
        panelC.gridy = 1;
        this.pnlBattlefield.add(bottomPanel, panelC);
    }

    public synchronized void updateGame(GameView game) {
        updateGame(game, null);
    }

    public synchronized void updateGame(GameView game, Map<String, Serializable> options) {
        if (playerId == null && game.getWatchedHands() == null) {
            this.handContainer.setVisible(false);
        } else {
            this.handContainer.setVisible(true);
            handCards.clear();
            if (game.getWatchedHands() != null) {
                for (Map.Entry<String, SimpleCardsView> hand : game.getWatchedHands().entrySet()) {
                    handCards.put(hand.getKey(), CardsViewUtil.convertSimple(hand.getValue(), loadedCards));
                }
            }
            if (playerId != null) {
                handCards.put(YOUR_HAND, game.getHand());
                // Mark playable
                if (game.getCanPlayInHand() != null) {
                    for (CardView card : handCards.get(YOUR_HAND).values()) {
                        if (game.getCanPlayInHand().contains(card.getId())) {
                            card.setPlayable(true);
                        }
                    }
                }
                // Get opponents hand cards if available (only possible for players)
                if (game.getOpponentHands() != null) {
                    for (Map.Entry<String, SimpleCardsView> hand : game.getOpponentHands().entrySet()) {
                        handCards.put(hand.getKey(), CardsViewUtil.convertSimple(hand.getValue(), loadedCards));
                    }
                }
                if (!handCards.containsKey(chosenHandKey)) {
                    chosenHandKey = YOUR_HAND;
                }
            } else if (chosenHandKey.isEmpty() && !handCards.isEmpty()) {
                chosenHandKey = handCards.keySet().iterator().next();
            }
            if (chosenHandKey != null && handCards.containsKey(chosenHandKey)) {
                handContainer.loadCards(handCards.get(chosenHandKey), bigCard, gameId);
            }

            hideAll();

            if (playerId != null) {
                // set visible only if we have any other hand visible than ours
                btnSwitchHands.setVisible(handCards.size() > 1);
                boolean change = (handCardsOfOpponentAvailable != (game.getOpponentHands() != null));
                if (change) {
                    handCardsOfOpponentAvailable = !handCardsOfOpponentAvailable;
                    if (handCardsOfOpponentAvailable) {
                        MageFrame.getInstance().showMessage("You control other player's turn. \nUse \"Switch Hand\" button to switch between cards in different hands.");
                    } else {
                        MageFrame.getInstance().showMessage("You lost control on other player's turn.");
                    }
                }
            } else {
                btnSwitchHands.setVisible(!handCards.isEmpty());
            }
        }

        if (game.getPhase() != null) {
            this.txtPhase.setText(game.getPhase().toString());
        } else {
            this.txtPhase.setText("");
        }

        if (game.getStep() != null) {
            updatePhases(game.getStep());
            this.txtStep.setText(game.getStep().toString());
        } else {
            logger.debug("Step is empty");
            this.txtStep.setText("");
        }
        if (game.getSpellsCastCurrentTurn() > 0 && PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_SHOW_STORM_COUNTER, "true").equals("true")) {
            this.txtSpellsCast.setVisible(true);
            this.txtSpellsCast.setText(' ' + Integer.toString(game.getSpellsCastCurrentTurn()) + ' ');
        } else {
            this.txtSpellsCast.setVisible(false);
        }

        this.txtActivePlayer.setText(game.getActivePlayerName());
        this.txtPriority.setText(game.getPriorityPlayerName());
        this.txtTurn.setText(Integer.toString(game.getTurn()));

        List<UUID> possibleAttackers = new ArrayList<>();
        if (options != null && options.containsKey(Constants.Option.POSSIBLE_ATTACKERS)) {
            if (options.get(Constants.Option.POSSIBLE_ATTACKERS) instanceof List) {
                possibleAttackers.addAll((List) options.get(Constants.Option.POSSIBLE_ATTACKERS));
            }
        }

        for (PlayerView player : game.getPlayers()) {
            if (players.containsKey(player.getPlayerId())) {
                if (!possibleAttackers.isEmpty()) {
                    for (UUID permanentId : possibleAttackers) {
                        if (player.getBattlefield().containsKey(permanentId)) {
                            player.getBattlefield().get(permanentId).setCanAttack(true);
                        }
                    }
                }
                players.get(player.getPlayerId()).update(player);
                if (player.getPlayerId().equals(playerId)) {
                    updateSkipButtons(player.isPassedTurn(), player.isPassedUntilEndOfTurn(), player.isPassedUntilNextMain(), player.isPassedAllTurns(), player.isPassedUntilStackResolved(),
                            player.isPassedUntilEndStepBeforeMyTurn());
                }
                // update open or remove closed graveyard windows
                graveyards.put(player.getName(), player.getGraveyard());
                if (graveyardWindows.containsKey(player.getName())) {
                    CardInfoWindowDialog cardInfoWindowDialog = graveyardWindows.get(player.getName());
                    if (cardInfoWindowDialog.isClosed()) {
                        graveyardWindows.remove(player.getName());
                    } else {
                        cardInfoWindowDialog.loadCards(player.getGraveyard(), bigCard, gameId, false);
                    }
                }
                // show top card window
                if (player.getTopCard() != null) {
                    CardsView cardsView = new CardsView();
                    cardsView.put(player.getTopCard().getId(), player.getTopCard());
                    handleGameInfoWindow(revealed, ShowType.REVEAL_TOP_LIBRARY, player.getName() + "'s top library card", cardsView);
                }
            } else if (!players.isEmpty()) {
                logger.warn("Couldn't find player.");
                logger.warn("   uuid:" + player.getPlayerId());
                logger.warn("   players:");
                for (PlayAreaPanel p : players.values()) {
                    logger.warn(String.valueOf(p));
                }
            } else {
                // can happen at the game start before player list is initiated
            }
        }
        if (!menuNameSet) {
            StringBuilder sb = new StringBuilder();
            if (playerId == null) {
                sb.append("Watching: ");
            } else {
                sb.append("Playing: ");
            }
            boolean first = true;
            for (PlayerView player : game.getPlayers()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" - ");
                }
                sb.append(player.getName());
            }
            menuNameSet = true;
            gamePane.setTitle(sb.toString());
        }

        GameManager.instance.setStackSize(game.getStack().size());
        displayStack(game, bigCard, feedbackPanel, gameId);

        for (ExileView exile : game.getExile()) {
            if (!exiles.containsKey(exile.getId())) {
                CardInfoWindowDialog newExile = new CardInfoWindowDialog(ShowType.EXILE, exile.getName());
                exiles.put(exile.getId(), newExile);
                MageFrame.getDesktop().add(newExile, JLayeredPane.PALETTE_LAYER);
                newExile.show();
            }
            exiles.get(exile.getId()).loadCards(exile, bigCard, gameId);
        }

        showRevealed(game);
        showLookedAt(game);
        if (!game.getCombat().isEmpty()) {
            CombatManager.instance.showCombat(game.getCombat(), gameId);
        } else {
            CombatManager.instance.hideCombat(gameId);
        }

        for (PlayerView player : game.getPlayers()) {
            if (player.hasLeft() && !playersWhoLeft.get(player.getPlayerId())) {
                PlayAreaPanel playerLeftPanel = players.get(player.getPlayerId());
                playersWhoLeft.put(player.getPlayerId(), true);

                Container parent = playerLeftPanel.getParent();
                GridBagLayout layout = (GridBagLayout) parent.getLayout();

                for (Component otherPanel : parent.getComponents()) {

                    if (otherPanel instanceof PlayAreaPanel) {
                        GridBagConstraints gbc = layout.getConstraints(otherPanel);
                        if (gbc.weightx > 0.1) {
                            gbc.weightx = 0.99;
                        }
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.anchor = GridBagConstraints.WEST;
                        if (gbc.gridx > 0) {
                            gbc.anchor = GridBagConstraints.EAST;
                        }
                        if (otherPanel == playerLeftPanel) {
                            gbc.weightx = 0.01;
                            Dimension d = playerLeftPanel.getPreferredSize();
                            d.width = 95;
                            otherPanel.setPreferredSize(d);
                        }
                        parent.remove(otherPanel);
                        parent.add(otherPanel, gbc);
                    }
                }

                parent.validate();
                parent.repaint();
            }
        }

        feedbackPanel.disableUndo();

        this.revalidate();
        this.repaint();
    }

    private static final int BORDER_SIZE = 2;

    private void updateSkipButtons(boolean turn, boolean endOfTurn, boolean nextMain, boolean allTurns, boolean stack, boolean endStepBeforeYourStep) {
        if (turn) { //F4
            btnSkipToNextTurn.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipToNextTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }
        if (endOfTurn) { // F5
            btnSkipToEndTurn.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipToEndTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }
        if (nextMain) { // F7
            btnSkipToNextMain.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipToNextMain.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }
        if (stack) { // F8
            btnSkipStack.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipStack.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }
        if (allTurns) { // F9
            btnSkipToYourTurn.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipToYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }

        if (endStepBeforeYourStep) { // F11
            btnSkipToEndStepBeforeYourTurn.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnSkipToEndStepBeforeYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }

    }

    /**
     * Set the same state for menu selections to all player areas.
     *
     * @param manaPoolAutomatic
     * @param manaPoolAutomaticRestricted
     * @param useFirstManaAbility
     */
    public void setMenuStates(boolean manaPoolAutomatic, boolean manaPoolAutomaticRestricted, boolean useFirstManaAbility, boolean holdPriority) {
        for (PlayAreaPanel playAreaPanel : players.values()) {
            playAreaPanel.setMenuStates(manaPoolAutomatic, manaPoolAutomaticRestricted, useFirstManaAbility, holdPriority);
        }
    }

    private void displayStack(GameView game, BigCard bigCard, FeedbackPanel feedbackPanel, UUID gameId) {
        this.stackObjects.loadCards(game.getStack(), bigCard, gameId, true);
    }

    /**
     * Update phase buttons\labels.
     */
    private void updatePhases(PhaseStep step) {
        if (step == null) {
            logger.warn("step is null");
            return;
        }
        if (currentStep != null) {
            currentStep.setLocation(prevPoint);
        }
        switch (step) {
            case UNTAP:
                updateButton("Untap");
                break;
            case UPKEEP:
                updateButton("Upkeep");
                break;
            case DRAW:
                updateButton("Draw");
                break;
            case PRECOMBAT_MAIN:
                updateButton("Main1");
                break;
            case BEGIN_COMBAT:
                updateButton("Combat_Start");
                break;
            case DECLARE_ATTACKERS:
                updateButton("Combat_Attack");
                break;
            case DECLARE_BLOCKERS:
                updateButton("Combat_Block");
                break;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
                updateButton("Combat_Damage");
                break;
            case END_COMBAT:
                updateButton("Combat_End");
                break;
            case POSTCOMBAT_MAIN:
                updateButton("Main2");
                break;
            case END_TURN:
                updateButton("Cleanup");
                break;
        }
    }

    private void updateButton(String name) {
        if (hoverButtons.containsKey(name)) {
            currentStep = hoverButtons.get(name);
            prevPoint = currentStep.getLocation();
            currentStep.setLocation(prevPoint.x - 15, prevPoint.y);
        }
    }

    // Called if the game frame is deactivated because the tabled the deck editor or other frames go to foreground
    public void deactivated() {
        // hide the non modal windows (because otherwise they are shown on top of the new active pane)
        for (CardInfoWindowDialog exileDialog : exiles.values()) {
            exileDialog.hideDialog();
        }
        for (CardInfoWindowDialog graveyardDialog : graveyardWindows.values()) {
            graveyardDialog.hideDialog();
        }
        for (CardInfoWindowDialog revealDialog : revealed.values()) {
            revealDialog.hideDialog();
        }
        for (CardInfoWindowDialog lookedAtDialog : lookedAt.values()) {
            lookedAtDialog.hideDialog();
        }
    }

    // Called if the game frame comes to front again
    public void activated() {
        // hide the non modal windows (because otherwise they are shown on top of the new active pane)
        for (CardInfoWindowDialog exileDialog : exiles.values()) {
            exileDialog.show();
        }
        for (CardInfoWindowDialog graveyardDialog : graveyardWindows.values()) {
            graveyardDialog.show();
        }
        for (CardInfoWindowDialog revealDialog : revealed.values()) {
            revealDialog.show();
        }
        for (CardInfoWindowDialog lookedAtDialog : lookedAt.values()) {
            lookedAtDialog.show();
        }
    }

    public void openGraveyardWindow(String playerName) {
        if (graveyardWindows.containsKey(playerName)) {
            CardInfoWindowDialog cardInfoWindowDialog = graveyardWindows.get(playerName);
            if (cardInfoWindowDialog.isVisible()) {
                cardInfoWindowDialog.hideDialog();
            } else {
                cardInfoWindowDialog.show();
            }
            return;
        }
        CardInfoWindowDialog newGraveyard = new CardInfoWindowDialog(ShowType.GRAVEYARD, playerName);
        graveyardWindows.put(playerName, newGraveyard);
        MageFrame.getDesktop().add(newGraveyard, JLayeredPane.PALETTE_LAYER);
        newGraveyard.loadCards(graveyards.get(playerName), bigCard, gameId, false);
    }

    public void openTopLibraryWindow(String playerName) {
        String title = playerName + "'s top library card";
        if (revealed.containsKey(title)) {
            CardInfoWindowDialog cardInfoWindowDialog = revealed.get(title);
            if (cardInfoWindowDialog.isVisible()) {
                cardInfoWindowDialog.hideDialog();
            } else {
                cardInfoWindowDialog.show();
            }
        }
    }

    private void showRevealed(GameView game) {
        for (RevealedView revealView : game.getRevealed()) {
            handleGameInfoWindow(revealed, ShowType.REVEAL, revealView.getName(), revealView.getCards());
        }
        removeClosedCardInfoWindows(revealed);
    }

    private void showLookedAt(GameView game) {
        for (LookedAtView lookedAtView : game.getLookedAt()) {
            handleGameInfoWindow(lookedAt, ShowType.LOOKED_AT, lookedAtView.getName(), lookedAtView.getCards());
        }
        removeClosedCardInfoWindows(lookedAt);
    }

    private void handleGameInfoWindow(Map<String, CardInfoWindowDialog> windowMap, ShowType showType, String name, LinkedHashMap cardsView) {
        CardInfoWindowDialog cardInfoWindowDialog;
        if (!windowMap.containsKey(name)) {
            cardInfoWindowDialog = new CardInfoWindowDialog(showType, name);
            windowMap.put(name, cardInfoWindowDialog);
            MageFrame.getDesktop().add(cardInfoWindowDialog, JLayeredPane.PALETTE_LAYER);
        } else {
            cardInfoWindowDialog = windowMap.get(name);
        }

        if (cardInfoWindowDialog != null && !cardInfoWindowDialog.isClosed()) {
            switch (showType) {
                case REVEAL:
                case REVEAL_TOP_LIBRARY:
                    cardInfoWindowDialog.loadCards((CardsView) cardsView, bigCard, gameId);
                    break;
                case LOOKED_AT:
                    cardInfoWindowDialog.loadCards((SimpleCardsView) cardsView, bigCard, gameId);
                    break;
            }
        }
    }

    private void removeClosedCardInfoWindows(Map<String, CardInfoWindowDialog> windowMap) {
        // Remove closed window objects from the maps
        windowMap.entrySet().removeIf(entry -> entry.getValue().isClosed());
    }

    public void ask(String question, GameView gameView, int messageId, Map<String, Serializable> options) {
        updateGame(gameView);
        this.feedbackPanel.getFeedback(FeedbackMode.QUESTION, question, false, options, messageId, true, gameView.getPhase());
    }

    /**
     * Shows a pick target dialog and allows the player to pick a target (e.g.
     * the pick triggered ability)
     *
     * @param message
     * @param cardView
     * @param gameView
     * @param targets
     * @param required
     * @param options
     * @param messageId
     */
    public void pickTarget(String message, CardsView cardView, GameView gameView, Set<UUID> targets, boolean required, Map<String, Serializable> options, int messageId) {
        PopUpMenuType popupMenuType = null;
        if (options != null) {
            if (options.containsKey("targetZone")) {
                if (Zone.HAND == options.get("targetZone")) { // mark selectable target cards in hand
                    List<UUID> choosen = null;
                    if (options.containsKey("chosen")) {
                        choosen = (List<UUID>) options.get("chosen");
                    }
                    for (CardView card : gameView.getHand().values()) {
                        if (targets == null || targets.isEmpty()) {
                            card.setPlayable(false);
                            card.setChoosable(true);
                        } else if (targets.contains(card.getId())) {
                            card.setPlayable(false);
                            card.setChoosable(true);
                        }
                        if (choosen != null && choosen.contains(card.getId())) {
                            card.setSelected(true);
                        }
                    }
                }
            }
            if (options.containsKey("queryType") && PlayerQueryEvent.QueryType.PICK_ABILITY == options.get("queryType")) {
                popupMenuType = PopUpMenuType.TRIGGER_ORDER;
            }
        }
        updateGame(gameView);
        Map<String, Serializable> options0 = options == null ? new HashMap<>() : options;
        ShowCardsDialog dialog = null;
        if (cardView != null && !cardView.isEmpty()) {
            dialog = showCards(message, cardView, required, options0, popupMenuType);
            options0.put("dialog", dialog);
        }
        this.feedbackPanel.getFeedback(required ? FeedbackMode.INFORM : FeedbackMode.CANCEL, message, gameView.getSpecial(), options0, messageId, true, gameView.getPhase());
        if (dialog != null) {
            this.pickTarget.add(dialog); // TODO: 01.01.2018, JayDi85: why feedbackPanel saved to pickTarget list? Need to research
        }
    }

    public void inform(String information, GameView gameView, int messageId) {
        updateGame(gameView);
        this.feedbackPanel.getFeedback(FeedbackMode.INFORM, information, gameView.getSpecial(), null, messageId, false, gameView.getPhase());
    }

    public void endMessage(String message, int messageId) {
        this.feedbackPanel.getFeedback(FeedbackMode.END, message, false, null, messageId, true, null);
        ArrowBuilder.getBuilder().removeAllArrows(gameId);
    }

    public void select(String message, GameView gameView, int messageId, Map<String, Serializable> options) {
        holdingPriority = false;
        txtHoldPriority.setVisible(false);
        setMenuStates(
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                false);

        updateGame(gameView, options);
        boolean controllingPlayer = false;
        for (PlayerView playerView : gameView.getPlayers()) {
            if (playerView.getPlayerId().equals(playerId)) {
                // magenoxx: because of uncaught bug with saving state, rolling back and stack
                // undo is allowed only for empty stack
                controllingPlayer = !gameView.getPriorityPlayerName().equals(playerView.getName());
                if (playerView.getStatesSavedSize() > 0 && gameView.getStack().isEmpty()) {
                    feedbackPanel.allowUndo(playerView.getStatesSavedSize());
                }
                break;
            }

        }
        Map<String, Serializable> panelOptions = new HashMap<>();
        if (options != null) {
            panelOptions.putAll(options);
        }
        panelOptions.put("your_turn", true);
        String activePlayerText;
        if (gameView.getActivePlayerId().equals(playerId)) {
            activePlayerText = "Your turn";
        } else {
            activePlayerText = gameView.getActivePlayerName() + "'s turn";
        }
        String priorityPlayerText = "";
        if (controllingPlayer) {
            priorityPlayerText = " / priority " + gameView.getPriorityPlayerName();
        }
        String messageToDisplay = message + FeedbackPanel.getSmallText(activePlayerText + " / " + gameView.getStep().toString() + priorityPlayerText);
        this.feedbackPanel.getFeedback(FeedbackMode.SELECT, messageToDisplay, gameView.getSpecial(), panelOptions, messageId, true, gameView.getPhase());
    }

    public void playMana(String message, GameView gameView, Map<String, Serializable> options, int messageId) {
        updateGame(gameView);
        DialogManager.getManager(gameId).fadeOut();
        this.feedbackPanel.getFeedback(FeedbackMode.CANCEL, message, gameView.getSpecial(), options, messageId, true, gameView.getPhase());
    }

    public void playXMana(String message, GameView gameView, int messageId) {
        updateGame(gameView);
        DialogManager.getManager(gameId).fadeOut();
        this.feedbackPanel.getFeedback(FeedbackMode.CONFIRM, message, gameView.getSpecial(), null, messageId, true, gameView.getPhase());
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
        ActionCallback callback = Plugins.instance.getActionCallback();
        ((MageActionCallback) callback).hideGameUpdate(gameId);
    }

    private ShowCardsDialog showCards(String title, CardsView cards, boolean required, Map<String, Serializable> options, PopUpMenuType popupMenuType) {
        hideAll();
        ShowCardsDialog showCards = new ShowCardsDialog();
        JPopupMenu popupMenu = null;
        if (PopUpMenuType.TRIGGER_ORDER == popupMenuType) {
            popupMenu = popupMenuTriggerOrder;
        }
        showCards.loadCards(title, cards, bigCard, gameId, required, options, popupMenu, getShowCardsEventListener(showCards));
        return showCards;
    }

    public void getAmount(int min, int max, String message) {
        pickNumber.showDialog(min, max, message);
        if (pickNumber.isCancel()) {
            SessionHandler.sendPlayerBoolean(gameId, false);
        } else {
            SessionHandler.sendPlayerInteger(gameId, pickNumber.getAmount());
        }
    }

    public void getChoice(Choice choice, UUID objectId) {
        hideAll();
        // TODO: remember last choices and search incremental for same events?
        PickChoiceDialog pickChoice = new PickChoiceDialog();
        pickChoice.showDialog(choice, objectId, choiceWindowState);
        if (choice.isKeyChoice()) {
            SessionHandler.sendPlayerString(gameId, choice.getChoiceKey());
            /* // old code, auto complete was for auto scripting?
            if (pickChoice.isAutoSelect()) {
                SessionHandler.sendPlayerString(gameId, '#' + choice.getChoiceKey());
            } else {
                SessionHandler.sendPlayerString(gameId, choice.getChoiceKey());
            }*/
        } else {
            SessionHandler.sendPlayerString(gameId, choice.getChoice());
        }
        choiceWindowState = new MageDialogState(pickChoice);
        pickChoice.removeDialog();
    }

    public void pickPile(String message, CardsView pile1, CardsView pile2) {
        hideAll();
        PickPileDialog pickPileDialog = new PickPileDialog();
        pickPileDialog.loadCards(message, pile1, pile2, bigCard, gameId);
        SessionHandler.sendPlayerBoolean(gameId, pickPileDialog.isPickedPile1());
        pickPileDialog.cleanUp();
        pickPileDialog.removeDialog();
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
        pnlHelperHandButtonsStackArea = new javax.swing.JPanel();
        pnlShortCuts = new javax.swing.JPanel();
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

        txtSpellsCast = new javax.swing.JLabel();
        Border paddingBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        txtSpellsCast.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        txtSpellsCast.setBackground(Color.LIGHT_GRAY);
        txtSpellsCast.setOpaque(true);
        txtSpellsCast.setToolTipText("spells cast during the current turn");

        txtHoldPriority = new javax.swing.JLabel();
        txtHoldPriority.setText("Hold");
        txtHoldPriority.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        txtHoldPriority.setBackground(Color.LIGHT_GRAY);
        txtHoldPriority.setOpaque(true);
        txtHoldPriority.setToolTipText("Holding priority after the next spell cast or ability activation");
        txtHoldPriority.setVisible(false);

        btnToggleMacro = new KeyboundButton(KEY_CONTROL_TOGGLE_MACRO);
        btnCancelSkip = new KeyboundButton(KEY_CONTROL_CANCEL_SKIP); // F3
        btnSkipToNextTurn = new KeyboundButton(KEY_CONTROL_NEXT_TURN); // F4
        btnSkipToEndTurn = new KeyboundButton(KEY_CONTROL_END_STEP); // F5
        btnSkipToNextMain = new KeyboundButton(KEY_CONTROL_MAIN_STEP); // F7
        btnSkipStack = new KeyboundButton(KEY_CONTROL_SKIP_STACK); // F10
        btnSkipToYourTurn = new KeyboundButton(KEY_CONTROL_YOUR_TURN); // F9
        btnSkipToEndStepBeforeYourTurn = new KeyboundButton(KEY_CONTROL_PRIOR_END); // F11

        btnConcede = new javax.swing.JButton();
        btnSwitchHands = new javax.swing.JButton();
        btnStopWatching = new javax.swing.JButton();

        bigCard = new mage.client.cards.BigCard();
        pnlReplay = new javax.swing.JPanel();
        btnStopReplay = new javax.swing.JButton();
        btnNextPlay = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        btnSkipForward = new javax.swing.JButton();
        btnPreviousPlay = new javax.swing.JButton();
        pnlBattlefield = new javax.swing.JPanel();
        gameChatPanel = new mage.client.chat.ChatPanelBasic();
        gameChatPanel.useExtendedView(ChatPanelBasic.VIEW_MODE.GAME);
        userChatPanel = new mage.client.chat.ChatPanelBasic();
        userChatPanel.setParentChat(gameChatPanel);
        userChatPanel.useExtendedView(ChatPanelBasic.VIEW_MODE.CHAT);
        userChatPanel.setChatType(ChatPanelBasic.ChatType.GAME);
        gameChatPanel.setConnectedChat(userChatPanel);
        gameChatPanel.disableInput();
        gameChatPanel.setMinimumSize(new java.awt.Dimension(100, 48));
        gameChatPanel.enableHyperlinks();
        jSplitPane2 = new javax.swing.JSplitPane();
        handContainer = new HandPanel();
        handCards = new HashMap<>();

        pnlShortCuts.setOpaque(false);
        pnlShortCuts.setPreferredSize(new Dimension(410, 72));

        stackObjects = new mage.client.cards.Cards();

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

        bigCard.setBorder(new LineBorder(Color.black, 1, true));

        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;

        btnToggleMacro.setContentAreaFilled(false);
        btnToggleMacro.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnToggleMacro.setIcon(new ImageIcon(ImageManagerImpl.instance.getToggleRecordMacroButtonImage()));
        btnToggleMacro.setToolTipText("Toggle Record Macro ("
                + getCachedKeyText(KEY_CONTROL_TOGGLE_MACRO) + ").");
        btnToggleMacro.setFocusable(false);
        btnToggleMacro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnToggleMacroActionPerformed(null);
                }
            }
        });

        KeyStroke kst = getCachedKeystroke(KEY_CONTROL_TOGGLE_MACRO);
        this.getInputMap(c).put(kst, "F8_PRESS");
        this.getActionMap().put("F8_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnToggleMacroActionPerformed(actionEvent);
            }
        });

        KeyStroke ks3 = getCachedKeystroke(KEY_CONTROL_CANCEL_SKIP);
        this.getInputMap(c).put(ks3, "F3_PRESS");
        this.getActionMap().put("F3_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                restorePriorityActionPerformed(actionEvent);
            }
        });

        btnCancelSkip.setContentAreaFilled(false);
        btnCancelSkip.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnCancelSkip.setIcon(new ImageIcon(ImageManagerImpl.instance.getCancelSkipButtonImage()));
        btnCancelSkip.setToolTipText("Cancel all skip actions ("
                + getCachedKeyText(KEY_CONTROL_CANCEL_SKIP) + ").");
        btnCancelSkip.setFocusable(false);
        btnCancelSkip.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    restorePriorityActionPerformed(null);
                }
            }
        });

        btnSkipToNextTurn.setContentAreaFilled(false);
        btnSkipToNextTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToNextTurn.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipNextTurnButtonImage()));
        btnSkipToNextTurn.setToolTipText("Skip to next turn ("
                + getCachedKeyText(KEY_CONTROL_NEXT_TURN) + ").");
        btnSkipToNextTurn.setFocusable(false);
        btnSkipToNextTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnEndTurnActionPerformed(null);
                }
            }
        });

        KeyStroke ks = getCachedKeystroke(KEY_CONTROL_NEXT_TURN);
        this.getInputMap(c).put(ks, "F4_PRESS");
        this.getActionMap().put("F4_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnEndTurnActionPerformed(actionEvent);
            }
        });

        btnSkipToEndTurn.setContentAreaFilled(false);
        btnSkipToEndTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToEndTurn.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipEndTurnButtonImage()));
        btnSkipToEndTurn.setToolTipText("Skip to (opponents/next) end of turn step ("
                + getCachedKeyText(KEY_CONTROL_END_STEP) + ") - adjust using preferences.");
        btnSkipToEndTurn.setFocusable(false);
        btnSkipToEndTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnUntilEndOfTurnActionPerformed(null);
                }
            }
        });

        ks = getCachedKeystroke(KEY_CONTROL_END_STEP);
        this.getInputMap(c).put(ks, "F5_PRESS");
        this.getActionMap().put("F5_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnUntilEndOfTurnActionPerformed(actionEvent);
            }
        });

        ks = getCachedKeystroke(KEY_CONTROL_SKIP_STEP);
        this.getInputMap(c).put(ks, "F6_PRESS");
        this.getActionMap().put("F6_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnEndTurnSkipStackActionPerformed(actionEvent);
            }
        });

        btnSkipToNextMain.setContentAreaFilled(false);
        btnSkipToNextMain.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToNextMain.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipMainButtonImage()));
        btnSkipToNextMain.setToolTipText("Skip to (your) next main phase ("
                + getCachedKeyText(KEY_CONTROL_MAIN_STEP) + ") - adjust using preferences.");
        btnSkipToNextMain.setFocusable(false);
        btnSkipToNextMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnUntilNextMainPhaseActionPerformed(null);
                }
            }
        });

        ks = getCachedKeystroke(KEY_CONTROL_MAIN_STEP);
        this.getInputMap(c).put(ks, "F7_PRESS");
        this.getActionMap().put("F7_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnUntilNextMainPhaseActionPerformed(actionEvent);
            }
        });

        btnSkipToYourTurn.setContentAreaFilled(false);
        btnSkipToYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToYourTurn.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipYourNextTurnButtonImage()));
        btnSkipToYourTurn.setToolTipText("Skip to your next turn ("
                + getCachedKeyText(KEY_CONTROL_YOUR_TURN) + ").");
        btnSkipToYourTurn.setFocusable(false);
        btnSkipToYourTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnPassPriorityUntilNextYourTurnActionPerformed(null);
                }
            }
        });

        KeyStroke ks9 = getCachedKeystroke(KEY_CONTROL_YOUR_TURN);
        this.getInputMap(c).put(ks9, "F9_PRESS");
        this.getActionMap().put("F9_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnPassPriorityUntilNextYourTurnActionPerformed(actionEvent);
            }
        });

        btnSkipToEndStepBeforeYourTurn.setContentAreaFilled(false);
        btnSkipToEndStepBeforeYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToEndStepBeforeYourTurn.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipEndStepBeforeYourTurnButtonImage()));
        btnSkipToEndStepBeforeYourTurn.setToolTipText("Skip to the end step before your turn ("
                + getCachedKeyText(KEY_CONTROL_PRIOR_END) + ") - adjust using preferences.");
        btnSkipToEndStepBeforeYourTurn.setFocusable(false);
        btnSkipToEndStepBeforeYourTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnSkipToEndStepBeforeYourTurnActionPerformed(null);
                }
            }
        });

        KeyStroke ks11 = getCachedKeystroke(KEY_CONTROL_PRIOR_END);
        this.getInputMap(c).put(ks11, "F11_PRESS");
        this.getActionMap().put("F11_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnSkipToEndStepBeforeYourTurnActionPerformed(actionEvent);
            }
        });

        btnSkipStack.setContentAreaFilled(false);
        btnSkipStack.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipStack.setIcon(new ImageIcon(ImageManagerImpl.instance.getSkipStackButtonImage()));
        btnSkipStack.setToolTipText("Skip until stack is resolved ("
                + getCachedKeyText(KEY_CONTROL_SKIP_STACK) + ").");
        btnSkipStack.setFocusable(false);
        btnSkipStack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnPassPriorityUntilStackResolvedActionPerformed(null);
                }
            }
        });

        ks = getCachedKeystroke(KEY_CONTROL_SKIP_STACK);
        this.getInputMap(c).put(ks, "F10_PRESS");
        this.getActionMap().put("F10_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnPassPriorityUntilStackResolvedActionPerformed(actionEvent);
            }
        });

        btnConcede.setContentAreaFilled(false);
        btnConcede.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnConcede.setIcon(new ImageIcon(ImageManagerImpl.instance.getConcedeButtonImage()));
        btnConcede.setToolTipText("Concede the current game.");
        btnConcede.setFocusable(false);
        btnConcede.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnConcedeActionPerformed(null);
                }
            }
        });

        KeyStroke ks2 = getCachedKeystroke(KEY_CONTROL_CONFIRM);
        this.getInputMap(c).put(ks2, "F2_PRESS");
        this.getActionMap().put("F2_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (feedbackPanel != null) {
                    feedbackPanel.pressOKYesOrDone();
                }
            }
        });

        KeyStroke ksAltE = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltE, "ENLARGE");
        this.getActionMap().put("ENLARGE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).enlargeCard(EnlargeMode.NORMAL);
            }
        });

        KeyStroke ksAltS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltS, "ENLARGE_SOURCE");
        this.getActionMap().put("ENLARGE_SOURCE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).enlargeCard(EnlargeMode.ALTERNATE);
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

        KeyStroke ksAlt1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAlt1, "USEFIRSTMANAABILITY");
        this.getActionMap().put("USEFIRSTMANAABILITY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SessionHandler.sendPlayerAction(PlayerAction.USE_FIRST_MANA_ABILITY_ON, gameId, null);
                setMenuStates(
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                        holdingPriority);
            }
        });

        final BasicSplitPaneUI myUi = (BasicSplitPaneUI) jSplitPane0.getUI();
        final BasicSplitPaneDivider divider = myUi.getDivider();
        final JButton upArrowButton = (JButton) divider.getComponent(0);
        upArrowButton.addActionListener(actionEvent -> PreferencesDialog.saveValue(PreferencesDialog.KEY_BIG_CARD_TOGGLED, "up"));

        final JButton downArrowButton = (JButton) divider.getComponent(1);
        downArrowButton.addActionListener(actionEvent -> PreferencesDialog.saveValue(PreferencesDialog.KEY_BIG_CARD_TOGGLED, "down"));

        KeyStroke ksAltEReleased = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAltEReleased, "ENLARGE_RELEASE");
        KeyStroke ksAltSReleased = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAltSReleased, "ENLARGE_RELEASE");
        this.getActionMap().put("ENLARGE_RELEASE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).hideEnlargedCard();
            }
        });

        KeyStroke ksAlt1Released = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAlt1Released, "USEFIRSTMANAABILITY_RELEASE");
        this.getActionMap().put("USEFIRSTMANAABILITY_RELEASE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SessionHandler.sendPlayerAction(PlayerAction.USE_FIRST_MANA_ABILITY_OFF, gameId, null);
                setMenuStates(
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                        holdingPriority);
            }
        });

        btnSwitchHands.setContentAreaFilled(false);
        btnSwitchHands.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnSwitchHands.setIcon(new ImageIcon(ImageManagerImpl.instance.getSwitchHandsButtonImage()));
        btnSwitchHands.setFocusable(false);
        btnSwitchHands.setToolTipText("Switch between your hand cards and hand cards of controlled players.");
        btnSwitchHands.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnSwitchHandActionPerformed(null);
                }
            }
        });

        btnStopWatching.setContentAreaFilled(false);
        btnStopWatching.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnStopWatching.setIcon(new ImageIcon(ImageManagerImpl.instance.getStopWatchButtonImage()));
        btnStopWatching.setFocusable(false);
        btnStopWatching.setToolTipText("Stop watching this game.");
        btnStopWatching.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    btnStopWatchingActionPerformed(null);
                }
            }
        });

        stackObjects.setBackgroundColor(new Color(0, 0, 0, 40));

        btnStopReplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop.png")));
        btnStopReplay.addActionListener(evt -> btnStopReplayActionPerformed(evt));

        btnNextPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop_right.png")));
        btnNextPlay.addActionListener(evt -> btnNextPlayActionPerformed(evt));

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_right.png")));
        btnPlay.addActionListener(evt -> btnPlayActionPerformed(evt));

        btnSkipForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_double_stop_right.png")));
        btnSkipForward.addActionListener(evt -> btnSkipForwardActionPerformed(evt));

        btnPreviousPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/control_stop_left.png")));
        btnPreviousPlay.addActionListener(evt -> btnPreviousPlayActionPerformed(evt));

        initPopupMenuTriggerOrder();

        setGUISize();

        // Replay panel to control replay of games
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

        // Game info panel (buttons on the right panel)
        javax.swing.GroupLayout gl_pnlShortCuts = new javax.swing.GroupLayout(pnlShortCuts);
        pnlShortCuts.setLayout(gl_pnlShortCuts);
        gl_pnlShortCuts.setHorizontalGroup(gl_pnlShortCuts.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(gl_pnlShortCuts.createSequentialGroup()
                        .addComponent(btnSkipToNextTurn)
                        .addComponent(btnSkipToEndTurn)
                        .addComponent(btnSkipToNextMain)
                        .addComponent(btnSkipToYourTurn)
                        .addComponent(btnSkipStack)
                        .addComponent(btnSkipToEndStepBeforeYourTurn)
                )
                .addGroup(gl_pnlShortCuts.createSequentialGroup()
                        .addComponent(txtHoldPriority)
                        .addComponent(txtSpellsCast)
                        /*.addComponent(btnToggleMacro)*/
                        .addComponent(btnSwitchHands)
                        .addComponent(btnCancelSkip)
                        .addComponent(btnConcede)
                        .addComponent(btnStopWatching)
                )
                //.addComponent(bigCard, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                //.addComponent(feedbackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                //.addComponent(stack, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)

                .addGroup(gl_pnlShortCuts.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(51, Short.MAX_VALUE))
        );
        gl_pnlShortCuts.setVerticalGroup(gl_pnlShortCuts.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gl_pnlShortCuts.createSequentialGroup()
                        //.addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        //.addGap(1, 1, 1)
                        //.addComponent(feedbackPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        //.addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gl_pnlShortCuts.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSkipToNextTurn)
                                .addComponent(btnSkipToEndTurn)
                                .addComponent(btnSkipToNextMain)
                                .addComponent(btnSkipToYourTurn)
                                .addComponent(btnSkipStack)
                                .addComponent(btnSkipToEndStepBeforeYourTurn)
                        )
                        .addGroup(gl_pnlShortCuts.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                /*.addComponent(btnToggleMacro)*/
                                .addComponent(txtHoldPriority)
                                .addComponent(txtSpellsCast)
                                .addComponent(btnSwitchHands)
                                .addComponent(btnCancelSkip)
                                .addComponent(btnConcede)
                                .addComponent(btnStopWatching)
                        )
                )
        );

        pnlBattlefield.setLayout(new java.awt.GridBagLayout());

        jPhases = new JPanel();
        jPhases.setBackground(new Color(0, 0, 0, 0));
        jPhases.setLayout(null);
        jPhases.setPreferredSize(new Dimension(X_PHASE_WIDTH, 435));

        MouseAdapter phasesMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                mouseClickPhaseBar(evt);
            }
        };
        String[] phases = {"Untap", "Upkeep", "Draw", "Main1",
            "Combat_Start", "Combat_Attack", "Combat_Block", "Combat_Damage", "Combat_End",
            "Main2", "Cleanup", "Next_Turn"};
        for (String name : phases) {
            createPhaseButton(name, phasesMouseAdapter);
        }

        int i = 0;
        for (String name : hoverButtons.keySet()) {
            HoverButton hoverButton = hoverButtons.get(name);
            hoverButton.setAlignmentX(LEFT_ALIGNMENT);
            hoverButton.setBounds(X_PHASE_WIDTH - 36, i * 36, 36, 36);
            jPhases.add(hoverButton);
            i++;
        }
        jPhases.addMouseListener(phasesMouseAdapter);

        pnlReplay.setOpaque(false);

        helper = new HelperPanel();
        feedbackPanel.setHelperPanel(helper);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.setLeftComponent(userChatPanel);
        jSplitPane2.setBottomComponent(gameChatPanel);

        phasesContainer = new JPanel();
        phasesContainer.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS));
        phasesContainer.setBackground(new Color(0, 0, 0, 0));
        Float ratio = (float) 1;
        JPanel empty1 = new JPanel();
        empty1.setBackground(new Color(0, 0, 0, 0));
        phasesContainer.add(empty1, ratio);
        phasesContainer.add(jPhases);

        javax.swing.GroupLayout gl_helperHandButtonsStackArea = new javax.swing.GroupLayout(pnlHelperHandButtonsStackArea);
        gl_helperHandButtonsStackArea.setHorizontalGroup(
                gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                //                        .addGap(0)
                                .addGroup(gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                                .addGroup(gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(helper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(handContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                )
                                                .addGroup(gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(pnlShortCuts, 410, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                        .addComponent(stackObjects, 410, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                )
                                        )
                                        .addGap(0)
                                        //.addComponent(jPhases, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                                .addComponent(pnlBattlefield, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                .addComponent(phasesContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )))
        );
        gl_helperHandButtonsStackArea.setVerticalGroup(
                gl_helperHandButtonsStackArea.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                .addGroup(gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pnlBattlefield, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(phasesContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                )
                                //.addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_helperHandButtonsStackArea.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                                .addGap(2)
                                                .addComponent(pnlShortCuts, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(stackObjects, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl_helperHandButtonsStackArea.createSequentialGroup()
                                                .addComponent(helper, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(handContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                        )
        );
        pnlHelperHandButtonsStackArea.setLayout(gl_helperHandButtonsStackArea);

        jSplitPane1.setLeftComponent(pnlHelperHandButtonsStackArea);
        jSplitPane1.setRightComponent(jSplitPane2);

        // Set individual area sizes of big card pane
        GridBagLayout gbl = new GridBagLayout();
        jPanel2.setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 4; // size 4/5
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbl.setConstraints(bigCard, gbc);
        jPanel2.add(bigCard);

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

    private void removeListener() {
        for (MouseListener ml : this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnToggleMacro.getMouseListeners()) {
            this.btnToggleMacro.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnCancelSkip.getMouseListeners()) {
            this.btnCancelSkip.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnConcede.getMouseListeners()) {
            this.btnConcede.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipToYourTurn.getMouseListeners()) {
            this.btnSkipToYourTurn.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipStack.getMouseListeners()) {
            this.btnSkipStack.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipToEndStepBeforeYourTurn.getMouseListeners()) {
            this.btnSkipToEndStepBeforeYourTurn.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipToEndTurn.getMouseListeners()) {
            this.btnSkipToEndTurn.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipToNextMain.getMouseListeners()) {
            this.btnSkipToNextMain.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSkipToNextTurn.getMouseListeners()) {
            this.btnSkipToNextTurn.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnSwitchHands.getMouseListeners()) {
            this.btnSwitchHands.removeMouseListener(ml);
        }
        for (MouseListener ml : this.btnStopWatching.getMouseListeners()) {
            this.btnStopWatching.removeMouseListener(ml);
        }
        for (MouseListener ml : this.jPhases.getMouseListeners()) {
            this.jPhases.removeMouseListener(ml);
        }
        for (String name : hoverButtons.keySet()) {
            HoverButton hoverButton = hoverButtons.get(name);
            for (MouseListener ml : hoverButton.getMouseListeners()) {
                hoverButton.removeMouseListener(ml);
            }
        }
        for (ActionListener al : this.btnPlay.getActionListeners()) {
            this.btnPlay.removeActionListener(al);
        }
        for (ActionListener al : this.btnStopReplay.getActionListeners()) {
            this.btnStopReplay.removeActionListener(al);
        }
        for (ActionListener al : this.btnNextPlay.getActionListeners()) {
            this.btnNextPlay.removeActionListener(al);
        }
        for (ActionListener al : this.btnNextPlay.getActionListeners()) {
            this.btnNextPlay.removeActionListener(al);
        }
        for (ActionListener al : this.btnPreviousPlay.getActionListeners()) {
            this.btnPreviousPlay.removeActionListener(al);
        }
        for (ActionListener al : this.btnSkipForward.getActionListeners()) {
            this.btnSkipForward.removeActionListener(al);
        }

        final BasicSplitPaneUI myUi = (BasicSplitPaneUI) jSplitPane0.getUI();
        final BasicSplitPaneDivider divider = myUi.getDivider();
        final JButton upArrowButton = (JButton) divider.getComponent(0);
        for (ActionListener al : upArrowButton.getActionListeners()) {
            upArrowButton.removeActionListener(al);
        }
        final JButton downArrowButton = (JButton) divider.getComponent(1);
        for (ActionListener al : downArrowButton.getActionListeners()) {
            downArrowButton.removeActionListener(al);
        }

        for (ComponentListener cl : this.getComponentListeners()) {
            this.removeComponentListener(cl);
        }
    }

    private void btnConcedeActionPerformed(java.awt.event.ActionEvent evt) {
        UserRequestMessage message = new UserRequestMessage("Confirm concede", "Are you sure you want to concede?");
        message.setButton1("No", null);
        message.setButton2("Yes", PlayerAction.CLIENT_CONCEDE_GAME);
        message.setGameId(gameId);
        MageFrame.getInstance().showUserRequestDialog(message);
    }

    private void btnToggleMacroActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.TOGGLE_RECORD_MACRO, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, false, false, false, false, false);
        if (btnToggleMacro.getBorder() instanceof EmptyBorder) {
            btnToggleMacro.setBorder(new LineBorder(Color.orange, BORDER_SIZE));
        } else {
            btnToggleMacro.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        }
    }

    private void btnEndTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(true, false, false, false, false, false);
    }

    private void btnUntilEndOfTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_TURN_END_STEP, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, true, false, false, false, false);
    }

    private void btnEndTurnSkipStackActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN_SKIP_STACK, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(true, false, false, false, true, false);
    }

    private void btnUntilNextMainPhaseActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_MAIN_PHASE, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, false, true, false, false, false);
    }

    private void btnPassPriorityUntilNextYourTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_MY_NEXT_TURN, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, false, false, true, false, false);
    }

    private void btnPassPriorityUntilStackResolvedActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_STACK_RESOLVED, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, false, false, false, true, false);
    }

    private void btnSkipToEndStepBeforeYourTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_END_STEP_BEFORE_MY_NEXT_TURN, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(false, false, false, false, false, true);
    }

    private void restorePriorityActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, gameId, null);
        AudioManager.playOnSkipButtonCancel();
        updateSkipButtons(false, false, false, false, false, false);
    }

    private void mouseClickPhaseBar(MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) { // Left button
            PreferencesDialog.main(new String[]{PreferencesDialog.OPEN_PHASES_TAB});
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

        if (newChosenHandKey != null && !newChosenHandKey.isEmpty()) {
            this.chosenHandKey = newChosenHandKey;
            CardsView cards = handCards.get(chosenHandKey);
            handContainer.loadCards(cards, bigCard, gameId);
        }
    }

    private void btnStopWatchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopWatchingActionPerformed
        UserRequestMessage message = new UserRequestMessage("Stop watching", "Are you sure you want to stop watching?");
        message.setButton1("No", null);
        message.setButton2("Yes", PlayerAction.CLIENT_STOP_WATCHING);
        message.setGameId(gameId);
        MageFrame.getInstance().showUserRequestDialog(message);
    }//GEN-LAST:event_btnStopWatchingActionPerformed

    private void btnStopReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopReplayActionPerformed
        if (replayTask != null && !replayTask.isDone()) {
            replayTask.cancel(true);
        } else {
            UserRequestMessage message = new UserRequestMessage("Stop replay", "Are you sure you want to stop replay?");
            message.setButton1("No", null);
            message.setButton2("Yes", PlayerAction.CLIENT_REPLAY_ACTION);
            message.setGameId(gameId);
            MageFrame.getInstance().showUserRequestDialog(message);
        }
    }//GEN-LAST:event_btnStopReplayActionPerformed

    private void btnNextPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPlayActionPerformed
        SessionHandler.nextPlay(gameId);
    }//GEN-LAST:event_btnNextPlayActionPerformed

    private void btnPreviousPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousPlayActionPerformed
        SessionHandler.previousPlay(gameId);
    }//GEN-LAST:event_btnPreviousPlayActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        if (replayTask == null || replayTask.isDone()) {
            replayTask = new ReplayTask(gameId);
            replayTask.execute();
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnSkipForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipForwardActionPerformed
        SessionHandler.skipForward(gameId, 10);
    }//GEN-LAST:event_btnSkipForwardActionPerformed

    public void setJLayeredPane(JLayeredPane jLayeredPane) {
        this.jLayeredPane = jLayeredPane;
    }

    public void installComponents() {
        jLayeredPane.setOpaque(false);
        jLayeredPane.add(abilityPicker, JLayeredPane.MODAL_LAYER);
        jLayeredPane.add(DialogManager.getManager(gameId), JLayeredPane.MODAL_LAYER, 0);
        abilityPicker.setVisible(false);
    }

    private void createPhaseButton(String name, MouseAdapter mouseAdapter) {
        if (hoverButtons == null) {
            hoverButtons = new LinkedHashMap<>();
        }
        Rectangle rect = new Rectangle(36, 36);
        HoverButton button = new HoverButton("", ImageManagerImpl.instance.getPhaseImage(name), rect);
        button.setToolTipText(name.replaceAll("_", " "));
        button.setPreferredSize(new Dimension(36, 36));
        button.addMouseListener(mouseAdapter);
        hoverButtons.put(name, button);
    }

    // Event listener for the ShowCardsDialog
    private Listener<Event> getShowCardsEventListener(final ShowCardsDialog dialog) {
        return (Listener<Event>) event -> {
            if (event.getEventType() == ClientEventType.SHOW_POP_UP_MENU) {
                if (event.getComponent() != null && event.getComponent() instanceof CardPanel) {
                    JPopupMenu menu = ((CardPanel) event.getComponent()).getPopupMenu();
                    if (menu != null) {
                        cardViewPopupMenu = ((CardView) event.getSource());
                        menu.show(event.getComponent(), event.getxPos(), event.getyPos());
                    }
                }
            }
            if (event.getEventType() == ClientEventType.ACTION_CONSUMED) {
                dialog.removeDialog();
            }
        };
    }

    public void handleTriggerOrderPopupMenuEvent(ActionEvent e) {
        UUID abilityId = null;
        String abilityRuleText = null;
        if (cardViewPopupMenu instanceof CardView && cardViewPopupMenu.getAbility() != null) {
            abilityId = cardViewPopupMenu.getAbility().getId();
            if (!cardViewPopupMenu.getAbility().getRules().isEmpty()
                    && !cardViewPopupMenu.getAbility().getRules().get(0).isEmpty()) {
                abilityRuleText = cardViewPopupMenu.getAbility().getRules().get(0);
            }
        }
        switch (e.getActionCommand()) {
            case CMD_AUTO_ORDER_FIRST:
                SessionHandler.sendPlayerAction(TRIGGER_AUTO_ORDER_ABILITY_FIRST, gameId, abilityId);
                SessionHandler.sendPlayerUUID(gameId, abilityId);
                break;
            case CMD_AUTO_ORDER_LAST:
                SessionHandler.sendPlayerAction(TRIGGER_AUTO_ORDER_ABILITY_LAST, gameId, abilityId);
                SessionHandler.sendPlayerUUID(gameId, null); // Don't use this but refresh the displayed abilities
                break;
            case CMD_AUTO_ORDER_NAME_FIRST:
                if (abilityRuleText != null) {
                    SessionHandler.sendPlayerAction(TRIGGER_AUTO_ORDER_NAME_FIRST, gameId, abilityRuleText);
                    SessionHandler.sendPlayerUUID(gameId, abilityId);
                }
                break;
            case CMD_AUTO_ORDER_NAME_LAST:
                if (abilityRuleText != null) {
                    SessionHandler.sendPlayerAction(TRIGGER_AUTO_ORDER_NAME_LAST, gameId, abilityRuleText);
                    SessionHandler.sendPlayerUUID(gameId, null); // Don't use this but refresh the displayed abilities
                }
                break;
            case CMD_AUTO_ORDER_RESET_ALL:
                SessionHandler.sendPlayerAction(TRIGGER_AUTO_ORDER_RESET_ALL, gameId, null);
                break;
        }
        for (ShowCardsDialog dialog : pickTarget) {
            dialog.removeDialog();
        }
    }

    private void initPopupMenuTriggerOrder() {

        ActionListener actionListener = e -> handleTriggerOrderPopupMenuEvent(e);

        popupMenuTriggerOrder = new JPopupMenu();

        // String tooltipText = "";
        JMenuItem menuItem;
        menuItem = new JMenuItem("Put this ability always first on the stack");
        menuItem.setActionCommand(CMD_AUTO_ORDER_FIRST);
        menuItem.addActionListener(actionListener);
        popupMenuTriggerOrder.add(menuItem);

        menuItem = new JMenuItem("Put this ability always last on the stack");
        menuItem.setActionCommand(CMD_AUTO_ORDER_LAST);
        menuItem.addActionListener(actionListener);
        popupMenuTriggerOrder.add(menuItem);

        menuItem = new JMenuItem("Put all abilities with that rule text always first on the stack");
        menuItem.setActionCommand(CMD_AUTO_ORDER_NAME_FIRST);
        menuItem.addActionListener(actionListener);
        popupMenuTriggerOrder.add(menuItem);

        menuItem = new JMenuItem("Put all abilities with that rule text always last on the stack");
        menuItem.setActionCommand(CMD_AUTO_ORDER_NAME_LAST);
        menuItem.addActionListener(actionListener);
        popupMenuTriggerOrder.add(menuItem);

        menuItem = new JMenuItem("Reset all order settings for triggered abilities");
        menuItem.setActionCommand(CMD_AUTO_ORDER_RESET_ALL);
        menuItem.addActionListener(actionListener);
        popupMenuTriggerOrder.add(menuItem);
    }

    public String getGameLog() {
        return gameChatPanel.getText();
    }

    public Map<String, Card> getLoadedCards() {
        return loadedCards;
    }

    public void setLoadedCards(Map<String, Card> loadedCards) {
        this.loadedCards = loadedCards;
    }

    public FeedbackPanel getFeedbackPanel() {
        return feedbackPanel;
    }

    // Use Cmd on OSX since Ctrl+click is already used to simulate right click
    private static final int holdPriorityMask = System.getProperty("os.name").contains("Mac OS X") ? InputEvent.META_DOWN_MASK : InputEvent.CTRL_DOWN_MASK;

    public void handleEvent(AWTEvent event) {
        if (event instanceof InputEvent) {
            int id = event.getID();
            boolean isActionEvent = false;
            if (id == MouseEvent.MOUSE_PRESSED) {
                isActionEvent = true;
            } else if (id == KeyEvent.KEY_PRESSED) {
                KeyEvent key = (KeyEvent) event;
                int keyCode = key.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
                    isActionEvent = true;
                }
            }
            if (isActionEvent) {
                InputEvent input = (InputEvent) event;
                if ((input.getModifiersEx() & holdPriorityMask) != 0) {
                    setMenuStates(
                            PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                            PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                            PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                            true);
                    holdPriority(true);
                }
            }
        }
    }

    public void holdPriority(boolean holdPriority) {
        if (holdingPriority != holdPriority) {
            holdingPriority = holdPriority;
            txtHoldPriority.setVisible(holdPriority);
            if (holdPriority) {
                SessionHandler.sendPlayerAction(PlayerAction.HOLD_PRIORITY, gameId, null);
            } else {
                SessionHandler.sendPlayerAction(PlayerAction.UNHOLD_PRIORITY, gameId, null);
            }
        }
    }

    private boolean holdingPriority;
    private mage.client.components.ability.AbilityPicker abilityPicker;
    private mage.client.cards.BigCard bigCard;

    //    private JPanel cancelSkipPanel;
    private KeyboundButton btnToggleMacro;
    private KeyboundButton btnCancelSkip;
    private KeyboundButton btnSkipToNextTurn; // F4
    private KeyboundButton btnSkipToEndTurn; // F5
    private KeyboundButton btnSkipToNextMain; // F7
    private KeyboundButton btnSkipStack; // F8
    private KeyboundButton btnSkipToYourTurn; // F9
    private KeyboundButton btnSkipToEndStepBeforeYourTurn; // F11

    private javax.swing.JButton btnConcede;
    private javax.swing.JButton btnSwitchHands;

    private javax.swing.JButton btnNextPlay;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPreviousPlay;
    private javax.swing.JButton btnSkipForward;
    private javax.swing.JButton btnStopReplay;

    private javax.swing.JButton btnStopWatching;

    private mage.client.chat.ChatPanelBasic gameChatPanel;
    private mage.client.game.FeedbackPanel feedbackPanel;
    private HelperPanel helper;
    private mage.client.chat.ChatPanelBasic userChatPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pnlHelperHandButtonsStackArea;
    private javax.swing.JSplitPane jSplitPane0;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblActivePlayer;
    private javax.swing.JLabel lblPhase;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblStep;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JPanel pnlBattlefield;
    private javax.swing.JPanel pnlShortCuts;
    private javax.swing.JPanel pnlReplay;
    private javax.swing.JLabel txtActivePlayer;
    private javax.swing.JLabel txtPhase;
    private javax.swing.JLabel txtPriority;
    private javax.swing.JLabel txtStep;
    private javax.swing.JLabel txtTurn;

    private Map<String, CardsView> handCards;

    private mage.client.cards.Cards stackObjects;
    private HandPanel handContainer;

    private javax.swing.JSplitPane jSplitPane2;
    private JPanel jPhases;
    private JPanel phasesContainer;
    private javax.swing.JLabel txtSpellsCast;
    private javax.swing.JLabel txtHoldPriority;

    private HoverButton currentStep;
    private Point prevPoint;

    private boolean imagePanelState;

}

class ReplayTask extends SwingWorker<Void, Collection<MatchView>> {

    private final UUID gameId;

    private static final Logger logger = Logger.getLogger(ReplayTask.class);

    ReplayTask(UUID gameId) {
        this.gameId = gameId;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            SessionHandler.nextPlay(gameId);
            TimeUnit.SECONDS.sleep(1);
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
        } catch (CancellationException ex) {
        }
    }

}
