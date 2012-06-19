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

import mage.cards.decks.importer.DeckImporterUtil;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.view.PlayerView;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayAreaPanel extends javax.swing.JPanel {

    private UUID playerId;
    private UUID gameId;

    /** Creates new form PlayAreaPanel */
    public PlayAreaPanel() {
        initComponents();
        setOpaque(false);
        //jScrollPane1.setOpaque(false);
        //jScrollPane1.getViewport().setOpaque(false);
        battlefieldPanel.setOpaque(false);
    }

    public PlayAreaPanel(PlayerView player, BigCard bigCard, UUID gameId, boolean me) {
        this();
        init(player, bigCard, gameId);
        update(player);
    }

    public final void init(PlayerView player, BigCard bigCard, UUID gameId) {
        this.playerPanel.init(gameId, player.getPlayerId(), bigCard);
        this.battlefieldPanel.init(gameId, bigCard);
        if (MageFrame.getSession().isTestMode()) {
            this.playerId = player.getPlayerId();
            this.gameId = gameId;
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
        if (smallMode) {
            this.playerPanel.setPreferredSize(new Dimension(92, 160));
            //this.jScrollPane1.setPreferredSize(new Dimension(160, 160));
            this.battlefieldPanel.setPreferredSize(new Dimension(160, 160));
        }
        else {
            this.playerPanel.setPreferredSize(new Dimension(92, 212));
            //this.jScrollPane1.setPreferredSize(new Dimension(160, 212));
            this.battlefieldPanel.setPreferredSize(new Dimension(160, 212));
        }
    }

    private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {
        MageFrame.getSession().cheat(gameId, playerId, DeckImporterUtil.importDeck("cheat.dck"));
    }


    private mage.client.game.BattlefieldPanel battlefieldPanel;
    private javax.swing.JButton btnCheat;
    //private javax.swing.JScrollPane jScrollPane1;
    private PlayerPanelExt playerPanel;

}
