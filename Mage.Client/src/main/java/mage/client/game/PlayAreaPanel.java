
package mage.client.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeListener;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import static mage.client.dialog.PreferencesDialog.KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS;
import static mage.client.dialog.PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT;
import static mage.client.dialog.PreferencesDialog.KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE;
import static mage.client.dialog.PreferencesDialog.KEY_USE_FIRST_MANA_ABILITY;
import mage.client.util.GUISizeHelper;
import mage.constants.PlayerAction;
import mage.view.PlayerView;
import mage.view.UserRequestMessage;

/**
 *
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
            addPopupMenuPlayer(player.getUserData().isAllowRequestShowHandCards());
        } else {
            addPopupMenuWatcher();
        }
        this.add(popupMenu);
        setGUISize();

        init(player, bigCard, gameId, priorityTime);
        update(player);
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
            }
        };

        menuItem = new JMenuItem("<html><b>F2</b> - Confirm current request");
        menuItem.setActionCommand("F2");
        menuItem.setMnemonic(KeyEvent.VK_O);
        popupMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F3</b> - Cancel active skip action");
        menuItem.setActionCommand("F3");
        menuItem.setMnemonic(KeyEvent.VK_N);
        popupMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        holdPriorityMenuItem = new JCheckBoxMenuItem("<html><b>" + (System.getProperty("os.name").contains("Mac OS X") ? "Cmd" : "Ctrl") + "+click</b> - Hold Priority");
        holdPriorityMenuItem.setMnemonic(KeyEvent.VK_P);
        holdPriorityMenuItem.setToolTipText("<html>Hold priority after casting a spell or activating an ability, instead of automatically passing priority.");
        popupMenu.add(holdPriorityMenuItem);
        holdPriorityMenuItem.addActionListener(e -> {
            boolean holdPriority = ((JCheckBoxMenuItem) e.getSource()).getState();
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolMenuItem2.getState(), useFirstManaAbilityItem.getState(), holdPriority);
            gamePanel.holdPriority(holdPriority);
        });

        JMenu skipMenu = new JMenu("Skip");
        skipMenu.setMnemonic(KeyEvent.VK_S);
        popupMenu.add(skipMenu);

        String tooltipText = "<html>This skip actions stops if something goes to <br><b>stack</b> and if <b>attackers</b> or <b>blocker</b> have to be <b>declared</b>.";
        String everythingTooltipText = "<html>This skip actions stops if <b>attackers</b> or <b>blocker</b> have to be <b>declared</b>, but not if something goes to the <b>stack</b>.";
        menuItem = new JMenuItem("<html><b>F4</b> - Phases until next turn");
        menuItem.setActionCommand("F4");
        menuItem.setToolTipText(tooltipText);
        menuItem.setMnemonic(KeyEvent.VK_T);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F5</b> - Phases until next end step");
        menuItem.setActionCommand("F5");
        menuItem.setToolTipText(tooltipText);
        menuItem.setMnemonic(KeyEvent.VK_E);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F6</b> - Everything until the next turn");
        menuItem.setActionCommand("F6");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_U);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F7</b> - Phases until begin of next main phase");
        menuItem.setToolTipText(tooltipText);
        menuItem.setActionCommand("F7");
        menuItem.setMnemonic(KeyEvent.VK_M);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F9</b> - Everything until your own next turn");
        menuItem.setActionCommand("F9");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_V);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        menuItem = new JMenuItem("<html><b>F11</b> - Everything until end step prior to your own next turn");
        menuItem.setActionCommand("F11");
        menuItem.setToolTipText(everythingTooltipText);
        menuItem.setMnemonic(KeyEvent.VK_P);
        skipMenu.add(menuItem);
        menuItem.addActionListener(skipListener);

        popupMenu.addSeparator();

        JMenu manaPoolMenu = new JMenu("Mana payment");
        manaPoolMenu.setMnemonic(KeyEvent.VK_M);
        popupMenu.add(manaPoolMenu);

        manaPoolMenuItem1 = new JCheckBoxMenuItem("Automatically", true);
        manaPoolMenuItem1.setMnemonic(KeyEvent.VK_A);
        manaPoolMenuItem1.setToolTipText("<html>If not active, produced mana goes only to the mana pool<br>"
                + "and you have to click the type of mana you want to use <br>"
                + "in the player mana pool panel for payment.");
        manaPoolMenu.add(manaPoolMenuItem1);

        // Auto pay mana from mana pool
        manaPoolMenuItem1.addActionListener(e -> {
            boolean manaPoolAutomatic = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_GAME_MANA_AUTOPAYMENT, manaPoolAutomatic ? "true" : "false");
            gamePanel.setMenuStates(manaPoolAutomatic, manaPoolMenuItem2.getState(), useFirstManaAbilityItem.getState(), holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(manaPoolAutomatic ? PlayerAction.MANA_AUTO_PAYMENT_ON : PlayerAction.MANA_AUTO_PAYMENT_OFF, gameId, null);
        });

        manaPoolMenuItem2 = new JCheckBoxMenuItem("No automatic usage for mana already in the pool", true);
        manaPoolMenuItem2.setMnemonic(KeyEvent.VK_N);
        manaPoolMenuItem2.setToolTipText("<html>Mana that is already in the mana pool as you start casting a spell or activating an ability<br>"
                + " needs to be payed manually. So you use the mana in the pool only by clicking on the related<br>"
                + " mana symbols of mana pool area.");
        manaPoolMenu.add(manaPoolMenuItem2);

        // Auto pay mana from mana pool
        manaPoolMenuItem2.addActionListener(e -> {
            boolean manaPoolAutomaticRestricted = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_GAME_MANA_AUTOPAYMENT_ONLY_ONE, manaPoolAutomaticRestricted ? "true" : "false");
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolAutomaticRestricted, useFirstManaAbilityItem.getState(), holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(manaPoolAutomaticRestricted ? PlayerAction.MANA_AUTO_PAYMENT_RESTRICTED_ON : PlayerAction.MANA_AUTO_PAYMENT_RESTRICTED_OFF, gameId, null);
        });

        useFirstManaAbilityItem = new JCheckBoxMenuItem("Use first mana ability when tapping lands", false);
        useFirstManaAbilityItem.setMnemonic(KeyEvent.VK_F);
        useFirstManaAbilityItem.setToolTipText("<html>Use the first mana ability when<br>"
                + " tapping lands for mana<br>"
                + "You can hold Alt+1 whilst tapping lands to use this feature");
        manaPoolMenu.add(useFirstManaAbilityItem);

        // Use first mana ability of lands
        useFirstManaAbilityItem.addActionListener(e -> {
            boolean useFirstManaAbility = ((JCheckBoxMenuItem) e.getSource()).getState();
            PreferencesDialog.saveValue(KEY_USE_FIRST_MANA_ABILITY, useFirstManaAbility ? "true" : "false");
            gamePanel.setMenuStates(manaPoolMenuItem1.getState(), manaPoolMenuItem2.getState(), useFirstManaAbility, holdPriorityMenuItem.getState());
            SessionHandler.sendPlayerAction(useFirstManaAbility ? PlayerAction.USE_FIRST_MANA_ABILITY_ON : PlayerAction.USE_FIRST_MANA_ABILITY_OFF, gameId, null);
        });

        JMenu automaticConfirmsMenu = new JMenu("Automatic confirms");
        automaticConfirmsMenu.setMnemonic(KeyEvent.VK_U);
        popupMenu.add(automaticConfirmsMenu);

        menuItem = new JMenuItem("Replacement effects - reset auto select");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setToolTipText("Reset all effects that were added to the list of auto select replacement effects this game.");
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.RESET_AUTO_SELECT_REPLACEMENT_EFFECTS, gameId, null));

        menuItem = new JMenuItem("Triggered abilities - reset auto stack order");
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setToolTipText("Deletes all triggered ability order settings you added during the game.");
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.TRIGGER_AUTO_ORDER_RESET_ALL, gameId, null));

        menuItem = new JMenuItem("Use requests - reset automatic answers");
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setToolTipText("Deletes all defined automatic answers for Yes/No usage requests.");
        automaticConfirmsMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REQUEST_AUTO_ANSWER_RESET_ALL, gameId, null));

        JMenu handCardsMenu = new JMenu("Cards on hand");
        handCardsMenu.setMnemonic(KeyEvent.VK_H);
        popupMenu.add(handCardsMenu);

        if (!options.playerItself) {
            menuItem = new JMenuItem("Request permission to see the hand cards");
            menuItem.setMnemonic(KeyEvent.VK_P);
            handCardsMenu.add(menuItem);

            // Request to see hand cards
            menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS, gameId, playerId));
        } else {
            allowViewHandCardsMenuItem = new JCheckBoxMenuItem("Allow requests to show from other users", allowRequestToShowHandCards);
            allowViewHandCardsMenuItem.setMnemonic(KeyEvent.VK_A);
            allowViewHandCardsMenuItem.setToolTipText("If activated watchers or other players can request to see your hand cards. If you grant this to a user, it's valid for the complete match.");
            handCardsMenu.add(allowViewHandCardsMenuItem);

            // Requests allowed
            allowViewHandCardsMenuItem.addActionListener(e -> {
                boolean requestsAllowed = ((JCheckBoxMenuItem) e.getSource()).getState();
                PreferencesDialog.setPrefValue(KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, requestsAllowed);
                SessionHandler.sendPlayerAction(requestsAllowed ? PlayerAction.PERMISSION_REQUESTS_ALLOWED_ON : PlayerAction.PERMISSION_REQUESTS_ALLOWED_OFF, gameId, null);
            });

            menuItem = new JMenuItem("Revoke all permission(s) to see your hand cards");
            menuItem.setMnemonic(KeyEvent.VK_R);
            menuItem.setToolTipText("Revoke already granted permission for all spectators to see your hand cards.");
            handCardsMenu.add(menuItem);

            // revoke permissions to see hand cards
            menuItem.addActionListener(e -> SessionHandler.sendPlayerAction(PlayerAction.REVOKE_PERMISSIONS_TO_SEE_HAND_CARDS, gameId, null));
        }

        if (options.rollbackTurnsAllowed) {
            ActionListener rollBackActionListener = e -> {
                int turnsToRollBack = Integer.parseInt(e.getActionCommand());
                SessionHandler.sendPlayerAction(PlayerAction.ROLLBACK_TURNS, gameId, turnsToRollBack);
            };

            JMenu rollbackMainItem = new JMenu("Rollback");
            rollbackMainItem.setMnemonic(KeyEvent.VK_R);
            rollbackMainItem.setToolTipText("The game will be rolled back to the start of the requested turn if all players agree.");
            popupMenu.add(rollbackMainItem);

            menuItem = new JMenuItem("To the start of the current turn");
            menuItem.setMnemonic(KeyEvent.VK_C);
            menuItem.setActionCommand("0");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem("To the start of the previous turn");
            menuItem.setMnemonic(KeyEvent.VK_P);
            menuItem.setActionCommand("1");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem("The current turn and the 2 turns before");
            menuItem.setMnemonic(KeyEvent.VK_2);
            menuItem.setActionCommand("2");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

            menuItem = new JMenuItem("The current turn and the 3 turns before");
            menuItem.setMnemonic(KeyEvent.VK_3);
            menuItem.setActionCommand("3");
            menuItem.addActionListener(rollBackActionListener);
            rollbackMainItem.add(menuItem);

        }

        JMenu concedeMenu = new JMenu("Concede");
        concedeMenu.setMnemonic(KeyEvent.VK_C);
        popupMenu.add(concedeMenu);

        ActionListener concedeListener = e -> {
            switch (e.getActionCommand()) {
                case "Game": {
                    UserRequestMessage message = new UserRequestMessage("Confirm concede game", "Are you sure you want to concede the game?");
                    message.setButton1("No", null);
                    message.setButton2("Yes", PlayerAction.CLIENT_CONCEDE_GAME);
                    message.setGameId(gameId);
                    MageFrame.getInstance().showUserRequestDialog(message);
                    break;
                }
                case "Match": {
                    UserRequestMessage message = new UserRequestMessage("Confirm concede match", "Are you sure you want to concede the complete match?");
                    message.setButton1("No", null);
                    message.setButton2("Yes", PlayerAction.CLIENT_CONCEDE_MATCH);
                    message.setGameId(gameId);
                    MageFrame.getInstance().showUserRequestDialog(message);
                    break;
                }
            }
        };

        // Concede Game
        menuItem = new JMenuItem("Game");
        menuItem.setMnemonic(KeyEvent.VK_G);
        menuItem.setActionCommand("Game");
        menuItem.setToolTipText("Concedes only the current game and after that the next game of the match is started if there is another game needed.");
        concedeMenu.add(menuItem);
        menuItem.addActionListener(concedeListener);

        // Concede Match
        menuItem = new JMenuItem("Match");
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.setActionCommand("Match");
        menuItem.setToolTipText("Concedes the complete match. So if you're in a tournament you finish the current tournament round.");
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
        
        menuItem = new JMenuItem("<html>View current deck");
        menuItem.setMnemonic(KeyEvent.VK_V);
        popupMenu.add(menuItem);
        
        // View limited deck
        menuItem.addActionListener(e -> {
            SessionHandler.sendPlayerAction(PlayerAction.VIEW_LIMITED_DECK, gameId, null);
        });
    }

    private void addPopupMenuWatcher() {
        JMenuItem menuItem;

        menuItem = new JMenuItem("Stop watching");
        popupMenu.add(menuItem);

        // Stop watching
        menuItem.addActionListener(e -> {
            UserRequestMessage message = new UserRequestMessage("Confirm stop watching game", "Are you sure you want to stop watching the game?");
            message.setButton1("No", null);
            message.setButton2("Yes", PlayerAction.CLIENT_STOP_WATCHING);
            message.setGameId(gameId);
            MageFrame.getInstance().showUserRequestDialog(message);
        });

        menuItem = new JMenuItem("Request permission to see hand cards");
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

    public final void update(PlayerView player) {
        this.playerPanel.update(player);
        this.battlefieldPanel.update(player.getBattlefield());
        if (this.allowViewHandCardsMenuItem != null) {
            this.allowViewHandCardsMenuItem.setSelected(player.getUserData().isAllowRequestShowHandCards());
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
        SessionHandler.cheat(gameId, playerId, DeckImporterUtil.importDeck("cheat.dck"));
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
