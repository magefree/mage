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

package mage.server.console.remote;

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
import javax.swing.JOptionPane;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.MageException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.server.console.ConsoleFrame;
import mage.view.DraftPickView;
import mage.view.GameTypeView;
import mage.view.TableView;
import mage.view.TournamentTypeView;
import mage.view.TournamentView;
import mage.view.UserView;
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
	private ConsoleFrame frame;
	private ServerState serverState;
	private ScheduledFuture<?> future;

	public Session(ConsoleFrame frame) {
		this.frame = frame;
	}
	public synchronized boolean connect(String password, String serverName, int port) {
		return connect(password, serverName, port, "", 0);
	}
	
	public synchronized boolean connect(String password, String serverName, int port, String proxyServer, int proxyPort) {
		if (isConnected()) {
			disconnect();
		}
		try {
			System.setSecurityManager(null);
			if (proxyServer.length() > 0) {
				System.setProperty("socksProxyHost", proxyServer);
				System.setProperty("socksProxyPort", Integer.toString(proxyPort));
			}
			else {
				System.clearProperty("socksProxyHost");
				System.clearProperty("socksProxyPort");
			}
			Registry reg = LocateRegistry.getRegistry(serverName, port);
			this.server = (Server) reg.lookup("mage-server");
			sessionId = server.registerAdmin(password, frame.getVersion());
			serverState = server.getServerState();
			future = sessionExecutor.scheduleWithFixedDelay(new ServerPinger(), 5, 5, TimeUnit.SECONDS);
			logger.info("Connected to RMI server at " + serverName + ":" + port);
			frame.setStatusText("Connected to " + serverName + ":" + port + " ");
			frame.enableButtons();
			return true;
		} catch (MageException ex) {
			logger.fatal("", ex);
			disconnect();
			JOptionPane.showMessageDialog(frame, "Unable to connect to server. "  + ex.getMessage());
		} catch (RemoteException ex) {
			logger.fatal("Unable to connect to server - ", ex);
			disconnect();
			JOptionPane.showMessageDialog(frame, "Unable to connect to server. "  + ex.getMessage());
		} catch (NotBoundException ex) {
			logger.fatal("Unable to connect to server - ", ex);
		} catch (Exception ex) {
			logger.fatal("Unable to connect to server - ", ex);
		}
		return false;
	}

	public synchronized void disconnect() {

		if (isConnected()) {
			try {
				server.deregisterClient(sessionId);
			} catch (RemoteException ex) {
				logger.fatal("Error disconnecting ...", ex);
			} catch (MageException ex) {
				logger.fatal("Error disconnecting ...", ex);
			}
			removeServer();
		}
	}

	public void removeServer() {
		if (future != null && !future.isDone())
			future.cancel(true);
		server = null;
		frame.setStatusText("Not connected");
		frame.disableButtons();
		logger.info("Disconnected ... ");
		JOptionPane.showMessageDialog(frame, "Disconnected.", "Disconnected", JOptionPane.INFORMATION_MESSAGE);
	}
	
//	public void ack(int messageId) {
//		try {
//			server.ack(messageId, sessionId);
//		} catch (RemoteException ex) {
//			handleRemoteException(ex);
//		} catch (MageException ex) {
//			handleMageException(ex);
//		}
//	}

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
		return server != null;
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
			server.watchTable(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList) {
		try {
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

	public Collection<TableView> getTables(UUID roomId) throws Exception {
		try {
			return server.getTables(roomId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new Exception();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new Exception();
		}
	}

	public TournamentView getTournament(UUID tournamentId) throws Exception {
		try {
			return server.getTournament(tournamentId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new Exception();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new Exception();
		}
	}

	public UUID getTournamentChatId(UUID tournamentId) {
		try {
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
			server.sendPlayerUUID(gameId, sessionId, data);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerBoolean(UUID gameId, boolean data) {
		try {
			server.sendPlayerBoolean(gameId, sessionId, data);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerInteger(UUID gameId, int data) {
		try {
			server.sendPlayerInteger(gameId, sessionId, data);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendPlayerString(UUID gameId, String data) {
		try {
			server.sendPlayerString(gameId, sessionId, data);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public DraftPickView sendCardPick(UUID draftId, UUID cardId) {
		try {
			return server.sendCardPick(draftId, sessionId, cardId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}

	public boolean leaveChat(UUID chatId) {
		try {
			server.leaveChat(chatId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean sendChatMessage(UUID chatId, String message) {
		try {
			server.sendChatMessage(chatId, "", message);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinGame(UUID gameId) {
		try {
			server.joinGame(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinDraft(UUID draftId) {
		try {
			server.joinDraft(draftId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTournament(UUID tournamentId) {
		try {
			server.joinTournament(tournamentId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean watchGame(UUID gameId) {
		try {
			server.watchGame(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean replayGame(UUID gameId) {
		try {
			server.replayGame(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public TableView createTable(UUID roomId, MatchOptions matchOptions) {
		try {
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
			return server.isTableOwner(sessionId, roomId, tableId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean removeTable(UUID tableId) {
		try {
			server.removeTable(sessionId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) {
		try {
			server.swapSeats(sessionId, roomId, tableId, seatNum1, seatNum2);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean leaveTable(UUID roomId, UUID tableId) {
		try {
			server.leaveTable(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startGame(UUID roomId, UUID tableId) {
		try {
			server.startMatch(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startTournament(UUID roomId, UUID tableId) {
		try {
			server.startTournament(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId) {
		try {
			server.startChallenge(sessionId, roomId, tableId, challengeId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		try {
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
			server.concedeGame(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean stopWatching(UUID gameId) {
		try {
			server.stopWatching(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean startReplay(UUID gameId) {
		try {
			server.startReplay(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean stopReplay(UUID gameId) {
		try {
			server.stopReplay(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean nextPlay(UUID gameId) {
		try {
			server.nextPlay(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean previousPlay(UUID gameId) {
		try {
			server.previousPlay(gameId, sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList) {
		try {
			server.cheat(gameId, sessionId, playerId, deckList);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	} 

	public List<UserView> getUsers() {
		try {
			return server.getUsers(sessionId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return null;
	}
	
	public boolean disconnectUser(UUID userSessionId) {
		try {
			server.disconnectUser(sessionId, userSessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}
	
	private void handleRemoteException(RemoteException ex) {
		logger.fatal("Communication error", ex);
		if (ex instanceof java.rmi.ConnectException) {
			server = null;
			frame.setStatusText("Not connected");
			frame.disableButtons();
			JOptionPane.showMessageDialog(frame, "Communication error - disconnecting.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(frame, "Communication error.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void handleMageException(MageException ex) {
		logger.fatal("Server error", ex);
		JOptionPane.showMessageDialog(frame, "Critical server error.  Disconnecting", "Error", JOptionPane.ERROR_MESSAGE);
		disconnect();
		frame.disableButtons();
	}

	private void handleGameException(GameException ex) {
		logger.fatal("Game error", ex);
		JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}


	public Server getServerRef() {
		return server;
	}

	class ServerPinger implements Runnable {

		private int missed = 0;
		
		@Override
		public void run() {
			if (!ping()) {
				missed++;
				if (missed > 10) {
					logger.info("Connection to server timed out");
					removeServer();
				}
			}
			else {
				missed = 0;
			}
		}

	}

}