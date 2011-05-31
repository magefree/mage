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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.MageException;
import mage.cards.decks.InvalidDeckException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackException;
import mage.interfaces.callback.ClientCallback;
import mage.server.game.DeckValidatorFactory;
import mage.server.draft.DraftManager;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.game.PlayerFactory;
import mage.server.game.ReplayManager;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ThreadExecutor;
import mage.utils.MageVersion;
import mage.view.ChatMessage.MessageColor;
import mage.view.DraftPickView;
import mage.view.GameView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ServerImpl extends RemoteServer implements Server {

	private final static Logger logger = Logger.getLogger("Mage Server");
	private static ExecutorService rmiExecutor = ThreadExecutor.getInstance().getRMIExecutor();

	private boolean testMode;
	private String password;

	public ServerImpl(int port, String name, boolean testMode, String password) {
		try {
			System.setSecurityManager(null);
			Registry reg = LocateRegistry.createRegistry(port);
			Server stub = (Server) UnicastRemoteObject.exportObject(this, port);
			reg.rebind(name, stub);
			this.testMode = testMode;
			this.password = password;
			logger.info("Started MAGE server - listening on port " + port);
			if (testMode)
				logger.info("MAGE server running in test mode");
		} catch (ExportException ex) {
			logger.fatal("ERROR:  Unable to start Mage Server - another server is likely running");
		} catch (RemoteException ex) {
			logger.fatal("Failed to start RMI server at port " + port, ex);
		}
	}

	public boolean isTestMode() {
		return testMode;
	}

	@Override
	public ClientCallback callback(UUID sessionId) {
		return SessionManager.getInstance().getSession(sessionId).callback();
	}

	@Override
	public void ack(int messageId, UUID sessionId) throws RemoteException, CallbackException {
		SessionManager.getInstance().getSession(sessionId).ack(messageId);
	}

 	@Override
	public boolean ping(UUID sessionId) {
		Session session = SessionManager.getInstance().getSession(sessionId);
		if (session != null) {
			session.ping();
			return true;
		}
		return false;
	}
	
	@Override
	public UUID registerClient(String userName, UUID clientId, MageVersion version) throws MageException, RemoteException {

		UUID sessionId = null;
		try {
			if (version.compareTo(Main.getVersion()) != 0)
				throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
			sessionId = SessionManager.getInstance().createSession(userName, getClientHost(), clientId);
			logger.info("User " + userName + " connected from " + getClientHost());
		} catch (Exception ex) {
			handleException(ex);
		}
		return sessionId;
		
	}
	
	@Override
	public UUID registerAdmin(String password, MageVersion version) throws RemoteException, MageException {
		UUID sessionId = null;
		try {
			if (version.compareTo(Main.getVersion()) != 0)
				throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
			if (!password.equals(this.password))
				throw new MageException("Wrong password");
			sessionId = SessionManager.getInstance().createSession(getClientHost());
			logger.info("Admin connected from " + getClientHost());
		} catch (Exception ex) {
			handleException(ex);
		}
		return sessionId;
	}

	@Override
	public TableView createTable(UUID sessionId, UUID roomId, MatchOptions options) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(sessionId, options);
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
	public TableView createTournamentTable(UUID sessionId, UUID roomId, TournamentOptions options) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTournamentTable(sessionId, options);
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
	public void removeTable(final UUID sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().removeTable(sessionId, tableId);
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
	public boolean joinTable(UUID sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTable(sessionId, tableId, name, playerType, skill, deckList);
				logger.info("Session " + sessionId + " joined table " + tableId);
				return ret;
			}
		}
		catch (InvalidDeckException ex) {
			throw ex;
		}
		catch (GameException ex) {
			throw ex;
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public boolean joinTournamentTable(UUID sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTournamentTable(sessionId, tableId, name, playerType, skill);
				logger.info("Session " + sessionId + " joined table " + tableId);
				return ret;
			}
		}
		catch (GameException ex) {
			throw ex;
		} 
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public boolean submitDeck(UUID sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				boolean ret = TableManager.getInstance().submitDeck(sessionId, tableId, deckList);
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
	public List<String> getConnectedPlayers(UUID roomId) throws MageException {
		try {
			List<String> players = new ArrayList<String>();
			for (Session session : SessionManager.getInstance().getSessions().values()) {
				players.add(session.getUsername());
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
	public void deregisterClient(final UUID sessionId) throws MageException {
		try {
			rmiExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						Session session = SessionManager.getInstance().getSession(sessionId);
						if (session != null) {
							session.kill();
							logger.info("Client deregistered ...");
						}
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void startMatch(final UUID sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().startMatch(sessionId, roomId, tableId);
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
	public void startChallenge(final UUID sessionId, final UUID roomId, final UUID tableId, final UUID challengeId) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().startChallenge(sessionId, roomId, tableId, challengeId);
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
	public void startTournament(final UUID sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().startTournament(sessionId, roomId, tableId);
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
	public TournamentView getTournament(UUID tournamentId) throws RemoteException, MageException {
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
			rmiExecutor.execute(
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
	public void joinChat(final UUID chatId, final UUID sessionId, final String userName) throws MageException {
		try {
			rmiExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						ChatManager.getInstance().joinChat(chatId, sessionId, userName);
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void leaveChat(final UUID chatId, final UUID sessionId) throws MageException {
		try {
			rmiExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						ChatManager.getInstance().leaveChat(chatId, sessionId);
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
	public boolean isTableOwner(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			return TableManager.getInstance().isTableOwner(tableId, sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public void swapSeats(final UUID sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().swapSeats(tableId, sessionId, seatNum1, seatNum2);
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
	public void leaveTable(final UUID sessionId, final UUID roomId, final UUID tableId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GamesRoomManager.getInstance().getRoom(roomId).leaveTable(sessionId, tableId);
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
	public void joinGame(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().joinGame(gameId, sessionId);
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
	public void joinDraft(final UUID draftId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							DraftManager.getInstance().joinDraft(draftId, sessionId);
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
	public void joinTournament(final UUID tournamentId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TournamentManager.getInstance().joinTournament(tournamentId, sessionId);
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
	public void sendPlayerUUID(final UUID gameId, final UUID sessionId, final UUID data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().sendPlayerUUID(gameId, sessionId, data);
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
	public void sendPlayerString(final UUID gameId, final UUID sessionId, final String data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().sendPlayerString(gameId, sessionId, data);
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
	public void sendPlayerBoolean(final UUID gameId, final UUID sessionId, final Boolean data) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().sendPlayerBoolean(gameId, sessionId, data);
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
	public void sendPlayerInteger(final UUID gameId, final UUID sessionId, final Integer data) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().sendPlayerInteger(gameId, sessionId, data);
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
	public DraftPickView sendCardPick(final UUID draftId, final UUID sessionId, final UUID cardPick) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				return DraftManager.getInstance().sendCardPick(draftId, sessionId, cardPick);
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void concedeGame(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().concedeGame(gameId, sessionId);
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
	public boolean watchTable(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
				return GamesRoomManager.getInstance().getRoom(roomId).watchTable(sessionId, tableId);
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public void watchGame(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().watchGame(gameId, sessionId);
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
	public void stopWatching(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							GameManager.getInstance().stopWatching(gameId, sessionId);
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
	public void replayGame(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							ReplayManager.getInstance().replayGame(gameId, sessionId);
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
	public void startReplay(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							ReplayManager.getInstance().startReplay(gameId, sessionId);
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
	public void stopReplay(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							ReplayManager.getInstance().stopReplay(gameId, sessionId);
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
	public void nextPlay(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							ReplayManager.getInstance().nextPlay(gameId, sessionId);
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
	public void previousPlay(final UUID gameId, final UUID sessionId) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							ReplayManager.getInstance().previousPlay(gameId, sessionId);
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
	public ServerState getServerState() throws RemoteException, MageException {
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
	public void cheat(final UUID gameId, final UUID sessionId, final UUID playerId, final DeckCardLists deckList) throws MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							if (testMode)
								GameManager.getInstance().cheat(gameId, sessionId, playerId, deckList);
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
	public boolean cheat(final UUID gameId, final UUID sessionId, final UUID playerId, final String cardName) throws MageException {
        if (testMode) {
			if (SessionManager.getInstance().isValidSession(sessionId)) {
		        return GameManager.getInstance().cheat(gameId, sessionId, playerId, cardName);
			}
        }
        return false;
 	}

	public void handleException(Exception ex) throws MageException {
		logger.fatal("", ex);
		throw new MageException("Server error: " + ex.getMessage());
	}

	@Override
    public GameView getGameView(final UUID gameId, final UUID sessionId, final UUID playerId) {
 		if (SessionManager.getInstance().isValidSession(sessionId)) {
			return GameManager.getInstance().getGameView(gameId, sessionId, playerId);
		}
		return null;
    }

	@Override
	public List<UserView> getUsers(UUID sessionId) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			return SessionManager.getInstance().getUsers(sessionId);
		}
		return null;
	}

	@Override
	public void disconnectUser(final UUID sessionId, final UUID userSessionId) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
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
	public void removeTable(final UUID sessionId, final UUID tableId) throws RemoteException, MageException {
		if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				rmiExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
							TableManager.getInstance().removeTable(sessionId, tableId);
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		}
	}
}
