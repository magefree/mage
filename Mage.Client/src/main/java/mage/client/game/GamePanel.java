package mage.client.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mage.cards.Card;
import mage.cards.MageCard;
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
import mage.client.components.tray.MageTray;
import mage.client.dialog.*;
import mage.client.dialog.CardInfoWindowDialog.ShowType;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Event;
import mage.client.util.*;
import mage.client.util.audio.AudioManager;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.MageDialogState;
import mage.constants.*;
import mage.game.events.PlayerQueryEvent;
import mage.players.PlayableObjectStats;
import mage.players.PlayableObjectsList;
import mage.util.CardUtil;
import mage.util.DebugUtil;
import mage.util.MultiAmountMessage;
import mage.util.StreamUtils;
import mage.view.*;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static mage.client.dialog.PreferencesDialog.*;
import static mage.constants.PlayerAction.*;

/**
 * Game GUI: main game panel with all controls
 *
 * @author BetaSteward_at_googlemail.com, nantuko8, JayDi85
 */
public final class GamePanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(GamePanel.class);
    private static final String YOUR_HAND = "Your hand";

    private static final int SKIP_BUTTONS_SPACE_H = 3;
    private static final int SKIP_BUTTONS_SPACE_V = 3;

    private static final int PHASE_BUTTONS_SPACE_H = 3;
    private static final int PHASE_BUTTONS_SPACE_V = 3;

    private static final String CMD_AUTO_ORDER_FIRST = "cmdAutoOrderFirst";
    private static final String CMD_AUTO_ORDER_LAST = "cmdAutoOrderLast";
    private static final String CMD_AUTO_ORDER_NAME_FIRST = "cmdAutoOrderNameFirst";
    private static final String CMD_AUTO_ORDER_NAME_LAST = "cmdAutoOrderNameLast";
    private static final String CMD_AUTO_ORDER_RESET_ALL = "cmdAutoOrderResetAll";

    // on window resize: 0.0 keep left size (hand), 1.0 keep right size (stack), 0.5 keep proportion
    private static final double DIVIDER_KEEP_LEFT_COMPONENT = 0.0;
    private static final double DIVIDER_KEEP_RIGHT_COMPONENT = 1.0;
    private static final double DIVIDER_KEEP_PROPORTION = 0.5;
    private static final int DIVIDER_POSITION_DEFAULT = -1;
    private static final int DIVIDER_POSITION_HIDDEN_LEFT_OR_TOP = -2;
    private static final int DIVIDER_POSITION_HIDDEN_RIGHT_OR_BOTTOM = -3;

    private final Map<UUID, PlayAreaPanel> players = new LinkedHashMap<>();
    private final Map<UUID, Boolean> playersWhoLeft = new LinkedHashMap<>();

    // non modal frames
    private final Map<UUID, CardInfoWindowDialog> exiles = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> revealed = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> lookedAt = new HashMap<>();
    private final Map<String, CardsView> graveyards = new HashMap<>(); // need to sync selection
    private final Map<String, CardInfoWindowDialog> graveyardWindows = new HashMap<>();
    private final Map<String, CardInfoWindowDialog> companion = new HashMap<>();
    private final Map<String, CardsView> sideboards = new HashMap<>(); // need to sync selection
    private final Map<String, CardInfoWindowDialog> sideboardWindows = new HashMap<>();
    private final ArrayList<ShowCardsDialog> pickTarget = new ArrayList<>();
    private final ArrayList<PickPileDialog> pickPile = new ArrayList<>();
    private final Map<String, CardHintsHelperDialog> cardHintsWindows = new LinkedHashMap<>();

    private UUID currentTableId;
    private UUID parentTableId;
    private UUID gameId;
    private UUID playerId; // playerId of the player
    GamePane gamePane;
    private ReplayTask replayTask;
    private final PickNumberDialog pickNumber;
    private final PickMultiNumberDialog pickMultiNumber;
    private JLayeredPane jLayeredPane;
    private String chosenHandKey = "You";
    private final skipButtonsList skipButtons = new skipButtonsList();

    private boolean menuNameSet = false;
    private boolean handCardsOfOpponentAvailable = false;

    private final Map<String, Card> loadedCards = new HashMap<>();

    private int storedHeight;
    private final Map<String, HoverButton> phaseButtons = new LinkedHashMap<>(); // phase name, phase button

    // splitters with save and restore feature
    // TODO: use same logic for draft and deck editor splitters like SplitterManager
    private final Map<String, MageSplitter> splitters = new LinkedHashMap<>(); // settings key, splitter
    // do not save splitters in intermediate state, e.g. connection to new server with active game
    private boolean isSplittersFullyRestored = false;

    public static class MageSplitter {
        JSplitPane splitPane;
        double defaultProportion;

        MageSplitter(JSplitPane splitPane, double defaultProportion) {
            this.splitPane = splitPane;
            this.defaultProportion = defaultProportion;
        }
    }

    private MageDialogState choiceWindowState;

    private boolean initComponents;

    private Timer resizeTimer; // can't be final

    private enum PopUpMenuType {
        TRIGGER_ORDER
    }

    // CardView popupMenu was invoked last
    private CardView cardViewPopupMenu;

    // popup menu for triggered abilities order
    private JPopupMenu popupMenuTriggerOrder;

    // keep game data for updates/re-draws
    public static class LastGameData {
        int messageId;
        GameView game;
        boolean showPlayable;
        Map<String, Serializable> options;
        Set<UUID> targets;
        Map<UUID, CardView> allCardsIndex = new HashMap<>(); // fast access to all game objects (for cards hints, etc)

        private void setNewGame(GameView game) {
            this.game = game;
            prepareAllCardsIndex();
        }

        private void prepareAllCardsIndex() {
            this.allCardsIndex.clear();
            if (this.game == null) {
                return;
            }

            this.game.getMyHand().values().forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getMyHelperEmblems().values().forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getStack().values().forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getExile()
                    .stream()
                    .flatMap(s -> s.values().stream())
                    .forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getLookedAt()
                    .stream()
                    .flatMap(s -> s.getCards().values().stream())
                    .filter(c -> c instanceof CardView)
                    .map(c -> (CardView) c)
                    .forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getRevealed().stream()
                    .flatMap(s -> s.getCards().values().stream())
                    .forEach(c -> this.allCardsIndex.put(c.getId(), c));
            this.game.getPlayers().forEach(player -> {
                player.getBattlefield().values().forEach(c -> this.allCardsIndex.put(c.getId(), c));
                player.getGraveyard().values().forEach(c -> this.allCardsIndex.put(c.getId(), c));
                Optional.ofNullable(player.getTopCard()).ifPresent(c -> this.allCardsIndex.put(c.getId(), c));
                // TODO: add support of dungeon, emblem all another non-card objects
                // commanders and custom emblems
                player.getCommandObjectList()
                        .forEach(object -> {
                            if (object instanceof CardView) {
                                // commanders and custom emblems
                                this.allCardsIndex.put(object.getId(), (CardView) object);
                            } else if (object instanceof DungeonView) {
                                // dungeons
                                this.allCardsIndex.put(object.getId(), new CardView((DungeonView) object));
                            } else {
                                // TODO: enable after all view types added here?
                                //throw new IllegalArgumentException("Unsupported object type: " + object.getName() + " - " + object.getClass().getSimpleName());
                            }
                        });
            });
        }

        public Set<UUID> getChosenTargets() {
            if (options != null && options.containsKey("chosenTargets")) {
                return (Set<UUID>) options.get("chosenTargets");
            } else {
                return Collections.emptySet();
            }
        }

        public CardView findCard(UUID id) {
            return this.allCardsIndex.getOrDefault(id, null);
        }
    }

    private final LastGameData lastGameData = new LastGameData();

    public LastGameData getLastGameData() {
        return this.lastGameData;
    }

    public GamePanel() {
        initComponents = true;
        initComponents();

        // prepare command panels (feedback, hand, skip buttons and stack)
        if (DebugUtil.GUI_GAME_DRAW_COMMANDS_PANEL_BORDER) {
            pnlHelperHandButtonsStackArea.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        }

        // all game panels
        pnlHelperHandButtonsStackArea.removeAll();
        pnlHelperHandButtonsStackArea.setLayout(new BorderLayout());
        // battlefields + phases
        JPanel pnlBattlefieldAndPhases = new JPanel(new BorderLayout());
        pnlBattlefieldAndPhases.setOpaque(false);
        pnlBattlefieldAndPhases.add(pnlBattlefield, BorderLayout.CENTER);
        pnlBattlefieldAndPhases.add(phasesContainer, BorderLayout.EAST);
        pnlHelperHandButtonsStackArea.add(pnlBattlefieldAndPhases, BorderLayout.CENTER);
        // commands (feedback + hand + skip + stack)
        JPanel pnlCommandsRoot = new JPanel(new BorderLayout());
        pnlCommandsRoot.setOpaque(false);
        // ... feedback + hand
        JPanel pnlCommandsFeedbackAndHand = new JPanel(new BorderLayout());
        pnlCommandsFeedbackAndHand.setOpaque(false);
        pnlCommandsFeedbackAndHand.add(feedbackPanel, BorderLayout.NORTH);
        pnlCommandsFeedbackAndHand.add(handContainer, BorderLayout.CENTER);
        // ... skip + stack
        JPanel pnlCommandsSkipAndStack = new JPanel(new BorderLayout());
        pnlCommandsSkipAndStack.setOpaque(false);
        pnlCommandsSkipAndStack.add(pnlShortCuts, BorderLayout.NORTH);
        pnlCommandsSkipAndStack.add(stackObjects, BorderLayout.CENTER);
        // ... split: feedback + hand <|> skip + stack
        splitHandAndStack.setLeftComponent(pnlCommandsFeedbackAndHand);
        splitHandAndStack.setRightComponent(pnlCommandsSkipAndStack);
        splitHandAndStack.setResizeWeight(DIVIDER_KEEP_RIGHT_COMPONENT);
        pnlCommandsFeedbackAndHand.setMinimumSize(new Dimension(0, 0)); // allow any sizes for hand
        pnlCommandsSkipAndStack.setMinimumSize(new Dimension(0, 0)); // allow any sizes for stack
        // ... all
        pnlCommandsRoot.add(splitHandAndStack, BorderLayout.CENTER);
        pnlHelperHandButtonsStackArea.add(pnlCommandsRoot, BorderLayout.SOUTH);

        // prepare commands buttons panel with flow layout (instead custom from IDE)
        // size changes in helper method at the end
        if (DebugUtil.GUI_GAME_DRAW_SKIP_BUTTONS_PANEL_BORDER) {
            pnlShortCuts.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        pnlShortCuts.removeAll();
        pnlShortCuts.setLayout(null); // real layout on size settings
        pnlShortCuts.add(btnSkipToNextTurn);
        pnlShortCuts.add(btnSkipToEndTurn);
        pnlShortCuts.add(btnSkipToNextMain);
        pnlShortCuts.add(btnSkipToYourTurn);
        pnlShortCuts.add(btnSkipStack);
        pnlShortCuts.add(btnSkipToEndStepBeforeYourTurn);
        pnlShortCuts.add(txtHoldPriority);
        //pnlShortCuts.add(btnToggleMacro);
        pnlShortCuts.add(btnSwitchHands);
        pnlShortCuts.add(btnCancelSkip);
        pnlShortCuts.add(btnConcede);
        pnlShortCuts.add(btnStopWatching);

        pickNumber = new PickNumberDialog();
        MageFrame.getDesktop().add(pickNumber, pickNumber.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);

        pickMultiNumber = new PickMultiNumberDialog();
        MageFrame.getDesktop().add(pickMultiNumber, pickMultiNumber.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);

        this.feedbackPanel.setConnectedChatPanel(this.userChatPanel);

        // Override layout (I can't edit generated code)
        // TODO: research - why it used all that panels on the root
        this.setLayout(new BorderLayout());
        final JLayeredPane jLayeredBackgroundPane = new JLayeredPane();
        jLayeredBackgroundPane.setSize(1024, 768);
        this.add(jLayeredBackgroundPane);
        jLayeredBackgroundPane.add(splitGameAndBigCard, JLayeredPane.DEFAULT_LAYER);

        Map<String, JComponent> myUi = getUIComponents(jLayeredBackgroundPane);
        Plugins.instance.updateGamePanel(myUi);

        // Enlarge jlayeredpane on resize of game panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = ((JComponent) e.getSource()).getWidth();
                int height = ((JComponent) e.getSource()).getHeight();
                jLayeredBackgroundPane.setSize(width, height);
                splitGameAndBigCard.setSize(width, height);

                if (height < storedHeight) {
                    // TODO: wtf, is it needs? Research and delete that code with storedHeight
                    pnlBattlefield.setSize(0, 200);
                }
                storedHeight = height;

                sizeToScreen();
            }
        });

        bigCardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // TODO: on panel resize card don't want to redraw until new mouse move over card
                sizeBigCard();
            }
        });

        // Resize the width of the stack area if the size of the play area is changed
        ComponentAdapter componentAdapterPlayField = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (initComponents) {
                    return;
                }
                if (resizeTimer.isRunning()) {
                    resizeTimer.restart();
                } else {
                    resizeTimer.start();
                }
            }
        };

        resizeTimer = new Timer(1000, evt -> SwingUtilities.invokeLater(() -> {
            if (initComponents) {
                return;
            }
            resizeTimer.stop();
            setGUISize(false);
            feedbackPanel.changeGUISize();
        }));

        pnlHelperHandButtonsStackArea.addComponentListener(componentAdapterPlayField);

        initComponents = false;

        setGUISize(true);
    }

    private Map<String, JComponent> getUIComponents(JLayeredPane jLayeredPane) {
        Map<String, JComponent> components = new HashMap<>();

        components.put("splitChatAndLogs", splitChatAndLogs);
        components.put("splitHandAndStack", splitHandAndStack);
        components.put("splitBattlefieldAndChats", splitBattlefieldAndChats);
        components.put("splitGameAndBigCard", splitGameAndBigCard);
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

        this.gameChatPanel.cleanUp();
        this.userChatPanel.cleanUp();

        this.removeListener();

        this.handContainer.cleanUp();
        this.stackObjects.cleanUp();
        for (Map.Entry<UUID, PlayAreaPanel> playAreaPanelEntry : players.entrySet()) {
            playAreaPanelEntry.getValue().CleanUp();
        }
        this.players.clear();
        this.playersWhoLeft.clear();

        uninstallComponents();

        if (pickNumber != null) {
            pickNumber.removeDialog();
        }
        if (pickMultiNumber != null) {
            pickMultiNumber.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : exiles.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : graveyardWindows.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : sideboardWindows.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : revealed.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : lookedAt.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardInfoWindowDialog windowDialog : companion.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }
        for (CardHintsHelperDialog windowDialog : cardHintsWindows.values()) {
            windowDialog.cleanUp();
            windowDialog.removeDialog();
        }

        clearPickDialogs();

        Plugins.instance.getActionCallback().hideOpenComponents();
        try {
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (InterruptedException ex) {
            logger.fatal("popupContainer error:", ex);
        }
    }

    private void hidePickDialogs() {
        // temporary hide opened dialog on redraw/update
        for (ShowCardsDialog dialog : this.pickTarget) {
            dialog.setVisible(false);
        }
        for (PickPileDialog dialog : this.pickPile) {
            dialog.setVisible(false);
        }
    }

    private void clearPickDialogs() {
        // remove dialogs forever on clean or full update
        clearPickTargetDialogs();
        clearPickPileDialogs();
    }

    private void clearPickTargetDialogs() {
        for (ShowCardsDialog dialog : this.pickTarget) {
            dialog.cleanUp();
            dialog.removeDialog();
        }
        this.pickTarget.clear();
    }

    private void clearPickPileDialogs() {
        for (PickPileDialog dialog : this.pickPile) {
            dialog.cleanUp();
            dialog.removeDialog();
        }
        this.pickPile.clear();
    }


    public void changeGUISize() {
        initComponents = true;
        setGUISize(true);
        stackObjects.changeGUISize();
        feedbackPanel.changeGUISize();
        handContainer.changeGUISize();
        for (PlayAreaPanel playAreaPanel : players.values()) {
            playAreaPanel.changeGUISize();
        }

        for (CardInfoWindowDialog windowDialog : exiles.values()) {
            windowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog windowDialog : revealed.values()) {
            windowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog windowDialog : lookedAt.values()) {
            windowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog windowDialog : companion.values()) {
            windowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog windowDialog : graveyardWindows.values()) {
            windowDialog.changeGUISize();
        }
        for (CardInfoWindowDialog windowDialog : sideboardWindows.values()) {
            windowDialog.changeGUISize();
        }
        for (CardHintsHelperDialog windowDialog : cardHintsWindows.values()) {
            windowDialog.changeGUISize();
        }
        for (ShowCardsDialog windowDialog : pickTarget) {
            windowDialog.changeGUISize();
        }
        for (PickPileDialog windowDialog : pickPile) {
            windowDialog.changeGUISize();
        }

        this.revalidate();
        this.repaint();
        initComponents = false;
    }

    private void setGUISize(boolean themeReload) {
        splitters.values().forEach(splitter -> {
            splitter.splitPane.setDividerSize(GUISizeHelper.dividerBarSize);
        });

        txtHoldPriority.setFont(new Font(GUISizeHelper.gameFeedbackPanelFont.getFontName(), Font.BOLD, GUISizeHelper.gameFeedbackPanelFont.getSize()));
        GUISizeHelper.changePopupMenuFont(popupMenuTriggerOrder);

        // commands panel
        // hand <|> stack
        int upperPanelsHeight = getSkipButtonsPanelDefaultHeight();
        feedbackPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, upperPanelsHeight));
        feedbackPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, upperPanelsHeight));
        pnlShortCuts.setPreferredSize(new Dimension(500, upperPanelsHeight));
        pnlShortCuts.setMaximumSize(new Dimension(500, upperPanelsHeight));

        // stack
        stackObjects.setCardDimension(GUISizeHelper.handCardDimension);
        stackObjects.changeGUISize(); // must call to cards fit

        // game logs and chat
        userChatPanel.changeGUISize(GUISizeHelper.chatFont);
        gameChatPanel.changeGUISize(GUISizeHelper.chatFont);

        // skip buttons - sizes
        // must be able to put controls in 2 rows
        float guiScale = GUISizeHelper.dialogGuiScale;
        int hGap = GUISizeHelper.guiSizeScale(SKIP_BUTTONS_SPACE_H, guiScale);
        int vGap = GUISizeHelper.guiSizeScale(SKIP_BUTTONS_SPACE_V, guiScale);
        pnlShortCuts.setLayout(new FlowLayout(FlowLayout.RIGHT, hGap, vGap));
        // skip buttons - sizes
        Dimension strictSize = new Dimension(2 * GUISizeHelper.gameCommandButtonHeight, GUISizeHelper.gameCommandButtonHeight);
        setSkipButtonSize(btnCancelSkip, guiScale, strictSize);
        setSkipButtonSize(btnSkipToNextTurn, guiScale, strictSize);
        setSkipButtonSize(btnSkipToEndTurn, guiScale, strictSize);
        setSkipButtonSize(btnSkipToEndStepBeforeYourTurn, guiScale, strictSize);
        setSkipButtonSize(btnSkipToYourTurn, guiScale, strictSize);
        setSkipButtonSize(btnSkipToNextMain, guiScale, strictSize);
        setSkipButtonSize(btnSkipStack, guiScale, strictSize);
        setSkipButtonSize(btnConcede, guiScale, strictSize);
        setSkipButtonSize(btnToggleMacro, guiScale, strictSize);
        setSkipButtonSize(btnSwitchHands, guiScale, strictSize);
        setSkipButtonSize(btnStopWatching, guiScale, strictSize);
        pnlShortCuts.invalidate();

        // phase buttons - sizes
        int buttonSize = GUISizeHelper.gamePhaseButtonSize;
        guiScale = GUISizeHelper.dialogGuiScale;
        hGap = GUISizeHelper.guiSizeScale(PHASE_BUTTONS_SPACE_H, guiScale);
        vGap = GUISizeHelper.guiSizeScale(PHASE_BUTTONS_SPACE_V, guiScale);
        BoxLayout layout = new BoxLayout(jPhases, BoxLayout.Y_AXIS);
        jPhases.setLayout(layout);
        int fullPhaseWidth = Math.round(1.5f * GUISizeHelper.gamePhaseButtonSize);
        jPhases.setPreferredSize(new Dimension(fullPhaseWidth, (vGap * phaseButtons.size()) + (buttonSize * phaseButtons.size())));
        jPhases.setMaximumSize(new Dimension(fullPhaseWidth, Short.MAX_VALUE));
        phaseButtons.forEach((phaseName, phaseButton) -> {
            phaseButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        });
        // phase buttons - active size
        if (lastGameData.game != null) {
            updateActivePhase(lastGameData.game.getStep());
        }

        if (themeReload) {
            reloadThemeRelatedGraphic();
        }
    }

    private int getSkipButtonsPanelDefaultHeight() {
        // make sure it will get two rows of buttons
        float guiScale = GUISizeHelper.dialogGuiScale;
        int vGap = GUISizeHelper.guiSizeScale(SKIP_BUTTONS_SPACE_V, guiScale);
        //int extraSpace = GUISizeHelper.guiSizeScale(30, guiScale); // extra space for messages in feedback
        int extraSpace = 0; // no needs in extra space for 3+ lines
        int lines = 3;
        return extraSpace + ((lines * 2 - 1) * vGap) + (lines * GUISizeHelper.gameCommandButtonHeight);
    }

    private void reloadThemeRelatedGraphic() {
        // skip buttons - images
        int buttonHeight = GUISizeHelper.gameCommandButtonHeight;
        setSkipButtonImage(btnCancelSkip, ImageManagerImpl.instance.getCancelSkipButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipToNextTurn, ImageManagerImpl.instance.getSkipNextTurnButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipToEndTurn, ImageManagerImpl.instance.getSkipEndTurnButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipToEndStepBeforeYourTurn, ImageManagerImpl.instance.getSkipEndStepBeforeYourTurnButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipToYourTurn, ImageManagerImpl.instance.getSkipYourNextTurnButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipToNextMain, ImageManagerImpl.instance.getSkipMainButtonImage(buttonHeight));
        setSkipButtonImage(btnSkipStack, ImageManagerImpl.instance.getSkipStackButtonImage(buttonHeight));
        setSkipButtonImage(btnConcede, ImageManagerImpl.instance.getConcedeButtonImage(buttonHeight));
        setSkipButtonImage(btnToggleMacro, ImageManagerImpl.instance.getToggleRecordMacroButtonImage(buttonHeight));
        setSkipButtonImage(btnSwitchHands, ImageManagerImpl.instance.getSwitchHandsButtonImage(buttonHeight));
        setSkipButtonImage(btnStopWatching, ImageManagerImpl.instance.getStopWatchButtonImage(buttonHeight));

        // hotkeys for skip buttons
        boolean displayButtonText = PreferencesDialog.getCurrentTheme().isShortcutsVisibleForSkipButtons();
        btnCancelSkip.setShowKey(displayButtonText);
        btnSkipToNextTurn.setShowKey(displayButtonText);
        btnSkipToEndTurn.setShowKey(displayButtonText);
        btnSkipToEndStepBeforeYourTurn.setShowKey(displayButtonText);
        btnSkipToYourTurn.setShowKey(displayButtonText);
        btnSkipToNextMain.setShowKey(displayButtonText);
        btnSkipStack.setShowKey(displayButtonText);
        btnToggleMacro.setShowKey(displayButtonText);

        // phase buttons
        phaseButtons.forEach((phaseName, phaseButton) -> {
            Image buttonImage = ImageManagerImpl.instance.getPhaseImage(phaseName, GUISizeHelper.gamePhaseButtonSize);
            Rectangle buttonRect = new Rectangle(buttonImage.getWidth(null), buttonImage.getHeight(null));
            phaseButton.update(phaseButton.getText(), buttonImage, buttonImage, buttonImage, buttonImage, buttonRect);
        });

        // player panels
        if (lastGameData.game != null) {
            lastGameData.game.getPlayers().forEach(player -> {
                PlayAreaPanel playPanel = this.players.getOrDefault(player.getPlayerId(), null);
                if (playPanel != null) {
                    // see test render dialog for refresh commands order
                    playPanel.getPlayerPanel().fullRefresh(GUISizeHelper.playerPanelGuiScale);
                    playPanel.init(player, bigCard, gameId, player.getPriorityTimeLeftSecs());
                    playPanel.update(lastGameData.game, player, lastGameData.targets, lastGameData.getChosenTargets());
                    playPanel.getPlayerPanel().sizePlayerPanel(false);
                }
            });
        }

        // as workaround: can change size for closed ability picker only
        if (this.abilityPicker != null && !this.abilityPicker.isVisible()) {
            this.abilityPicker.fullRefresh(GUISizeHelper.dialogGuiScale);
            this.abilityPicker.init(gameId, bigCard);
        }
        if (this.pickMultiNumber != null && !this.pickMultiNumber.isVisible()) {
            // TODO: add pick number dialogs support here
            //this.pickMultiNumber.fullRefresh(GUISizeHelper.dialogGuiScale);
            this.pickMultiNumber.init(gameId, bigCard);
        }
    }

    private void setSkipButtonImage(JButton button, Image image) {
        button.setIcon(new ImageIcon(image));
    }

    private void setSkipButtonSize(JComponent button, float guiScale, Dimension size) {
        if (button instanceof KeyboundButton) {
            ((KeyboundButton) button).updateGuiScale(guiScale);
        }
    }

    private Map<String, Integer> loadSplitterLocationsFromSettings(String settingsKey) {
        Map<String, Integer> res;
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        try {
            String savedData = PreferencesDialog.getCachedValue(settingsKey, "");
            res = new Gson().fromJson(savedData, type);
        } catch (Exception e) {
            res = null;
            logger.error("Found broken data for divider locations " + settingsKey, e);
        }

        if (res == null) {
            res = new HashMap<>();
        }

        return res;
    }

    private void saveSplitterLocationsToSettings(Map<String, Integer> newValues, String settingsKey) {
        PreferencesDialog.saveValue(settingsKey, new Gson().toJson(newValues));
    }

    private void saveSplitters() {
        if (!isSplittersFullyRestored) {
            logger.warn("splitters do not fully restored yet");
            return;
        }

        splitters.forEach((settingsKey, splitter) -> {
            saveSplitter(splitter.splitPane, settingsKey);
        });
    }

    private void saveSplitter(JSplitPane splitPane, String settingsKey) {
        Map<String, Integer> allLocations = loadSplitterLocationsFromSettings(settingsKey);

        Rectangle screenRec = MageFrame.getDesktop().getBounds();
        String screenKey = String.format("%d_x_%d", screenRec.width, screenRec.height);

        // store location information (position or hidden state)
        // splits with hidden panels will give location < min/max divider
        int newLocation = splitPane.getDividerLocation();
        if (newLocation == 0 || newLocation < splitPane.getMinimumDividerLocation()) {
            newLocation = DIVIDER_POSITION_HIDDEN_LEFT_OR_TOP;
        } else if (newLocation > splitPane.getMaximumDividerLocation()) {
            newLocation = DIVIDER_POSITION_HIDDEN_RIGHT_OR_BOTTOM;
        }

        allLocations.put(screenKey, newLocation);
        saveSplitterLocationsToSettings(allLocations, settingsKey);
    }

    /**
     * Restore split position from last time used
     *
     * @param splitPane
     * @param settingsKey
     * @param defaultProportion 0.25 means 25% for left component and 75% for right
     */
    private void restoreSplitter(JSplitPane splitPane, String settingsKey, double defaultProportion) {
        Map<String, Integer> allLocations = loadSplitterLocationsFromSettings(settingsKey);

        Rectangle screenRec = MageFrame.getDesktop().getBounds();
        String screenKey = String.format("%d_x_%d", screenRec.width, screenRec.height);

        // on first run it has nothing in saved values, so make sure to use default location (depends on inner components preferred sizes)
        // WARNING, new divider location must be restored independently in swing threads one by one
        int newLocation = allLocations.getOrDefault(screenKey, DIVIDER_POSITION_DEFAULT);
        if (newLocation == DIVIDER_POSITION_DEFAULT) {
            // use default location
            SwingUtilities.invokeLater(() -> {
                splitPane.resetToPreferredSizes();
                splitPane.setDividerLocation(defaultProportion);
            });
        } else if (newLocation == DIVIDER_POSITION_HIDDEN_LEFT_OR_TOP) {
            // use hidden (hide left)
            SwingUtilities.invokeLater(() -> {
                splitPane.resetToPreferredSizes();
                splitPane.setDividerLocation(defaultProportion);
                splitPane.getLeftComponent().setMinimumSize(new Dimension());
                splitPane.setDividerLocation(0.0d);
            });
        } else if (newLocation == DIVIDER_POSITION_HIDDEN_RIGHT_OR_BOTTOM) {
            // use hidden (hide right)
            SwingUtilities.invokeLater(() -> {
                splitPane.resetToPreferredSizes();
                splitPane.setDividerLocation(defaultProportion);
                splitPane.getRightComponent().setMinimumSize(new Dimension());
                splitPane.setDividerLocation(1.0d);
            });
        } else {
            // use saved location
            SwingUtilities.invokeLater(() -> {
                splitPane.resetToPreferredSizes();
                splitPane.setDividerLocation(defaultProportion);
                splitPane.setDividerLocation(newLocation);
            });
        }
    }

    private void restoreSplitters() {
        // split/divider locations must be restored after game panel will be visible, e.g. on frame activated
        isSplittersFullyRestored = false;
        SwingUtilities.invokeLater(() -> {
            restoreSplittersByQueue(new LinkedHashMap<>(this.splitters));
        });
    }

    private void restoreSplittersByQueue(Map<String, MageSplitter> splittersQueue) {
        if (splittersQueue.isEmpty()) {
            isSplittersFullyRestored = true;
            return;
        }

        // current
        String currentKey = splittersQueue.keySet().stream().findFirst().get();
        MageSplitter currentSplitter = splittersQueue.remove(currentKey);
        restoreSplitter(currentSplitter.splitPane, currentKey, currentSplitter.defaultProportion);

        // next in queue
        SwingUtilities.invokeLater(() -> {
            restoreSplittersByQueue(splittersQueue);
        });
    }

    private void sizeBigCard() {
        int width = bigCard.getParent().getWidth();
        int height = Math.round(width * GUISizeHelper.CARD_WIDTH_TO_HEIGHT_COEF);
        bigCard.setPreferredSize(new Dimension(width, height));
        bigCard.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
    }

    private void sizeToScreen() {
        // on resize frame
        Rectangle rect = this.getBounds();
        pnlShortCuts.revalidate();
        for (PlayAreaPanel p : players.values()) {
            p.getPlayerPanel().sizePlayerPanel(false);
        }

        ArrowBuilder.getBuilder().setSize(rect.width, rect.height);

        DialogManager.getManager(gameId).setScreenWidth(rect.width);
        DialogManager.getManager(gameId).setScreenHeight(rect.height);
        DialogManager.getManager(gameId).setBounds(0, 0, rect.width, rect.height);
    }

    public synchronized void showGame(UUID currentTableId, UUID parentTableId, UUID gameId, UUID playerId, GamePane gamePane) {
        this.currentTableId = currentTableId;
        this.parentTableId = parentTableId;
        this.gameId = gameId;
        this.gamePane = gamePane;
        this.playerId = playerId;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId, bigCard);
        this.feedbackPanel.clear();
        this.pickMultiNumber.init(gameId, bigCard);
        this.abilityPicker.init(gameId, bigCard);
        this.btnConcede.setVisible(true);
        this.btnStopWatching.setVisible(false);
        this.btnSwitchHands.setVisible(false);
        this.btnCancelSkip.setVisible(true);
        this.btnToggleMacro.setVisible(true);

        // cards popup info in chats
        this.gameChatPanel.setGameData(gameId, bigCard);
        this.userChatPanel.setGameData(gameId, bigCard);

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
            if (!AppUtil.isAppActive()) {
                MageTray.instance.displayMessage("Your game has started!");
                MageTray.instance.blink();
            }
        }
    }

    public synchronized void watchGame(UUID currentTableId, UUID parentTableId, UUID gameId, GamePane gamePane) {
        this.currentTableId = currentTableId;
        this.parentTableId = parentTableId;
        this.gameId = gameId;
        this.gamePane = gamePane;
        this.playerId = null;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId, bigCard);
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
        this.currentTableId = null;
        this.parentTableId = null;
        this.gameId = gameId;
        this.playerId = null;
        MageFrame.addGame(gameId, this);
        this.feedbackPanel.init(gameId, bigCard);
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

    public synchronized void init(int messageId, GameView game, boolean callGameUpdateAfterInit) {
        addPlayers(game);

        // default menu states
        setMenuStates(
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                holdingPriority
        );

        if (callGameUpdateAfterInit) {
            updateGame(messageId, game);
        }
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
                new PlayAreaPanelOptions(game.isPlayer(), player.isHuman(), game.isPlayer(),
                        game.isRollbackTurnsAllowed(), row == 0));
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
                    new PlayAreaPanelOptions(game.isPlayer(), player.isHuman(), false, game.isRollbackTurnsAllowed(),
                            row == 0));
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

        // set init sizes
        for (PlayAreaPanel p : players.values()) {
            p.getPlayerPanel().sizePlayerPanel(false);
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

    public synchronized void updateGame(int messageId, GameView game) {
        updateGame(messageId, game, false, null, null);
    }

    public synchronized void updateGame(int messageId, GameView game, boolean showPlayable, Map<String, Serializable> options, Set<UUID> targets) {
        keepLastGameData(messageId, game, showPlayable, options, targets);

        if (this.players.isEmpty() && !game.getPlayers().isEmpty()) {
            logger.warn("Found empty players list, trying to init game again (possible reason: reconnection)");
            init(messageId, game, false);
        }

        prepareSelectableView();
        updateGame();
    }

    public synchronized void updateGame() {
        if (playerId == null && lastGameData.game.getWatchedHands().isEmpty()) {
            this.handContainer.setVisible(false);
        } else {
            this.handContainer.setVisible(true);
            handCards.clear();
            if (!lastGameData.game.getWatchedHands().isEmpty()) {
                for (Map.Entry<String, SimpleCardsView> hand : lastGameData.game.getWatchedHands().entrySet()) {
                    handCards.put(hand.getKey(), CardsViewUtil.convertSimple(hand.getValue(), loadedCards));
                }
            }
            if (playerId != null) {
                handCards.put(YOUR_HAND, lastGameData.game.getMyHand());
                // Get opponents hand cards if available (only possible for players)
                if (!lastGameData.game.getOpponentHands().isEmpty()) {
                    for (Map.Entry<String, SimpleCardsView> hand : lastGameData.game.getOpponentHands().entrySet()) {
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
                boolean change = (handCardsOfOpponentAvailable == lastGameData.game.getOpponentHands().isEmpty());
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

        if (lastGameData.game.getPhase() != null) {
            this.txtPhase.setText(lastGameData.game.getPhase().toString());
        } else {
            this.txtPhase.setText("");
        }

        if (lastGameData.game.getStep() != null) {
            updateActivePhase(lastGameData.game.getStep());
            this.txtStep.setText(lastGameData.game.getStep().toString());
        } else {
            logger.debug("Step is empty");
            this.txtStep.setText("");
        }

        this.txtActivePlayer.setText(lastGameData.game.getActivePlayerName());
        this.txtPriority.setText(lastGameData.game.getPriorityPlayerName());
        this.txtTurn.setText(Integer.toString(lastGameData.game.getTurn()));

        List<UUID> possibleAttackers = new ArrayList<>();
        if (lastGameData.options != null && lastGameData.options.containsKey(Constants.Option.POSSIBLE_ATTACKERS)) {
            if (lastGameData.options.get(Constants.Option.POSSIBLE_ATTACKERS) instanceof List) {
                possibleAttackers.addAll((List) lastGameData.options.get(Constants.Option.POSSIBLE_ATTACKERS));
            }
        }

        List<UUID> possibleBlockers = new ArrayList<>();
        if (lastGameData.options != null && lastGameData.options.containsKey(Constants.Option.POSSIBLE_BLOCKERS)) {
            if (lastGameData.options.get(Constants.Option.POSSIBLE_BLOCKERS) instanceof List) {
                possibleBlockers.addAll((List) lastGameData.options.get(Constants.Option.POSSIBLE_BLOCKERS));
            }
        }

        for (PlayerView player : lastGameData.game.getPlayers()) {
            if (players.containsKey(player.getPlayerId())) {
                if (!possibleAttackers.isEmpty()) {
                    for (UUID permanentId : possibleAttackers) {
                        if (player.getBattlefield().containsKey(permanentId)) {
                            player.getBattlefield().get(permanentId).setCanAttack(true);
                        }
                    }
                }
                if (!possibleBlockers.isEmpty()) {
                    for (UUID permanentId : possibleBlockers) {
                        if (player.getBattlefield().containsKey(permanentId)) {
                            player.getBattlefield().get(permanentId).setCanBlock(true);
                        }
                    }
                }
                players.get(player.getPlayerId()).update(lastGameData.game, player, lastGameData.targets, lastGameData.getChosenTargets());
                if (player.getPlayerId().equals(playerId)) {
                    skipButtons.updateFromPlayer(player);
                }

                // update open or remove closed graveyard windows
                graveyards.put(player.getName(), player.getGraveyard());
                if (graveyardWindows.containsKey(player.getName())) {
                    CardInfoWindowDialog windowDialog = graveyardWindows.get(player.getName());
                    if (windowDialog.isClosed()) {
                        graveyardWindows.remove(player.getName());
                    } else {
                        windowDialog.loadCardsAndShow(player.getGraveyard(), bigCard, gameId, false);
                    }
                }

                // update open or remove closed sideboard windows
                sideboards.put(player.getName(), player.getSideboard());
                if (sideboardWindows.containsKey(player.getName())) {
                    CardInfoWindowDialog windowDialog = sideboardWindows.get(player.getName());
                    if (windowDialog.isClosed()) {
                        sideboardWindows.remove(player.getName());
                    } else {
                        windowDialog.loadCardsAndShow(player.getSideboard(), bigCard, gameId, false);
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
        updateSkipButtons();

        if (!menuNameSet) {
            StringBuilder sb = new StringBuilder();
            if (playerId == null) {
                sb.append("Watching: ");
            } else {
                sb.append("Playing: ");
            }
            boolean first = true;
            for (PlayerView player : lastGameData.game.getPlayers()) {
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

        displayStack(lastGameData.game, bigCard, feedbackPanel, gameId);

        // auto-show exile views
        for (ExileView exile : lastGameData.game.getExile()) {
            CardInfoWindowDialog exileWindow = exiles.getOrDefault(exile.getId(), null);
            if (exileWindow == null) {
                exileWindow = new CardInfoWindowDialog(ShowType.EXILE, exile.getName());
                exiles.put(exile.getId(), exileWindow);
                MageFrame.getDesktop().add(exileWindow, exileWindow.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
                exileWindow.show();
            }
            exileWindow.loadCardsAndShow(exile, bigCard, gameId);
        }

        // update open or remove closed card hints windows
        clearClosedCardHintsWindows();
        cardHintsWindows.forEach((s, windowDialog) -> {
            // TODO: optimize for multiple windows (prepare data here and send it for filters/groups)
            windowDialog.loadHints(lastGameData.game);
        });

        // reveal and look at dialogs can unattached, so windows opened by game doesn't have it
        showRevealed(lastGameData.game);
        showLookedAt(lastGameData.game);

        // sideboard dialogs is unattached all the time -- user opens it by command

        showCompanion(lastGameData.game);
        if (!lastGameData.game.getCombat().isEmpty()) {
            CombatManager.instance.showCombat(lastGameData.game.getCombat(), gameId);
        } else {
            CombatManager.instance.hideCombat(gameId);
        }

        for (PlayerView player : lastGameData.game.getPlayers()) {
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

        //logger.info("game update, message = " + lastGameData.messageId + ", options = " + lastGameData.options + ", priority = " + lastGameData.game.getPriorityPlayerName());
        feedbackPanel.disableUndo();
        feedbackPanel.updateOptions(lastGameData.options);

        this.revalidate();
        this.repaint();
    }

    // skip buttons border
    private static final int BORDER_SIZE = 2;
    private static final Border BORDER_ACTIVE = new LineBorder(Color.orange, BORDER_SIZE);
    private static final Border BORDER_NON_ACTIVE = new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);

    // skip buttons info
    private class skipButton {

        private final String text;
        private final String extraFalse;
        private final String extraTrue;
        private final String hotkeyName;
        private boolean extraMode = false; // extra option enabled from preferences
        private boolean pressState = false; // activated by user or not

        skipButton(String text, String extraFalse, String extraTrue, String hotkeyName) {
            this.text = text;
            this.extraFalse = extraFalse;
            this.extraTrue = extraTrue;
            this.hotkeyName = hotkeyName;
        }

        public void setExtraMode(boolean enable) {
            this.extraMode = enable;
        }

        public void setPressState(boolean enable) {
            this.pressState = enable;
        }

        public String getTooltip() {
            // show hotkey and selects current button mode

            // text
            String res = "<html>"
                    + "<b>" + getCachedKeyText(this.hotkeyName) + "</b>"
                    + " - " + text;

            // mode
            String mesTrue = this.extraTrue;
            String mesFalse = this.extraFalse;
            if (!this.extraTrue.isEmpty() || !this.extraFalse.isEmpty()) {
                if (this.extraMode) {
                    mesTrue = "<b>" + mesTrue + "</b>";
                } else {
                    mesFalse = "<b>" + mesFalse + "</b>";
                }
                res = res.replace("EXTRA_FALSE", mesFalse);
                res = res.replace("EXTRA_TRUE", mesTrue);
                res = res + " - adjust using preferences";
            }
            return res;
        }

        public Border getBorder() {
            return this.pressState ? BORDER_ACTIVE : BORDER_NON_ACTIVE;
        }

    }

    private class skipButtonsList {

        private final skipButton turn;
        private final skipButton untilEndOfTurn;
        private final skipButton untilNextMain;
        private final skipButton allTurns;
        private final skipButton untilStackResolved;
        private final skipButton untilUntilEndStepBeforeMyTurn;

        skipButtonsList() {
            this.turn = new skipButton("Skip to next turn", "", "", KEY_CONTROL_NEXT_TURN);
            this.untilEndOfTurn = new skipButton("Skip to [EXTRA_TRUE / EXTRA_FALSE] END OF TURN step", "opponent", "next", KEY_CONTROL_END_STEP);
            this.untilNextMain = new skipButton("Skip to [EXTRA_TRUE / EXTRA_FALSE] MAIN step", "opponent", "next", KEY_CONTROL_MAIN_STEP);
            this.allTurns = new skipButton("Skip to YOUR turn", "", "", KEY_CONTROL_YOUR_TURN);
            this.untilStackResolved = new skipButton("Skip until stack is resolved [EXTRA_TRUE]", "", "or stop on new objects added", KEY_CONTROL_SKIP_STACK);
            this.untilUntilEndStepBeforeMyTurn = new skipButton("Skip to END OF TURN before YOUR", "", "", KEY_CONTROL_PRIOR_END);
        }

        private void updateExtraMode(PlayerView player) {
            this.turn.setExtraMode(false); // not used
            this.untilEndOfTurn.setExtraMode(player.getUserData().getUserSkipPrioritySteps().isStopOnAllEndPhases());
            this.untilNextMain.setExtraMode(player.getUserData().getUserSkipPrioritySteps().isStopOnAllMainPhases());
            this.allTurns.setExtraMode(false); // not used
            this.untilStackResolved.setExtraMode(player.getUserData().getUserSkipPrioritySteps().isStopOnStackNewObjects());
            this.untilUntilEndStepBeforeMyTurn.setExtraMode(false); // not used
        }

        private void updatePressState(PlayerView player) {
            this.turn.setPressState(player.isPassedTurn());
            this.untilEndOfTurn.setPressState(player.isPassedUntilEndOfTurn());
            this.untilNextMain.setPressState(player.isPassedUntilNextMain());
            this.allTurns.setPressState(player.isPassedAllTurns());
            this.untilStackResolved.setPressState(player.isPassedUntilStackResolved());
            this.untilUntilEndStepBeforeMyTurn.setPressState(player.isPassedUntilEndStepBeforeMyTurn());
        }

        public void updateFromPlayer(PlayerView player) {
            updateExtraMode(player);
            updatePressState(player);
        }

        private skipButton findButton(String hotkey) {
            switch (hotkey) {
                case KEY_CONTROL_NEXT_TURN:
                    return this.turn;
                case KEY_CONTROL_END_STEP:
                    return this.untilEndOfTurn;
                case KEY_CONTROL_MAIN_STEP:
                    return this.untilNextMain;
                case KEY_CONTROL_YOUR_TURN:
                    return this.allTurns;
                case KEY_CONTROL_SKIP_STACK:
                    return this.untilStackResolved;
                case KEY_CONTROL_PRIOR_END:
                    return this.untilUntilEndStepBeforeMyTurn;
                default:
                    logger.error("Unknown hotkey name " + hotkey);
                    return null;
            }
        }

        public String getTooltip(String hotkey) {
            skipButton butt = findButton(hotkey);
            return butt != null ? butt.getTooltip() : "";
        }

        public Border getBorder(String hotkey) {
            skipButton butt = findButton(hotkey);
            return butt != null ? butt.getBorder() : BORDER_NON_ACTIVE;
        }

        public void activateSkipButton(String hotkey) {
            // enable ONE button and disable all other (no needs to wait server feedback)
            this.turn.setPressState(false);
            this.untilEndOfTurn.setPressState(false);
            this.untilNextMain.setPressState(false);
            this.allTurns.setPressState(false);
            this.untilStackResolved.setPressState(false);
            this.untilUntilEndStepBeforeMyTurn.setPressState(false);

            if (!hotkey.isEmpty()) {
                skipButton butt = findButton(hotkey);
                if (butt != null) butt.setPressState(true);
            }
        }
    }

    private void updateSkipButtons() {
        // hints
        btnSkipToNextTurn.setToolTipText(skipButtons.turn.getTooltip());
        btnSkipToEndTurn.setToolTipText(skipButtons.untilEndOfTurn.getTooltip());
        btnSkipToNextMain.setToolTipText(skipButtons.untilNextMain.getTooltip());
        btnSkipStack.setToolTipText(skipButtons.untilStackResolved.getTooltip());
        btnSkipToYourTurn.setToolTipText(skipButtons.allTurns.getTooltip());
        btnSkipToEndStepBeforeYourTurn.setToolTipText(skipButtons.untilUntilEndStepBeforeMyTurn.getTooltip());

        // border
        btnSkipToNextTurn.setBorder(skipButtons.turn.getBorder());
        btnSkipToEndTurn.setBorder(skipButtons.untilEndOfTurn.getBorder());
        btnSkipToNextMain.setBorder(skipButtons.untilNextMain.getBorder());
        btnSkipStack.setBorder(skipButtons.untilStackResolved.getBorder());
        btnSkipToYourTurn.setBorder(skipButtons.allTurns.getBorder());
        btnSkipToEndStepBeforeYourTurn.setBorder(skipButtons.untilUntilEndStepBeforeMyTurn.getBorder());
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

    private void updateActivePhase(PhaseStep currentStep) {
        if (currentStep == null) {
            return;
        }

        switch (currentStep) {
            case UNTAP:
                updatePhaseButtons("Untap");
                break;
            case UPKEEP:
                updatePhaseButtons("Upkeep");
                break;
            case DRAW:
                updatePhaseButtons("Draw");
                break;
            case PRECOMBAT_MAIN:
                updatePhaseButtons("Main1");
                break;
            case BEGIN_COMBAT:
                updatePhaseButtons("Combat_Start");
                break;
            case DECLARE_ATTACKERS:
                updatePhaseButtons("Combat_Attack");
                break;
            case DECLARE_BLOCKERS:
                updatePhaseButtons("Combat_Block");
                break;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
                updatePhaseButtons("Combat_Damage");
                break;
            case END_COMBAT:
                updatePhaseButtons("Combat_End");
                break;
            case POSTCOMBAT_MAIN:
                updatePhaseButtons("Main2");
                break;
            case END_TURN:
            case CLEANUP:
                updatePhaseButtons("Cleanup");
                break;
            default:
                break;
        }
    }

    private void updatePhaseButtons(String currentPhaseName) {
        phaseButtons.forEach((phaseName, phaseButton) -> {
            if (phaseName.equals(currentPhaseName)) {
                //phaseButton.setBorder(this.phaseButtonBorderActive);
                phaseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            } else {
                //phaseButton.setBorder(this.phaseButtonBorderInactive);
                phaseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        });
        jPhases.invalidate();
    }

    public void onDeactivated() {
        // save current dividers location
        saveSplitters();

        // hide the non modal windows (because otherwise they are shown on top of the new active pane)
        // TODO: is it need to hide other dialogs like graveyards (CardsView)?
        for (CardInfoWindowDialog windowDialog : exiles.values()) {
            windowDialog.hideDialog();
        }
        for (CardInfoWindowDialog windowDialog : revealed.values()) {
            windowDialog.hideDialog();
        }
        for (CardInfoWindowDialog windowDialog : lookedAt.values()) {
            windowDialog.hideDialog();
        }
        for (CardInfoWindowDialog windowDialog : graveyardWindows.values()) {
            windowDialog.hideDialog();
        }
        for (CardInfoWindowDialog windowDialog : companion.values()) {
            windowDialog.hideDialog();
        }
        for (CardInfoWindowDialog windowDialog : sideboardWindows.values()) {
            windowDialog.hideDialog();
        }
        for (CardHintsHelperDialog windowDialog : cardHintsWindows.values()) {
            windowDialog.hideDialog();
        }
    }

    public void onActivated() {
        // restore divider positions
        // must be called by swing after all pack and paint done (possible bug: zero size in restored divider)
        SwingUtilities.invokeLater(this::restoreSplitters);

        // hide the non modal windows (because otherwise they are shown on top of the new active pane)
        for (CardInfoWindowDialog windowDialog : exiles.values()) {
            windowDialog.show();
        }
        for (CardInfoWindowDialog windowDialog : revealed.values()) {
            windowDialog.show();
        }
        for (CardInfoWindowDialog windowDialog : lookedAt.values()) {
            windowDialog.show();
        }
        for (CardInfoWindowDialog windowDialog : graveyardWindows.values()) {
            windowDialog.show();
        }
        for (CardInfoWindowDialog windowDialog : companion.values()) {
            windowDialog.show();
        }
        for (CardInfoWindowDialog windowDialog : sideboardWindows.values()) {
            windowDialog.show();
        }
        for (CardHintsHelperDialog windowDialog : cardHintsWindows.values()) {
            windowDialog.show();
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
        MageFrame.getDesktop().add(newGraveyard, newGraveyard.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        // use graveyards to sync selection (don't use player data here)
        newGraveyard.loadCardsAndShow(graveyards.get(playerName), bigCard, gameId, false);
    }

    private void clearClosedCardHintsWindows() {
        cardHintsWindows.entrySet().removeIf(entry -> entry.getValue().isClosed());
    }

    public void openCardHintsWindow(String code) {
        // clear closed
        clearClosedCardHintsWindows();

        // too many dialogs can cause bad GUI performance, so limit it
        if (cardHintsWindows.size() >= CardHintsHelperDialog.GUI_MAX_CARD_HINTS_DIALOGS_PER_GAME) {
            // show last one instead
            cardHintsWindows.values().stream().reduce((a, b) -> b).ifPresent(CardHintsHelperDialog::show);
            return;
        }

        // open new
        CardHintsHelperDialog newDialog = new CardHintsHelperDialog();
        newDialog.setSize(GUISizeHelper.dialogGuiScaleSize(newDialog.getSize()));
        newDialog.setGameData(this.lastGameData.game, this.gameId, this.bigCard);
        cardHintsWindows.put(code + UUID.randomUUID(), newDialog);
        MageFrame.getDesktop().add(newDialog, newDialog.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        newDialog.loadHints(lastGameData.game);
    }

    public void openSideboardWindow(UUID playerId) {
        if (lastGameData == null) {
            return;
        }

        PlayerView playerView = lastGameData.game.getPlayers().stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElse(null);
        if (playerView == null) {
            return;
        }

        if (sideboardWindows.containsKey(playerView.getName())) {
            CardInfoWindowDialog windowDialog = sideboardWindows.get(playerView.getName());
            if (windowDialog.isVisible()) {
                windowDialog.hideDialog();
            } else {
                windowDialog.show();
            }
            return;
        }

        CardInfoWindowDialog windowDialog = new CardInfoWindowDialog(ShowType.SIDEBOARD, playerView.getName());
        sideboardWindows.put(playerView.getName(), windowDialog);
        MageFrame.getDesktop().add(windowDialog, windowDialog.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        // use sideboards to sync selection (don't use player data here)
        windowDialog.loadCardsAndShow(sideboards.get(playerView.getName()), bigCard, gameId, false);
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

    private void showCompanion(GameView game) {
        for (RevealedView revealView : game.getCompanion()) {
            handleGameInfoWindow(companion, ShowType.COMPANION, revealView.getName(), revealView.getCards());
        }
        // Close the companion view if not in the game view
        companion.forEach((name, companionDialog) -> {
            if (game.getCompanion().stream().noneMatch(revealedView -> revealedView.getName().equals(name))) {
                try {
                    companionDialog.setClosed(true);
                } catch (PropertyVetoException e) {
                    logger.error("Couldn't close companion dialog", e);
                }
            }
        });
        removeClosedCardInfoWindows(companion);
    }

    private void handleGameInfoWindow(Map<String, CardInfoWindowDialog> windowMap, ShowType showType, String name, LinkedHashMap cardsView) {
        CardInfoWindowDialog cardInfoWindowDialog;
        if (!windowMap.containsKey(name)) {
            cardInfoWindowDialog = new CardInfoWindowDialog(showType, name);
            windowMap.put(name, cardInfoWindowDialog);
            MageFrame.getDesktop().add(cardInfoWindowDialog, cardInfoWindowDialog.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        } else {
            cardInfoWindowDialog = windowMap.get(name);
        }

        if (cardInfoWindowDialog != null && !cardInfoWindowDialog.isClosed()) {
            switch (showType) {
                case REVEAL:
                case REVEAL_TOP_LIBRARY:
                case COMPANION:
                    cardInfoWindowDialog.loadCardsAndShow((CardsView) cardsView, bigCard, gameId, false);
                    break;
                case LOOKED_AT:
                    cardInfoWindowDialog.loadCardsAndShow(CardsViewUtil.convertSimple((SimpleCardsView) cardsView), bigCard, gameId, false);
                    break;
                default:
                    break;
            }
        }
    }

    private void removeClosedCardInfoWindows(Map<String, CardInfoWindowDialog> windowMap) {
        // Remove closed window objects from the maps
        windowMap.entrySet().removeIf(entry -> entry.getValue().isClosed());
    }

    public void ask(int messageId, GameView gameView, String question, Map<String, Serializable> options) {
        updateGame(messageId, gameView, false, options, null);
        this.feedbackPanel.prepareFeedback(FeedbackMode.QUESTION, question, "", false, options, true, gameView.getPhase());
    }

    public boolean isMissGameData() {
        return lastGameData.game == null || lastGameData.game.getPlayers().isEmpty();
    }

    private void keepLastGameData(int messageId, GameView game, boolean showPlayable, Map<String, Serializable> options, Set<UUID> targets) {
        lastGameData.messageId = messageId;
        lastGameData.setNewGame(game);
        lastGameData.showPlayable = showPlayable;
        lastGameData.options = options;
        lastGameData.targets = targets;
    }

    private void prepareSelectableView() {
        // make cards/perm selectable/chooseable/playable update game data updates
        if (lastGameData.game == null) {
            return;
        }

        Zone needZone = Zone.ALL;
        if (lastGameData.options != null && lastGameData.options.containsKey("targetZone")) {
            needZone = (Zone) lastGameData.options.get("targetZone");
        }

        Set<UUID> needChosen = lastGameData.getChosenTargets();

        Set<UUID> needSelectable;
        if (lastGameData.targets != null) {
            needSelectable = lastGameData.targets;
        } else {
            needSelectable = new HashSet<>();
        }

        PlayableObjectsList needPlayable;
        if (lastGameData.showPlayable && lastGameData.game.getCanPlayObjects() != null) {
            needPlayable = lastGameData.game.getCanPlayObjects();
        } else {
            needPlayable = new PlayableObjectsList();
        }

        if (needChosen.isEmpty() && needSelectable.isEmpty() && needPlayable.isEmpty()) {
            return;
        }

        // hand
        if (needZone == Zone.HAND || needZone == Zone.ALL) {
            // my hand
            for (CardView card : lastGameData.game.getMyHand().values()) {
                if (needSelectable.contains(card.getId())) {
                    card.setChoosable(true);
                }
                if (needChosen.contains(card.getId())) {
                    card.setSelected(true);
                }
                if (needPlayable.containsObject(card.getId())) {
                    card.setPlayableStats(needPlayable.getStats(card.getId()));
                }
            }

            // opponent hands (switching by GUI's button with my hand)
            List<SimpleCardView> list = lastGameData.game.getOpponentHands().values().stream().flatMap(s -> s.values().stream()).collect(Collectors.toList());
            for (SimpleCardView card : list) {
                if (needSelectable.contains(card.getId())) {
                    card.setChoosable(true);
                }
                if (needChosen.contains(card.getId())) {
                    card.setSelected(true);
                }
                if (needPlayable.containsObject(card.getId())) {
                    card.setPlayableStats(needPlayable.getStats(card.getId()));
                }
            }

            // watched hands (switching by GUI's button with my hand)
            list = lastGameData.game.getWatchedHands().values().stream().flatMap(s -> s.values().stream()).collect(Collectors.toList());
            for (SimpleCardView card : list) {
                if (needSelectable.contains(card.getId())) {
                    card.setChoosable(true);
                }
                if (needChosen.contains(card.getId())) {
                    card.setSelected(true);
                }
                if (needPlayable.containsObject(card.getId())) {
                    card.setPlayableStats(needPlayable.getStats(card.getId()));
                }
            }
        }

        // stack
        if (needZone == Zone.STACK || needZone == Zone.ALL) {
            for (Map.Entry<UUID, CardView> card : lastGameData.game.getStack().entrySet()) {
                if (needSelectable.contains(card.getKey())) {
                    card.getValue().setChoosable(true);
                }
                if (needChosen.contains(card.getKey())) {
                    card.getValue().setSelected(true);
                }
                // users can activate abilities of the spell on the stack (example: Lightning Storm);
                if (needPlayable.containsObject(card.getKey())) {
                    card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                }
            }
        }

        // battlefield
        if (needZone == Zone.BATTLEFIELD || needZone == Zone.ALL) {
            for (PlayerView player : lastGameData.game.getPlayers()) {
                for (Map.Entry<UUID, PermanentView> perm : player.getBattlefield().entrySet()) {
                    if (needSelectable.contains(perm.getKey())) {
                        perm.getValue().setChoosable(true);
                    }
                    if (needChosen.contains(perm.getKey())) {
                        perm.getValue().setSelected(true);
                    }
                    if (needPlayable.containsObject(perm.getKey())) {
                        perm.getValue().setPlayableStats(needPlayable.getStats(perm.getKey()));
                    }
                }
            }
        }

        // graveyard
        if (needZone == Zone.GRAVEYARD || needZone == Zone.ALL) {
            for (PlayerView player : lastGameData.game.getPlayers()) {
                for (Map.Entry<UUID, CardView> card : player.getGraveyard().entrySet()) {
                    if (needSelectable.contains(card.getKey())) {
                        card.getValue().setChoosable(true);
                    }
                    if (needChosen.contains(card.getKey())) {
                        card.getValue().setSelected(true);
                    }
                    if (needPlayable.containsObject(card.getKey())) {
                        card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                    }
                }
            }
        }

        // sideboard
        if (needZone == Zone.OUTSIDE || needZone == Zone.ALL) {
            for (PlayerView player : lastGameData.game.getPlayers()) {
                for (Map.Entry<UUID, CardView> card : player.getSideboard().entrySet()) {
                    if (needSelectable.contains(card.getKey())) {
                        card.getValue().setChoosable(true);
                    }
                    if (needChosen.contains(card.getKey())) {
                        card.getValue().setSelected(true);
                    }
                    if (needPlayable.containsObject(card.getKey())) {
                        card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                    }
                }
            }
        }
        // sideboards (old windows all the time, e.g. unattached from game data)
        prepareSelectableWindows(sideboardWindows.values(), needSelectable, needChosen, needPlayable);

        // exile
        if (needZone == Zone.EXILED || needZone == Zone.ALL) {
            // exile from player panel
            for (PlayerView player : lastGameData.game.getPlayers()) {
                for (CardView card : player.getExile().values()) {
                    if (needSelectable.contains(card.getId())) {
                        card.setChoosable(true);
                    }
                    if (needChosen.contains(card.getId())) {
                        card.setSelected(true);
                    }
                    if (needPlayable.containsObject(card.getId())) {
                        card.setPlayableStats(needPlayable.getStats(card.getId()));
                    }
                }
            }

            // exile from windows
            for (ExileView exile : lastGameData.game.getExile()) {
                for (Map.Entry<UUID, CardView> card : exile.entrySet()) {
                    if (needSelectable.contains(card.getKey())) {
                        card.getValue().setChoosable(true);
                    }
                    if (needChosen.contains(card.getKey())) {
                        card.getValue().setSelected(true);
                    }
                    if (needPlayable.containsObject(card.getKey())) {
                        card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                    }
                }
            }
        }

        // command
        if (needZone == Zone.COMMAND || needZone == Zone.ALL) {
            for (PlayerView player : lastGameData.game.getPlayers()) {
                for (CommandObjectView com : player.getCommandObjectList()) {
                    if (needSelectable.contains(com.getId())) {
                        com.setChoosable(true);
                    }
                    if (needChosen.contains(com.getId())) {
                        com.setSelected(true);
                    }
                    if (needPlayable.containsObject(com.getId())) {
                        com.setPlayableStats(needPlayable.getStats(com.getId()));
                    }
                }
            }
        }

        // companion
        for (RevealedView rev : lastGameData.game.getCompanion()) {
            for (Map.Entry<UUID, CardView> card : rev.getCards().entrySet()) {
                if (needSelectable.contains(card.getKey())) {
                    card.getValue().setChoosable(true);
                }
                if (needChosen.contains(card.getKey())) {
                    card.getValue().setSelected(true);
                }
                if (needPlayable.containsObject(card.getKey())) {
                    card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                }
            }
        }

        // revealed (current cards)
        for (RevealedView rev : lastGameData.game.getRevealed()) {
            for (Map.Entry<UUID, CardView> card : rev.getCards().entrySet()) {
                if (needSelectable.contains(card.getKey())) {
                    card.getValue().setChoosable(true);
                }
                if (needChosen.contains(card.getKey())) {
                    card.getValue().setSelected(true);
                }
                if (needPlayable.containsObject(card.getKey())) {
                    card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                }
            }
        }
        // revealed (old windows)
        prepareSelectableWindows(revealed.values(), needSelectable, needChosen, needPlayable);

        // looked at (current cards)
        for (LookedAtView look : lastGameData.game.getLookedAt()) {
            for (Map.Entry<UUID, SimpleCardView> card : look.getCards().entrySet()) {
                if (needPlayable.containsObject(card.getKey())) {
                    card.getValue().setPlayableStats(needPlayable.getStats(card.getKey()));
                }
            }
        }
        // looked at (old windows)
        prepareSelectableWindows(lookedAt.values(), needSelectable, needChosen, needPlayable);
    }

    private void prepareSelectableWindows(
            Collection<CardInfoWindowDialog> windows,
            Set<UUID> needSelectable,
            Set<UUID> needChosen,
            PlayableObjectsList needPlayable
    ) {
        // lookAt or reveals windows clean up on next priority, so users can see dialogs, but xmage can't restore it
        // so it must be updated manually (it's ok to keep outdated cards in dialog, but not ok to show wrong selections)
        for (CardInfoWindowDialog window : windows) {
            for (MageCard mageCard : window.getMageCardsForUpdate().values()) {
                CardView cardView = mageCard.getOriginal();
                cardView.setChoosable(needSelectable.contains(cardView.getId()));
                cardView.setSelected(needChosen.contains(cardView.getId()));
                if (needPlayable.containsObject(cardView.getId())) {
                    cardView.setPlayableStats(needPlayable.getStats(cardView.getId()));
                } else {
                    cardView.setPlayableStats(new PlayableObjectStats());
                }
                // TODO: little bug with toggled night card after update/clicks, but that's ok (can't click on second side)
                mageCard.update(cardView);
            }
        }
    }

    /**
     * Shows a pick target dialog and allows the player to pick a target (e.g.
     * the pick triggered ability)
     *
     * @param message
     * @param cardsView
     * @param gameView
     * @param targets
     * @param required
     * @param options
     * @param messageId
     */
    public void pickTarget(int messageId, GameView gameView, Map<String, Serializable> options, String message, CardsView cardsView, Set<UUID> targets, boolean required) {
        updateGame(messageId, gameView, false, options, targets);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();
        clearPickTargetDialogs();

        PopUpMenuType popupMenuType = null;
        if (lastGameData.options != null) {
            if (options.containsKey("queryType")) {
                PlayerQueryEvent.QueryType needType = (PlayerQueryEvent.QueryType) lastGameData.options.get("queryType");
                switch (needType) {
                    case PICK_ABILITY:
                        popupMenuType = PopUpMenuType.TRIGGER_ORDER;
                        break;
                    case PICK_TARGET:
                        break;
                    default:
                        logger.warn("Unknown query type in pick target: " + needType + " in " + message);
                        break;
                }
            }
        }

        Map<String, Serializable> options0 = lastGameData.options == null ? new HashMap<>() : lastGameData.options;
        ShowCardsDialog dialog = null;
        if (cardsView != null && !cardsView.isEmpty()) {
            dialog = prepareCardsDialog(message, cardsView, required, options0, popupMenuType);
            options0.put("dialog", dialog);
        }
        this.feedbackPanel.prepareFeedback(required ? FeedbackMode.INFORM : FeedbackMode.CANCEL, message, "", gameView.getSpecial(), options0, true, gameView.getPhase());
        if (dialog != null) {
            this.pickTarget.add(dialog);
        }
    }

    public void inform(int messageId, GameView gameView, String information) {
        updateGame(messageId, gameView);
        this.feedbackPanel.prepareFeedback(FeedbackMode.INFORM, information, "", gameView.getSpecial(), null, false, gameView.getPhase());
    }

    public void endMessage(int messageId, GameView gameView, Map<String, Serializable> options, String message) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        this.feedbackPanel.prepareFeedback(FeedbackMode.END, message, "", false, null, true, null);
        ArrowBuilder.getBuilder().removeAllArrows(gameId);
    }

    public void select(int messageId, GameView gameView, Map<String, Serializable> options, String message) {
        updateGame(messageId, gameView, true, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        this.abilityPicker.setVisible(false);

        holdingPriority = false;
        txtHoldPriority.setVisible(false);
        setMenuStates(
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                false);

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
        if (lastGameData.options != null) {
            panelOptions.putAll(lastGameData.options);
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
        String additionalMessage = activePlayerText + " / " + gameView.getStep().toString() + priorityPlayerText;
        this.feedbackPanel.prepareFeedback(FeedbackMode.SELECT, message, additionalMessage, gameView.getSpecial(), panelOptions, true, gameView.getPhase());
    }

    public void playMana(int messageId, GameView gameView, Map<String, Serializable> options, String message) {
        updateGame(messageId, gameView, true, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        this.feedbackPanel.prepareFeedback(FeedbackMode.CANCEL, message, "", gameView.getSpecial(), options, true, gameView.getPhase());
    }

    public void playXMana(int messageId, GameView gameView, Map<String, Serializable> options, String message) {
        updateGame(messageId, gameView, true, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        this.feedbackPanel.prepareFeedback(FeedbackMode.CONFIRM, message, "", gameView.getSpecial(), null, true, gameView.getPhase());
    }

    public void replayMessage(String message) {
        //TODO: implement this
    }

    public void pickAbility(int messageId, GameView gameView, Map<String, Serializable> options, AbilityPickerView choices) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        this.abilityPicker.show(choices, MageFrame.getDesktop().getMousePosition());
    }

    private void hideAll() {
        hidePickDialogs();
        this.abilityPicker.setVisible(false);
        ActionCallback callback = Plugins.instance.getActionCallback();
        ((MageActionCallback) callback).hideGameUpdate(gameId);
    }

    private ShowCardsDialog prepareCardsDialog(String title, CardsView cards, boolean required, Map<String, Serializable> options, PopUpMenuType popupMenuType) {
        ShowCardsDialog showCards = new ShowCardsDialog();
        JPopupMenu popupMenu = null;
        if (PopUpMenuType.TRIGGER_ORDER == popupMenuType) {
            popupMenu = popupMenuTriggerOrder;
        }
        showCards.loadCards(title, cards, bigCard, gameId, required, options, popupMenu, getShowCardsEventListener(showCards));
        return showCards;
    }

    public void getAmount(int messageId, GameView gameView, Map<String, Serializable> options, int min, int max, String message) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        pickNumber.showDialog(min, max, message, () -> {
            if (pickNumber.isCancel()) {
                SessionHandler.sendPlayerBoolean(gameId, false);
            } else {
                SessionHandler.sendPlayerInteger(gameId, pickNumber.getAmount());
            }
        });
    }

    public void getMultiAmount(int messageId, GameView gameView, List<MultiAmountMessage> messages, Map<String, Serializable> options,
                               int min, int max) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        pickMultiNumber.init(gameId, bigCard);
        pickMultiNumber.showDialog(messages, min, max, lastGameData.options, () -> {
            if (pickMultiNumber.isCancel()) {
                SessionHandler.sendPlayerBoolean(gameId, false);
            } else {
                SessionHandler.sendPlayerString(gameId, pickMultiNumber.getMultiAmount());
            }
        });
    }

    public void getChoice(int messageId, GameView gameView, Map<String, Serializable> options, Choice choice, UUID objectId) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        // TODO: remember last choices and search incremental for same events?
        PickChoiceDialog pickChoice = new PickChoiceDialog();
        pickChoice.showDialog(choice, null, objectId, choiceWindowState, bigCard, () -> {
            // special mode adds # to the answer (server side code must process that prefix, see replacementEffectChoice)
            String specialPrefix = choice.isChosenSpecial() ? "#" : "";

            String valueToSend;
            if (choice.isKeyChoice()) {
                valueToSend = choice.getChoiceKey();
            } else {
                valueToSend = choice.getChoice();
            }
            SessionHandler.sendPlayerString(gameId, valueToSend == null ? null : specialPrefix + valueToSend);

            // keep dialog position
            choiceWindowState = new MageDialogState(pickChoice);

            pickChoice.removeDialog();
        });
    }

    public void pickPile(int messageId, GameView gameView, Map<String, Serializable> options, String message, CardsView pile1, CardsView pile2) {
        updateGame(messageId, gameView, false, options, null);
        hideAll();
        DialogManager.getManager(gameId).fadeOut();

        // remove old dialogs before the new
        clearPickPileDialogs();

        PickPileDialog pickPileDialog = new PickPileDialog();
        this.pickPile.add(pickPileDialog);

        pickPileDialog.showDialog(message, pile1, pile2, bigCard, gameId, () -> {
            if (pickPileDialog.isPickedOK()) {
                SessionHandler.sendPlayerBoolean(gameId, pickPileDialog.isPickedPile1());
            }
        });
    }

    public Map<UUID, PlayAreaPanel> getPlayers() {
        return players;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        abilityPicker = new mage.client.components.ability.AbilityPicker(GUISizeHelper.dialogGuiScale);
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
        helper = new HelperPanel();
        feedbackPanel.setHelperPanel(helper);
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.add(helper, BorderLayout.CENTER);

        Border paddingBorder = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        txtHoldPriority = new javax.swing.JLabel();
        txtHoldPriority.setText("Hold");
        txtHoldPriority.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        txtHoldPriority.setBackground(Color.LIGHT_GRAY);
        txtHoldPriority.setOpaque(true);
        txtHoldPriority.setToolTipText("Holding priority after the next spell cast or ability activation");
        txtHoldPriority.setVisible(false);

        boolean displayButtonText = PreferencesDialog.getCurrentTheme().isShortcutsVisibleForSkipButtons();
        btnToggleMacro = new KeyboundButton(KEY_CONTROL_TOGGLE_MACRO, displayButtonText);
        btnCancelSkip = new KeyboundButton(KEY_CONTROL_CANCEL_SKIP, displayButtonText); // F3
        btnSkipToNextTurn = new KeyboundButton(KEY_CONTROL_NEXT_TURN, displayButtonText); // F4
        btnSkipToEndTurn = new KeyboundButton(KEY_CONTROL_END_STEP, displayButtonText); // F5
        btnSkipToNextMain = new KeyboundButton(KEY_CONTROL_MAIN_STEP, displayButtonText); // F7
        btnSkipStack = new KeyboundButton(KEY_CONTROL_SKIP_STACK, displayButtonText); // F10
        btnSkipToYourTurn = new KeyboundButton(KEY_CONTROL_YOUR_TURN, displayButtonText); // F9
        btnSkipToEndStepBeforeYourTurn = new KeyboundButton(KEY_CONTROL_PRIOR_END, displayButtonText); // F11

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
        handContainer = new HandPanel();
        handCards = new HashMap<>();

        pnlShortCuts.setOpaque(false);
        //pnlShortCuts.setPreferredSize(new Dimension(410, 72));

        stackObjects = new mage.client.cards.Cards();

        // split: [hand] <|> [stack]
        splitHandAndStack = new javax.swing.JSplitPane();
        splitHandAndStack.setBorder(null);
        splitHandAndStack.setResizeWeight(DIVIDER_KEEP_RIGHT_COMPONENT);
        splitHandAndStack.setOneTouchExpandable(true);

        // split: [game + chats] <|> [big card]
        splitGameAndBigCard = new javax.swing.JSplitPane();
        splitGameAndBigCard.setBorder(null);
        splitGameAndBigCard.setResizeWeight(DIVIDER_KEEP_RIGHT_COMPONENT);
        splitGameAndBigCard.setOneTouchExpandable(true);

        // split: chat <|> game logs
        splitChatAndLogs = new javax.swing.JSplitPane();
        splitChatAndLogs.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitChatAndLogs.setResizeWeight(DIVIDER_KEEP_LEFT_COMPONENT);
        splitChatAndLogs.setTopComponent(userChatPanel);
        splitChatAndLogs.setBottomComponent(gameChatPanel);

        // split: [battlefield + hand + stack] <|> [chats]
        splitBattlefieldAndChats = new javax.swing.JSplitPane();
        splitBattlefieldAndChats.setBorder(null);
        splitBattlefieldAndChats.setResizeWeight(DIVIDER_KEEP_RIGHT_COMPONENT);
        splitBattlefieldAndChats.setOneTouchExpandable(true);
        splitBattlefieldAndChats.setLeftComponent(pnlHelperHandButtonsStackArea);
        splitBattlefieldAndChats.setRightComponent(splitChatAndLogs);

        // warning, it's important to store/restore splitters in same order as real life GUI
        // from outer to inner (otherwise panels will be hidden or weird)
        // also it must be restored by thread queue
        this.splitters.put(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATIONS_GAME_AND_BIG_CARD, new MageSplitter(splitGameAndBigCard, 0.85));
        this.splitters.put(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATIONS_BATTLEFIELD_AND_CHATS, new MageSplitter(splitBattlefieldAndChats, 0.80));
        this.splitters.put(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATIONS_HAND_STACK, new MageSplitter(splitHandAndStack, 0.70));
        this.splitters.put(PreferencesDialog.KEY_GAMEPANEL_DIVIDER_LOCATIONS_CHAT_AND_LOGS, new MageSplitter(splitChatAndLogs, 0.40));

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

        // CHATS and HINTS support

        // HOTKEYS

        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;

        // special hotkeys for custom rendered dialogs without focus
        // call it before any user defined hotkeys
        this.abilityPicker.injectHotkeys(this, "ABILITY_PICKER");

        btnToggleMacro.setContentAreaFilled(false);
        btnToggleMacro.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnToggleMacro.setToolTipText("Toggle Record Macro ("
                + getCachedKeyText(KEY_CONTROL_TOGGLE_MACRO) + ").");
        btnToggleMacro.setFocusable(false);
        btnToggleMacro.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnToggleMacroActionPerformed(null)));

        KeyStroke kst = getCachedKeystroke(KEY_CONTROL_TOGGLE_MACRO);
        this.getInputMap(c).put(kst, "F8_PRESS");
        this.getActionMap().put("F8_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnToggleMacroActionPerformed(actionEvent);
            }
        });

        KeyStroke ks3 = getCachedKeystroke(KEY_CONTROL_CANCEL_SKIP);
        this.getInputMap(c).put(ks3, "F3_PRESS");
        this.getActionMap().put("F3_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                restorePriorityActionPerformed(actionEvent);
            }
        });

        // SKIP BUTTONS (button's hint/state is dynamic)
        // button icons setup in setGUISize

        btnCancelSkip.setContentAreaFilled(false);
        btnCancelSkip.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnCancelSkip.setToolTipText("CANCEL all skips");
        btnCancelSkip.setFocusable(false);
        btnCancelSkip.addMouseListener(new FirstButtonMousePressedAction(e ->
                restorePriorityActionPerformed(null)));

        btnSkipToNextTurn.setContentAreaFilled(false);
        btnSkipToNextTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToNextTurn.setToolTipText("dynamic");
        btnSkipToNextTurn.setFocusable(false);
        btnSkipToNextTurn.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnEndTurnActionPerformed(null)));

        KeyStroke ks = getCachedKeystroke(KEY_CONTROL_NEXT_TURN);
        this.getInputMap(c).put(ks, "F4_PRESS");
        this.getActionMap().put("F4_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnEndTurnActionPerformed(actionEvent);
            }
        });

        btnSkipToEndTurn.setContentAreaFilled(false);
        btnSkipToEndTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToEndTurn.setToolTipText("dynamic");
        btnSkipToEndTurn.setFocusable(false);
        btnSkipToEndTurn.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnUntilEndOfTurnActionPerformed(null)));

        ks = getCachedKeystroke(KEY_CONTROL_END_STEP);
        this.getInputMap(c).put(ks, "F5_PRESS");
        this.getActionMap().put("F5_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnUntilEndOfTurnActionPerformed(actionEvent);
            }
        });

        ks = getCachedKeystroke(KEY_CONTROL_SKIP_STEP);
        this.getInputMap(c).put(ks, "F6_PRESS");
        this.getActionMap().put("F6_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnEndTurnSkipStackActionPerformed(actionEvent);
            }
        });

        btnSkipToNextMain.setContentAreaFilled(false);
        btnSkipToNextMain.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToNextMain.setToolTipText("dynamic");
        btnSkipToNextMain.setFocusable(false);
        btnSkipToNextMain.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnUntilNextMainPhaseActionPerformed(null)));

        ks = getCachedKeystroke(KEY_CONTROL_MAIN_STEP);
        this.getInputMap(c).put(ks, "F7_PRESS");
        this.getActionMap().put("F7_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnUntilNextMainPhaseActionPerformed(actionEvent);
            }
        });

        btnSkipToYourTurn.setContentAreaFilled(false);
        btnSkipToYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToYourTurn.setToolTipText("dynamic");
        btnSkipToYourTurn.setFocusable(false);
        btnSkipToYourTurn.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnPassPriorityUntilNextYourTurnActionPerformed(null)));

        KeyStroke ks9 = getCachedKeystroke(KEY_CONTROL_YOUR_TURN);
        this.getInputMap(c).put(ks9, "F9_PRESS");
        this.getActionMap().put("F9_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnPassPriorityUntilNextYourTurnActionPerformed(actionEvent);
            }
        });

        btnSkipToEndStepBeforeYourTurn.setContentAreaFilled(false);
        btnSkipToEndStepBeforeYourTurn.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipToEndStepBeforeYourTurn.setToolTipText("dynamic");
        btnSkipToEndStepBeforeYourTurn.setFocusable(false);
        btnSkipToEndStepBeforeYourTurn.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnSkipToEndStepBeforeYourTurnActionPerformed(null)));

        KeyStroke ks11 = getCachedKeystroke(KEY_CONTROL_PRIOR_END);
        this.getInputMap(c).put(ks11, "F11_PRESS");
        this.getActionMap().put("F11_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnSkipToEndStepBeforeYourTurnActionPerformed(actionEvent);
            }
        });

        btnSkipStack.setContentAreaFilled(false);
        btnSkipStack.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnSkipStack.setToolTipText("dynamic");
        btnSkipStack.setFocusable(false);
        btnSkipStack.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnPassPriorityUntilStackResolvedActionPerformed(null)));

        ks = getCachedKeystroke(KEY_CONTROL_SKIP_STACK);
        this.getInputMap(c).put(ks, "F10_PRESS");
        this.getActionMap().put("F10_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                btnPassPriorityUntilStackResolvedActionPerformed(actionEvent);
            }
        });

        btnConcede.setContentAreaFilled(false);
        btnConcede.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        btnConcede.setToolTipText("CONCEDE current game");
        btnConcede.setFocusable(false);
        btnConcede.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnConcedeActionPerformed(null)));

        // update button hint/states to default values
        updateSkipButtons();

        // HOTKEYS

        KeyStroke ks2 = getCachedKeystroke(KEY_CONTROL_CONFIRM);
        this.getInputMap(c).put(ks2, "F2_PRESS");
        this.getActionMap().put("F2_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                if (feedbackPanel != null) {
                    feedbackPanel.pressOKYesOrDone();
                }
            }
        });

        KeyStroke ks12 = getCachedKeystroke(KEY_CONTROL_SWITCH_CHAT);
        this.getInputMap(c).put(ks12, "F12_PRESS");
        this.getActionMap().put("F12_PRESS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // switch in/out to chat, must triggers in chat input too
                //if (isUserImputActive()) return;
                if (isChatInputActive()) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                } else if (!isUserImputActive()) {
                    userChatPanel.getTxtMessageInputComponent().requestFocusInWindow();
                }
            }
        });

        KeyStroke ksAltE = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltE, "ENLARGE");
        this.getActionMap().put("ENLARGE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).enlargeCard(EnlargeMode.NORMAL);
            }
        });

        KeyStroke ksAltS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAltS, "ENLARGE_SOURCE");
        this.getActionMap().put("ENLARGE_SOURCE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: doesn't work? 26.11.2023, delete as useless
                if (isUserImputActive()) return;
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).enlargeCard(EnlargeMode.ALTERNATE);
            }
        });

        KeyStroke ksAlt1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK);
        this.getInputMap(c).put(ksAlt1, "USEFIRSTMANAABILITY");
        this.getActionMap().put("USEFIRSTMANAABILITY", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                SessionHandler.sendPlayerAction(PlayerAction.USE_FIRST_MANA_ABILITY_ON, gameId, null);
                setMenuStates(
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, "true").equals("true"),
                        PreferencesDialog.getCachedValue(KEY_USE_FIRST_MANA_ABILITY, "false").equals("true"),
                        holdingPriority);
            }
        });

        // TODO: delete as useless
        KeyStroke ksAltEReleased = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAltEReleased, "ENLARGE_RELEASE");
        KeyStroke ksAltSReleased = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAltSReleased, "ENLARGE_RELEASE");
        this.getActionMap().put("ENLARGE_RELEASE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
                ActionCallback callback = Plugins.instance.getActionCallback();
                ((MageActionCallback) callback).hideEnlargedCard();
            }
        });

        KeyStroke ksAlt1Released = KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_MASK, true);
        this.getInputMap(c).put(ksAlt1Released, "USEFIRSTMANAABILITY_RELEASE");
        this.getActionMap().put("USEFIRSTMANAABILITY_RELEASE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isUserImputActive()) return;
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
        btnSwitchHands.setFocusable(false);
        btnSwitchHands.setToolTipText("Switch between your hand cards and hand cards of controlled players.");
        btnSwitchHands.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnSwitchHandActionPerformed(null)));

        btnStopWatching.setContentAreaFilled(false);
        btnStopWatching.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnStopWatching.setFocusable(false);
        btnStopWatching.setToolTipText("Stop watching this game.");
        btnStopWatching.addMouseListener(new FirstButtonMousePressedAction(e ->
                btnStopWatchingActionPerformed(null)));

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

        pnlBattlefield.setLayout(new java.awt.GridBagLayout());

        jPhases = new JPanel();
        jPhases.setBackground(new Color(0, 0, 0, 0));
        // layout on gui size

        MouseAdapter phasesMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                mouseClickPhaseBar(evt);
            }
        };

        /// phase buttons
        if (DebugUtil.GUI_GAME_DRAW_PHASE_BUTTONS_PANEL_BORDER) {
            jPhases.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        String[] phases = {"Untap", "Upkeep", "Draw", "Main1",
                "Combat_Start", "Combat_Attack", "Combat_Block", "Combat_Damage", "Combat_End",
                "Main2", "Cleanup", "Next_Turn"};
        for (String name : phases) {
            createPhaseButton(name, phasesMouseAdapter);
        }

        pnlReplay.setOpaque(false);

        // phases buttons
        phasesContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phasesContainer.setOpaque(false);
        phasesContainer.add(jPhases);

        // card hint panel
        bigCardPanel = new javax.swing.JPanel();
        bigCardPanel.setOpaque(false);
        bigCardPanel.setLayout(new BorderLayout());
        bigCardPanel.add(bigCard, BorderLayout.NORTH);

        // split: game <|> chat/log
        splitGameAndBigCard.setLeftComponent(splitBattlefieldAndChats);
        splitGameAndBigCard.setRightComponent(bigCardPanel);
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
        for (String name : phaseButtons.keySet()) {
            HoverButton hoverButton = phaseButtons.get(name);
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

        final BasicSplitPaneUI myUi = (BasicSplitPaneUI) splitGameAndBigCard.getUI();
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

        for (KeyListener kl : this.getKeyListeners()) {
            this.removeKeyListener(kl);
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
        skipButtons.activateSkipButton("");

        AudioManager.playOnSkipButton();
        if (btnToggleMacro.getBorder().equals(BORDER_ACTIVE)) {
            btnToggleMacro.setBorder(BORDER_NON_ACTIVE);
        } else {
            btnToggleMacro.setBorder(BORDER_ACTIVE);
        }
    }

    private void btnEndTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_NEXT_TURN);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private boolean isChatInputUnderCursor(Point p) {
        Component c = this.getComponentAt(p);
        return gameChatPanel.getTxtMessageInputComponent().equals(c) || userChatPanel.getTxtMessageInputComponent().equals(c);
    }

    private boolean isChatInputActive() {
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        return gameChatPanel.getTxtMessageInputComponent().equals(c) || userChatPanel.getTxtMessageInputComponent().equals(c);
    }

    private boolean isUserImputActive() {
        // any imput or choose dialog active (need to disable skip buttons in dialogs and chat)
        return MageDialog.isModalDialogActivated() || isChatInputActive();
    }

    private void btnUntilEndOfTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_TURN_END_STEP, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_END_STEP);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private void btnEndTurnSkipStackActionPerformed(java.awt.event.ActionEvent evt) {
        logger.error("Skip action don't used", new Throwable());
        /*
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN_SKIP_STACK, gameId, null);
        AudioManager.playOnSkipButton();
        updateSkipButtons(true, false, false, false, true, false);
        */
    }

    private void btnUntilNextMainPhaseActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_MAIN_PHASE, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_MAIN_STEP);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private void btnPassPriorityUntilNextYourTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_MY_NEXT_TURN, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_YOUR_TURN);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private void btnPassPriorityUntilStackResolvedActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_STACK_RESOLVED, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_SKIP_STACK);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private void btnSkipToEndStepBeforeYourTurnActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_END_STEP_BEFORE_MY_NEXT_TURN, gameId, null);
        skipButtons.activateSkipButton(KEY_CONTROL_PRIOR_END);

        AudioManager.playOnSkipButton();
        updateSkipButtons();
    }

    private void restorePriorityActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, gameId, null);
        skipButtons.activateSkipButton("");

        AudioManager.playOnSkipButtonCancel();
        updateSkipButtons();
    }

    private void mouseClickPhaseBar(MouseEvent evt) {
        if (SwingUtilities.isLeftMouseButton(evt)) {
            PreferencesDialog.main(new String[]{PreferencesDialog.OPEN_PHASES_TAB});
            // TODO: add event handler on preferences closed and refresh game data from server
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
        jLayeredPane.add(DialogManager.getManager(gameId), JLayeredPane.MODAL_LAYER, 0);
        installAbilityPicker();
    }

    private void installAbilityPicker() {
        jLayeredPane.add(abilityPicker, JLayeredPane.MODAL_LAYER);
        abilityPicker.setVisible(false);
    }

    private void uninstallComponents() {
        if (jLayeredPane != null) {
            jLayeredPane.remove(DialogManager.getManager(gameId));
        }
        DialogManager.removeGame(gameId);
        uninstallAbilityPicker();
    }

    private void uninstallAbilityPicker() {
        abilityPicker.setVisible(false);
        if (jLayeredPane != null) {
            jLayeredPane.remove(abilityPicker);
        }
        this.abilityPicker.cleanUp();
    }

    private void createPhaseButton(String name, MouseAdapter mouseAdapter) {
        int buttonSize = GUISizeHelper.gamePhaseButtonSize;
        Rectangle rect = new Rectangle(buttonSize, buttonSize);
        HoverButton button = new HoverButton("", ImageManagerImpl.instance.getPhaseImage(name, buttonSize), rect);
        button.setToolTipText(name.replaceAll("_", " "));
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
        button.addMouseListener(mouseAdapter);
        phaseButtons.put(name, button);
        jPhases.add(button);
    }

    // Event listener for the ShowCardsDialog
    private Listener<Event> getShowCardsEventListener(final ShowCardsDialog dialog) {
        return event -> {
            if (event.getEventType() == ClientEventType.CARD_POPUP_MENU) {
                if (event.getComponent() != null && event.getComponent() instanceof MageCard) {
                    JPopupMenu menu = ((MageCard) event.getComponent()).getPopupMenu();
                    if (menu != null) {
                        cardViewPopupMenu = ((CardView) event.getSource());
                        menu.show(event.getComponent(), event.getxPos(), event.getyPos());
                    }
                }
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
                abilityRuleText = abilityRuleText.replace("{this}", cardViewPopupMenu.getName());
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
            default:
                break;
        }

        // TODO: 2021-01-23 why it here? Can be removed?
        for (ShowCardsDialog dialog : pickTarget) {
            dialog.removeDialog();
        }
        for (PickPileDialog dialog : pickPile) {
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
                // clear chat focus on click
                if (event instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) event;
                    if (isChatInputActive() && !isChatInputUnderCursor(me.getPoint())) {
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                    }
                }
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
    private javax.swing.JPanel bigCardPanel;
    private javax.swing.JPanel pnlHelperHandButtonsStackArea;
    private javax.swing.JSplitPane splitGameAndBigCard;
    private javax.swing.JSplitPane splitBattlefieldAndChats;
    private javax.swing.JSplitPane splitChatAndLogs;
    private javax.swing.JSplitPane splitHandAndStack;
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

    private JPanel jPhases;
    private JPanel phasesContainer;
    private javax.swing.JLabel txtHoldPriority;

    private boolean imagePanelState;

}

class ReplayTask extends SwingWorker<Void, Collection<MatchView>> {

    // replay without table - just single game
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
        } catch (InterruptedException | ExecutionException ex) {
            logger.fatal("Replay Match Task error", ex);
        } catch (CancellationException ex) {
        }
    }
}
