package mage.client.game;

import mage.cards.decks.importer.DeckImporter;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.constants.PlayerAction;
import mage.view.GameView;
import mage.view.PlayerView;
import mage.view.UserRequestMessage;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.UUID;

import static mage.client.dialog.PreferencesDialog.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PlayAreaPanel extends javax.swing.JPanel {

    private final JPopupMenu popupMenu;
    private UUID playerId;
    private UUID gameId;
    private boolean smallMode = false;
    private boolean playingMode = true;
    private final GamePanel gamePanel;
    private final PlayAreaPanelOptions options;

    private JCheckBoxMenuItem manaPoolMenuItem1;
    private JCheckBoxMenuItem manaPoolMenuItem2;
    private JCheckBoxMenuItem useFirstManaAbilityItem;
    private JCheckBoxMenuItem allowViewHandCardsMenuItem;
    private JCheckBoxMenuItem holdPriorityMenuItem;

    public static final int PANEL_HEIGHT = 263;
    public static final int PANEL_HEIGHT_SMALL = 210;

    /**
     * Creates new form PlayAreaPanel
     *
     * @param player
     * @param bigCard
     * @param gameId
     * @param priorityTime
     * @param gamePanel
     * @param options
     */
    public PlayAreaPanel(PlayerView player, BigCard bigCard, UUID gameId, int priorityTime, GamePanel gamePanel, PlayAreaPanelOptions options) {
        this.gamePanel = gamePanel;
        this.options = options;
        initComponents();
        setOpaque(false);
        battlefieldPanel.setOpaque(false);

        popupMenu = new JPopupMenu();
        if (options.isPlayer) {
            addPopupMenuPlayer(player.getUserData().isAllowRequestHandToAll());
        } else {
            addPopupMenuWatcher();
        }
        this.add(popupMenu);
        setGUISize();

        init(player, bigCard, gameId, priorityTime);
        update(null, player, null);
    }

    public void CleanUp() {
        battlefieldPanel.cleanUp();
        playerPanel.cleanUp();

        for (ActionListener al : btnCheat.getActionListeners()) {
            btnCheat.removeActionListener(al);
        }

        // Taken form : https://community.oracle.com/thread/2183145
        // removed the internal focus of a popupMenu data to allow GC before another popup menu is selected
        for (ChangeListener listener : MenuSelectionManager.defaultManager().getChangeListeners()) {
            if (listener.getClass().getName().contains("MenuKeyboardHelper")) {
                try {
                    Field field = listener.getClass().getDeclaredField("menuInputMap");
                    field.setAccessible(true);
                    field.set(listener, null);
                } catch (Exception e) {
                    // ignored
                }
                break;
            }
        }

        for (MouseListener ml : battlefieldPanel.getMainPanel().getMouseListeners()) {
            battlefieldPanel.getMainPanel().removeMouseListener(ml);
        }
        popupMenu.getUI().uninstallUI(this);

    }

    public void changeGUISize() {
        setGUISize();
        battlefieldPanel.changeGUISize();
        playerPanel.changeGUISize();
    }

    private void setGUISize() {
        GUISizeHelper.changePopupMenuFont(popupMenu);
    }

    private void addPopupMenuPlayer(boolean allowRequestToShowHandCards) {

        JMenuItem menuItem;

        ActionListener skipListener = e -> {
            switch (e.getActionCommand()) {
                case "F2": {
                    if (gamePanel.getFeedbackPanel() != null) {
                        gamePanel.getFeedbackPanel().pressOKYesOrDone();
                    }
                    break;
                }
                case "F3": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, gameId, null);
                    break;
                }
                case "F4": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN, gameId, null);
                    break;
                }
                case "F5": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_TURN_END_STEP, gameId, null);
                    break;
                }
                case "F6": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN_SKIP_STACK, gameId, null);
                    break;
                }
                case "F7": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_MAIN_PHASE, gameId, null);
                    break;
                }
                case "F9": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_MY_NEXT_TURN, gameId, null);
                    break;
                }
                case "F11": {
                    SessionHandler.sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_END_STEP_BEFORE_MY_NEXT_TURN, gameId, null);
                    break;
                }
                default:
                    break;
            }
        };

        menuItem = new JMenuItem("<html><b>F2</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblConfirmRequest"));
        menuItem.setActionCommand("F2");
        menuItem.setMnemonic(KeyEvent.VK_O);
        popupMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F3</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblCancelSkipAction"));
        menuItem.setActionCommand("F3");
        menuItem.setMnemonic(KeyEvent.VK_N);
        popupMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        holdPriorityMenuItem = new JCheckBoxMenuItem("<html><b>" + (System.getProperty("os.name").contains("Mac OS X") ? "Cmd" : "Ctrl") + "+click</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblHoldPriority"));
        holdPriorityMenuItem.setMnemonic(KeyEvent.VK_P);
        holdPriorityMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("htmlHoldPriorityDest"));
        popupMenu.add(holdPriorityMenuItem);
        holdPriorityMenuItem.addActionListener(e -> {
            boolean holdPriority = ((JCheckBoxMenuItem) e.getSource()).getState();
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolMenuItem2.getState(), useFirstManaAbilityItem.getState(), holdPriority);
            gamePanel.holdPriority(holdPriority);
        });

        JMenu skipMenu = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkip"));
        skipMenu.setMnemonic(KeyEvent.VK_S);
        popupMenu.add(skipMenu);

        String tooltipText = java.util.ResourceBundle.getBundle("otherMessage").getString("htmlSkipButtonDest");
        String everythingTooltipText = java.util.ResourceBundle.getBundle("otherMessage").getString("htmlEverythingSkipButtonDest");
        menuItem = new JMenuItem("<html><b>F4</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipUntilNextTurn"));
        menuItem.setActionCommand("F4");
        menuItem.setToolTipText(tooltipText);
        menuItem.setMnemonic(KeyEvent.VK_T);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F5</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipUntilNextEndStep"));
        menuItem.setActionCommand("F5");
        menuItem.setToolTipText(tooltipText);
        menuItem.setMnemonic(KeyEvent.VK_E);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F6</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipEverythingUntilNextTurn"));
        menuItem.setActionCommand("F6");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_U);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F7</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipUntilNextMainBegin"));
        menuItem.setToolTipText(tooltipText);
        menuItem.setActionCommand("F7");
        menuItem.setMnemonic(KeyEvent.VK_M);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F9</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipEverythingUntilYourNextTurn"));
        menuItem.setActionCommand("F9");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_V);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F11</b> - " + java.util.ResourceBundle.getBundle("otherMessage").getString("lblSkipUntilYourNextTurnProirEndStep"));
        menuItem.setActionCommand("F11");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_P);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        popupMenu.addSeparator();

        JMenu manaPoolMenu = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblManaPayment"));
        manaPoolMenu.setMnemonic(KeyEvent.VK_M);
        popupMenu.add(manaPoolMenu);

        manaPoolMenuItem1 = new JCheckBoxMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblAutoPay"), true);
        manaPoolMenuItem1.setMnemonic(KeyEvent.VK_A);
        manaPoolMenuItem1.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("htmlAutomaticallyPayDest"));
        manaPoolMenu.add(manaPoolMenuItem1);

        // Auto pay mana from mana pool
        manaPoolMenuItem1.addActionListener(e -> {
            boolean manaPoolAutomatic = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_GAME_MANA_AUTOPAYMENT, manaPoolAutomatic ? "true" : "false");
            gamePanel.setMenuStates(manaPoolAutomatic, manaPoolMenuItem2.getState(), useFirstManaAbilityItem.getState(), holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(manaPoolAutomatic ? PlayerAction.MANA_AUTO_PAYMENT_ON : PlayerAction.MANA_AUTO_PAYMENT_OFF, gameId, null);
        });

        manaPoolMenuItem2 = new JCheckBoxMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblNoAlreadyAutoPayMana"), true);
        manaPoolMenuItem2.setMnemonic(KeyEvent.VK_N);
        manaPoolMenuItem2.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("htmlNoAlreadyAutoPayManaDest"));
        manaPoolMenu.add(manaPoolMenuItem2);

        // Auto pay mana from mana pool
        manaPoolMenuItem2.addActionListener(e -> {
            boolean manaPoolAutomaticRestricted = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, manaPoolAutomaticRestricted ? "true" : "false");
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolAutomaticRestricted, useFirstManaAbilityItem.getState(), holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(manaPoolAutomaticRestricted ? PlayerAction.MANA_AUTO_PAYMENT_RESTRICTED_ON : PlayerAction.MANA_AUTO_PAYMENT_RESTRICTED_OFF, gameId, null);
        });

        useFirstManaAbilityItem = new JCheckBoxMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblFirstUseManaAbilityWhenTapLands"), false);
        useFirstManaAbilityItem.setMnemonic(KeyEvent.VK_F);
        useFirstManaAbilityItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("htmlFirstUseManaAbilityWhenTapLandsDest"));
        manaPoolMenu.add(useFirstManaAbilityItem);

        // Use first mana ability of lands
        useFirstManaAbilityItem.addActionListener(e -> {
            boolean useFirstManaAbility = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_USE_FIRST_MANA_ABILITY, useFirstManaAbility ? "true" : "false");
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolMenuItem2.getState(), useFirstManaAbility, holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(useFirstManaAbility ? PlayerAction.USE_FIRST_MANA_ABILITY_ON : PlayerAction.USE_FIRST_MANA_ABILITY_OFF, gameId, null);
        });

        JMenu automaticConfirmsMenu = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblAutoConfirms"));
        automaticConfirmsMenu.setMnemonic(KeyEvent.VK_U);
        popupMenu.add(automaticConfirmsMenu);

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoSelectReplacementEffects"));
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoSelectReplacementEffectsDest"));
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.RESET_AUTO_SELECT_REPLACEMENT_EFFECTS, gameId, null));

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoSelectTriggeredAbilitiesOrder"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoSelectTriggeredAbilitiesOrderDest"));
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.TRIGGER_AUTO_ORDER_RESET_ALL, gameId, null));

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoAnswersRequests"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblResetAutoAnswersRequestsDest"));
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL, gameId, null));

        JMenu handCardsMenu = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblHand"));
        handCardsMenu.setMnemonic(KeyEvent.VK_H);
        popupMenu.add(handCardsMenu);

        if (!options.playerItself) {
            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRequestSeeHandPermission"));
            menuItem.setMnemonic(KeyEvent.VK_P);
            handCardsMenu.add(menuItem);

            // Request to see hand cards
            menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS, gameId, playerId));
        } else {
            allowViewHandCardsMenuItem = new JCheckBoxMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblAllowSeeHandRequests"), allowRequestToShowHandCards);
            allowViewHandCardsMenuItem.setMnemonic(KeyEvent.VK_A);
            allowViewHandCardsMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblAllowSeeHandRequestsDest"));
            handCardsMenu.add(allowViewHandCardsMenuItem);

            // requests allowed (disable -> enable to reset requested list)
            allowViewHandCardsMenuItem.addActionListener(e -> {
                boolean requestsAllowed = ((JCheckBoxMenuItem) e.getSource()).getState();
                PreferencesDialog.setPrefValue(KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, requestsAllowed);
                SessionHandler.sendPlayerAction(requestsAllowed ? PlayerAction.PERMISSION_REQUESTS_ALLOWED_ON : PlayerAction.PERMISSION_REQUESTS_ALLOWED_OFF, gameId, null);
            });

            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRevokeAllSeeHandPermission"));
            menuItem.setMnemonic(KeyEvent.VK_R);
            menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRevokeAllAllowRequests"));
            handCardsMenu.add(menuItem);

            // revoke permissions to see hand cards
            menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REVOKE_PERMISSIONS_TO_SEE_HAND_CARDS, gameId, null));
        }

        if (options.rollbackTurnsAllowed) {
            ActionListener rollBackActionListener = e -> {
                int turnsToRollBack = Integer.parseInt(e.getActionCommand());
                SessionHandler.sendPlayerAction(PlayerAction.ROLLBACK_TURNS, gameId, turnsToRollBack);
            };

            JMenu rollbackMainItem = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRollbacks"));
            rollbackMainItem.setMnemonic(KeyEvent.VK_R);
            rollbackMainItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRollbackDest"));
            popupMenu.add(rollbackMainItem);

            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblToCurrentTurnStart"));
            menuItem.setMnemonic(KeyEvent.VK_C);
            menuItem.setActionCommand("0");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblToPreviousTurnStart"));
            menuItem.setMnemonic(KeyEvent.VK_P);
            menuItem.setActionCommand("1");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblToBefore2TurnStart"));
            menuItem.setMnemonic(KeyEvent.VK_2);
            menuItem.setActionCommand("2");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblToBefore3TurnStart"));
            menuItem.setMnemonic(KeyEvent.VK_3);
            menuItem.setActionCommand("3");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

        }

        JMenu concedeMenu = new JMenu(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConcede"));
        concedeMenu.setMnemonic(KeyEvent.VK_C);
        popupMenu.add(concedeMenu);

        ActionListener concedeListener = e -> {
            switch (e.getActionCommand()) {
                case "Game": {
                    UserRequestMessage message = new UserRequestMessage(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConfirmConcedeGame"), java.util.ResourceBundle.getBundle("otherMessage").getString("lblAreYouSureConcedeGame"));
                    message.setButton1(java.util.ResourceBundle.getBundle("otherMessage").getString("lblNo"), null);
                    message.setButton2(java.util.ResourceBundle.getBundle("otherMessage").getString("lblYes"), PlayerAction.CLIENT_CONCEDE_GAME);
                    message.setGameId(gameId);
                    MageFrame.getInstance().showUserRequestDialog(message);
                    break;
                }
                case "Match": {
                    UserRequestMessage message = new UserRequestMessage(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConfirmConcedeMatch"), java.util.ResourceBundle.getBundle("otherMessage").getString("lblAreYouSureConcedeMatch"));
                    message.setButton1(java.util.ResourceBundle.getBundle("otherMessage").getString("lblNo"), null);
                    message.setButton2(java.util.ResourceBundle.getBundle("otherMessage").getString("lblYes"), PlayerAction.CLIENT_CONCEDE_MATCH);
                    message.setGameId(gameId);
                    MageFrame.getInstance().showUserRequestDialog(message);
                    break;
                }
                default:
                    break;
            }
        };

        // Concede Game
        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblGame"));
        menuItem.setMnemonic(KeyEvent.VK_G);
        menuItem.setActionCommand("Game");
        menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConcedeGameDest"));
        concedeMenu.add(menuItem);
        menuItem.addActionListener(concedeListener);

        // Concede Match
        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblMatch"));
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.setActionCommand("Match");
        menuItem.setToolTipText(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConcedeMatchDest"));
        concedeMenu.add(menuItem);
        menuItem.addActionListener(concedeListener);

        battlefieldPanel.getMainPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent Me) {
                this.checkMenu(Me);
            }

            // neccessary for linux and mac systems
            @Override
            public void mousePressed(MouseEvent Me) {
                this.checkMenu(Me);
            }

            private void checkMenu(MouseEvent Me) {
                if (Me.isPopupTrigger() && playingMode) {
                    popupMenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });


        popupMenu.addSeparator();

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("htmlViewCurrentDeck"));
        menuItem.setMnemonic(KeyEvent.VK_V);
        popupMenu.add(menuItem);

        // View limited deck
        menuItem.addActionListener(e -> {
            SessionHandler.sendPlayerAction(PlayerAction.VIEW_LIMITED_DECK, gameId, null);
        });
    }

    private void addPopupMenuWatcher() {
        JMenuItem menuItem;

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblStopWatching"));
        popupMenu.add(menuItem);

        // Stop watching
        menuItem.addActionListener(e -> {
            UserRequestMessage message = new UserRequestMessage(java.util.ResourceBundle.getBundle("otherMessage").getString("lblConfirmStopWatching"), java.util.ResourceBundle.getBundle("otherMessage").getString("lblAreYouSureStopWatching"));
            message.setButton1(java.util.ResourceBundle.getBundle("otherMessage").getString("lblNo"), null);
            message.setButton2(java.util.ResourceBundle.getBundle("otherMessage").getString("lblYes"), PlayerAction.CLIENT_STOP_WATCHING);
            message.setGameId(gameId);
            MageFrame.getInstance().showUserRequestDialog(message);
        });

        menuItem = new JMenuItem(java.util.ResourceBundle.getBundle("otherMessage").getString("lblRequestSeeHandPermission"));
        popupMenu.add(menuItem);

        // Request to see hand cards
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS, gameId, playerId));

        battlefieldPanel.getMainPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent Me) {
                this.checkMenu(Me);
            }

            // neccessary for linux and mac systems
            @Override
            public void mousePressed(MouseEvent Me) {
                this.checkMenu(Me);
            }

            private void checkMenu(MouseEvent Me) {
                if (Me.isPopupTrigger() && playingMode) {
                    popupMenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });

    }

    public final void init(PlayerView player, BigCard bigCard, UUID gameId, int priorityTime) {
        this.playerPanel.init(gameId, player.getPlayerId(), bigCard, priorityTime);
        this.battlefieldPanel.init(gameId, bigCard);
        this.gameId = gameId;
        this.playerId = player.getPlayerId();
        if (SessionHandler.isTestMode()) {
            this.btnCheat.setVisible(true);
        } else {
            this.btnCheat.setVisible(false);
        }
    }

    public final void update(GameView game, PlayerView player, Set<UUID> possibleTargets) {
        this.playerPanel.update(game, player, possibleTargets);
        this.battlefieldPanel.update(player.getBattlefield());
        if (this.allowViewHandCardsMenuItem != null) {
            this.allowViewHandCardsMenuItem.setSelected(player.getUserData().isAllowRequestHandToAll());
        }
    }

    public mage.client.game.BattlefieldPanel getBattlefieldPanel() {
        return battlefieldPanel;
    }

    public PlayerPanelExt getPlayerPanel() {
        return playerPanel;
    }

    private void initComponents() {
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
        playerPanel = new PlayerPanelExt();
        btnCheat = new javax.swing.JButton();
        battlefieldPanel = new mage.client.game.BattlefieldPanel();
        battlefieldPanel.setTopPanelBattlefield(options.topRow);

        btnCheat.setText("Cheat");
        btnCheat.addActionListener(evt -> btnCheatActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(playerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(battlefieldPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(playerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(battlefieldPanel, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        this.setLayout(layout);
    }

    public void sizePlayer(boolean smallMode) {
        this.playerPanel.sizePlayerPanel(smallMode);
        this.smallMode = smallMode;
        if (smallMode) {
            this.playerPanel.setPreferredSize(new Dimension(92, PANEL_HEIGHT_SMALL));
            //this.jScrollPane1.setPreferredSize(new Dimension(160, 160));
            this.battlefieldPanel.setPreferredSize(new Dimension(160, PANEL_HEIGHT_SMALL));
        } else {
            this.playerPanel.setPreferredSize(new Dimension(92, PANEL_HEIGHT));
            //this.jScrollPane1.setPreferredSize(new Dimension(160, 212));
            this.battlefieldPanel.setPreferredSize(new Dimension(160, PANEL_HEIGHT));
        }
    }

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {
        SessionHandler.cheat(gameId, playerId, DeckImporter.importDeckFromFile("cheat.dck"));
    }

    public boolean isSmallMode() {
        return smallMode;
    }

    public void setPlayingMode(boolean playingMode) {
        this.playingMode = playingMode;
    }

    public void setMenuStates(boolean manaPoolAutomatic, boolean manaPoolAutomaticRestricted, boolean useFirstManaAbility, boolean holdPriority) {
        if (manaPoolMenuItem1 != null) {
            manaPoolMenuItem1.setSelected(manaPoolAutomatic);
        }
        if (manaPoolMenuItem2 != null) {
            manaPoolMenuItem2.setSelected(manaPoolAutomaticRestricted);
        }
        if (useFirstManaAbilityItem != null) {
            useFirstManaAbilityItem.setSelected(useFirstManaAbility);
        }
        if (holdPriorityMenuItem != null) {
            holdPriorityMenuItem.setSelected(holdPriority);
        }
    }

    private mage.client.game.BattlefieldPanel battlefieldPanel;
    private javax.swing.JButton btnCheat;
    //private javax.swing.JScrollPane jScrollPane1;
    private PlayerPanelExt playerPanel;

}
