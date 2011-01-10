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

package mage.server.game;

import java.io.File;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.server.ChatManager;
import mage.server.util.ThreadExecutor;
import mage.util.Logging;
import mage.view.DraftPickView;
import mage.view.ChatMessage.MessageColor;
import mage.view.DraftView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftController {

	private final static Logger logger = Logging.getLogger(GameController.class.getName());
	public static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";

	private ConcurrentHashMap<UUID, DraftSession> draftSessions = new ConcurrentHashMap<UUID, DraftSession>();
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap;
	private UUID draftSessionId;
	private Draft draft;
	private UUID chatId;
	private UUID tableId;

	public DraftController(Draft draft, ConcurrentHashMap<UUID, UUID> sessionPlayerMap, UUID tableId) {
		draftSessionId = UUID.randomUUID();
		this.sessionPlayerMap = sessionPlayerMap;
		chatId = ChatManager.getInstance().createChatSession();
		this.draft = draft;
		this.tableId = tableId;
		init();
	}

	private void init() {
		draft.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					switch (event.getEventType()) {
						case UPDATE:
							updateDraft();
							break;
						case END:
							endDraft();
							break;
					}
				}
			}
		);
		draft.addPlayerQueryEventListener(
			new Listener<PlayerQueryEvent> () {
				@Override
				public void event(PlayerQueryEvent event) {
					switch (event.getQueryType()) {
						case PICK_CARD:
							pickCard(event.getPlayerId(), event.getMax());
							break;
					}
				}
			}
		);
	}

	private UUID getPlayerId(UUID sessionId) {
		return sessionPlayerMap.get(sessionId);
	}

	public void join(UUID sessionId) {
		UUID playerId = sessionPlayerMap.get(sessionId);
		DraftSession draftSession = new DraftSession(sessionId, draft.getId());
		draftSessions.put(playerId, draftSession);
		logger.info("player " + playerId + " has joined draft " + draft.getId());
		ChatManager.getInstance().broadcast(chatId, "", draft.getPlayer(playerId).getPlayer().getName() + " has joined the draft", MessageColor.BLACK);
		if (allJoined()) {
			ThreadExecutor.getInstance().getRMIExecutor().execute(
				new Runnable() {
					@Override
					public void run() {
						startDraft();
					}
			});
		}
	}

	private synchronized void startDraft() {
		for (final Entry<UUID, DraftSession> entry: draftSessions.entrySet()) {
			if (!entry.getValue().init(getDraftView())) {
				logger.severe("Unable to initialize client");
				//TODO: generate client error message
				return;
			}
		}
		draft.start();
	}

	private boolean allJoined() {
		for (DraftPlayer player: draft.getPlayers()) {
			if (player.getPlayer().isHuman() && draftSessions.get(player.getPlayer().getId()) == null) {
				return false;
			}
		}
		return true;
	}

	private void leave(UUID sessionId) {
		draft.leave(getPlayerId(sessionId));
	}

	private void endDraft() {
		TableManager.getInstance().endDraft(tableId);
	}

	public void kill(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			draftSessions.get(sessionPlayerMap.get(sessionId)).setKilled();
			draftSessions.remove(sessionPlayerMap.get(sessionId));
			leave(sessionId);
			sessionPlayerMap.remove(sessionId);
		}
	}

	public void timeout(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			ChatManager.getInstance().broadcast(chatId, "", draft.getPlayer(sessionPlayerMap.get(sessionId)).getPlayer().getName() + " has timed out.  Auto picking.", MessageColor.BLACK);
			draft.autoPick(sessionPlayerMap.get(sessionId));
		}
	}

	public void endDraft(final String message) {
		for (final DraftSession draftSession: draftSessions.values()) {
			draftSession.gameOver(message);
		}
		TableManager.getInstance().endDraft(tableId);
	}

	public UUID getSessionId() {
		return this.draftSessionId;
	}

	public UUID getChatId() {
		return chatId;
	}

	public void sendCardPick(UUID sessionId, UUID cardId) {
		draft.addPick(sessionPlayerMap.get(sessionId), cardId);
	}

	private synchronized void updateDraft() {
		for (final Entry<UUID, DraftSession> entry: draftSessions.entrySet()) {
			entry.getValue().update(getDraftView());
		}
	}

	private synchronized void pickCard(UUID playerId, int timeout) {
		if (draftSessions.containsKey(playerId))
			draftSessions.get(playerId).pickCard(getDraftPickView(playerId), timeout);
	}

	private DraftView getDraftView() {
		return new DraftView(draft);
	}

	private DraftPickView getDraftPickView(UUID playerId) {
		return new DraftPickView(draft.getPlayer(playerId));
	}

}
