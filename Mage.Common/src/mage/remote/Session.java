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

package mage.remote;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.MageException;
import mage.constants.Constants.SessionState;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.MageClient;
import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackClientDaemon;
import mage.view.DraftPickView;
import mage.view.GameTypeView;
import mage.view.TableView;
import mage.view.TournamentTypeView;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

	private final static Logger logger = Logger.getLogger(Session.class);
	private static ScheduledExecutorService sessionExecutor = Executors.newScheduledThreadPool(1);

	private UUID sessionId;
	private Server server;
	private MageClient client;
	private String userName;
	private ServerState serverState;
	private SessionState sessionState = SessionState.DISCONNECTED;
	private CallbackClientDaemon callbackDaemon;
	private ScheduledFuture<?> future;
	private Connection connection;

	/**
	 * For locking session object.
	 * Read-write locking is used for better performance, as in most cases session object won't be changed so
	 * there shouldn't be any penalty for synchronization.
	 *
	 * @author nantuko
	*/
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public Session(MageClient client) {
		this.client = client;
	}
	
	public synchronized boolean connect(Connection connection) {
		if (isConnected()) {
			disconnect(true);
		}
		this.connection = connection;
		return connect();
	}

	public boolean connect() {
		sessionState = SessionState.CONNECTING;
		try {
			System.setSecurityManager(null);
			System.setProperty("http.nonProxyHosts", "code.google.com");
			System.setProperty("socksNonProxyHosts", "code.google.com");

			// clear previous values
			System.clearProperty("socksProxyHost");
			System.clearProperty("socksProxyPort");
			System.clearProperty("http.proxyHost");
			System.clearProperty("http.proxyPort");

			switch (connection.getProxyType()) {
				case SOCKS:
					System.setProperty("socksProxyHost", connection.getProxyHost());
					System.setProperty("socksProxyPort", Integer.toString(connection.getProxyPort()));
					break;
				case HTTP:
					System.setProperty("http.proxyHost", connection.getProxyHost());
					System.setProperty("http.proxyPort", Integer.toString(connection.getProxyPort()));
					Authenticator.setDefault(new MageAuthenticator(connection.getProxyUsername(), connection.getProxyPassword()));
					break;
			}
			Registry reg = LocateRegistry.getRegistry(connection.getHost(), connection.getPort());
			this.server = (Server) reg.lookup("mage-server");
			this.userName = connection.getUsername();
			sessionId = server.registerClient(userName, client.getId(), client.getVersion());
			callbackDaemon = new CallbackClientDaemon(sessionId, client, server);
			sessionState = SessionState.CONNECTED;
			serverState = server.getServerState();
			future = sessionExecutor.scheduleWithFixedDelay(new ServerPinger(), 5, 5, TimeUnit.SECONDS);
			logger.info("Connected to RMI server at " + connection.getHost() + ":" + connection.getPort());
			client.connected("Connected to " + connection.getHost() + ":" + connection.getPort() + " ");
			return true;
		} catch (MageException ex) {
			logger.fatal("", ex);
			disconnect(false);
			client.showMessage("Unable to connect to server. "  + ex.getMessage());
		} catch (RemoteException ex) {
			logger.fatal("Unable to connect to server - ", ex);
			disconnect(false);
			client.showMessage("Unable to connect to server. "  + ex.getMessage());
		} catch (NotBoundException ex) {
			logger.fatal("Unable to connect to server - ", ex);
		}
		return false;
	}
	
	public synchronized void disconnect(boolean showMessage) {
		if (sessionState == SessionState.CONNECTED)
			sessionState = SessionState.DISCONNECTING;
		if (future != null && !future.isDone())
			future.cancel(true);
		if (connection == null || server == null)
			return;
		if (sessionState == SessionState.DISCONNECTING) {
			try {
				server.deregisterClient(sessionId);
			} catch (Exception ex) {
					logger.fatal("Error disconnecting ...", ex);
			}
		}
		server = null;
		if (sessionState == SessionState.DISCONNECTING) {
			sessionState = SessionState.DISCONNECTED;
			logger.info("Disconnected ... ");
		}
		client.disconnected();
		if (showMessage)
			client.showError("Server error.  You have been disconnected");
	}

	public boolean ping() {
		try {
			return server.ping(sessionId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}
		
	public boolean isConnected() {
		return sessionState == SessionState.CONNECTED;
	}

	public String[] getPlayerTypes() {
		return serverState.getPlayerTypes();
	}

	public List<GameTypeView> getGameTypes() {
		return serverState.getGameTypes();
	}

	public String[] getDeckTypes() {
		return serverState.getDeckTypes();
	}

	public List<TournamentTypeView> getTournamentTypes() {
		return serverState.getTournamentTypes();
	}

	public boolean isTestMode() {
		if (serverState != null)
			return serverState.isTestMode();
		return false;
	}

	public UUID getMainRoomId() {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getMainRoomId();
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public UUID getRoomChatId(UUID roomId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getRoomChatId(roomId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public UUID getTableChatId(UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getTableChatId(tableId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public UUID getGameChatId(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getGameChatId(gameId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public TableView getTable(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getTable(roomId, tableId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public boolean watchTable(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.watchTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.joinTable(sessionId, roomId, tableId, playerName, playerType, skill, deckList);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.joinTournamentTable(sessionId, roomId, tableId, playerName, playerType, skill);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
		lock.readLock().lock();
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getTables(roomId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new MageRemoteException();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		} finally {
			lock.readLock().unlock();
		}
		return null;
	}

	public Collection<String> getConnectedPlayers(UUID roomId) throws MageRemoteException {
		lock.readLock().lock();
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getConnectedPlayers(roomId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new MageRemoteException();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		} finally {
			lock.readLock().unlock();
		}
		return null;
	}

	public TournamentView getTournament(UUID tournamentId) throws MageRemoteException {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getTournament(tournamentId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new MageRemoteException();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		}
		return null;
	}

	public UUID getTournamentChatId(UUID tournamentId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.getTournamentChatId(tournamentId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public boolean sendPlayerUUID(UUID gameId, UUID data) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.sendPlayerUUID(gameId, sessionId, data);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerBoolean(UUID gameId, boolean data) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.sendPlayerBoolean(gameId, sessionId, data);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerInteger(UUID gameId, int data) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.sendPlayerInteger(gameId, sessionId, data);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerString(UUID gameId, String data) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.sendPlayerString(gameId, sessionId, data);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public DraftPickView sendCardPick(UUID draftId, UUID cardId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.sendCardPick(draftId, sessionId, cardId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public boolean joinChat(UUID chatId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.joinChat(chatId, sessionId, userName);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean leaveChat(UUID chatId) {
		lock.readLock().lock();
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.leaveChat(chatId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} finally {
			lock.readLock().unlock();
		}
		return false;
	}

	public boolean sendChatMessage(UUID chatId, String message) {
		lock.readLock().lock();
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.sendChatMessage(chatId, userName, message);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} finally {
			lock.readLock().unlock();
		}
		return false;
	}

	public boolean joinGame(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.joinGame(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinDraft(UUID draftId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.joinDraft(draftId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTournament(UUID tournamentId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.joinTournament(tournamentId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean watchGame(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.watchGame(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean replayGame(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.replayGame(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public TableView createTable(UUID roomId, MatchOptions matchOptions) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.createTable(sessionId, roomId, matchOptions);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.createTournamentTable(sessionId, roomId, tournamentOptions);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public boolean isTableOwner(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.isTableOwner(sessionId, roomId, tableId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean removeTable(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.removeTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.swapSeats(sessionId, roomId, tableId, seatNum1, seatNum2);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean leaveTable(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.leaveTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startGame(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.startMatch(sessionId, roomId, tableId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startTournament(UUID roomId, UUID tableId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.startTournament(sessionId, roomId, tableId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.startChallenge(sessionId, roomId, tableId, challengeId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		try {
			if (sessionState == SessionState.CONNECTED)
				return server.submitDeck(sessionId, tableId, deck);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean concedeGame(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.concedeGame(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean stopWatching(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.stopWatching(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startReplay(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.startReplay(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean stopReplay(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.stopReplay(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean nextPlay(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.nextPlay(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean previousPlay(UUID gameId) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.previousPlay(gameId, sessionId);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
		try {
			if (sessionState == SessionState.CONNECTED) {
				server.cheat(gameId, sessionId, playerId, deckList);
				return true;
			}
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	private void handleRemoteException(RemoteException ex) {
		logger.fatal("Communication error", ex);
		sessionState = SessionState.SERVER_UNAVAILABLE;
		disconnect(true);
	}

	private void handleMageException(MageException ex) {
		logger.fatal("Server error", ex);
	}

	private void handleGameException(GameException ex) {
		logger.warn(ex.getMessage());
		client.showError(ex.getMessage());
	}


	public String getUserName() {
		return userName;
	}

	class ServerPinger implements Runnable {

		private int missed = 0;
		
		@Override
		public void run() {
			if (!ping()) {
				missed++;
				if (missed > 10) {
					logger.info("Connection to server timed out");
					disconnect(true);
				}
			}
			else {
				missed = 0;
			}
		}

	}
}

class MageAuthenticator extends Authenticator {

	private String username;
	private String password;

	public MageAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication () {
		return new PasswordAuthentication (username, password.toCharArray());
	}
}