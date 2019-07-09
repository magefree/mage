
package mage.server.tournament;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum TournamentManager {
    instance;
    private final ConcurrentMap<UUID, TournamentController> controllers = new ConcurrentHashMap<>();

    public Optional<TournamentController> getTournamentController(UUID tournamentId) {
        return Optional.ofNullable(controllers.get(tournamentId));
    }

    public void createTournamentSession(Tournament tournament, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        TournamentController tournamentController = new TournamentController(tournament, userPlayerMap, tableId);
        controllers.put(tournament.getId(), tournamentController);
    }

    public void joinTournament(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).join(userId);
    }

    public void quit(UUID tournamentId, UUID userId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            tournamentController.quit(userId);
        } else {
            Logger.getLogger(TournamentManager.class).error("Tournament controller missing  tournamentid: " + tournamentId + " userId: " + userId);
        }
    }

    public void timeout(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).timeout(userId);
    }

    public void submitDeck(UUID tournamentId, UUID playerId, Deck deck) {
        controllers.get(tournamentId).submitDeck(playerId, deck);
    }

    public boolean updateDeck(UUID tournamentId, UUID playerId, Deck deck) {
        return controllers.get(tournamentId).updateDeck(playerId, deck);
    }

    public TournamentView getTournamentView(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            return tournamentController.getTournamentView();
        }
        return null;
    }

    public Optional<UUID> getChatId(UUID tournamentId) {
        if (controllers.containsKey(tournamentId)) {
            return Optional.of(controllers.get(tournamentId).getChatId());
        }
        return Optional.empty();
    }

    public void removeTournament(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            controllers.remove(tournamentId);
            tournamentController.cleanUpOnRemoveTournament();

        }
    }

}
