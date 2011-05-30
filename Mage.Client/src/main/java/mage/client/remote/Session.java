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

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import mage.cards.decks.DeckCardLists;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.components.MageUI;
import mage.client.draft.DraftPanel;
import mage.client.game.GamePanel;
import mage.remote.method.*;
import mage.client.tournament.TournamentPanel;
import mage.game.GameException;
import mage.MageException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackClientDaemon;
import mage.remote.Connection;
import mage.remote.ServerCache;
import mage.remote.ServerUnavailable;
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

	public enum SessionState {
		DISCONNECTED, CONNECTED, CONNECTING, DISCONNECTING, SERVER_UNAVAILABLE;
	}

	private UUID sessionId;
	private Client client;
	private String userName;
	private MageFrame frame;
	private ServerState serverState;
	private SessionState sessionState = SessionState.DISCONNECTED;
	private Map<UUID, ChatPanel> chats = new HashMap<UUID, ChatPanel>();
	private Map<UUID, GamePanel> games = new HashMap<UUID, GamePanel>();
	private Map<UUID, DraftPanel> drafts = new HashMap<UUID, DraftPanel>();
	private Map<UUID, TournamentPanel> tournaments = new HashMap<UUID, TournamentPanel>();
	private CallbackClientDaemon callbackDaemon;
	private ScheduledFuture<?> future;
	private MageUI ui = new MageUI();
	private Connection connection;

	public Session(MageFrame frame) {
		this.frame = frame;
	}
	
	public synchronized boolean connect(Connection connection) {
		if (this.connection != null && sessionState == SessionState.DISCONNECTED) {
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
			this.userName = connection.getUsername();
			if (client == null)
				client = new Client(this, frame);
			sessionId = registerClient(userName, client.getId(), frame.getVersion());
			callbackDaemon = new CallbackClientDaemon(sessionId, client, connection);
			serverState = getServerState();
			future = sessionExecutor.scheduleWithFixedDelay(new ServerPinger(), 5, 5, TimeUnit.SECONDS);
			logger.info("Connected to RMI server at " + connection.getHost() + ":" + connection.getPort());
			frame.setStatusText("Connected to " + connection.getHost() + ":" + connection.getPort() + " ");
			frame.enableButtons();
			sessionState = SessionState.CONNECTED;
			return true;
		} catch (Exception ex) {
			logger.fatal("", ex);
			if (sessionState == SessionState.CONNECTING) {
				disconnect(false);
				JOptionPane.showMessageDialog(frame, "Unable to connect to server. "  + ex.getMessage());
			}
			sessionState = SessionState.SERVER_UNAVAILABLE;
		}
		return false;
	}
	
	public synchronized void disconnect(boolean voluntary) {
		sessionState = SessionState.DISCONNECTING;
		if (connection == null)
			return;
		if (future != null && !future.isDone())
			future.cancel(true);
		frame.setStatusText("Not connected");
		frame.disableButtons();
		try {
			for (UUID chatId: chats.keySet()) {
				leaveChat(chatId);
			}
		}
		catch (Exception ignore) {
			//swallow all exceptions at this point
		}
		try {
			if (callbackDaemon != null)
				callbackDaemon.stopDaemon();
			deregisterClient();
		} catch (MageException ex) {
			logger.fatal("Error disconnecting ...", ex);
		}
		ServerCache.removeServerFromCache(connection);
		frame.hideGames();
		frame.hideTables();
		logger.info("Disconnected ... ");
		if (!voluntary)
			JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Server error.  You have been disconnected", "Error", JOptionPane.ERROR_MESSAGE);
	}
		
	public boolean ping() {
		Ping method = new Ping(connection, sessionId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("ping error", ex);
		}
		return false;
	}

	private UUID registerClient(String userName, UUID clientId, MageVersion version) throws MageException, ServerUnavailable {
		RegisterClient method = new RegisterClient(connection, userName, clientId, version);
		return method.makeCall();
	}

	private void deregisterClient() throws MageException {
		DeregisterClient method = new DeregisterClient(connection, sessionId);
		try {
			method.makeCall();
		} catch (ServerUnavailable ex) {
			logger.fatal("server unavailable - ", ex);
		}
	}

	private ServerState getServerState() {
		GetServerState method = new GetServerState(connection);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetServerState error", ex);
		}
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

	public ChatPanel getChat(UUID chatId) {
		return chats.get(chatId);
	}

	public GamePanel getGame(UUID gameId) {
		return games.get(gameId);
	}

	public void addGame(UUID gameId, GamePanel gamePanel) {
		games.put(gameId, gamePanel);
	}

	public DraftPanel getDraft(UUID draftId) {
		return drafts.get(draftId);
	}

	public void addDraft(UUID draftId, DraftPanel draftPanel) {
		drafts.put(draftId, draftPanel);
	}

	public void addTournament(UUID tournamentId, TournamentPanel tournament) {
		tournaments.put(tournamentId, tournament);
	}

	public UUID getMainRoomId() {
		GetMainRoomId method = new GetMainRoomId(connection);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetMainRoomId error", ex);
		}
		return null;
	}

	public UUID getRoomChatId(UUID roomId) {
		GetRoomChatId method = new GetRoomChatId(connection, roomId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetRoomChatId error", ex);
		}
		return null;
	}

	public UUID getTableChatId(UUID tableId) {
		GetTableChatId method = new GetTableChatId(connection, tableId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetTableChatId error", ex);
		}
		return null;
	}

	public UUID getGameChatId(UUID gameId) {
		GetGameChatId method = new GetGameChatId(connection, gameId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetGameChatId error", ex);
		}
		return null;
	}

	public TableView getTable(UUID roomId, UUID tableId) {
		GetTable method = new GetTable(connection, roomId, tableId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetTable error", ex);
		}
		return null;
	}

	public boolean watchTable(UUID roomId, UUID tableId) {
		WatchTable method = new WatchTable(connection, sessionId, roomId, tableId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("WatchTable error", ex);
		}
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList) {
		JoinTable method = new JoinTable(connection, sessionId, roomId, tableId, playerName, playerType, skill, deckList);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			logger.fatal("JoinTable error", ex);
		}
		return false;
	}

	public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill) {
		JoinTournamentTable method = new JoinTournamentTable(connection, sessionId, roomId, tableId, playerName, playerType, skill);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			logger.fatal("JoinTournamentTable error", ex);
		}
		return false;
	}

	public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
		GetTables method = new GetTables(connection, roomId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetTables error", ex);
		}
		return null;
	}

	public Collection<String> getConnectedPlayers(UUID roomId) throws MageRemoteException {
		GetConnectedPlayers method = new GetConnectedPlayers(connection, roomId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetConnectedPlayers error", ex);
		}
		return null;
	}

	public TournamentView getTournament(UUID tournamentId) throws MageRemoteException {
		GetTournament method = new GetTournament(connection, tournamentId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetTable error", ex);
		}
		return null;
	}

	public UUID getTournamentChatId(UUID tournamentId) {
		GetTournamentChatId method = new GetTournamentChatId(connection, tournamentId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("GetTournamentChatId error", ex);
		}
		return null;
	}

	public boolean sendPlayerUUID(UUID gameId, UUID data) {
		SendPlayerUUID method = new SendPlayerUUID(connection, sessionId, gameId, data);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendPlayerUUID error", ex);
		}
		return false;
	}

	public boolean sendPlayerBoolean(UUID gameId, boolean data) {
		SendPlayerBoolean method = new SendPlayerBoolean(connection, sessionId, gameId, data);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendPlayerBoolean error", ex);
		}
		return false;
	}

	public boolean sendPlayerInteger(UUID gameId, int data) {
		SendPlayerInteger method = new SendPlayerInteger(connection, sessionId, gameId, data);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendPlayerInteger error", ex);
		}
		return false;
	}

	public boolean sendPlayerString(UUID gameId, String data) {
		SendPlayerString method = new SendPlayerString(connection, sessionId, gameId, data);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendPlayerString error", ex);
		}
		return false;
	}

	public DraftPickView sendCardPick(UUID draftId, UUID cardId) {
		SendCardPick method = new SendCardPick(connection, sessionId, draftId, cardId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendCardPick error", ex);
		}
		return null;
	}

	public boolean joinChat(UUID chatId, ChatPanel chat) {
		JoinChat method = new JoinChat(connection, sessionId, chatId, userName);
		try {
			method.makeCall();
			chats.put(chatId, chat);
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("JoinChat error", ex);
		}
		return false;
	}

	public boolean leaveChat(UUID chatId) {
		LeaveChat method = new LeaveChat(connection, sessionId, chatId);
		try {
			method.makeCall();
			chats.remove(chatId);
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("LeaveChat error", ex);
		}
		return false;
	}

	public boolean sendChatMessage(UUID chatId, String message) {
		SendChatMessage method = new SendChatMessage(connection, chatId, message, userName);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("SendChatMessage error", ex);
		}
		return false;
	}

	public boolean joinGame(UUID gameId) {
		JoinGame method = new JoinGame(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("JoinGame error", ex);
		}
		return false;
	}

	public boolean joinDraft(UUID draftId) {
		JoinDraft method = new JoinDraft(connection, sessionId, draftId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("JoinDraft error", ex);
		}
		return false;
	}

	public boolean joinTournament(UUID tournamentId) {
		JoinTournament method = new JoinTournament(connection, sessionId, tournamentId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("JoinTournament error", ex);
		}
		return false;
	}

	public boolean watchGame(UUID gameId) {
		WatchGame method = new WatchGame(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("WatchGame error", ex);
		}
		return false;
	}

	public boolean replayGame(UUID gameId) {
		ReplayGame method = new ReplayGame(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("ReplayGame error", ex);
		}
		return false;
	}

	public TableView createTable(UUID roomId, MatchOptions matchOptions) {
		CreateTable method = new CreateTable(connection, sessionId, roomId, matchOptions);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("CreateTable error", ex);
		}
		return null;
	}

	public TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions) {
		CreateTournamentTable method = new CreateTournamentTable(connection, sessionId, roomId, tournamentOptions);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("CreateTournamentTable error", ex);
		}
		return null;
	}

	public boolean isTableOwner(UUID roomId, UUID tableId) {
		IsTableOwner method = new IsTableOwner(connection, sessionId, roomId, tableId);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("IsTableOwner error", ex);
		}
		return false;
	}

	public boolean removeTable(UUID roomId, UUID tableId) {
		RemoveTable method = new RemoveTable(connection, sessionId, roomId, tableId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("RemoveTable error", ex);
		}
		return false;
	}

	public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
		SwapSeats method = new SwapSeats(connection, sessionId, roomId, tableId, seatNum1, seatNum2);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("RemoveTable error", ex);
		}
		return false;
	}

	public boolean leaveTable(UUID roomId, UUID tableId) {
		LeaveTable method = new LeaveTable(connection, sessionId, roomId, tableId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("LeaveTable error", ex);
		}
		return false;
	}

	public boolean startGame(UUID roomId, UUID tableId) {
		StartGame method = new StartGame(connection, sessionId, roomId, tableId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StartGame error", ex);
		}
		return false;
	}

	public boolean startTournament(UUID roomId, UUID tableId) {
		StartTournament method = new StartTournament(connection, sessionId, roomId, tableId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StartTournament error", ex);
		}
		return false;
	}

	public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
		StartChallenge method = new StartChallenge(connection, sessionId, roomId, tableId, challengeId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StartChallenge error", ex);
		}
		return false;
	}

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		SubmitDeck method = new SubmitDeck(connection, sessionId, tableId, deck);
		try {
			return method.makeCall();
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			logger.fatal("SubmitDeck error", ex);
		}
		return false;
	}

	public boolean concedeGame(UUID gameId) {
		ConcedeGame method = new ConcedeGame(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("ConcedeGame error", ex);
		}
		return false;
	}

	public boolean stopWatching(UUID gameId) {
		StopWatching method = new StopWatching(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StopWatching error", ex);
		}
		return false;
	}

	public boolean startReplay(UUID gameId) {
		StartReplay method = new StartReplay(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StartReplay error", ex);
		}
		return false;
	}

	public boolean stopReplay(UUID gameId) {
		StopReplay method = new StopReplay(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("StopReplay error", ex);
		}
		return false;
	}

	public boolean nextPlay(UUID gameId) {
		NextPlay method = new NextPlay(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("NextPlay error", ex);
		}
		return false;
	}

	public boolean previousPlay(UUID gameId) {
		PreviousPlay method = new PreviousPlay(connection, sessionId, gameId);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("PreviousPlay error", ex);
		}
		return false;
	}

	public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
		Cheat method = new Cheat(connection, sessionId, gameId, playerId, deckList);
		try {
			method.makeCall();
			return true;
		} catch (ServerUnavailable ex) {
			handleServerUnavailable(ex);
		} catch (MageException ex) {
			logger.fatal("Cheat error", ex);
		}
		return false;
	}

//	private void handleRemoteException(RemoteException ex) {
//		logger.fatal("Communication error", ex);
//		disconnect(false);
//	}

//	private void handleMageException(MageException ex) {
//		logger.fatal("Server error", ex);
//		disconnect(false);
//	}

	private void handleServerUnavailable(ServerUnavailable ex) {
		logger.fatal("server unavailable - ", ex);
		disconnect(false);
	}
	
	private void handleGameException(GameException ex) {
		logger.warn(ex.getMessage());
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}


	public String getUserName() {
		return userName;
	}

	public MageUI getUI() {
		return ui;
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