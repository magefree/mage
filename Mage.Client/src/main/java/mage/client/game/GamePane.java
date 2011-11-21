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

/*
 * GamePane.java
 *
 * Created on Dec 17, 2009, 9:34:10 AM
 */

package mage.client.game;

import mage.client.*;

import javax.swing.*;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GamePane extends MagePane {

    /** Creates new form GamePane */
    public GamePane() {
        initComponents();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gamePanel.setJLayeredPane(getLayeredPane());
				gamePanel.installComponents();
			}
		});

    }

	public void showGame(UUID gameId, UUID playerId) {
		this.setTitle("Game " + gameId);
		gamePanel.showGame(gameId, playerId);
	}

	public void hideGame() {
		gamePanel.hideGame();
	}
	
	public void watchGame(UUID gameId) {
		this.setTitle("Watching " + gameId);
		gamePanel.watchGame(gameId);
	}

	public void replayGame(UUID gameId) {
		this.setTitle("Replaying " + gameId);
		gamePanel.replayGame(gameId);
	}

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        gamePanel = new mage.client.game.GamePanel();

        jScrollPane1.setViewportView(gamePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        pack();
    }


    private mage.client.game.GamePanel gamePanel;
    private javax.swing.JScrollPane jScrollPane1;

}
