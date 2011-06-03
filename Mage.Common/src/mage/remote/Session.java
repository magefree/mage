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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import mage.cards.decks.DeckCardLists;
import mage.remote.method.*;
import mage.game.GameException;
import mage.MageException;
import mage.cards.decks.InvalidDeckException;
import mage.constants.Constants.SessionState;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Client;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackClientDaemon;
import mage.utils.MageVersion;
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
	private Client client;
	private String userName;
	private ServerState serverState;
	private SessionState sessionState = SessionState.DISCONNECTED;
	private CallbackClientDaemon callbackDaemon;
	private RMIClientDaemon rmiDaemon;
	private RemoteMethodCallQueue q = new RemoteMethodCallQueue();
	private ScheduledFuture<?> future;
	private Connection connection;

	public Session(Client client) {
		this.client = client;
		rmiDaemon = new RMIClientDaemon(q);
	}
	
	public synchronized boolean connect(Connection connection) {
		cleanupSession();
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
			this.userName = connection.getUsername();
			sessionId = registerClient(userName, client.getId(), client.getVersion());
			callbackDaemon = new CallbackClientDaemon(sessionId, client, connection);
			serverState = getServerState();
			future = sessionExecutor.scheduleWithFixedDelay(new ServerPinger(), 5, 5, TimeUnit.SECONDS);
			logger.info("Connected to RMI server at " + connection.getHost() + ":" + connection.getPort());
			client.connected("Connected to " + connection.getHost() + ":" + connection.getPort() + " ");
			sessionState = SessionState.CONNECTED;
			return true;
		} catch (Exception ex) {
			logger.fatal("", ex);
			sessionState = SessionState.SERVER_UNAVAILABLE;
			disconnect(false);
			client.showMessage("Unable to connect to server. "  + ex.getMessage());
		}
		return false;
	}
	
	public synchronized void disconnect(boolean showMessage) {
		if (sessionState == SessionState.CONNECTED)
			sessionState = SessionState.DISCONNECTING;
		cleanupSession();
		if (connection == null)
			return;
		if (sessionState == SessionState.CONNECTED) {
			try {
				deregisterClient();
			} catch (Exception ex) {
				logger.fatal("Error disconnecting ...", ex);
			}
		}
		ServerCache.removeServerFromCache(connection);
		client.disconnected();
		logger.info("Disconnected ... ");
		if (sessionState == SessionState.SERVER_UNAVAILABLE && showMessage) {
			client.showError("Server error.  You have been disconnected");
		}
		else {
			sessionState = SessionState.DISCONNECTED;
		}
	}

	private void cleanupSession() {
		q.clear();
		if (future != null && !future.isDone())
			future.cancel(true);
		if (callbackDaemon != null)
			callbackDaemon.stopDaemon();
	}

	private boolean handleCall(RemoteMethodCall method) {
		try {
			if (sessionState == method.getAllowedState()) {
				q.callMethod(method);
				return true;
			}
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (InvalidDeckException ex) {
			handleInvalidDeckException(ex);
		} catch (MageException ex) {
			logger.fatal(method.getName() + " error", ex);
		}
		return false;
	}

	public boolean ping() {
		Ping method = new Ping(connection, sessionId);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	private UUID registerClient(String userName, UUID clientId, MageVersion version) throws MageException, ServerUnavailable {
		if (sessionState == SessionState.CONNECTING) {
			RegisterClient method = new RegisterClient(connection, userName, clientId, version);
			return method.makeDirectCall();
		}
		return null;
	}

	private void deregisterClient() throws MageException, ServerUnavailable {
		DeregisterClient method = new DeregisterClient(connection, sessionId);
		method.makeDirectCall();
	}

	private ServerState getServerState() {
		GetServerState method = new GetServerState(connection);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public SessionState getState() {
		return sessionState;
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
		GetMainRoomId method = new GetMainRoomId(connection);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public UUID getRoomChatId(UUID roomId) {
		GetRoomChatId method = new GetRoomChatId(connection, roomId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public UUID getTableChatId(UUID tableId) {
		GetTableChatId method = new GetTableChatId(connection, tableId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public UUID getGameChatId(UUID gameId) {
		GetGameChatId method = new GetGameChatId(connection, gameId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public TableView getTable(UUID roomId, UUID tableId) {
		GetTable method = new GetTable(connection, roomId, tableId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public boolean watchTable(UUID roomId, UUID tableId) {
		WatchTable method = new WatchTable(connection, sessionId, roomId, tableId);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList) {
		JoinTable method = new JoinTable(connection, sessionId, roomId, tableId, playerName, playerType, skill, deckList);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill) {
		JoinTournamentTable method = new JoinTournamentTable(connection, sessionId, roomId, tableId, playerName, playerType, skill);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
		GetTables method = new GetTables(connection, roomId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public Collection<String> getConnectedPlayers(UUID roomId) throws MageRemoteException {
		GetConnectedPlayers method = new GetConnectedPlayers(connection, roomId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public TournamentView getTournament(UUID tournamentId) throws MageRemoteException {
		GetTournament method = new GetTournament(connection, tournamentId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public UUID getTournamentChatId(UUID tournamentId) {
		GetTournamentChatId method = new GetTournamentChatId(connection, tournamentId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public boolean sendPlayerUUID(UUID gameId, UUID data) {
		SendPlayerUUID method = new SendPlayerUUID(connection, sessionId, gameId, data);
		return handleCall(method);
	}

	public boolean sendPlayerBoolean(UUID gameId, boolean data) {
		SendPlayerBoolean method = new SendPlayerBoolean(connection, sessionId, gameId, data);
		return handleCall(method);
	}

	public boolean sendPlayerInteger(UUID gameId, int data) {
		SendPlayerInteger method = new SendPlayerInteger(connection, sessionId, gameId, data);
		return handleCall(method);
	}

	public boolean sendPlayerString(UUID gameId, String data) {
		SendPlayerString method = new SendPlayerString(connection, sessionId, gameId, data);
		return handleCall(method);
	}

	public DraftPickView sendCardPick(UUID draftId, UUID cardId) {
		SendCardPick method = new SendCardPick(connection, sessionId, draftId, cardId);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public boolean joinChat(UUID chatId) {
		JoinChat method = new JoinChat(connection, sessionId, chatId, userName);
		return handleCall(method);
	}

	public boolean leaveChat(UUID chatId) {
		LeaveChat method = new LeaveChat(connection, sessionId, chatId);
		return handleCall(method);
	}

	public boolean sendChatMessage(UUID chatId, String message) {
		SendChatMessage method = new SendChatMessage(connection, chatId, message, userName);
		return handleCall(method);
	}

	public boolean joinGame(UUID gameId) {
		JoinGame method = new JoinGame(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean joinDraft(UUID draftId) {
		JoinDraft method = new JoinDraft(connection, sessionId, draftId);
		return handleCall(method);
	}

	public boolean joinTournament(UUID tournamentId) {
		JoinTournament method = new JoinTournament(connection, sessionId, tournamentId);
		return handleCall(method);
	}

	public boolean watchGame(UUID gameId) {
		WatchGame method = new WatchGame(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean replayGame(UUID gameId) {
		ReplayGame method = new ReplayGame(connection, sessionId, gameId);
		return handleCall(method);
	}

	public TableView createTable(UUID roomId, MatchOptions matchOptions) {
		CreateTable method = new CreateTable(connection, sessionId, roomId, matchOptions);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions) {
		CreateTournamentTable method = new CreateTournamentTable(connection, sessionId, roomId, tournamentOptions);
		if (handleCall(method))
			return method.getReturnVal();
		return null;
	}

	public boolean isTableOwner(UUID roomId, UUID tableId) {
		IsTableOwner method = new IsTableOwner(connection, sessionId, roomId, tableId);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	public boolean removeTable(UUID roomId, UUID tableId) {
		RemoveTable method = new RemoveTable(connection, sessionId, roomId, tableId);
		return handleCall(method);
	}

	public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
		SwapSeats method = new SwapSeats(connection, sessionId, roomId, tableId, seatNum1, seatNum2);
		return handleCall(method);
	}

	public boolean leaveTable(UUID roomId, UUID tableId) {
		LeaveTable method = new LeaveTable(connection, sessionId, roomId, tableId);
		return handleCall(method);
	}

	public boolean startGame(UUID roomId, UUID tableId) {
		StartGame method = new StartGame(connection, sessionId, roomId, tableId);
		return handleCall(method);
	}

	public boolean startTournament(UUID roomId, UUID tableId) {
		StartTournament method = new StartTournament(connection, sessionId, roomId, tableId);
		return handleCall(method);
	}

	public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
		StartChallenge method = new StartChallenge(connection, sessionId, roomId, tableId, challengeId);
		return handleCall(method);
	}

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		SubmitDeck method = new SubmitDeck(connection, sessionId, tableId, deck);
		if (handleCall(method))
			return method.getReturnVal();
		return false;
	}

	public boolean concedeGame(UUID gameId) {
		ConcedeGame method = new ConcedeGame(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean stopWatching(UUID gameId) {
		StopWatching method = new StopWatching(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean startReplay(UUID gameId) {
		StartReplay method = new StartReplay(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean stopReplay(UUID gameId) {
		StopReplay method = new StopReplay(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean nextPlay(UUID gameId) {
		NextPlay method = new NextPlay(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean previousPlay(UUID gameId) {
		PreviousPlay method = new PreviousPlay(connection, sessionId, gameId);
		return handleCall(method);
	}

	public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
		Cheat method = new Cheat(connection, sessionId, gameId, playerId, deckList);
		return handleCall(method);
	}

	private void handleServerUnavailable(ServerUnavailable ex) {
		sessionState = SessionState.SERVER_UNAVAILABLE;
		logger.fatal("server unavailable - ", ex);
		disconnect(true);
	}
	
	private void handleGameException(GameException ex) {
		logger.warn(ex.getMessage());
		client.showError(ex.getMessage());
	}

	private void handleInvalidDeckException(InvalidDeckException ex) {
		StringBuilder sbMessage = new StringBuilder();
		logger.warn(ex.getMessage());
		sbMessage.append(ex.getMessage()).append("\n");
		for (Entry<String, String> entry: ex.getInvalid().entrySet()) {
			sbMessage.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
		}
		client.showError(sbMessage.toString());
	}

	public String getUserName() {
		return userName;
	}

	class ServerPinger implements Runnable {

		@Override
		public void run() {
			ping();
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