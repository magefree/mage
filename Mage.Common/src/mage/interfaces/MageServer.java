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

import mage.game.match.MatchOptions;
import java.util.List;
import java.util.UUID;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.tournament.TournamentOptions;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.TableView;
import mage.view.GameView;
import mage.view.TournamentView;
import mage.view.UserView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface MageServer {

	public boolean registerClient(String userName, String sessionId, MageVersion version) throws MageException;
	public boolean registerAdmin(String password, String sessionId, MageVersion version) throws MageException;
	public void deregisterClient(String sessionId) throws MageException;

	public ServerState getServerState() throws MageException;

	//table methods
	public TableView createTable(String sessionId, UUID roomId, MatchOptions matchOptions) throws MageException;
	public TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions tournamentOptions) throws MageException;
	public boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException, GameException;
	public boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill) throws MageException, GameException;
	public boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;
	public boolean watchTable(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public void leaveTable(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws MageException;
	public void removeTable(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public boolean isTableOwner(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public TableView getTable(UUID roomId, UUID tableId) throws MageException;
	public List<TableView> getTables(UUID roomId) throws MageException;
	public List<String> getConnectedPlayers(UUID roomId) throws MageException;

	//chat methods
	public void sendChatMessage(UUID chatId, String userName, String message) throws MageException;
	public void joinChat(UUID chatId, String sessionId, String userName) throws MageException;
	public void leaveChat(UUID chatId, String sessionId) throws MageException;
	public UUID getTableChatId(UUID tableId) throws MageException;
	public UUID getGameChatId(UUID gameId) throws MageException;
	public UUID getRoomChatId(UUID roomId) throws MageException;
	public UUID getTournamentChatId(UUID tournamentId) throws MageException;

	//room methods
	public UUID getMainRoomId() throws MageException;

	//game methods
	public void startMatch(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public void joinGame(UUID gameId, String sessionId) throws MageException;
	public void watchGame(UUID gameId, String sessionId) throws MageException;
	public void stopWatching(UUID gameId, String sessionId) throws MageException;
	public void sendPlayerUUID(UUID gameId, String sessionId, UUID data) throws MageException;
	public void sendPlayerString(UUID gameId, String sessionId, String data) throws MageException;
	public void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data) throws MageException;
	public void sendPlayerInteger(UUID gameId, String sessionId, Integer data) throws MageException;
	public void concedeGame(UUID gameId, String sessionId) throws MageException;
    

	//tournament methods
	public void startTournament(String sessionId, UUID roomId, UUID tableId) throws MageException;
	public void joinTournament(UUID draftId, String sessionId) throws MageException;
	public TournamentView getTournament(UUID tournamentId) throws MageException;

	//draft methods
	public void joinDraft(UUID draftId, String sessionId) throws MageException;
	public DraftPickView sendCardPick(UUID draftId, String sessionId, UUID cardId) throws MageException;

	//challenge methods
	public void startChallenge(String sessionId, UUID roomId, UUID tableId, UUID challengeId) throws MageException;

	//replay methods
	public void replayGame(UUID gameId, String sessionId) throws MageException;
	public void startReplay(UUID gameId, String sessionId) throws MageException;
	public void stopReplay(UUID gameId, String sessionId) throws MageException;
	public void nextPlay(UUID gameId, String sessionId) throws MageException;
	public void previousPlay(UUID gameId, String sessionId) throws MageException;

	//test methods
	public void cheat(UUID gameId, String sessionId, UUID playerId, DeckCardLists deckList) throws MageException;
    public boolean cheat(UUID gameId, String sessionId, UUID playerId, String cardName) throws MageException;
    public GameView getGameView(UUID gameId, String sessionId, UUID playerId) throws MageException;

	//admin methods
	public List<UserView> getUsers(String sessionId) throws MageException;
	public void disconnectUser(String sessionId, String userSessionId) throws MageException;
	public void removeTable(String sessionId, UUID tableId) throws MageException;

	// messages of the day
	public Object getServerMessagesCompressed(String sessionId) throws MageException;
}
