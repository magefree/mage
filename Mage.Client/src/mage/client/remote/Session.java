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
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mage.Constants;
import mage.cards.decks.DeckCardLists;
import mage.client.MageFrame;
import mage.client.util.Config;
import mage.interfaces.ChatClient;
import mage.interfaces.Client;
import mage.interfaces.GameClient;
import mage.interfaces.GameReplayClient;
import mage.interfaces.MageException;
import mage.interfaces.Server;
import mage.util.Logging;
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
	private String[] playerTypes;
	private String[] gameTypes;

	public Session(MageFrame frame) {
		this.frame = frame;
	}

	public void connect(String userName, String serverName, int port) throws RemoteException, NotBoundException {
		if (isConnected()) {
			disconnect();
		}
		this.userName = userName;
		this.client = new ClientImpl(frame, userName);
		System.setSecurityManager(null);
		Registry reg = LocateRegistry.getRegistry(serverName, port);
		this.server = (Server) reg.lookup(Config.remoteServer);
		try {
			sessionId = server.registerClient(client);
			playerTypes = server.getPlayerTypes();
			gameTypes = server.getGameTypes();
			logger.info("Connected to RMI server at " + serverName + ":" + port);
			frame.enableButtons();
		} catch (MageException ex) {
			Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void disconnect() {

		if (isConnected()) {
			try {
				server.deregisterClient(sessionId);
				server = null;
				logger.info("Disconnected ... ");
			} catch (RemoteException ex) {
				logger.log(Level.SEVERE, "Error disconnecting ...", ex);
			} catch (MageException ex) {
				logger.log(Level.SEVERE, "Error disconnecting ...", ex);
			}
		}
	}

	public boolean isConnected() {
		return server != null;
	}

	public String[] getPlayerTypes() {
		return playerTypes;
	}

	public String[] getGameTypes() {
		return gameTypes;
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

	public boolean joinTable(UUID roomId, UUID tableId, int seat, String playerName, DeckCardLists deckList) {
		try {
			return server.joinTable(sessionId, roomId, tableId, seat, playerName, deckList);
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
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

	public boolean joinChat(UUID chatId, ChatClient client) {
		try {
			server.joinChat(chatId, client);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean leaveChat(UUID chatId, UUID clientId) {
		try {
			server.leaveChat(chatId, clientId);
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

	public boolean joinGame(UUID gameId, GameClient gameClient) {
		try {
			server.joinGame(gameId, sessionId, gameClient);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean watchGame(UUID gameId, GameClient gameClient) {
		try {
			server.watchGame(gameId, sessionId, gameClient);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public boolean replayGame(UUID gameId, GameReplayClient replayClient) {
		try {
			server.replayGame(gameId, sessionId, replayClient);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
		} catch (MageException ex) {
			handleMageException(ex);
		}
		return false;
	}

	public TableView createTable(UUID roomId, String gameType, Constants.DeckType deckType, List<String> playerTypes) {
		try {
			return server.createTable(sessionId, roomId, gameType, deckType, playerTypes);
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
			server.startGame(sessionId, roomId, tableId);
			return true;
		} catch (RemoteException ex) {
			handleRemoteException(ex);
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

	public boolean stopWatching(UUID gameId, UUID gameClientId) {
		try {
			server.stopWatching(gameId, gameClientId);
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

	public boolean cheat(UUID gameId, DeckCardLists deckList) {
		try {
			server.cheat(gameId, sessionId, deckList);
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
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Connection to server lost.", "Error", JOptionPane.ERROR_MESSAGE);
		frame.disableButtons();
	}

	private void handleMageException(MageException ex) {
		logger.log(Level.SEVERE, "Server error", ex);
		JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Critical server error.  Disconnecting", "Error", JOptionPane.ERROR_MESSAGE);
		disconnect();
		frame.disableButtons();
	}

	public String getUserName() {
		return userName;
	}

}