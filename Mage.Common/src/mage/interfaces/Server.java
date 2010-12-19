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

package mage.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.interfaces.callback.CallbackServer;
import mage.view.GameTypeView;
import mage.view.TableView;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Server extends Remote, CallbackServer {

	public UUID registerClient(String userName, UUID clientId) throws RemoteException, MageException;
	public void deregisterClient(UUID sessionId) throws RemoteException, MageException;
	public void ack(String message, UUID sessionId) throws RemoteException, MageException;

	public ServerState getServerState() throws RemoteException, MageException;

	//table methods
	public TableView createTable(UUID sessionId, UUID roomId, String gameType, String deckType, List<String> playerTypes, MultiplayerAttackOption attackOption, RangeOfInfluence range) throws RemoteException, MageException;
	public boolean joinTable(UUID sessionId, UUID roomId, UUID tableId, String name, DeckCardLists deckList) throws RemoteException, MageException, GameException;
	public boolean watchTable(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public boolean replayTable(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public void leaveTable(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public void swapSeats(UUID sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws RemoteException, MageException;
	public void removeTable(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public boolean isTableOwner(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public TableView getTable(UUID roomId, UUID tableId) throws RemoteException, MageException;
	public List<TableView> getTables(UUID roomId) throws RemoteException, MageException;

	//chat methods
	public void sendChatMessage(UUID chatId, String userName, String message) throws RemoteException, MageException;
	public void joinChat(UUID chatId, UUID sessionId, String userName) throws RemoteException, MageException;
	public void leaveChat(UUID chatId, UUID sessionId) throws RemoteException, MageException;
	public UUID getTableChatId(UUID tableId) throws RemoteException, MageException;
	public UUID getGameChatId(UUID gameId) throws RemoteException, MageException;
	public UUID getRoomChatId(UUID roomId) throws RemoteException, MageException;

	//room methods
	public UUID getMainRoomId() throws RemoteException, MageException;

	//game methods
	public void startGame(UUID sessionId, UUID roomId, UUID tableId) throws RemoteException, MageException;
	public void joinGame(UUID gameId, UUID sessionId) throws RemoteException, MageException;
	public void watchGame(UUID gameId, UUID sessionId) throws RemoteException, MageException;
	public void stopWatching(UUID gameId, UUID sessionId) throws RemoteException, MageException;
	public void sendPlayerUUID(UUID gameId, UUID sessionId, UUID data) throws RemoteException, MageException;
	public void sendPlayerString(UUID gameId, UUID sessionId, String data) throws RemoteException, MageException;
	public void sendPlayerBoolean(UUID gameId, UUID sessionId, Boolean data) throws RemoteException, MageException;
	public void sendPlayerInteger(UUID gameId, UUID sessionId, Integer data) throws RemoteException, MageException;
	public void concedeGame(UUID gameId, UUID sessionId) throws RemoteException, MageException;

	//replay methods
	public void replayGame(UUID sessionId) throws RemoteException, MageException;
	public void stopReplay(UUID sessionId) throws RemoteException, MageException;
	public void nextPlay(UUID sessionId) throws RemoteException, MageException;
	public void previousPlay(UUID sessionId) throws RemoteException, MageException;

	//test methods
	public void cheat(UUID gameId, UUID sessionId, UUID playerId, DeckCardLists deckList) throws RemoteException, MageException;
    public void cheat(UUID gameId, UUID sessionId, UUID playerId, String cardName) throws RemoteException, MageException;
    public GameView getGameView(UUID gameId, UUID sessionId, UUID playerId) throws RemoteException, MageException;
}
