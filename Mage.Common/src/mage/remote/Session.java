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
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.MageException;
import mage.constants.Constants.SessionState;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.MageClient;
import mage.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.interfaces.callback.ClientCallback;
import mage.view.DraftPickView;
import mage.view.GameTypeView;
import mage.view.TableView;
import mage.view.TournamentTypeView;
import mage.view.TournamentView;
import mage.view.UserView;
import org.apache.log4j.Logger;
import org.jboss.remoting.Client;
import org.jboss.remoting.ConnectionListener;
import org.jboss.remoting.ConnectionValidator;
import org.jboss.remoting.InvokerLocator;
import org.jboss.remoting.callback.Callback;
import org.jboss.remoting.callback.HandleCallbackException;
import org.jboss.remoting.callback.InvokerCallbackHandler;
import org.jboss.remoting.transport.bisocket.Bisocket;
import org.jboss.remoting.transport.socket.SocketWrapper;
import org.jboss.remoting.transporter.TransporterClient;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

	private final static Logger logger = Logger.getLogger(Session.class);

	private String sessionId;
	private MageServer server;
	private MageClient client;
	private Client callbackClient;
	private ServerState serverState;
	private SessionState sessionState = SessionState.DISCONNECTED;
	private Connection connection;

	/**
	 * For locking session object.
	 * Read-write locking is used for better performance, as in most cases session object won't be changed so
	 * there shouldn't be any penalty for synchronization.
	 *
	 * @author nantuko
	*/
//	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

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
			InvokerLocator clientLocator = new InvokerLocator(connection.getURI());
			Map<String, String> metadata = new HashMap<String, String>();
			metadata.put(SocketWrapper.WRITE_TIMEOUT, "2000");
			metadata.put("generalizeSocketException", "true");
			server = (MageServer) TransporterClient.createTransporterClient(clientLocator.getLocatorURI(), MageServer.class, metadata);
			
			Map<String, String> clientMetadata = new HashMap<String, String>();
			clientMetadata.put(SocketWrapper.WRITE_TIMEOUT, "2000");
			clientMetadata.put("generalizeSocketException", "true");
			clientMetadata.put(Client.ENABLE_LEASE, "true");
			callbackClient = new Client(clientLocator, "callback", clientMetadata);
			
			Map<String, String> listenerMetadata = new HashMap<String, String>();
			listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_PERIOD, "5000");
			listenerMetadata.put(ConnectionValidator.VALIDATOR_PING_TIMEOUT, "2000");
			callbackClient.connect(new ClientConnectionListener(), listenerMetadata);
			
			Map<String, String> callbackMetadata = new HashMap<String, String>();
			callbackMetadata.put(Bisocket.IS_CALLBACK_SERVER, "true");
			CallbackHandler callbackHandler = new CallbackHandler();
			callbackClient.addListener(callbackHandler, callbackMetadata);
									
			this.sessionId = callbackClient.getSessionId();
			boolean registerResult = false;
			if (connection.getPassword() == null)
				registerResult = server.registerClient(connection.getUsername(), sessionId, client.getVersion());
			else
				registerResult = server.registerAdmin(connection.getPassword(), sessionId, client.getVersion());
			if (registerResult) {
				sessionState = SessionState.CONNECTED;
				serverState = server.getServerState();
				logger.info("Connected to MAGE server at " + connection.getHost() + ":" + connection.getPort());
				client.connected("Connected to " + connection.getHost() + ":" + connection.getPort() + " ");
				return true;
			}
			disconnect(false);
			client.showMessage("Unable to connect to server.");
		} catch (MalformedURLException ex) {
			logger.fatal("", ex);
			client.showMessage("Unable to connect to server. "  + ex.getMessage());
		} catch (Throwable t) {
			logger.fatal("Unable to connect to server - ", t);
			disconnect(false);
			client.showMessage("Unable to connect to server. "  + t.getMessage());
		}
		return false;
	}
	
	public synchronized void disconnect(boolean showMessage) {
		if (isConnected())
			sessionState = SessionState.DISCONNECTING;
		if (connection == null)
			return;
		try {
			callbackClient.disconnect();
			TransporterClient.destroyTransporterClient(server);
		} catch (Throwable ex) {
			logger.fatal("Error disconnecting ...", ex);
		}
		if (sessionState == SessionState.DISCONNECTING || sessionState == SessionState.CONNECTING) {
			sessionState = SessionState.DISCONNECTED;
			logger.info("Disconnected ... ");
		}
		client.disconnected();
		if (showMessage)
			client.showError("Server error.  You have been disconnected");
	}

	class CallbackHandler implements InvokerCallbackHandler {
		@Override
		public void handleCallback(Callback callback) throws HandleCallbackException {
			client.processCallback((ClientCallback)callback.getCallbackObject());
		}
	}

	class ClientConnectionListener implements ConnectionListener {
		@Override
		public void handleConnectionException(Throwable throwable, Client client) {
			logger.info("connection to server lost - " + throwable.getMessage());
			disconnect(true);
		}
	}
	
	public boolean isConnected() {
		if (callbackClient == null)
			return false;
		return callbackClient.isConnected();
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
			if (isConnected())
				return server.getMainRoomId();
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public UUID getRoomChatId(UUID roomId) {
		try {
			if (isConnected())
				return server.getRoomChatId(roomId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public UUID getTableChatId(UUID tableId) {
		try {
			if (isConnected())
				return server.getTableChatId(tableId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public UUID getGameChatId(UUID gameId) {
		try {
			if (isConnected())
				return server.getGameChatId(gameId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public TableView getTable(UUID roomId, UUID tableId) {
		try {
			if (isConnected())
				return server.getTable(roomId, tableId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public boolean watchTable(UUID roomId, UUID tableId) {
		try {
			if (isConnected()) {
				server.watchTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList) {
		try {
			if (isConnected())
				return server.joinTable(sessionId, roomId, tableId, playerName, playerType, skill, deckList);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill) {
		try {
			if (isConnected())
				return server.joinTournamentTable(sessionId, roomId, tableId, playerName, playerType, skill);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
//		lock.readLock().lock();
		try {
			if (isConnected())
				return server.getTables(roomId);
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		} catch (Throwable t) {
			handleThrowable(t);		
//		} finally {
//			lock.readLock().unlock();
		}
		return null;
	}

	public Collection<String> getConnectedPlayers(UUID roomId) throws MageRemoteException {
//		lock.readLock().lock();
		try {
			if (isConnected())
				return server.getConnectedPlayers(roomId);
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		} catch (Throwable t) {
			handleThrowable(t);		
//		} finally {
//			lock.readLock().unlock();
		}
		return null;
	}

	public TournamentView getTournament(UUID tournamentId) throws MageRemoteException {
		try {
			if (isConnected())
				return server.getTournament(tournamentId);
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public UUID getTournamentChatId(UUID tournamentId) {
		try {
			if (isConnected())
				return server.getTournamentChatId(tournamentId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public boolean sendPlayerUUID(UUID gameId, UUID data) {
		try {
			if (isConnected()) {
				server.sendPlayerUUID(gameId, sessionId, data);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean sendPlayerBoolean(UUID gameId, boolean data) {
		try {
			if (isConnected()) {
				server.sendPlayerBoolean(gameId, sessionId, data);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean sendPlayerInteger(UUID gameId, int data) {
		try {
			if (isConnected()) {
				server.sendPlayerInteger(gameId, sessionId, data);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean sendPlayerString(UUID gameId, String data) {
		try {
			if (isConnected()) {
				server.sendPlayerString(gameId, sessionId, data);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public DraftPickView sendCardPick(UUID draftId, UUID cardId) {
		try {
			if (isConnected())
				return server.sendCardPick(draftId, sessionId, cardId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public boolean joinChat(UUID chatId) {
		try {
			if (isConnected()) {
				server.joinChat(chatId, sessionId, connection.getUsername());
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean leaveChat(UUID chatId) {
//		lock.readLock().lock();
		try {
			if (isConnected()) {
				server.leaveChat(chatId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
//		} finally {
//			lock.readLock().unlock();
		}
		return false;
	}

	public boolean sendChatMessage(UUID chatId, String message) {
//		lock.readLock().lock();
		try {
			if (isConnected()) {
				server.sendChatMessage(chatId, connection.getUsername(), message);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
//		} finally {
//			lock.readLock().unlock();
		}
		return false;
	}

	public boolean joinGame(UUID gameId) {
		try {
			if (isConnected()) {
				server.joinGame(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean joinDraft(UUID draftId) {
		try {
			if (isConnected()) {
				server.joinDraft(draftId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean joinTournament(UUID tournamentId) {
		try {
			if (isConnected()) {
				server.joinTournament(tournamentId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean watchGame(UUID gameId) {
		try {
			if (isConnected()) {
				server.watchGame(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean replayGame(UUID gameId) {
		try {
			if (isConnected()) {
				server.replayGame(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public TableView createTable(UUID roomId, MatchOptions matchOptions) {
		try {
			if (isConnected())
				return server.createTable(sessionId, roomId, matchOptions);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions) {
		try {
			if (isConnected())
				return server.createTournamentTable(sessionId, roomId, tournamentOptions);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}

	public boolean isTableOwner(UUID roomId, UUID tableId) {
		try {
			if (isConnected())
				return server.isTableOwner(sessionId, roomId, tableId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean removeTable(UUID roomId, UUID tableId) {
		try {
			if (isConnected()) {
				server.removeTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean removeTable(UUID tableId) {
		try {
			if (isConnected()) {
				server.removeTable(sessionId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
		try {
			if (isConnected()) {
				server.swapSeats(sessionId, roomId, tableId, seatNum1, seatNum2);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean leaveTable(UUID roomId, UUID tableId) {
		try {
			if (isConnected()) {
				server.leaveTable(sessionId, roomId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean startGame(UUID roomId, UUID tableId) {
		try {
			if (isConnected()) {
				server.startMatch(sessionId, roomId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean startTournament(UUID roomId, UUID tableId) {
		try {
			if (isConnected()) {
				server.startTournament(sessionId, roomId, tableId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
		try {
			if (isConnected()) {
				server.startChallenge(sessionId, roomId, tableId, challengeId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		try {
			if (isConnected())
				return server.submitDeck(sessionId, tableId, deck);
		} catch (GameException ex) {
			handleGameException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean concedeGame(UUID gameId) {
		try {
			if (isConnected()) {
				server.concedeGame(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean stopWatching(UUID gameId) {
		try {
			if (isConnected()) {
				server.stopWatching(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean startReplay(UUID gameId) {
		try {
			if (isConnected()) {
				server.startReplay(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean stopReplay(UUID gameId) {
		try {
			if (isConnected()) {
				server.stopReplay(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean nextPlay(UUID gameId) {
		try {
			if (isConnected()) {
				server.nextPlay(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean previousPlay(UUID gameId) {
		try {
			if (isConnected()) {
				server.previousPlay(gameId, sessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
		try {
			if (isConnected()) {
				server.cheat(gameId, sessionId, playerId, deckList);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	public List<UserView> getUsers() {
		try {
			if (isConnected())
				return server.getUsers(sessionId);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return null;
	}
	
	public boolean disconnectUser(String userSessionId) {
		try {
			if (isConnected()) {
				server.disconnectUser(sessionId, userSessionId);
				return true;
			}
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (Throwable t) {
			handleThrowable(t);		
		}
		return false;
	}

	private void handleThrowable(Throwable t) {
		logger.fatal("Communication error", t);
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
		return connection.getUsername();
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