package mage.server.draft;

import mage.game.draft.Draft;
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
    protected UUID markedCard;
    protected int timeoutCardNum; // the pick number for which the current timeout has been set up

    private ScheduledFuture<?> futureTimeout;
    protected final ScheduledExecutorService timeoutExecutor;

    public DraftSession(ManagerFactory managerFactory, Draft draft, UUID userId, UUID playerId) {
        this.managerFactory = managerFactory;
        this.timeoutExecutor = managerFactory.threadExecutor().getTimeoutExecutor();
        this.userId = userId;
        this.draft = draft;
        this.playerId = playerId;
        this.markedCard = null;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = managerFactory.userManager().getUser(userId);
            if (user.isPresent()) {
                if (futureTimeout != null && !futureTimeout.isDone()) {
                    int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                    user.get().fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_INIT, draft.getId(),
                            new DraftClientMessage(getDraftView(), getDraftPickView(remaining))));
                }
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            managerFactory.userManager()
                    .getUser(userId).
                    ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_UPDATE, draft.getId(),
                            new DraftClientMessage(getDraftView(), null))));
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
            setupTimeout(timeout);
            timeoutCardNum = draft.getCardNum();
            managerFactory.userManager()
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_PICK, draft.getId(),
                            new DraftClientMessage(getDraftView(), getDraftPickView(timeout)))));

        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(
                    () -> {
                        try {
                            if (timeoutCardNum == draft.getCardNum()) {
                                managerFactory.draftManager().timeout(draft.getId(), userId);
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
        if (draft.addPick(playerId, cardId, hiddenCards)) {
            return getDraftPickView(0);
        }
        return null;
    }

    public void removeDraft() {
        managerFactory.userManager().getUser(userId).ifPresent(user -> user.removeDraft(playerId));

    }

    private DraftView getDraftView() {
        return new DraftView(draft);
    }

    private DraftPickView getDraftPickView(int timeout) {
        return new DraftPickView(draft.getPlayer(playerId), timeout);
    }

    public UUID getDraftId() {
        return draft.getId();
    }

    public UUID getMarkedCard() {
        return markedCard;
    }

    public void setMarkedCard(UUID markedCard) {
        this.markedCard = markedCard;
    }
    
    public void setBoosterLoaded() {
        draft.setBoosterLoaded(playerId);
    }

}
