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

package mage.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.MageServer;
//import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.remote.MageVersionException;
import mage.server.game.DeckValidatorFactory;
import mage.server.draft.DraftManager;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.game.PlayerFactory;
import mage.server.game.ReplayManager;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.ThreadExecutor;
import mage.utils.CompressUtil;
import mage.utils.MageVersion;
import mage.view.ChatMessage.MessageColor;
import mage.view.DraftPickView;
import mage.view.GameView;
import mage.view.MatchView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MageServerImpl implements MageServer {

	private final static Logger logger = Logger.getLogger("Mage Server");
	private static ExecutorService callExecutor = ThreadExecutor.getInstance().getCallExecutor();

	private String password;
	private boolean testMode;

	public MageServerImpl(String password, boolean testMode) {
		this.password = password;
		this.testMode = testMode;
		ServerMessagesUtil.getInstance().getMessages();
	}

	@Override
	public boolean registerClient(String userName, String sessionId, MageVersion version) throws MageException {

		try {
			if (version.compareTo(Main.getVersion()) != 0)
				throw new MageVersionException(version, Main.getVersion());
			return SessionManager.getInstance().registerUser(sessionId, userName);
		} catch (Exception ex) {
            if (ex instanceof MageVersionException)
                throw (MageVersionException)ex;
			handleException(ex);
		}
		return false;
	}
	
	@Override
	public boolean registerAdmin(String password, String sessionId, MageVersion version) throws MageException {
		try {
			if (version.compareTo(Main.getVersion()) != 0)
				throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
			if (!password.equals(this.password))
				throw new MageException("Wrong password");
			return SessionManager.getInstance().registerAdmin(sessionId);
		} catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public TableView createTable(String sessionId, UUID roomId, MatchOptions options) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(userId, options);
				logger.info("Table " + table.getTableId() + " created");
				return table;
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions options) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTournamentTable(userId, options);
				logger.info("Tournament table " + table.getTableId() + " created");
				return table;
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void removeTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().removeTable(userId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTable(userId, tableId, name, playerType, skill, deckList);
				logger.info("Session " + sessionId + " joined table " + tableId);
				return ret;
			}
		}
		catch (Exception ex) {
			if (ex instanceof GameException)
				throw (GameException)ex;
			handleException(ex);
		}
		return false;
	}

	@Override
	public boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTournamentTable(userId, tableId, name, playerType, skill);
				logger.info("Session " + sessionId + " joined table " + tableId);
				return ret;
			}
		}
		catch (Exception ex) {
			if (ex instanceof GameException)
				throw (GameException)ex;
			handleException(ex);
		}
		return false;
	}

	@Override
	public boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				boolean ret = TableManager.getInstance().submitDeck(userId, tableId, deckList);
				logger.info("Session " + sessionId + " submitted deck");
				return ret;
			}
		}
		catch (Exception ex) {
			if (ex instanceof GameException)
				throw (GameException)ex;
			handleException(ex);
		}
		return false;
	}

	@Override
	public List<TableView> getTables(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getTables();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public List<MatchView> getFinishedMatches(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getFinished();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

    @Override
	public List<String> getConnectedPlayers(UUID roomId) throws MageException {
		try {
			List<String> players = new ArrayList<String>();
			for (User user : UserManager.getInstance().getUsers()) {
				players.add(user.getName());
			}
			return players;
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public TableView getTable(UUID roomId, UUID tableId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getTable(tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void deregisterClient(final String sessionId) throws MageException {
		try {
			callExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						SessionManager.getInstance().disconnect(sessionId, true);
						logger.info("Client deregistered ...");
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void startMatch(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().startMatch(userId, roomId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void startChallenge(final String sessionId, final UUID roomId, final UUID tableId, final UUID challengeId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().startChallenge(userId, roomId, tableId, challengeId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void startTournament(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().startTournament(userId, roomId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public TournamentView getTournament(UUID tournamentId) throws MageException {
		try {
			return TournamentManager.getInstance().getTournamentView(tournamentId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void sendChatMessage(final UUID chatId, final String userName, final String message) throws MageException {
		try {
			callExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						ChatManager.getInstance().broadcast(chatId, userName, message, MessageColor.BLUE);
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void joinChat(final UUID chatId, final String sessionId, final String userName) throws MageException {
		try {
			callExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
						ChatManager.getInstance().joinChat(chatId, userId);
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void leaveChat(final UUID chatId, final String sessionId) throws MageException {
		try {
			callExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
						ChatManager.getInstance().leaveChat(chatId, userId);
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public UUID getMainRoomId() throws MageException {
		try {
			return GamesRoomManager.getInstance().getMainRoomId();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public UUID getRoomChatId(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getChatId();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public boolean isTableOwner(String sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
			return TableManager.getInstance().isTableOwner(tableId, userId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public void swapSeats(final String sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().swapSeats(tableId, userId, seatNum1, seatNum2);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void leaveTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							GamesRoomManager.getInstance().getRoom(roomId).leaveTable(userId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public UUID getTableChatId(UUID tableId) throws MageException {
		try {
			return TableManager.getInstance().getChatId(tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void joinGame(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							GameManager.getInstance().joinGame(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void joinDraft(final UUID draftId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							DraftManager.getInstance().joinDraft(draftId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void joinTournament(final UUID tournamentId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TournamentManager.getInstance().joinTournament(tournamentId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public UUID getGameChatId(UUID gameId) throws MageException {
		try {
			return GameManager.getInstance().getChatId(gameId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public UUID getTournamentChatId(UUID tournamentId) throws MageException {
		try {
			return TournamentManager.getInstance().getChatId(tournamentId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void sendPlayerUUID(final UUID gameId, final String sessionId, final UUID data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							User user = SessionManager.getInstance().getUser(sessionId);
							user.sendPlayerUUID(gameId, data);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void sendPlayerString(final UUID gameId, final String sessionId, final String data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							User user = SessionManager.getInstance().getUser(sessionId);
							user.sendPlayerString(gameId, data);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void sendPlayerBoolean(final UUID gameId, final String sessionId, final Boolean data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							User user = SessionManager.getInstance().getUser(sessionId);
							user.sendPlayerBoolean(gameId, data);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void sendPlayerInteger(final UUID gameId, final String sessionId, final Integer data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							User user = SessionManager.getInstance().getUser(sessionId);
							user.sendPlayerInteger(gameId, data);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public DraftPickView sendCardPick(final UUID draftId, final String sessionId, final UUID cardPick) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				return DraftManager.getInstance().sendCardPick(draftId, userId, cardPick);
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void concedeGame(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							GameManager.getInstance().concedeGame(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public boolean watchTable(String sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				return GamesRoomManager.getInstance().getRoom(roomId).watchTable(userId, tableId);
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public void watchGame(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							GameManager.getInstance().watchGame(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void stopWatching(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							GameManager.getInstance().stopWatching(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void replayGame(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							ReplayManager.getInstance().replayGame(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void startReplay(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							ReplayManager.getInstance().startReplay(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void stopReplay(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							ReplayManager.getInstance().stopReplay(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void nextPlay(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							ReplayManager.getInstance().nextPlay(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public void previousPlay(final UUID gameId, final String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							ReplayManager.getInstance().previousPlay(gameId, userId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public ServerState getServerState() throws MageException {
		try {
			return new ServerState(
					GameFactory.getInstance().getGameTypes(),
					TournamentFactory.getInstance().getTournamentTypes(),
					PlayerFactory.getInstance().getPlayerTypes().toArray(new String[0]),
					DeckValidatorFactory.getInstance().getDeckTypes().toArray(new String[0]),
					testMode,
					Main.getVersion());
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void cheat(final UUID gameId, final String sessionId, final UUID playerId, final DeckCardLists deckList) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							if (testMode) {
								UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
								GameManager.getInstance().cheat(gameId, userId, playerId, deckList);
							}
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

    @Override
	public boolean cheat(final UUID gameId, final String sessionId, final UUID playerId, final String cardName) throws MageException {
        if (testMode) {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
		        return GameManager.getInstance().cheat(gameId, userId, playerId, cardName);
			}
        }
        return false;
 	}

	public void handleException(Exception ex) throws MageException {
		logger.fatal("", ex);
		throw new MageException("Server error: " + ex.getMessage());
	}

	@Override
    public GameView getGameView(final UUID gameId, final String sessionId, final UUID playerId) {
 		if (SessionManager.getInstance().isValidSession(sessionId)) {
			UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
			return GameManager.getInstance().getGameView(gameId, userId, playerId);
		}
		return null;
    }

	@Override
	public List<UserView> getUsers(String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId) && SessionManager.getInstance().isAdmin(sessionId)) {
			List<UserView> users = new ArrayList<UserView>();
			for (User user: UserManager.getInstance().getUsers()) {
				users.add(new UserView(user.getName(), "", user.getSessionId(), user.getConnectionTime()));
			}
			return users;
		}
		return null;
	}

	@Override
	public void disconnectUser(final String sessionId, final String userSessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							SessionManager.getInstance().disconnectUser(sessionId, userSessionId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}
	
	@Override
	public void removeTable(final String sessionId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
							TableManager.getInstance().removeTable(userId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}

	@Override
	public Object getServerMessagesCompressed(String sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				return CompressUtil.compress(ServerMessagesUtil.getInstance().getMessages());
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
		return null;
	}
}
