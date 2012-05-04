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

import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Action;
import mage.view.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Extracted interface for SessionImpl class.
 *
 * @author noxx
 */
public interface Session {

    boolean connect(Connection connection);

    boolean stopConnecting();

    boolean connect();

    void disconnect(boolean showMessage);

    boolean sendFeedback(String title, String type, String message, String email);

    boolean isConnected();

    boolean ping();

    String[] getPlayerTypes();

    List<GameTypeView> getGameTypes();

    String[] getDeckTypes();

    List<TournamentTypeView> getTournamentTypes();

    boolean isTestMode();

    UUID getMainRoomId();

    UUID getRoomChatId(UUID roomId);

    UUID getTableChatId(UUID tableId);

    UUID getGameChatId(UUID gameId);

    TableView getTable(UUID roomId, UUID tableId);

    boolean watchTable(UUID roomId, UUID tableId);

    boolean joinTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill, DeckCardLists deckList);

    boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, String playerType, int skill);

    Collection<TableView> getTables(UUID roomId) throws MageRemoteException;

    Collection<MatchView> getFinishedMatches(UUID roomId) throws MageRemoteException;

    Collection<String> getConnectedPlayers(UUID roomId) throws MageRemoteException;

    TournamentView getTournament(UUID tournamentId) throws MageRemoteException;

    UUID getTournamentChatId(UUID tournamentId);

    boolean sendPlayerUUID(UUID gameId, UUID data);

    boolean sendPlayerBoolean(UUID gameId, boolean data);

    boolean sendPlayerInteger(UUID gameId, int data);

    boolean sendPlayerString(UUID gameId, String data);

    DraftPickView sendCardPick(UUID draftId, UUID cardId);

    boolean joinChat(UUID chatId);

    boolean leaveChat(UUID chatId);

    boolean sendChatMessage(UUID chatId, String message);

    boolean sendBroadcastMessage(String message);

    boolean joinGame(UUID gameId);

    boolean joinDraft(UUID draftId);

    boolean joinTournament(UUID tournamentId);

    boolean watchGame(UUID gameId);

    boolean replayGame(UUID gameId);

    TableView createTable(UUID roomId, MatchOptions matchOptions);

    TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions);

    boolean isTableOwner(UUID roomId, UUID tableId);

    boolean removeTable(UUID roomId, UUID tableId);

    boolean removeTable(UUID tableId);

    boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean leaveTable(UUID roomId, UUID tableId);

    boolean startGame(UUID roomId, UUID tableId);

    boolean startTournament(UUID roomId, UUID tableId);

    boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId);

    boolean submitDeck(UUID tableId, DeckCardLists deck);

    boolean updateDeck(UUID tableId, DeckCardLists deck);

    boolean concedeGame(UUID gameId);

    boolean stopWatching(UUID gameId);

    boolean startReplay(UUID gameId);

    boolean stopReplay(UUID gameId);

    boolean nextPlay(UUID gameId);

    boolean previousPlay(UUID gameId);

    boolean skipForward(UUID gameId, int moves);

    boolean cheat(UUID gameId, UUID playerId, DeckCardLists deckList);

    List<UserView> getUsers();

    List<String> getServerMessages();

    boolean disconnectUser(String userSessionId);

    String getUserName();

    boolean updateAvatar(int avatarId);

    void setEmbeddedMageServerAction(Action embeddedMageServerAction);
}
