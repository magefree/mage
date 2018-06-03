
package mage.remote.interfaces;

import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.PlayerType;
import mage.remote.MageRemoteException;
import mage.view.TableView;
import mage.view.TournamentView;

import java.util.Optional;
import java.util.UUID;

/**
 * @author noxx
 */
public interface PlayerActions {

    TableView createTable(UUID roomId, MatchOptions matchOptions);

    TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions);

    boolean removeTable(UUID roomId, UUID tableId);

    boolean removeTable(UUID tableId);

    boolean joinGame(UUID gameId);

    boolean joinDraft(UUID draftId);

    boolean joinTournament(UUID tournamentId);

    boolean leaveTable(UUID roomId, UUID tableId);

    boolean swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean startTournament(UUID roomId, UUID tableId);

    // boolean startChallenge(UUID roomId, UUID tableId, UUID challengeId);

    boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    boolean watchTable(UUID roomId, UUID tableId);

    boolean watchTournamentTable(UUID tableId);

    boolean joinTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    Optional<TableView> getTable(UUID roomId, UUID tableId);

    TournamentView getTournament(UUID tournamentId) throws MageRemoteException;

    boolean isTableOwner(UUID roomId, UUID tableId);
}
