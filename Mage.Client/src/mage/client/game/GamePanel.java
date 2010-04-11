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
 * GamePanel.java
 *
 * Created on Dec 16, 2009, 9:29:58 AM
 */

package mage.client.game;

import java.awt.MouseInfo;
import java.util.logging.Level;
import mage.client.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mage.cards.decks.DeckCardLists;
import mage.client.dialog.CombatDialog;
import mage.client.dialog.ExileZoneDialog;
import mage.client.dialog.PickChoiceDialog;
import mage.client.dialog.PickNumberDialog;
import mage.client.dialog.ShowCardsDialog;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.remote.Session;
import mage.util.Logging;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ExileView;
import mage.view.GameView;
import mage.view.PlayerView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GamePanel extends javax.swing.JPanel {

	private final static Logger logger = Logging.getLogger(GamePanel.class.getName());

	private Map<UUID, PlayAreaPanel> players = new HashMap<UUID, PlayAreaPanel>();
	private Map<UUID, ExileZoneDialog> exiles = new HashMap<UUID, ExileZoneDialog>();
	private UUID gameId;
	private Session session;
	private CombatDialog combat = new CombatDialog();

    /** Creates new form GamePanel */
    public GamePanel() {
        initComponents();
    }

	public synchronized void showGame(UUID gameId, UUID playerId) {
		this.gameId = gameId;
		session = MageFrame.getSession();
		session.setGame(this);
		this.feedbackPanel.init(gameId);
		this.feedbackPanel.clear();
		this.abilityPicker.init(session, gameId);
		this.btnConcede.setVisible(true);
		this.pnlReplay.setVisible(false);
		this.btnStopWatching.setVisible(false);
		this.setVisible(true);
		this.chatPanel.connect(session.getGameChatId(gameId));
		if (!session.joinGame(gameId))
			hideGame();
	}

	public synchronized void watchGame(UUID gameId) {
		this.gameId = gameId;
		session = MageFrame.getSession();
		session.setGame(this);
		this.feedbackPanel.init(gameId);
		this.feedbackPanel.clear();
		this.btnConcede.setVisible(false);
		this.btnStopWatching.setVisible(true);
		this.pnlReplay.setVisible(false);
		this.setVisible(true);
		this.chatPanel.connect(session.getGameChatId(gameId));
		if (!session.watchGame(gameId))
			hideGame();
	}

	public synchronized void replayGame(UUID gameId) {
		this.gameId = gameId;
		session = MageFrame.getSession();
		session.setGame(this);
		this.feedbackPanel.clear();
		this.btnConcede.setVisible(false);
		this.btnStopWatching.setVisible(false);
		this.pnlReplay.setVisible(true);
		this.setVisible(true);
		if (!session.replayGame())
			hideGame();
	}

	public void hideGame() {
		this.chatPanel.disconnect();
		this.players.clear();
		this.pnlBattlefield.removeAll();
		this.combat.hideDialog();
		this.setVisible(false);
	}

	public synchronized void init(GameView game) {
		combat.init(gameId, bigCard);
		MageFrame.getDesktop().add(combat, JLayeredPane.PALETTE_LAYER);
		this.players.clear();
		this.pnlBattlefield.removeAll();
		for (PlayerView player: game.getPlayers()) {
			PlayAreaPanel playerPanel = new PlayAreaPanel(player, bigCard, gameId);
			players.put(player.getPlayerId(), playerPanel);
			this.pnlBattlefield.add(playerPanel);
			playerPanel.setVisible(true);
		}
		updateGame(game);
	}

	public synchronized void updateGame(GameView game) {
		if (game.getHand() != null)
			this.hand.loadCards(game.getHand(), bigCard, gameId);
		else
			this.hand.setVisible(false);
		if (game.getPhase() != null)
			this.txtPhase.setText(game.getPhase().toString());
		if (game.getStep() != null)
			this.txtStep.setText(game.getStep().toString());
		this.txtActivePlayer.setText(game.getActivePlayerName());
		this.txtPriority.setText(game.getPriorityPlayerName());
		this.txtTurn.setText(Integer.toString(game.getTurn()));
		for (PlayerView player: game.getPlayers()) {
			players.get(player.getPlayerId()).update(player);
		}
		this.stack.loadCards(game.getStack(), bigCard, gameId);
		for (ExileView exile: game.getExile()) {
			if (!exiles.containsKey(exile.getId())) {
				ExileZoneDialog newExile = new ExileZoneDialog();
				exiles.put(exile.getId(), newExile);
			}
			exiles.get(exile.getId()).loadCards(exile, bigCard, gameId);
		}
		if (game.getCombat().size() > 0) {
			combat.showDialog(game.getCombat());
			combat.setLocation(300, 200);
		}
		else {
			combat.hideDialog();
		}
		this.revalidate();
		this.repaint();
	}

	public void ask(String question) {
		this.feedbackPanel.getFeedback(FeedbackMode.QUESTION, question, true);
	}

	public void inform(String information, CardsView cardView, GameView gameView) {
		updateGame(gameView);
		if (cardView != null && cardView.size() > 0) {
			showCards(information, cardView);
		}
		this.feedbackPanel.getFeedback(FeedbackMode.INFORM, information, false);
	}

	public void cancel(String message, CardsView cardView, GameView gameView) {
		updateGame(gameView);
		if (cardView != null && cardView.size() > 0) {
			showCards(message, cardView);
		}
		this.feedbackPanel.getFeedback(FeedbackMode.CANCEL, message, false);
	}

	public void modalMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "", JOptionPane.INFORMATION_MESSAGE);
	}

	public int modalQuestion(String message, String title) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
	}

	public JPanel getHand() {
		return hand;
	}

	public void select(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.SELECT, message, false);
	}

	public void playMana(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.CANCEL, message, false);
	}

	public void playXMana(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.CONFIRM, message, false);
	}

	public void replayMessage(String message) {
		//TODO: implement this
	}

	public void pickAbility(AbilityPickerView choices) {
		this.abilityPicker.show(choices, MouseInfo.getPointerInfo().getLocation());
	}

	public void revealCards(String name, CardsView cards) {
		ShowCardsDialog showCards = new ShowCardsDialog();
		MageFrame.getDesktop().add(showCards);
		showCards.loadCards(name, cards, bigCard, gameId);
	}

	private void showCards(String title, CardsView cards) {
		ShowCardsDialog showCards = new ShowCardsDialog();
		showCards.loadCards(title, cards, bigCard, gameId);
	}

	public void getAmount(int min, int max) {
		PickNumberDialog pickNumber = new PickNumberDialog();
		pickNumber.showDialog(min, max);
		if (pickNumber.isCancel())
			session.sendPlayerBoolean(gameId, false);
		else
			session.sendPlayerInteger(gameId, pickNumber.getAmount());
	}

	public void getChoice(String message, String[] choices) {
		PickChoiceDialog pickChoice = new PickChoiceDialog();
		pickChoice.showDialog(message, choices);
		session.sendPlayerString(gameId, pickChoice.getChoice());
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abilityPicker = new mage.client.game.AbilityPicker();
        jSplitPane1 = new javax.swing.JSplitPane();
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
        btnStopWatching = new javax.swing.JButton();
        bigCard = new mage.client.cards.BigCard();
        stack = new mage.client.cards.Cards();
        btnCheat = new javax.swing.JButton();
        pnlReplay = new javax.swing.JPanel();
        btnStopReplay = new javax.swing.JButton();
        btnPreviousPlay = new javax.swing.JButton();
        btnNextPlay = new javax.swing.JButton();
        pnlHand = new javax.swing.JPanel();
        hand = new mage.client.cards.Cards();
        pnlBattlefield = new javax.swing.JPanel();
        chatPanel = new mage.client.chat.ChatPanel();

        jSplitPane1.setDividerLocation(1000);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setResizeWeight(1.0);

        pnlGameInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btnConcede.setText("Concede");
        btnConcede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConcedeActionPerformed(evt);
            }
        });

        btnStopWatching.setText("Stop Watching");
        btnStopWatching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopWatchingActionPerformed(evt);
            }
        });

        btnCheat.setText("?");
        btnCheat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheatActionPerformed(evt);
            }
        });

        btnStopReplay.setText("Stop");
        btnStopReplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopReplayActionPerformed(evt);
            }
        });

        btnPreviousPlay.setText("Previous");
        btnPreviousPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousPlayActionPerformed(evt);
            }
        });

        btnNextPlay.setText("Next");
        btnNextPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlReplayLayout = new javax.swing.GroupLayout(pnlReplay);
        pnlReplay.setLayout(pnlReplayLayout);
        pnlReplayLayout.setHorizontalGroup(
            pnlReplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReplayLayout.createSequentialGroup()
                .addComponent(btnStopReplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPreviousPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextPlay))
        );
        pnlReplayLayout.setVerticalGroup(
            pnlReplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnStopReplay)
                .addComponent(btnPreviousPlay)
                .addComponent(btnNextPlay))
        );

        javax.swing.GroupLayout pnlGameInfoLayout = new javax.swing.GroupLayout(pnlGameInfo);
        pnlGameInfo.setLayout(pnlGameInfoLayout);
        pnlGameInfoLayout.setHorizontalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPriority)
                    .addComponent(lblPhase)
                    .addComponent(lblStep)
                    .addComponent(lblTurn)
                    .addComponent(lblActivePlayer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtActivePlayer, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtPriority, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtTurn, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtStep, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtPhase, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnConcede)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStopWatching)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCheat)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(bigCard, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
            .addComponent(feedbackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
            .addComponent(stack, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnlGameInfoLayout.setVerticalGroup(
            pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGameInfoLayout.createSequentialGroup()
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhase)
                    .addComponent(txtPhase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStep)
                    .addComponent(txtStep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTurn)
                    .addComponent(txtTurn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActivePlayer)
                    .addComponent(txtActivePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPriority)
                    .addComponent(txtPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(feedbackPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConcede)
                    .addComponent(btnStopWatching)
                    .addComponent(btnCheat)))
        );

        pnlHand.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlHandLayout = new javax.swing.GroupLayout(pnlHand);
        pnlHand.setLayout(pnlHandLayout);
        pnlHandLayout.setHorizontalGroup(
            pnlHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
        );
        pnlHandLayout.setVerticalGroup(
            pnlHandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );

        pnlBattlefield.setLayout(new java.awt.GridLayout(0, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBattlefield, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(pnlBattlefield, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnlHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnlGameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel3);
        jSplitPane1.setRightComponent(chatPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	private void btnConcedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcedeActionPerformed
		if (modalQuestion("Are you sure you want to concede?", "Confirm concede") == JOptionPane.YES_OPTION) {
			session.concedeGame(gameId);
		}
	}//GEN-LAST:event_btnConcedeActionPerformed

	private void btnStopWatchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopWatchingActionPerformed
		if (modalQuestion("Are you sure you want to stop watching?", "Stop watching") == JOptionPane.YES_OPTION) {
			session.stopWatching(gameId);
		}
	}//GEN-LAST:event_btnStopWatchingActionPerformed

	private void btnStopReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopReplayActionPerformed
		if (modalQuestion("Are you sure you want to stop replay?", "Stop replay") == JOptionPane.YES_OPTION) {
			session.stopReplay();
		}
	}//GEN-LAST:event_btnStopReplayActionPerformed

	private void btnNextPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPlayActionPerformed
		session.nextPlay();
	}//GEN-LAST:event_btnNextPlayActionPerformed

	private void btnPreviousPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousPlayActionPerformed
		session.previousPlay();
	}//GEN-LAST:event_btnPreviousPlayActionPerformed

	private void btnCheatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheatActionPerformed
		try {
			session.cheat(gameId, DeckCardLists.load("cheat.dck"));
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}//GEN-LAST:event_btnCheatActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.game.AbilityPicker abilityPicker;
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnCheat;
    private javax.swing.JButton btnConcede;
    private javax.swing.JButton btnNextPlay;
    private javax.swing.JButton btnPreviousPlay;
    private javax.swing.JButton btnStopReplay;
    private javax.swing.JButton btnStopWatching;
    private mage.client.chat.ChatPanel chatPanel;
    private mage.client.game.FeedbackPanel feedbackPanel;
    private mage.client.cards.Cards hand;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblActivePlayer;
    private javax.swing.JLabel lblPhase;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblStep;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JPanel pnlBattlefield;
    private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlHand;
    private javax.swing.JPanel pnlReplay;
    private mage.client.cards.Cards stack;
    private javax.swing.JLabel txtActivePlayer;
    private javax.swing.JLabel txtPhase;
    private javax.swing.JLabel txtPriority;
    private javax.swing.JLabel txtStep;
    private javax.swing.JLabel txtTurn;
    // End of variables declaration//GEN-END:variables

}
