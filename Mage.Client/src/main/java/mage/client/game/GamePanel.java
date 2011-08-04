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

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import mage.Constants;
import mage.client.MageFrame;
import mage.client.cards.Cards;
import mage.client.chat.ChatPanel;
import mage.client.dialog.*;
import mage.client.game.FeedbackPanel.FeedbackMode;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.GameManager;
import mage.client.util.PhaseManager;
import mage.remote.Session;
import mage.view.*;
import org.apache.log4j.Logger;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko8
 */
public class GamePanel extends javax.swing.JPanel {

	private final static Logger logger = Logger.getLogger(GamePanel.class);
	static final String YOUR_HAND = "Your hand";

	private Map<UUID, PlayAreaPanel> players = new HashMap<UUID, PlayAreaPanel>();
	private Map<UUID, ExileZoneDialog> exiles = new HashMap<UUID, ExileZoneDialog>();
	private Map<String, ShowCardsDialog> revealed = new HashMap<String, ShowCardsDialog>();
	private Map<String, ShowCardsDialog> lookedAt = new HashMap<String, ShowCardsDialog>();
	private UUID gameId;
	private UUID playerId;
	private Session session;
    private CombatDialog combat;
    private PickNumberDialog pickNumber;
	private JLayeredPane jLayeredPane;
	private String chosenHandKey = "You";

    private static final int HAND_CARD_WIDTH = 75;
	private static final Dimension handCardDimension = new Dimension(HAND_CARD_WIDTH, (int)(HAND_CARD_WIDTH * 3.5f / 2.5f));
    private static final Dimension handCardDimensionBig;

    static {
        double factor = Config.handScalingFactor;
        if (factor < 1) factor = 1;
        if (factor > 2) factor = 2;
        int width = (int)(factor * HAND_CARD_WIDTH);
        handCardDimensionBig = new Dimension(width, (int)(width * 3.5f / 2.5f));
    }

    /** Creates new form GamePanel */
    public GamePanel() {
        initComponents();

        hand.setHScrollSpeed(8);
        hand.setBackgroundColor(new Color(0, 0, 0, 100));
        hand.setVisibleIfEmpty(false);
        combat = new CombatDialog();
        pickNumber = new PickNumberDialog();
        MageFrame.getDesktop().add(combat, JLayeredPane.POPUP_LAYER);
        combat.hideDialog();
        MageFrame.getDesktop().add(pickNumber, JLayeredPane.POPUP_LAYER);

        //FIXME: remove from here
		try {
	        // Override layout (I can't edit generated code)
	        this.setLayout(new BorderLayout());
			final JLayeredPane j = new JLayeredPane();
			j.setSize(1024,768);
			this.add(j);
			j.add(jSplitPane1, JLayeredPane.DEFAULT_LAYER);

			Map<String, JComponent> ui = getUIComponents(j);
			Plugins.getInstance().updateGamePanel(ui);

			// Enlarge jlayeredpane on resize
			addComponentListener(new ComponentAdapter(){
				@Override
				public void componentResized(ComponentEvent e) {
					int width = ((JComponent)e.getSource()).getWidth();
					int height = ((JComponent)e.getSource()).getHeight();
					j.setSize(width, height);
					jSplitPane1.setSize(width, height);
				}
	        });

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    private Map<String, JComponent> getUIComponents(JLayeredPane jLayeredPane) {
    	Map<String, JComponent> components = new HashMap<String, JComponent>();

		components.put("jSplitPane1", jSplitPane1);
		components.put("pnlBattlefield", pnlBattlefield);
		components.put("jPanel3", jPanel3);
		components.put("hand", hand);
		components.put("gameChatPanel", gameChatPanel);
		components.put("userChatPanel", userChatPanel);
		components.put("jLayeredPane", jLayeredPane);
		components.put("gamePanel", this);

		return components;
    }

	public void cleanUp() {
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
		this.btnSwitchHands.setVisible(false);
		this.pnlReplay.setVisible(false);
		this.btnStopWatching.setVisible(false);
		this.gameChatPanel.clear();
		this.gameChatPanel.connect(session.getGameChatId(gameId));
		if (!session.joinGame(gameId))
			hideGame();
	}

	public synchronized void watchGame(UUID gameId) {
		this.gameId = gameId;
		this.playerId = null;
		session = MageFrame.getSession();
		MageFrame.addGame(gameId, this);
		this.feedbackPanel.init(gameId);
		this.feedbackPanel.clear();
		this.btnConcede.setVisible(false);
		this.btnSwitchHands.setVisible(false);
		this.btnStopWatching.setVisible(true);
		this.pnlReplay.setVisible(false);
		this.gameChatPanel.clear();
		this.gameChatPanel.connect(session.getGameChatId(gameId));
		if (!session.watchGame(gameId))
			hideGame();
	}

	public synchronized void replayGame(UUID gameId) {
		this.gameId = gameId;
		this.playerId = null;
		session = MageFrame.getSession();
		MageFrame.addGame(gameId, this);
		this.feedbackPanel.clear();
		this.btnConcede.setVisible(false);
		this.btnSwitchHands.setVisible(false);
		this.btnStopWatching.setVisible(false);
		this.pnlReplay.setVisible(true);
		this.gameChatPanel.clear();
		if (!session.startReplay(gameId))
			hideGame();
	}

	public void hideGame() {
		cleanUp();
		Component c = this.getParent();
		while (c != null && !(c instanceof GamePane)) {
			c = c.getParent();
		}
		if (c != null)
			((GamePane)c).hideFrame();
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
				if (playerId.equals(player.getPlayerId()))
					break;
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
		if (oddNumber)
			c.gridwidth = 2;
		c.gridx = col;
		c.gridy = row;
		this.pnlBattlefield.add(sessionPlayer, c);
		sessionPlayer.setVisible(true);
		if (oddNumber)
			col++;
		int playerNum = playerSeat + 1;
		if (playerNum >= numSeats)
			playerNum = 0;
		while (true) {
			if (row == 1)
				col++;
			else
				col--;
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
			if (playerNum >= numSeats)
				playerNum = 0;
			if (playerNum == playerSeat)
				break;
		}
	}

	public synchronized void updateGame(GameView game) {
		if (playerId == null || game.getHand() == null) {
			this.hand.setVisible(false);
		} else {
			handCards.clear();
			handCards.put(YOUR_HAND, game.getHand());

			// Get opponents hand cards if available
			if (game.getOpponentHands() != null) {
				for (Map.Entry<String, CardsView> hand: game.getOpponentHands().entrySet()) {
					handCards.put(hand.getKey(), hand.getValue());
				}
			}

			if (!handCards.containsKey(chosenHandKey)) {
				chosenHandKey = YOUR_HAND;
			}
			this.hand.loadCards(handCards.get(chosenHandKey), bigCard, gameId);
			hand.setPreferredSize(new java.awt.Dimension((getHandCardDimension().width + 5) * game.getHand().size() + 5, getHandCardDimension().height + 20)); // for scroll

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
		if (game.getPhase() != null)
			this.txtPhase.setText(game.getPhase().toString());
		else
			this.txtPhase.setText("");
		if (game.getStep() != null)
			this.txtStep.setText(game.getStep().toString());
		else
			this.txtStep.setText("");
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

		this.stack.loadCards(game.getStack(), bigCard, gameId);
        GameManager.getInstance().setStackSize(game.getStack().size());

		for (ExileView exile: game.getExile()) {
			if (!exiles.containsKey(exile.getId())) {
				ExileZoneDialog newExile = new ExileZoneDialog();
				exiles.put(exile.getId(), newExile);
				MageFrame.getDesktop().add(newExile, JLayeredPane.POPUP_LAYER);
				newExile.show();
			}
			exiles.get(exile.getId()).loadCards(exile, bigCard, gameId);
		}
		showRevealed(game);
		showLookedAt(game);
		if (game.getCombat().size() > 0) {
			combat.showDialog(game.getCombat());
		} else {
			combat.hideDialog();
		}
		updatePhases(game.getStep());
		this.revalidate();
		this.repaint();
	}

	/**
	 * Update phase buttons\labels.
	 */
	private void updatePhases(Constants.PhaseStep step) {
		if (step == null) {
			logger.warn("step is null");
			return;
		}
		if (prevStep != null && prevBGColor != null) {
			prevStep.setBackground(prevBGColor);
			prevStep.setForeground(DEFAULT_FOREGROUND_COLOR);
		}
		switch (step) {
			case UNTAP: updateButton(untap); break;
			case UPKEEP: updateButton(upkeep); break;
			case DRAW: updateButton(draw); break;
			case PRECOMBAT_MAIN: updateButton(main1); break;
			case BEGIN_COMBAT:
			case DECLARE_ATTACKERS: updateButton(attack); break;
			case DECLARE_BLOCKERS: updateButton(block); break;
			case FIRST_COMBAT_DAMAGE:
			case COMBAT_DAMAGE: updateButton(combatButton); break;
			case POSTCOMBAT_MAIN: updateButton(main2); break;
			case END_TURN: updateButton(endOfTurn); break;
		}
	}

	private void updateButton(JButton button) {
		if (prevBGColor == null) prevBGColor = button.getBackground();
		button.setBackground(new Color(0,0,0,100));
		button.setForeground(Color.white);
		prevStep = button;
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
			lookedAt.get(looked.getName()).loadCards("LookedAt by " + looked.getName(), looked.getCards(), bigCard, Config.dimensions, gameId, false);
		}
	}

	public void ask(String question, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.QUESTION, question, true, false, null);
	}

	public void pickTarget(String message, CardsView cardView, GameView gameView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(required?FeedbackMode.INFORM:FeedbackMode.CANCEL, message, false, gameView.getSpecial(), options);
		if (cardView != null && cardView.size() > 0) {
			showCards(message, cardView, required);
		}
	}

	public void inform(String information, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.INFORM, information, false, gameView.getSpecial(), null);
	}

	public void modalMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "", JOptionPane.INFORMATION_MESSAGE);
	}

	public int modalQuestion(String message, String title) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
	}

	/*public JPanel getHand() {
		return hand;
	}*/

	public void select(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.SELECT, message, false, gameView.getSpecial(), null);
        if (PhaseManager.getInstance().isSkip(gameView, message)) {
            this.feedbackPanel.doClick();
        }
	}

	public void playMana(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.CANCEL, message, false, gameView.getSpecial(), null);
	}

	public void playXMana(String message, GameView gameView) {
		updateGame(gameView);
		this.feedbackPanel.getFeedback(FeedbackMode.CONFIRM, message, false, gameView.getSpecial(), null);
	}

	public void replayMessage(String message) {
		//TODO: implement this
	}

	public void pickAbility(AbilityPickerView choices) {
		this.abilityPicker.show(choices, MageFrame.getDesktop().getMousePosition());
	}

//	public void revealCards(String name, CardsView cards) {
//		ShowCardsDialog showCards = new ShowCardsDialog();
//		showCards.loadCards(name, cards, bigCard, Config.dimensions, gameId, false);
//	}
//
	private void showCards(String title, CardsView cards, boolean required) {
		ShowCardsDialog showCards = new ShowCardsDialog();
		showCards.loadCards(title, cards, bigCard, Config.dimensions, gameId, required);
	}

	public void getAmount(int min, int max, String message) {
		pickNumber.showDialog(min, max, message);
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

	public Map<UUID, PlayAreaPanel> getPlayers() {
		return players;
	}

    /*public javax.swing.JPanel getBattlefield() {
		return pnlBattlefield;
	}*/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // </editor-fold>//GEN-END:initComponents
    private void initComponents() {

        //abilityPicker = new mage.client.game.AbilityPicker();
		abilityPicker = new mage.client.components.ability.AbilityPicker();
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
		btnSwitchHands = new javax.swing.JButton();
        btnStopWatching = new javax.swing.JButton();
        bigCard = new mage.client.cards.BigCard();
        stack = new mage.client.cards.Cards();
        pnlReplay = new javax.swing.JPanel();
        btnStopReplay = new javax.swing.JButton();
        btnPreviousPlay = new javax.swing.JButton();
        btnNextPlay = new javax.swing.JButton();
        pnlBattlefield = new javax.swing.JPanel();
        hand = new mage.client.cards.Cards(true);
        gameChatPanel = new mage.client.chat.ChatPanel();
		gameChatPanel.useExtendedView(ChatPanel.VIEW_MODE.GAME);
		userChatPanel = new mage.client.chat.ChatPanel();
		userChatPanel.setParentChat(gameChatPanel);
		userChatPanel.useExtendedView(ChatPanel.VIEW_MODE.CHAT);
		gameChatPanel.setConnectedChat(userChatPanel);
		gameChatPanel.disableInput();
		jTabbedPane1 = new JTabbedPane();

		hand.setCardDimension(getHandCardDimension());
		handCards = new HashMap<String, CardsView>();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(7);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(26, 48));
		//jSplitPane1.setDividerLocation(Integer.MAX_VALUE);
        //pnlGameInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
		btnConcede.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				btnConcedeActionPerformed(null);
			}
		});

		btnSwitchHands.setText("Switch Hands");
		btnSwitchHands.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				btnSwitchHandActionPerformed(null);
			}
		});
		btnSwitchHands.setBorder(BorderFactory.createLineBorder(Color.red));

        btnStopWatching.setText("Stop Watching");
        btnStopWatching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopWatchingActionPerformed(evt);
            }
        });

        stack.setPreferredSize(new java.awt.Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 25));

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
						/*.addGroup(pnlGameInfoLayout.createSequentialGroup()
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
										.addContainerGap())*/
						.addGroup(pnlGameInfoLayout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(btnConcede)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnSwitchHands)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnStopWatching)
								.addContainerGap(62, Short.MAX_VALUE))
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
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(feedbackPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                /*.addGap(7, 7, 7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                */
				.addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(pnlReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConcede)
					.addComponent(btnSwitchHands)
                    .addComponent(btnStopWatching)))
        );

        pnlBattlefield.setLayout(new java.awt.GridBagLayout());

		jPhases = new JPanel();
		jPhases.setBackground(new Color(0,0,0,100));
		jPhases.setLayout(new GridBagLayout());

		untap = new JButton("Un");
		untap.setToolTipText("Untap");
		upkeep = new JButton("Up");
		upkeep.setToolTipText("Upkeep");
		draw = new JButton("D");
		draw.setToolTipText("Draw");
		main1 = new JButton("M1");
		main1.setToolTipText("Main#1");
		attack = new JButton("A");
		attack.setToolTipText("Attack");
		block = new JButton("B");
		block.setToolTipText("Block");
		combatButton = new JButton("C");
		combatButton.setToolTipText("Combat damage");
		main2 = new JButton("M2");
		main2.setToolTipText("Main#2");
		endOfTurn = new JButton("End");
		endOfTurn.setToolTipText("End Of Turn");

		jPhases.add(untap);
		jPhases.add(upkeep);
		jPhases.add(draw);
		jPhases.add(main1);
		jPhases.add(attack);
		jPhases.add(block);
		jPhases.add(combatButton);
		jPhases.add(main2);
		jPhases.add(endOfTurn);

        //hand.setPreferredSize(new java.awt.Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 20)); // for scroll
		hand.setBorder(emptyBorder);
        hand.setZone(Constants.Zone.HAND.toString());
		HandContainer handContainer = new HandContainer(hand);

		jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
		jTabbedPane1.addTab("Game", gameChatPanel);
		jTabbedPane1.addTab("Chat", userChatPanel);
		jTabbedPane1.setSelectedIndex(1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlGameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                    .addComponent(pnlBattlefield, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
					.addComponent(jPhases, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
				))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(pnlBattlefield, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(handContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jPhases, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnlGameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setMinimumSize(new Dimension(1024, 768));
        jSplitPane1.setLeftComponent(jPanel3);

        gameChatPanel.setMinimumSize(new java.awt.Dimension(100, 48));
        jSplitPane1.setRightComponent(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
        );

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bigCard.setDefaultImage();
			}
		});
    }

	private void btnConcedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcedeActionPerformed
		if (modalQuestion("Are you sure you want to concede?", "Confirm concede") == JOptionPane.YES_OPTION) {
			session.concedeGame(gameId);
		}
	}//GEN-LAST:event_btnConcedeActionPerformed

	private void btnSwitchHandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcedeActionPerformed
		String[] choices = handCards.keySet().toArray(new String[0]);

		String chosenHandKey = (String) JOptionPane.showInputDialog(
                    this,
                    "Choose hand to display:", "Switch between hands",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    choices,
                    YOUR_HAND);

		if (chosenHandKey != null && chosenHandKey.length() > 0) {
			chosenHandKey = chosenHandKey;
			CardsView cards = handCards.get(chosenHandKey);
			this.hand.loadCards(cards, bigCard, gameId);
			hand.setPreferredSize(new java.awt.Dimension((getHandCardDimension().width + 5) * cards.size() + 5, getHandCardDimension().height + 20)); // for scroll
		}
	}

	private void btnStopWatchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopWatchingActionPerformed
		if (modalQuestion("Are you sure you want to stop watching?", "Stop watching") == JOptionPane.YES_OPTION) {
			session.stopWatching(gameId);
			this.hideGame();
		}
	}//GEN-LAST:event_btnStopWatchingActionPerformed

	private void btnStopReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopReplayActionPerformed
		if (modalQuestion("Are you sure you want to stop replay?", "Stop replay") == JOptionPane.YES_OPTION) {
			session.stopReplay(gameId);
		}
	}//GEN-LAST:event_btnStopReplayActionPerformed

	private void btnNextPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPlayActionPerformed
		session.nextPlay(gameId);
	}//GEN-LAST:event_btnNextPlayActionPerformed

	private void btnPreviousPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousPlayActionPerformed
		session.previousPlay(gameId);
	}//GEN-LAST:event_btnPreviousPlayActionPerformed

    private Dimension getHandCardDimension() {
        Preferences pref = MageFrame.getPreferences();
        String useBigCards = pref.get(PreferencesDialog.KEY_HAND_USE_BIG_CARDS, "false");
        if (useBigCards.equals("true")) {
            return handCardDimensionBig;
        }
        return handCardDimension;
    }

	private class HandContainer extends JPanel {

        public HandContainer(Cards hand) {
            super();
            initComponents(hand);
        }

        public void initComponents(Cards hand) {
            jPanel = new JPanel();
            jScrollPane1 = new JScrollPane(jPanel);
            jScrollPane1.getViewport().setBackground(new Color(0,0,0,0));

            jPanel.setLayout(new GridBagLayout()); // centers hand
            jPanel.setBackground(new Color(0,0,0,0));
            jPanel.add(hand);

            HelperPanel helper = new HelperPanel();
            //helper.setBackground(new Color(0, 0, 0, 80));
            //helper.setPreferredSize(new Dimension(0, 35));

            //feedbackPanel.setPreferredSize(new Dimension(0, 70));

			setOpaque(false);
			jPanel.setOpaque(false);
			jScrollPane1.setOpaque(false);

			jPanel.setBorder(emptyBorder);
			jScrollPane1.setBorder(emptyBorder);
			jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			jScrollPane1.getHorizontalScrollBar().setUnitIncrement(8);

            setLayout(new BorderLayout());
            add(jScrollPane1, BorderLayout.CENTER);
            //add(helper, BorderLayout.PAGE_END);
            //add(feedbackPanel, BorderLayout.PAGE_END);
            feedbackPanel.setHelperPanel(helper);
        }

        private JPanel jPanel;
        private javax.swing.JScrollPane jScrollPane1;
    }

	public void setJLayeredPane(JLayeredPane jLayeredPane) {
		this.jLayeredPane = jLayeredPane;
	}

	public void installComponents() {
		jLayeredPane.setOpaque(false);
		jLayeredPane.add(abilityPicker);
		abilityPicker.setVisible(false);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    //private mage.client.game.AbilityPicker abilityPicker;
	private mage.client.components.ability.AbilityPicker abilityPicker;
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnConcede;
	private javax.swing.JButton btnSwitchHands;
    private javax.swing.JButton btnNextPlay;
    private javax.swing.JButton btnPreviousPlay;
    private javax.swing.JButton btnStopReplay;
    private javax.swing.JButton btnStopWatching;
    private mage.client.chat.ChatPanel gameChatPanel;
    private mage.client.game.FeedbackPanel feedbackPanel;
	private mage.client.chat.ChatPanel userChatPanel;
    private mage.client.cards.Cards hand;
	private Map<String, CardsView> handCards;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblActivePlayer;
    private javax.swing.JLabel lblPhase;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblStep;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JPanel pnlBattlefield;
	private javax.swing.JPanel pnlGameInfo;
    private javax.swing.JPanel pnlReplay;
    private mage.client.cards.Cards stack;
    private javax.swing.JLabel txtActivePlayer;
    private javax.swing.JLabel txtPhase;
    private javax.swing.JLabel txtPriority;
    private javax.swing.JLabel txtStep;
    private javax.swing.JLabel txtTurn;
    // End of variables declaration//GEN-END:variables

	private JTabbedPane jTabbedPane1;
	private Border emptyBorder = new EmptyBorder(0,0,0,0);
	private Color prevBGColor;
	private final static Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
	private JPanel jPhases;

	private JButton untap;
	private JButton upkeep;
	private JButton draw;
	private JButton main1;
	private JButton attack;
	private JButton block;
	private JButton combatButton;
	private JButton main2;
	private JButton endOfTurn;
	private JButton prevStep;
}
