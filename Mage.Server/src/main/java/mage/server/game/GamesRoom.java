

package mage.server.game;

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.PlayerType;
import mage.server.Room;
import mage.view.MatchView;
import mage.view.RoomUsersView;
import mage.view.TableView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface GamesRoom extends Room {

    List<TableView> getTables();
    List<MatchView> getFinished();
    List<RoomUsersView> getRoomUsersInfo();
    boolean joinTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException;
    boolean joinTournamentTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws GameException;
    TableView createTable(UUID userId, MatchOptions options);
    TableView createTournamentTable(UUID userId, TournamentOptions options);
    void removeTable(UUID userId, UUID tableId);
    void removeTable(UUID tableId);
    Optional<TableView> getTable(UUID tableId);
    void leaveTable(UUID userId, UUID tableId);
    boolean watchTable(UUID userId, UUID tableId) throws MageException;

}
