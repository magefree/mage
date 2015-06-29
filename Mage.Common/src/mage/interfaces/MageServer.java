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

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.net.UserData;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.GameView;
import mage.view.MatchView;
import mage.view.RoomUsersView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface MageServer {

    // connection methods
    boolean registerClient(String userName, String sessionId, MageVersion version) throws MageException;

    boolean registerAdmin(String password, String sessionId, MageVersion version) throws MageException;
// Not used
//    void deregisterClient(String sessionId) throws MageException;

    // update methods
    List<ExpansionInfo> getMissingExpansionData(List<String> codes);

    List<CardInfo> getMissingCardsData(List<String> classNames);

    // user methods
    boolean setUserData(String userName, String sessionId, UserData userData) throws MageException;

    void sendFeedbackMessage(String sessionId, String username, String title, String type, String message, String email) throws MageException;

    // server state methods
    ServerState getServerState() throws MageException;

    List<RoomUsersView> getRoomUsers(UUID roomId) throws MageException;

    List<MatchView> getFinishedMatches(UUID roomId) throws MageException;

    Object getServerMessagesCompressed(String sessionId) throws MageException;     // messages of the day

    // ping - extends session
    boolean ping(String sessionId, String pingInfo) throws MageException;

    //table methods
    TableView createTable(String sessionId, UUID roomId, MatchOptions matchOptions) throws MageException;

    TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions tournamentOptions) throws MageException;

    boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    void updateDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    boolean watchTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean watchTournamentTable(String sessionId, UUID tableId) throws MageException;

    boolean leaveTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws MageException;

    void removeTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean isTableOwner(String sessionId, UUID roomId, UUID tableId) throws MageException;

    TableView getTable(UUID roomId, UUID tableId) throws MageException;

    List<TableView> getTables(UUID roomId) throws MageException;

    //chat methods
    void sendChatMessage(UUID chatId, String userName, String message) throws MageException;

    void joinChat(UUID chatId, String sessionId, String userName) throws MageException;

    void leaveChat(UUID chatId, String sessionId) throws MageException;

    UUID getTableChatId(UUID tableId) throws MageException;

    UUID getGameChatId(UUID gameId) throws MageException;

    UUID getRoomChatId(UUID roomId) throws MageException;

    UUID getTournamentChatId(UUID tournamentId) throws MageException;

    //room methods
    UUID getMainRoomId() throws MageException;

    //game methods
    boolean startMatch(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void joinGame(UUID gameId, String sessionId) throws MageException;

    void watchGame(UUID gameId, String sessionId) throws MageException;

    void stopWatching(UUID gameId, String sessionId) throws MageException;

    void sendPlayerUUID(UUID gameId, String sessionId, UUID data) throws MageException;

    void sendPlayerString(UUID gameId, String sessionId, String data) throws MageException;

    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data) throws MageException;

    void sendPlayerInteger(UUID gameId, String sessionId, Integer data) throws MageException;

    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data) throws MageException;

    void quitMatch(UUID gameId, String sessionId) throws MageException;

    GameView getGameView(UUID gameId, String sessionId, UUID playerId) throws MageException;

    // priority, undo, concede, mana pool

    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Object data) throws MageException;

    //tournament methods
    boolean startTournament(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void joinTournament(UUID draftId, String sessionId) throws MageException;

    void quitTournament(UUID tournamentId, String sessionId) throws MageException;

    TournamentView getTournament(UUID tournamentId) throws MageException;

    //draft methods
    void joinDraft(UUID draftId, String sessionId) throws MageException;

    void quitDraft(UUID draftId, String sessionId) throws MageException;

    DraftPickView sendCardPick(UUID draftId, String sessionId, UUID cardId, Set<UUID> hiddenCards) throws MageException;

    void sendCardMark(UUID draftId, String sessionId, UUID cardId) throws MageException;

    //challenge methods
    // void startChallenge(String sessionId, UUID roomId, UUID tableId, UUID challengeId) throws MageException;
    //replay methods
    void replayGame(UUID gameId, String sessionId) throws MageException;

    void startReplay(UUID gameId, String sessionId) throws MageException;

    void stopReplay(UUID gameId, String sessionId) throws MageException;

    void nextPlay(UUID gameId, String sessionId) throws MageException;

    void previousPlay(UUID gameId, String sessionId) throws MageException;

    void skipForward(UUID gameId, String sessionId, int moves) throws MageException;

    //test methods
    void cheat(UUID gameId, String sessionId, UUID playerId, DeckCardLists deckList) throws MageException;

    boolean cheat(UUID gameId, String sessionId, UUID playerId, String cardName) throws MageException;

    //admin methods
    List<UserView> getUsers(String sessionId) throws MageException;

    void disconnectUser(String sessionId, String userSessionId) throws MageException;

    void endUserSession(String sessionId, String userSessionId) throws MageException;

    void removeTable(String sessionId, UUID tableId) throws MageException;

    void sendBroadcastMessage(String sessionId, String message) throws MageException;
}
