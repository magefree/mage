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

package mage.client.remote;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.plugins.impl.Plugins;
import mage.client.util.GameManager;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.util.Logging;
import mage.view.AbilityPickerView;
import mage.view.ChatMessage;
import mage.view.DraftClientMessage;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameView;
import mage.view.TableClientMessage;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Client implements CallbackClient {

	private final static Logger logger = Logging.getLogger(Client.class.getName());

	private UUID clientId;
	private String userName;
	private MageFrame frame;
	private Session session;
	private int messageId = 0;

	public Client(Session session, MageFrame frame, String userName) {

		this.clientId = UUID.randomUUID();
		this.userName = userName;
		this.frame = frame;
		this.session = session;

	}

	@Override
	public synchronized void processCallback(ClientCallback callback) {
		logger.info(callback.getMessageId() + " - " + callback.getMethod());
		try {
			if (callback.getMethod().equals("startGame")) {
				TableClientMessage message = (TableClientMessage) callback.getData();
                GameManager.getInstance().setCurrentPlayerUUID(message.getPlayerId());
				gameStarted(message.getGameId(), message.getPlayerId());
			}
			else if(callback.getMethod().equals("startDraft")) {
				TableClientMessage message = (TableClientMessage) callback.getData();
                draftStarted(message.getGameId(), message.getPlayerId());
			}
			else if (callback.getMethod().equals("replayGame")) {
				replayGame();
			}
			else if (callback.getMethod().equals("watchGame")) {
				watchGame((UUID) callback.getData());
			}
			else if (callback.getMethod().equals("chatMessage")) {
				ChatMessage message = (ChatMessage) callback.getData();
				ChatPanel panel = session.getChats().get(message.getChatId());
				if (panel != null)
					panel.receiveMessage(message.getMessage(), message.getColor());
			}
			else if (callback.getMethod().equals("replayInit")) {
				session.getGame().init((GameView) callback.getData());
			}
			else if (callback.getMethod().equals("replayDone")) {
				session.getGame().modalMessage((String) callback.getData());
				session.getGame().hideGame();
			}
			else if (callback.getMethod().equals("replayUpdate")) {
				session.getGame().updateGame((GameView) callback.getData());
			}
			else if (callback.getMethod().equals("gameInit")) {
				session.getGame().init((GameView) callback.getData());
				session.ack("gameInit");
			}
			else if (callback.getMethod().equals("gameOver")) {
				session.getGame().modalMessage((String) callback.getData());
				session.getGame().hideGame();
			}
			else if (callback.getMethod().equals("gameAsk")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().ask(message.getMessage(), message.getGameView());
			}
			else if (callback.getMethod().equals("gameTarget")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().pickTarget(message.getMessage(), message.getCardsView(), message.getGameView(), message.getTargets(), message.isFlag(), message.getOptions());
			}
			else if (callback.getMethod().equals("gameSelect")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().select(message.getMessage(), message.getGameView());
			}
			else if (callback.getMethod().equals("gameChooseAbility")) {
				session.getGame().pickAbility((AbilityPickerView) callback.getData());
			}
			else if (callback.getMethod().equals("gameChoose")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().getChoice(message.getMessage(), message.getStrings());
			}
			else if (callback.getMethod().equals("gamePlayMana")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().playMana(message.getMessage(), message.getGameView());
			}
			else if (callback.getMethod().equals("gamePlayXMana")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().playXMana(message.getMessage(), message.getGameView());
			}
			else if (callback.getMethod().equals("gameSelectAmount")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().getAmount(message.getMin(), message.getMax(), message.getMessage());
			}
			/*else if (callback.getMethod().equals("gameReveal")) {
				GameClientMessage message = (GameClientMessage) callback.getData();
				session.getGame().revealCards(message.getMessage(), message.getCardsView());
			}*/
			else if (callback.getMethod().equals("gameUpdate")) {
				session.getGame().updateGame((GameView) callback.getData());
			}
			else if (callback.getMethod().equals("gameInform")) {
				if (callback.getMessageId() > messageId) {
					GameClientMessage message = (GameClientMessage) callback.getData();
					session.getGame().inform(message.getMessage(), message.getGameView());
				}
				else {
					logger.warning("message out of sequence - ignoring");
				}
			}
			else if (callback.getMethod().equals("sideboard")) {
				TableClientMessage message = (TableClientMessage) callback.getData();
				sideboard(message.getDeck(), message.getTableId());
			}
			else if (callback.getMethod().equals("construct")) {
				TableClientMessage message = (TableClientMessage) callback.getData();
				construct(message.getDeck(), message.getTableId());
			}
			else if (callback.getMethod().equals("draftPick")) {
				DraftClientMessage message = (DraftClientMessage) callback.getData();
				session.getDraft().loadBooster(message.getDraftPickView());
			}
			else if (callback.getMethod().equals("draftUpdate")) {
				session.getDraft().updateDraft((DraftView) callback.getData());
			}
			else if (callback.getMethod().equals("draftInform")) {
				if (callback.getMessageId() > messageId) {
					DraftClientMessage message = (DraftClientMessage) callback.getData();
				}
				else {
					logger.warning("message out of sequence - ignoring");
				}
			}
			else if (callback.getMethod().equals("draftInit")) {
				session.ack("draftInit");
			}
			messageId = callback.getMessageId();
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	public UUID getId() throws RemoteException {
		return clientId;
	}

	protected void gameStarted(UUID gameId, UUID playerId) {
		try {
			frame.showGame(gameId, playerId);
			logger.info("Game " + gameId + " started for player " + playerId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		
		if (Plugins.getInstance().isCounterPluginLoaded()) {
			Plugins.getInstance().addGamesPlayed();
		}
	}

	protected void draftStarted(UUID draftId, UUID playerId) {
		try {
			frame.showDraft(draftId);
			logger.info("Draft " + draftId + " started for player " + playerId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	protected void watchGame(UUID gameId) {
		try {
			frame.watchGame(gameId);
			logger.info("Watching game " + gameId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	protected void replayGame() {
		try {
			frame.replayGame();
			logger.info("Replaying game");
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	protected void sideboard(Deck deck, UUID tableId) {
		frame.showDeckEditor(DeckEditorMode.Sideboard, deck, tableId);
	}

	protected void construct(Deck deck, UUID tableId) {
		frame.showDeckEditor(DeckEditorMode.Limited, deck, tableId);
	}

	private void handleException(Exception ex) {
		logger.log(Level.SEVERE, "Client error\n", ex);
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Unrecoverable client error.  Disconnecting", "Error", JOptionPane.ERROR_MESSAGE);
		session.disconnect();
		frame.disableButtons();
	}

}
