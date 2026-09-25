package mage.server.draft;

import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.draft.DraftPlayerSnapshot;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.User;
import mage.server.managers.ManagerFactory;
import mage.view.DraftClientMessage;
import mage.view.DraftPickView;
import mage.view.DraftView;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DraftSession {

    protected static final Logger logger = Logger.getLogger(DraftSession.class);

    private final ManagerFactory managerFactory;
    protected final UUID userId;
    protected final UUID playerId;
    protected final Draft draft;
    protected boolean killed = false;
    
    protected int timeoutCardNum; // the pick number for which the current timeout has been set up
    protected int timeoutCounter = 0; // increments every second that the player has run out of picking time
    protected final int AUTOPICK_BUFFER = 2; // seconds - when the player has run out of picking time, the autopick happens after this many seconds (to account for client timer possibly lagging behind server)

    private ScheduledFuture<?> futureTimeout;
    protected final ScheduledExecutorService timeoutExecutor;

    public DraftSession(ManagerFactory managerFactory, Draft draft, UUID userId, UUID playerId) {
        this.managerFactory = managerFactory;
        this.timeoutExecutor = managerFactory.threadExecutor().getTimeoutExecutor();
        this.userId = userId;
        this.draft = draft;
        this.playerId = playerId;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = managerFactory.userManager().getUser(userId);
            DraftPlayerSnapshot snapshot = draft.getPlayerSnapshot(playerId);
            if (snapshot == null) {
                logger.warn("Draft " + draft.getId() + ": can't init unknown player " + playerId);
                return false;
            }
            if (user.isPresent()) {
                int remaining;
                if (futureTimeout != null && !futureTimeout.isDone()) {
                    // picking already runs
                    remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                } else {
                    // picking not started yet
                    remaining = draft.getPickTimeout();
                }
                user.get().fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_INIT, draft.getId(),
                        new DraftClientMessage(new DraftView(draft, snapshot), new DraftPickView(snapshot, remaining))));
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            DraftPlayerSnapshot snapshot = draft.getPlayerSnapshot(playerId);
            if (snapshot == null) {
                return;
            }
            managerFactory.userManager()
                    .getUser(userId).
                    ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_UPDATE, draft.getId(),
                            new DraftClientMessage(new DraftView(draft, snapshot), null))));
        }
    }

    public void draftOver() {
        if (!killed) {
            managerFactory.userManager()
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_OVER, draft.getId())));
        }
    }

    public void pickCard(int timeout) {
        if (!killed) {
            DraftPlayerSnapshot snapshot = draft.getPlayerSnapshot(playerId);
            if (snapshot == null || !snapshot.isPicking()) {
                // nothing to pick, e.g. a resend after the player's own pick
                return;
            }
            setupTimeout(timeout);
            timeoutCardNum = snapshot.getCardNum();
            managerFactory.userManager()
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_PICK, draft.getId(),
                            new DraftClientMessage(new DraftView(draft, snapshot), new DraftPickView(snapshot, timeout)))));

        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            if (seconds > 1 ) {
                timeoutCounter = 0;
            }
            futureTimeout = timeoutExecutor.schedule(
                    () -> {
                        try {
                            if (timeoutCardNum == draft.getCardNum()) {
                                if (timeoutCounter++ > AUTOPICK_BUFFER) { // the autopick happens after n seconds (to account for client timer possibly lagging behind server)
                                    managerFactory.draftManager().timeout(draft.getId(), userId);
                                }
                                setupTimeout(1); // The timeout keeps happening at a 1 second interval to make sure that the draft moves onto the next pick
                            }
                        } catch (Exception e) {
                            logger.fatal("DraftSession error - userId " + userId + " draftId " + draft.getId(), e);
                        }
                    },
                    seconds, TimeUnit.SECONDS
            );
        }
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    protected void handleRemoteException(RemoteException ex) {
        logger.fatal("DraftSession error ", ex);
        managerFactory.draftManager().kill(draft.getId(), userId);
    }

    public void setKilled() {
        killed = true;
    }

    public DraftPickView sendCardPick(UUID cardId, Set<UUID> hiddenCards) {
        // answer uses data from the pick moment (the draft can start the next round before the answer)
        DraftPlayerSnapshot snapshot = draft.addPick(playerId, cardId, hiddenCards);
        if (snapshot != null) {
            return new DraftPickView(snapshot, 0);
        }
        return null;
    }

    public void removeDraft() {
        managerFactory.userManager().getUser(userId).ifPresent(user -> user.removeDraft(playerId));

    }

    public DraftPlayer getDraftPlayer() {
        return draft.getPlayer(playerId);
    }

    public Draft getDraft() {
        return draft;
    }
}
