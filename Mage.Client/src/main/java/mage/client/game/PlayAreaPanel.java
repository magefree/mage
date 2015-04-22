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
import java.awt.event.ActionEvent;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeListener;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import static mage.client.dialog.PreferencesDialog.KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS;
import mage.constants.PlayerAction;
import mage.view.PlayerView;

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
    private final boolean playerItself;

    private JCheckBoxMenuItem manaPoolMenuItem;
    private JCheckBoxMenuItem allowViewHandCardsMenuItem;
    
    public static final int PANEL_HEIGHT = 242;
    public static final int PANEL_HEIGHT_SMALL = 190;

    /** Creates new form PlayAreaPanel
     * @param player
     * @param bigCard
     * @param gameId
     * @param isPlayer true if the client is a player / false if the client is a watcher
     * @param playerItself true if it's the area of the player itself
     * @param priorityTime
     * @param gamePanel */
    public PlayAreaPanel(PlayerView player, BigCard bigCard, UUID gameId, boolean playerItself, int priorityTime, boolean isPlayer, GamePanel gamePanel) {
        //this(isPlayer);
        this.playerItself = playerItself;
        initComponents();
        setOpaque(false);
        battlefieldPanel.setOpaque(false);

        popupMenu = new JPopupMenu();
        if (isPlayer) {
            addPopupMenuPlayer(player.getUserData().allowRequestShowHandCards());
        } else {
            addPopupMenuWatcher();
        }
        this.add(popupMenu);
        this.gamePanel = gamePanel;
        init(player, bigCard, gameId, priorityTime);
        update(player);
    }

    public void CleanUp() {
        battlefieldPanel.cleanUp();
        playerPanel.cleanUp();
        

        for (ActionListener al : btnCheat.getActionListeners() ) {
            btnCheat.removeActionListener(al);
        }
        
        // Taken form : https://community.oracle.com/thread/2183145
        // removed the internal focus of a popupMenu data to allow GC before another popup menu is selected
        for(ChangeListener listener : MenuSelectionManager.defaultManager().getChangeListeners()) {
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

        for (MouseListener ml :battlefieldPanel.getMainPanel().getMouseListeners()) {
            battlefieldPanel.getMainPanel().removeMouseListener(ml);
        }
        popupMenu.getUI().uninstallUI(this);

    }

    private void addPopupMenuPlayer(boolean allowRequestToShowHandCards) {

        JMenuItem menuItem;

        menuItem = new JMenuItem("F2 - Confirm");
        popupMenu.add(menuItem);

        // Confirm (F2)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gamePanel.getFeedbackPanel() != null) {
                    gamePanel.getFeedbackPanel().pressOKYesOrDone();
                }
            }
        });


        menuItem = new JMenuItem("F3 - Cancel previous F4/F9 skip action");
        popupMenu.add(menuItem);

        // Cancel (F3)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.PASS_PRIORITY_CANCEL_ALL_ACTIONS, gameId, null);
            }
        });

        popupMenu.addSeparator();

        menuItem = new JMenuItem("F4 - Skip phases until next turn (stop on stack/attack/block)");
        popupMenu.add(menuItem);

        // Skip to next turn (F4)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_TURN, gameId, null);
            }
        });

        menuItem = new JMenuItem("F5 - Skip phases until next end step (stop on stack/attack/block)");
        popupMenu.add(menuItem);

        // Skip to next end step of turn (F5)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_TURN_END_STEP, gameId, null);
            }
        });

        menuItem = new JMenuItem("F7 - Skip phases until begin of next main phase (stop on stack/attack/block)");
        popupMenu.add(menuItem);

        // Skip to next main phase (F7)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_NEXT_MAIN_PHASE, gameId, null);
            }
        });
        menuItem = new JMenuItem("F9 - Skip everything until own next turn (stop on attack/block)");
        popupMenu.add(menuItem);

        // Skip to next own turn (F9)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.PASS_PRIORITY_UNTIL_MY_NEXT_TURN, gameId, null);
            }
        });

        popupMenu.addSeparator();

        manaPoolMenuItem = new JCheckBoxMenuItem("Use mana from pool automatically", true);
        manaPoolMenuItem.setMnemonic(KeyEvent.VK_M);
        manaPoolMenuItem.setToolTipText("If not active, you have to click the type of mana you want to pay in the player panel.");
        popupMenu.add(manaPoolMenuItem);

        // Auto pay mana from mana pool
        manaPoolMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean manaPoolAutomatic = ((JCheckBoxMenuItem)e.getSource()).getState();
                gamePanel.setMenuStates(manaPoolAutomatic);
                gamePanel.getSession().sendPlayerAction(manaPoolAutomatic ? PlayerAction.MANA_AUTO_PAYMENT_ON: PlayerAction.MANA_AUTO_PAYMENT_OFF, gameId, null);
            }
        });


        menuItem = new JMenuItem("Replacement effects - reset auto select");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setToolTipText("Reset all effects that were added to the list of auto select replacement effects this game.");
        popupMenu.add(menuItem);
        // Reset the replacement effcts that were auto selected for the game
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.RESET_AUTO_SELECT_REPLACEMENT_EFFECTS, gameId, null);
            }
        });

        popupMenu.addSeparator();

        if (!playerItself) {
            menuItem = new JMenuItem("Request permission to see hand cards");
            popupMenu.add(menuItem);

            // Request to see hand cards
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gamePanel.getSession().sendPlayerAction(PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS, gameId, playerId);
                }
            });
        } else {
            allowViewHandCardsMenuItem = new JCheckBoxMenuItem("Allow requests to show your hand cards", allowRequestToShowHandCards);
            allowViewHandCardsMenuItem.setMnemonic(KeyEvent.VK_A);
            allowViewHandCardsMenuItem.setToolTipText("If activated watchers or other players can request to see your hand cards. If you grant this to a user, it's valid for the complete match.");
            popupMenu.add(allowViewHandCardsMenuItem);

            // Requests allowed
            allowViewHandCardsMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean requestsAllowed = ((JCheckBoxMenuItem)e.getSource()).getState();
                    PreferencesDialog.setPrefValue(KEY_GAME_ALLOW_REQUEST_SHOW_HAND_CARDS, requestsAllowed);
                    gamePanel.getSession().sendPlayerAction(requestsAllowed ? PlayerAction.PERMISSION_REQUESTS_ALLOWED_ON: PlayerAction.PERMISSION_REQUESTS_ALLOWED_OFF, gameId, null);
                }
            });

            menuItem = new JMenuItem("Revoke all permission(s) to see your hand cards");
            menuItem.setMnemonic(KeyEvent.VK_P);
            menuItem.setToolTipText("Revoke already granted permission for all spectators to see your hand cards.");
            popupMenu.add(menuItem);

            // revoke permissions to see hand cards
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gamePanel.getSession().sendPlayerAction(PlayerAction.REVOKE_PERMISSIONS_TO_SEE_HAND_CARDS, gameId, null);
                }
            });
        }
        popupMenu.addSeparator();

        menuItem = new JMenuItem("Concede game");
        popupMenu.add(menuItem);

        // Concede
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(PlayAreaPanel.this, "Are you sure you want to concede the game?", "Confirm concede game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    MageFrame.getSession().sendPlayerAction(PlayerAction.CONCEDE, gameId, null);
                }
            }
        });

        popupMenu.addSeparator();

        menuItem = new JMenuItem("Concede complete match");
        popupMenu.add(menuItem);

        // Quit match
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(PlayAreaPanel.this, "Are you sure you want to concede the complete match?", "Confirm concede match", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    MageFrame.getSession().quitMatch(gameId);
                }
            }
        });


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

            private void checkMenu(MouseEvent Me){
                if (Me.isPopupTrigger() && playingMode) {
                    popupMenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });

    }

    private void addPopupMenuWatcher() {
        JMenuItem menuItem;

        menuItem = new JMenuItem("Stop watching");
        popupMenu.add(menuItem);

        // Stop watching
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(PlayAreaPanel.this, "Are you sure you want to stop watching the game?", "Confirm stop watching game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    gamePanel.getSession().stopWatching(gameId);
                    gamePanel.removeGame();
                }
            }
        });

        menuItem = new JMenuItem("Request permission to see hand cards");
        popupMenu.add(menuItem);

        // Request to see hand cards
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().sendPlayerAction(PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS, gameId, playerId);
            }
        });

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

            private void checkMenu(MouseEvent Me){
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
        if (MageFrame.getSession().isTestMode()) {
            this.playerId = player.getPlayerId();
            this.btnCheat.setVisible(true);
        }
        else {
            this.btnCheat.setVisible(false);
        }
    }

    public final void update(PlayerView player) {
        this.playerPanel.update(player);
        this.battlefieldPanel.update(player.getBattlefield());
        if (this.allowViewHandCardsMenuItem != null) {
            this.allowViewHandCardsMenuItem.setSelected(player.getUserData().allowRequestShowHandCards());
        }
    }

    public mage.client.game.BattlefieldPanel getBattlefieldPanel() {
        return battlefieldPanel;
    }

    public PlayerPanelExt getPlayerPanel() {
        return playerPanel;
    }

    private void initComponents() {
        setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        playerPanel = new PlayerPanelExt();
        btnCheat = new javax.swing.JButton();
        //jScrollPane1 = new javax.swing.JScrollPane();
        //battlefieldPanel = new mage.client.game.BattlefieldPanel(jScrollPane1);
        battlefieldPanel = new mage.client.game.BattlefieldPanel();

        btnCheat.setText("Cheat");
        btnCheat.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheatActionPerformed(evt);
            }
        });

        //jScrollPane1.setViewportView(battlefieldPanel);
        //Border empty = new EmptyBorder(0,0,0,0);
        //jScrollPane1.setBorder(empty);
        //jScrollPane1.setViewportBorder(empty);

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
        }
        else {
            this.playerPanel.setPreferredSize(new Dimension(92, PANEL_HEIGHT));
            //this.jScrollPane1.setPreferredSize(new Dimension(160, 212));
            this.battlefieldPanel.setPreferredSize(new Dimension(160, PANEL_HEIGHT));
        }
    }

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {
        MageFrame.getSession().cheat(gameId, playerId, DeckImporterUtil.importDeck("cheat.dck"));
    }

    public boolean isSmallMode() {
        return smallMode;
    }

    public void setPlayingMode(boolean playingMode) {
        this.playingMode = playingMode;
    }

    public void setMenuStates(boolean manaPoolAutomatic) {
        manaPoolMenuItem.setSelected(manaPoolAutomatic);
    }
    
    private mage.client.game.BattlefieldPanel battlefieldPanel;
    private javax.swing.JButton btnCheat;
    //private javax.swing.JScrollPane jScrollPane1;
    private PlayerPanelExt playerPanel;

}
