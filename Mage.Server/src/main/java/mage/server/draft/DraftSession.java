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

    // pick timeout and auto-pick controlled by draft itself (pick deadline), see DraftImpl.picksWait
    public DraftSession(ManagerFactory managerFactory, Draft draft, UUID userId, UUID playerId) {
        this.managerFactory = managerFactory;
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
                // remaining time of the current pick (e.g. on reconnect) or a full time before the first pick
                int remaining = draft.getPickTimeout(playerId);
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
            managerFactory.userManager()
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_PICK, draft.getId(),
                            new DraftClientMessage(new DraftView(draft, snapshot), new DraftPickView(snapshot, timeout)))));

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

        // rejected pick, e.g. a late click after an autopick
        // client must get it and refresh draft view with own cards only (hide outdated pick)
        DraftPlayerSnapshot current = draft.getPlayerSnapshot(playerId);
        if (current != null && !current.isPicking()) {
            return new DraftPickView(current, 0);
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
