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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mage.cards.decks.DeckCardLists;
import mage.client.MageFrame;
import mage.client.chat.ChatPanel;
import mage.client.components.MageUI;
import mage.client.game.GamePanel;
import mage.client.util.Config;
import mage.game.GameException;
import mage.interfaces.MageException;
import mage.game.match.MatchOptions;
import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackClientDaemon;
import mage.util.Logging;
import mage.view.GameTypeView;
import mage.view.TableView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

	private final static Logger logger = Logging.getLogger(Session.class.getName());

	private UUID sessionId;
	private Server server;
	private Client client;
	private String userName;
	private MageFrame frame;
	private ServerState serverState;
	private Map<UUID, ChatPanel> chats = new HashMap<UUID, ChatPanel>();
	private GamePanel game;
	private CallbackClientDaemon callbackDaemon;
	private MageUI ui = new MageUI();

	public Session(MageFrame frame) {
		this.frame = frame;
	}

	public boolean connect(String userName, String serverName, int port) {
		if (isConnected()) {
			disconnect();
		}
		try {
			System.setSecurityManager(null);
			Registry reg = LocateRegistry.getRegistry(serverName, port);
			this.server = (Server) reg.lookup(Config.remoteServer);
			this.userName = userName;
			this.client = new Client(this, frame, userName);
			sessionId = server.registerClient(userName, client.getId());
			callbackDaemon = new CallbackClientDaemon(sessionId, client, server);
			serverState = server.getServerState();
			logger.info("Connected to RMI server at " + serverName + ":" + port);
			frame.setStatusText("Connected to " + serverName + ":" + port + " ");
			frame.enableButtons();
			return true;
		} catch (MageException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, "Unable to connect to server - ", ex);
		} catch (NotBoundException ex) {
			logger.log(Level.SEVERE, "Unable to connect to server - ", ex);
		}
		return false;
	}

	public void disconnect() {

		if (isConnected()) {
			try {
				for (UUID chatId: chats.keySet()) {
					server.leaveChat(chatId, sessionId);
				}
				//TODO: stop daemon
				server.deregisterClient(sessionId);
				server = null;
				logger.info("Disconnected ... ");
			} catch (RemoteException ex) {
				logger.log(Level.SEVERE, "Error disconnecting ...", ex);
			} catch (MageException ex) {
				logger.log(Level.SEVERE, "Error disconnecting ...", ex);
			}
			frame.setStatusText("Not connected ");
			frame.disableButtons();
		}
	}

	public void ack(String message) {
		try {
			server.ack(message, sessionId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
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

	public boolean isTestMode() {
		return serverState.isTestMode();
	}

	public Map<UUID, ChatPanel> getChats() {
		return chats;
	}

	public GamePanel getGame() {
		return game;
	}

	public void setGame(GamePanel gamePanel) {
		game = gamePanel;
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

	public boolean replayTable(UUID roomId, UUID tableId) {
		try {
			server.replayTable(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean joinTable(UUID roomId, UUID tableId, String playerName, DeckCardLists deckList) {
		try {
			return server.joinTable(sessionId, roomId, tableId, playerName, deckList);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (GameException ex) {
			handleGameException(ex);
		}
		return false;
	}

	public Collection<TableView> getTables(UUID roomId) throws MageRemoteException {
		try {
			return server.getTables(roomId);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
			throw new MageRemoteException();
		} catch (MageException ex) {
			handleMageException(ex);
			throw new MageRemoteException();
		}
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

	public boolean joinChat(UUID chatId, ChatPanel chat) {
		try {
			server.joinChat(chatId, sessionId, userName);
			chats.put(chatId, chat);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean leaveChat(UUID chatId) {
		try {
			server.leaveChat(chatId, sessionId);
			chats.remove(chatId);
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
			server.sendChatMessage(chatId, userName, message);
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

	public boolean replayGame() {
		try {
			server.replayGame(sessionId);
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

	public boolean removeTable(UUID roomId, UUID tableId) {
		try {
			server.removeTable(sessionId, roomId, tableId);
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

	public boolean submitDeck(UUID tableId, DeckCardLists deck) {
		try {
			server.submitDeck(sessionId, tableId, deck);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		} catch (GameException ex) {
			handleGameException(ex);
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

	public boolean stopReplay() {
		try {
			server.stopReplay(sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean nextPlay() {
		try {
			server.nextPlay(sessionId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean previousPlay() {
		try {
			server.previousPlay(sessionId);
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

	private void handleRemoteException(RemoteException ex) {
		server = null;
		logger.log(Level.SEVERE, "Connection to server lost", ex);
		frame.setStatusText("Not connected");
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Connection to server lost.", "Error", JOptionPane.ERROR_MESSAGE);
		frame.disableButtons();
	}

	private void handleMageException(MageException ex) {
		logger.log(Level.SEVERE, "Server error", ex);
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Critical server error.  Disconnecting", "Error", JOptionPane.ERROR_MESSAGE);
		disconnect();
		frame.disableButtons();
	}

	private void handleGameException(GameException ex) {
		logger.log(Level.WARNING, "Game error", ex.getMessage());
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}


	public String getUserName() {
		return userName;
	}

	public MageUI getUI() {
		return ui;
	}
}