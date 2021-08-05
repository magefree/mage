package mage.server.tournament;

import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.server.managers.TournamentManager;
import mage.server.managers.ManagerFactory;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentManagerImpl implements TournamentManager {

    private final ManagerFactory managerFactory;
    private final ConcurrentMap<UUID, TournamentController> controllers = new ConcurrentHashMap<>();

    public TournamentManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public Optional<TournamentController> getTournamentController(UUID tournamentId) {
        return Optional.ofNullable(controllers.get(tournamentId));
    }

    @Override
    public void createTournamentSession(Tournament tournament, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        TournamentController tournamentController = new TournamentController(managerFactory, tournament, userPlayerMap, tableId);
        controllers.put(tournament.getId(), tournamentController);
    }

    @Override
    public void joinTournament(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).join(userId);
    }

    @Override
    public void quit(UUID tournamentId, UUID userId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            tournamentController.quit(userId);
        } else {
            Logger.getLogger(TournamentManagerImpl.class).error("Tournament controller missing  tournamentid: " + tournamentId + " userId: " + userId);
        }
    }

    @Override
    public void timeout(UUID tournamentId, UUID userId) {
        controllers.get(tournamentId).timeout(userId);
    }

    @Override
    public void submitDeck(UUID tournamentId, UUID playerId, Deck deck) {
        controllers.get(tournamentId).submitDeck(playerId, deck);
    }

    @Override
    public boolean updateDeck(UUID tournamentId, UUID playerId, Deck deck) {
        return controllers.get(tournamentId).updateDeck(playerId, deck);
    }

    @Override
    public TournamentView getTournamentView(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            return tournamentController.getTournamentView();
        }
        return null;
    }

    @Override
    public Optional<UUID> getChatId(UUID tournamentId) {
        if (controllers.containsKey(tournamentId)) {
            return Optional.of(controllers.get(tournamentId).getChatId());
        }
        return Optional.empty();
    }

    @Override
    public void removeTournament(UUID tournamentId) {
        TournamentController tournamentController = controllers.get(tournamentId);
        if (tournamentController != null) {
            controllers.remove(tournamentId);
            tournamentController.cleanUpOnRemoveTournament();

        }
    }

}
