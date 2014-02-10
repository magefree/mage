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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeListener;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
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
    private GamePanel gamePanel;

    public static final int PANEL_HEIGHT = 242;
    public static final int PANEL_HEIGHT_SMALL = 190;

    /** Creates new form PlayAreaPanel
     * @param isPlayer */
    public PlayAreaPanel(boolean isPlayer) {
        initComponents();
        setOpaque(false);
        battlefieldPanel.setOpaque(false);
        
        popupMenu = new JPopupMenu();
        if (isPlayer) {
            addPopupMenuPlayer();
        } else {
            addPopupMenuWatcher();
        }
        this.add(popupMenu);
    }

    public PlayAreaPanel(PlayerView player, BigCard bigCard, UUID gameId, boolean me, int priorityTime, boolean isPlayer, GamePanel gamePanel) {
        this(isPlayer);
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

    private void addPopupMenuPlayer() {

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
                gamePanel.getSession().restorePriority(gameId);
            }
        });

        popupMenu.addSeparator();

        menuItem = new JMenuItem("F4 - Skip phases until next turn (stop on stack/attack/block)");
        popupMenu.add(menuItem);

        // Skip to next turn (F4)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().passTurnPriority(gameId);;
            }
        });

        menuItem = new JMenuItem("F9 - Skip everything until own next turn (stop on attack/block)");
        popupMenu.add(menuItem);

        // Skip to next own turn (F9)
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getSession().passPriorityUntilNextYourTurn(gameId);
            }
        });

        popupMenu.addSeparator();

        menuItem = new JMenuItem("Concede game");
        popupMenu.add(menuItem);

        // Concede
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(PlayAreaPanel.this, "Are you sure you want to concede the game?", "Confirm concede game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    MageFrame.getSession().concedeGame(gameId);
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
        battlefieldPanel.getMainPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent Me) {
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

    private mage.client.game.BattlefieldPanel battlefieldPanel;
    private javax.swing.JButton btnCheat;
    //private javax.swing.JScrollPane jScrollPane1;
    private PlayerPanelExt playerPanel;

}
