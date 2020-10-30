package mage.server.tournament;

import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.User;
import mage.server.managers.ManagerFactory;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentSession {

    protected static final Logger logger = Logger.getLogger(TournamentSession.class);

    private final ManagerFactory managerFactory;
    protected final UUID userId;
    protected final UUID playerId;
    protected final UUID tableId;
    protected final Tournament tournament;
    protected boolean killed = false;

    private ScheduledFuture<?> futureTimeout;
    protected final ScheduledExecutorService timeoutExecutor;

    public TournamentSession(ManagerFactory managerFactory, Tournament tournament, UUID userId, UUID tableId, UUID playerId) {
        this.managerFactory = managerFactory;
        this.timeoutExecutor = managerFactory.threadExecutor().getTimeoutExecutor();
        this.userId = userId;
        this.tournament = tournament;
        this.playerId = playerId;
        this.tableId = tableId;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = managerFactory.userManager().getUser(userId);
            if (user.isPresent()) {
                user.get().fireCallback(new ClientCallback(ClientCallbackMethod.TOURNAMENT_INIT, tournament.getId(), getTournamentView()));
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            managerFactory.userManager().getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.TOURNAMENT_UPDATE, tournament.getId(), getTournamentView())));

        }
    }

    public void gameOver(final String message) {
        if (!killed) {
            managerFactory.userManager().getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.TOURNAMENT_OVER, tournament.getId(), message)));

        }
    }

    public void construct(int timeout) {
        if (!killed) {
            setupTimeout(timeout);
            managerFactory.userManager().getUser(userId).ifPresent(user -> {
                int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                user.ccConstruct(tournament.getPlayer(playerId).getDeck(), tableId, remaining);
            });
        }
    }

    public void submitDeck(Deck deck) {
        cancelTimeout();
        tournament.submitDeck(playerId, deck);
    }

    public boolean updateDeck(Deck deck) {
        return tournament.updateDeck(playerId, deck);
    }

    public void setKilled() {
        killed = true;
    }

    public boolean isKilled() {
        return killed;
    }

    private synchronized void setupTimeout(int seconds) {
        if (futureTimeout != null && !futureTimeout.isDone()) {
            return;
        }
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(
                    () -> {
                        try {
                            managerFactory.tournamentManager().timeout(tournament.getId(), userId);
                        } catch (Exception e) {
                            logger.fatal("TournamentSession error - userId " + userId + " tId " + tournament.getId(), e);
                        }
                    },
                    seconds, TimeUnit.SECONDS
            );
        }
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
            logger.debug("Timeout is Done: " + futureTimeout.isDone() + "  userId: " + userId);
        }
    }

    private TournamentView getTournamentView() {
        return new TournamentView(tournament);
    }

    public UUID getTournamentId() {
        return tournament.getId();
    }

    public void tournamentOver() {
        cleanUp();
        removeTournamentForUser();
    }

    public void quit() {
        cleanUp();
        removeTournamentForUser();
    }

    private void cleanUp() {
        if (futureTimeout != null && !futureTimeout.isDone()) {
            futureTimeout.cancel(true);
        }
    }

    private void removeTournamentForUser() {
        Optional<User> user = managerFactory.userManager().getUser(userId);
        if (user.isPresent()) {
            user.get().removeTable(playerId);
            user.get().removeTournament(playerId);
        }
    }

}
