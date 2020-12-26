package mage.server.managers;

import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.server.tournament.TournamentController;
import mage.view.TournamentView;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface TournamentManager {
    Optional<TournamentController> getTournamentController(UUID tournamentId);

    void createTournamentSession(Tournament tournament, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId);

    void joinTournament(UUID tournamentId, UUID userId);

    void quit(UUID tournamentId, UUID userId);

    void timeout(UUID tournamentId, UUID userId);

    void submitDeck(UUID tournamentId, UUID playerId, Deck deck);

    boolean updateDeck(UUID tournamentId, UUID playerId, Deck deck);

    TournamentView getTournamentView(UUID tournamentId);

    Optional<UUID> getChatId(UUID tournamentId);

    void removeTournament(UUID tournamentId);
}
