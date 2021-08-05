package mage.server.managers;

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.players.PlayerType;
import mage.server.TableController;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface TableManager {
    Table createTable(UUID roomId, UUID userId, MatchOptions options);

    Table createTable(UUID roomId, MatchOptions options);

    Table createTournamentTable(UUID roomId, UUID userId, TournamentOptions options);

    Table getTable(UUID tableId);

    Optional<Match> getMatch(UUID tableId);

    Collection<Table> getTables();

    Collection<TableController> getControllers();

    Optional<TableController> getController(UUID tableId);

    boolean joinTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException;

    boolean joinTournament(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws GameException;

    boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException;

    void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException;

    // removeUserFromAllTablesAndChat user from all tournament sub tables
    void userQuitTournamentSubTables(UUID userId);

    // removeUserFromAllTablesAndChat user from all sub tables of a tournament
    void userQuitTournamentSubTables(UUID tournamentId, UUID userId);

    boolean isTableOwner(UUID tableId, UUID userId);

    boolean removeTable(UUID userId, UUID tableId);

    void leaveTable(UUID userId, UUID tableId);

    Optional<UUID> getChatId(UUID tableId);

    void startMatch(UUID userId, UUID roomId, UUID tableId);

    void startTournamentSubMatch(UUID roomId, UUID tableId);

    void startTournament(UUID userId, UUID roomId, UUID tableId);

    void startDraft(UUID tableId, Draft draft);

    boolean watchTable(UUID userId, UUID tableId);

    void endGame(UUID tableId);

    void endDraft(UUID tableId, Draft draft);

    void endTournament(UUID tableId, Tournament tournament);

    void swapSeats(UUID tableId, UUID userId, int seatNum1, int seatNum2);

    void construct(UUID tableId);

    void initTournament(UUID tableId);

    void addPlayer(UUID userId, UUID tableId, TournamentPlayer player) throws GameException;

    void removeTable(UUID tableId);

    void debugServerState();
}
