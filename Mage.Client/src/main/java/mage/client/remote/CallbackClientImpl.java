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

import java.util.UUID;
import javax.swing.SwingUtilities;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.draft.DraftPanel;
import mage.client.game.GamePanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.GameManager;
import mage.client.util.object.SaveObjectUtil;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.utils.CompressUtil;
import mage.view.AbilityPickerView;
import mage.view.ChatMessage;
import mage.view.DraftClientMessage;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameView;
import mage.view.TableClientMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CallbackClientImpl implements CallbackClient {

	private final static Logger logger = Logger.getLogger(CallbackClientImpl.class);

	private UUID clientId;
	private MageFrame frame;
	private int messageId = 0;

	public CallbackClientImpl(MageFrame frame) {

		this.clientId = UUID.randomUUID();
		this.frame = frame;

	}

	@Override
	public synchronized void processCallback(final ClientCallback callback) {
		logger.info(callback.getMessageId() + " - " + callback.getMethod());
		SaveObjectUtil.saveObject(callback.getData(), callback.getMethod());
		callback.setData(CompressUtil.decompress(callback.getData()));
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info(callback.getMessageId() + " -- " + callback.getMethod());
					if (callback.getMethod().equals("startGame")) {
						TableClientMessage message = (TableClientMessage) callback.getData();
						GameManager.getInstance().setCurrentPlayerUUID(message.getPlayerId());
						gameStarted(message.getGameId(), message.getPlayerId());
					}
					else if(callback.getMethod().equals("startTournament")) {
						TableClientMessage message = (TableClientMessage) callback.getData();
						tournamentStarted(message.getGameId(), message.getPlayerId());
					}
					else if(callback.getMethod().equals("startDraft")) {
						TableClientMessage message = (TableClientMessage) callback.getData();
						draftStarted(message.getGameId(), message.getPlayerId());
					}
					else if (callback.getMethod().equals("replayGame")) {
						replayGame(callback.getObjectId());
					}
					else if (callback.getMethod().equals("watchGame")) {
						watchGame((UUID) callback.getObjectId());
					}
					else if (callback.getMethod().equals("chatMessage")) {
						ChatMessage message = (ChatMessage) callback.getData();
						ChatPanel panel = frame.getChat(callback.getObjectId());
						if (panel != null) {
							if (message.isUserMessage() && panel.getConnectedChat() != null) {
								panel.getConnectedChat().receiveMessage(message.getUsername(), message.getMessage(), message.getTime(), ChatMessage.MessageColor.BLACK);
							} else {
								panel.receiveMessage(message.getUsername(), message.getMessage(), message.getTime(), message.getColor());
							}
						}
					}
					else if (callback.getMethod().equals("replayInit")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.init((GameView) callback.getData());
					}
					else if (callback.getMethod().equals("replayDone")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null) {
							panel.modalMessage((String) callback.getData());
							panel.hideGame();
						}
					}
					else if (callback.getMethod().equals("replayUpdate")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.updateGame((GameView) callback.getData());
					}
					else if (callback.getMethod().equals("gameInit")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null) {
							panel.init((GameView) callback.getData());

						}
					}
					else if (callback.getMethod().equals("gameOver")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null) {
							panel.modalMessage((String) callback.getData());
							panel.hideGame();
						}
					}
					else if (callback.getMethod().equals("gameError")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null) {
							panel.modalMessage((String) callback.getData());
						}
					}
					else if (callback.getMethod().equals("gameAsk")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.ask(message.getMessage(), message.getGameView());
					}
					else if (callback.getMethod().equals("gameTarget")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.pickTarget(message.getMessage(), message.getCardsView(), message.getGameView(), message.getTargets(), message.isFlag(), message.getOptions());
					}
					else if (callback.getMethod().equals("gameSelect")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.select(message.getMessage(), message.getGameView());
					}
					else if (callback.getMethod().equals("gameChooseAbility")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.pickAbility((AbilityPickerView) callback.getData());
					}
					else if (callback.getMethod().equals("gameChoose")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.getChoice(message.getMessage(), message.getStrings());
					}
					else if (callback.getMethod().equals("gamePlayMana")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.playMana(message.getMessage(), message.getGameView());
					}
					else if (callback.getMethod().equals("gamePlayXMana")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.playXMana(message.getMessage(), message.getGameView());
					}
					else if (callback.getMethod().equals("gameSelectAmount")) {
						GameClientMessage message = (GameClientMessage) callback.getData();
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.getAmount(message.getMin(), message.getMax(), message.getMessage());
					}
					else if (callback.getMethod().equals("gameUpdate")) {
						GamePanel panel = frame.getGame(callback.getObjectId());
						if (panel != null)
							panel.updateGame((GameView) callback.getData());
					}
					else if (callback.getMethod().equals("gameInform")) {
						
						if (callback.getMessageId() > messageId) {
							GameClientMessage message = (GameClientMessage) callback.getData();
							GamePanel panel = frame.getGame(callback.getObjectId());
							if (panel != null)
								panel.inform(message.getMessage(), message.getGameView());
						}
						else {
							logger.warn("message out of sequence - ignoring");
						}
					}
					else if (callback.getMethod().equals("sideboard")) {
						TableClientMessage message = (TableClientMessage) callback.getData();
						sideboard(message.getDeck(), message.getTableId(), message.getTime());
					}
					else if (callback.getMethod().equals("construct")) {
						TableClientMessage message = (TableClientMessage) callback.getData();
						construct(message.getDeck(), message.getTableId(), message.getTime());
					}
					else if (callback.getMethod().equals("draftOver")) {
						DraftPanel panel = frame.getDraft(callback.getObjectId());
						if (panel != null)
							panel.hideDraft();
					}
					else if (callback.getMethod().equals("draftPick")) {
						DraftClientMessage message = (DraftClientMessage) callback.getData();
						DraftPanel panel = frame.getDraft(callback.getObjectId());
						if (panel != null)
							panel.loadBooster(message.getDraftPickView());
					}
					else if (callback.getMethod().equals("draftUpdate")) {
						DraftPanel panel = frame.getDraft(callback.getObjectId());
						if (panel != null)
							panel.updateDraft((DraftView) callback.getData());
					}
					else if (callback.getMethod().equals("draftInform")) {
						if (callback.getMessageId() > messageId) {
							DraftClientMessage message = (DraftClientMessage) callback.getData();
						}
						else {
							logger.warn("message out of sequence - ignoring");
						}
					}
					else if (callback.getMethod().equals("draftInit")) {

					}
					else if (callback.getMethod().equals("tournamentInit")) {

					}
					messageId = callback.getMessageId();
				}
				catch (Exception ex) {
					handleException(ex);
				}
			}
		});
	}

	public UUID getId() {
		return clientId;
	}

	protected void gameStarted(final UUID gameId, final UUID playerId) {
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

	protected void tournamentStarted(UUID tournamentId, UUID playerId) {
		try {
			frame.showTournament(tournamentId);
			logger.info("Tournament " + tournamentId + " started for player " + playerId);
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

	protected void replayGame(UUID gameId) {
		try {
			frame.replayGame(gameId);
			logger.info("Replaying game");
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	protected void sideboard(Deck deck, UUID tableId, int time) {
		frame.showDeckEditor(DeckEditorMode.Sideboard, deck, tableId, time);
	}

	protected void construct(Deck deck, UUID tableId, int time) {
		frame.showDeckEditor(DeckEditorMode.Limited, deck, tableId, time);
	}

	private void handleException(Exception ex) {
		logger.fatal("Client error\n", ex);
		frame.showError("Error: " + ex.getMessage());
	}

}
