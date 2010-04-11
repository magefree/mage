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
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.interfaces.MageException;
import mage.interfaces.Server;
import mage.interfaces.callback.ClientCallback;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.game.PlayerFactory;
import mage.server.game.ReplayManager;
import mage.server.game.TableManager;
import mage.util.Logging;
import mage.view.TableView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ServerImpl extends RemoteServer implements Server {

	private final static Logger logger = Logging.getLogger("Mage Server");

	private boolean testMode;

	public ServerImpl(int port, String name, boolean testMode) {
		try {
			System.setSecurityManager(null);
			Registry reg = LocateRegistry.createRegistry(port);
			Server stub = (Server) UnicastRemoteObject.exportObject(this, port);
			reg.rebind(name, stub);
			this.testMode = testMode;
			logger.info("Started MAGE server - listening on port " + port);
			if (testMode)
				logger.info("MAGE server running in test mode");
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, "Failed to start RMI server at port " + port, ex);
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
	public UUID registerClient(String userName, UUID clientId) throws MageException, RemoteException {

		UUID sessionId = SessionManager.getInstance().createSession(userName, clientId);
		try {
			logger.info("Session " + sessionId + " created for user " + userName + " at " + getClientHost());
		} catch (Exception ex) {
			handleException(ex);
		}
		return sessionId;
		
	}

	@Override
	public TableView createTable(UUID sessionId, UUID roomId, String gameType, String deckType, List<String> playerTypes) throws MageException {
		try {
			TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(sessionId, gameType, deckType, playerTypes);
			logger.info("Table " + table.getTableId() + " created");
			return table;
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void removeTable(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			GamesRoomManager.getInstance().getRoom(roomId).removeTable(sessionId, tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public boolean joinTable(UUID sessionId, UUID roomId, UUID tableId, int seatNum, String name, DeckCardLists deckList) throws MageException, GameException {
		try {
			boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTable(sessionId, tableId, seatNum, name, deckList);
			logger.info("Session " + sessionId + " joined table " + tableId);
			return ret;
		}
		catch (Exception ex) {
			if (ex instanceof GameException)
				throw (GameException)ex;
			handleException(ex);
		}
		return false;
	}

	@Override
	public Collection<TableView> getTables(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getTables();
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
	public void deregisterClient(UUID sessionId) throws MageException {
		try {
			SessionManager.getInstance().getSession(sessionId).kill();
			logger.info("Client deregistered ...");
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void startGame(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			TableManager.getInstance().startGame(sessionId, roomId, tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void sendChatMessage(UUID chatId, String userName, String message) throws MageException {
		try {
			ChatManager.getInstance().broadcast(chatId, userName, message);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void joinChat(UUID chatId, UUID sessionId, String userName) throws MageException {
		try {
			ChatManager.getInstance().joinChat(chatId, sessionId, userName);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void leaveChat(UUID chatId, UUID sessionId) throws MageException {
		try {
			ChatManager.getInstance().leaveChat(chatId, sessionId);
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
	public void leaveTable(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			GamesRoomManager.getInstance().getRoom(roomId).leaveTable(sessionId, tableId);
		}
		catch (Exception ex) {
			handleException(ex);
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
	public void joinGame(UUID gameId, UUID sessionId) throws MageException {
		try {
			GameManager.getInstance().joinGame(gameId, sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
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
	public void sendPlayerUUID(UUID gameId, UUID sessionId, UUID data) throws MageException {
		try {
			GameManager.getInstance().sendPlayerUUID(gameId, sessionId, data);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void sendPlayerString(UUID gameId, UUID sessionId, String data) throws MageException {
		try {
			GameManager.getInstance().sendPlayerString(gameId, sessionId, data);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void sendPlayerBoolean(UUID gameId, UUID sessionId, Boolean data) throws MageException {
		try {
			GameManager.getInstance().sendPlayerBoolean(gameId, sessionId, data);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void sendPlayerInteger(UUID gameId, UUID sessionId, Integer data) throws RemoteException, MageException {
		try {
			GameManager.getInstance().sendPlayerInteger(gameId, sessionId, data);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void concedeGame(UUID gameId, UUID sessionId) throws MageException {
		try {
			GameManager.getInstance().concedeGame(gameId, sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public boolean watchTable(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).watchTable(sessionId, tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public void watchGame(UUID gameId, UUID sessionId) throws MageException {
		try {
			GameManager.getInstance().watchGame(gameId, sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void stopWatching(UUID gameId, UUID sessionId) throws MageException {
		try {
			GameManager.getInstance().stopWatching(gameId, sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void replayGame(UUID sessionId) throws MageException {
		try {
			ReplayManager.getInstance().startReplay(sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void stopReplay(UUID sessionId) throws MageException {
		try {
			ReplayManager.getInstance().stopReplay(sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void nextPlay(UUID sessionId) throws MageException {
		try {
			ReplayManager.getInstance().nextPlay(sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void previousPlay(UUID sessionId) throws MageException {
		try {
			ReplayManager.getInstance().previousPlay(sessionId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public boolean replayTable(UUID sessionId, UUID roomId, UUID tableId) throws MageException {
		try {
			return TableManager.getInstance().replayTable(sessionId, tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public String[] getGameTypes() throws MageException {
		try {
			return GameFactory.getInstance().getGameTypes().toArray(new String[0]);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public String[] getPlayerTypes() throws MageException {
		try {
			return PlayerFactory.getInstance().getPlayerTypes().toArray(new String[0]);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public String[] getDeckTypes() throws MageException {
		try {
			return DeckValidatorFactory.getInstance().getDeckTypes().toArray(new String[0]);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void cheat(UUID gameId, UUID sessionId, DeckCardLists deckList) throws MageException {
		try {
			if (testMode)
				GameManager.getInstance().cheat(gameId, sessionId, deckList);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	public void handleException(Exception ex) throws MageException {
		logger.log(Level.SEVERE, "", ex);
		throw new MageException("Server error");
	}

}
