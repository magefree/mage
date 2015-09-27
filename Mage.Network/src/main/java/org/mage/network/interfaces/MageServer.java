package org.mage.network.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.ServerState;
import mage.players.net.UserData;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;

/**
 *
 * @author BetaSteward
 */
public interface MageServer {

    boolean registerClient(Connection connection, String sessionId, MageVersion version, String host);

    void disconnect(String sessionId, DisconnectReason reason);

    void setPreferences(String sessionId, UserData userData);

    void sendFeedbackMessage(String sessionId, String title, String type, String message, String email);

    void receiveChatMessage(UUID chatId, String sessionId, String message);

    void joinChat(UUID chatId, String sessionId);

    void leaveChat(UUID chatId, String sessionId);

    UUID getRoomChatId(UUID roomId);

    void receiveBroadcastMessage(String title, String message, String sessionId);

    ServerState getServerState();

    List<String> getServerMessages();

    List<String> getCards();

    List<CardInfo> getMissingCardData(List<String> cards);

    List<ExpansionInfo> getMissingExpansionData(List<String> setCodes);

    RoomView getRoom(UUID roomId);

    TableView createTable(String sessionId, UUID roomId, MatchOptions options);

    boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password);

    TableView getTable(UUID roomId, UUID tableId);

    boolean leaveTable(String sessionId, UUID roomId, UUID tableId);

    void removeTable(String sessionId, UUID roomId, UUID tableId);

    void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password);

    boolean startMatch(String sessionId, UUID roomId, UUID tableId);

    void quitMatch(UUID gameId, String sessionId);

    UUID joinGame(UUID gameId, String sessionId);

    void sendPlayerUUID(UUID gameId, String sessionId, UUID data);

    void sendPlayerString(UUID gameId, String sessionId, String data);

    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data);

    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data);

    void sendPlayerInteger(UUID gameId, String sessionId, Integer data);

    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Serializable data);

    boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList);

    void updateDeck(String sessionId, UUID tableId, DeckCardLists deckList);

    boolean joinDraft(UUID draftId, String sessionId);

    void quitDraft(UUID draftId, String sessionId);

    void markCard(UUID draftId, String sessionId, UUID cardPick);

    DraftPickView pickCard(UUID draftId, String sessionId, UUID cardPick, Set<UUID> hiddenCards);

    TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions options);

    boolean startTournament(String sessionId, UUID roomId, UUID tableId);

    UUID getTournamentChatId(UUID tournamentId);

    TournamentView getTournament(UUID tournamentId);

    boolean joinTournament(UUID tournamentId, String sessionId);

    void quitTournament(UUID tournamentId, String sessionId);

    void watchTable(String sessionId, UUID roomId, UUID tableId);

    void watchTournamentTable(String sessionId, UUID tableId);

    void stopWatching(UUID gameId, String sessionId);

    void pingTime(long milliSeconds, String sessionId);

    void cheat(UUID gameId, String sessionId, UUID playerId, DeckCardLists deckList);

}
