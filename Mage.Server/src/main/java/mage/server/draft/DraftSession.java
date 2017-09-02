/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server.draft;

import java.rmi.RemoteException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import mage.game.draft.Draft;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ThreadExecutor;
import mage.view.DraftClientMessage;
import mage.view.DraftPickView;
import mage.view.DraftView;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DraftSession {

    protected final static Logger logger = Logger.getLogger(DraftSession.class);

    protected final UUID userId;
    protected final UUID playerId;
    protected final Draft draft;
    protected boolean killed = false;
    protected UUID markedCard;

    private ScheduledFuture<?> futureTimeout;
    protected static final ScheduledExecutorService timeoutExecutor = ThreadExecutor.instance.getTimeoutExecutor();

    public DraftSession(Draft draft, UUID userId, UUID playerId) {
        this.userId = userId;
        this.draft = draft;
        this.playerId = playerId;
        this.markedCard = null;
    }

    public boolean init() {
        if (!killed) {
            Optional<User> user = UserManager.instance.getUser(userId);
            if (user.isPresent()) {
                if (futureTimeout != null && !futureTimeout.isDone()) {
                    int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                    user.get().fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_INIT, draft.getId(), new DraftClientMessage(getDraftPickView(remaining))));
                }
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            UserManager.instance
                    .getUser(userId).
                    ifPresent(user -> user.fireCallback(
                    new ClientCallback(ClientCallbackMethod.DRAFT_UPDATE, draft.getId(), getDraftView())));
        }
    }

    public void draftOver() {
        if (!killed) {
            UserManager.instance
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_OVER, draft.getId())));

        }
    }

    public void pickCard(int timeout) {
        if (!killed) {
            setupTimeout(timeout);
            UserManager.instance
                    .getUser(userId)
                    .ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.DRAFT_PICK, draft.getId(), new DraftClientMessage(getDraftPickView(timeout)))));

        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(
                    () -> DraftManager.instance.timeout(draft.getId(), userId),
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
        DraftManager.instance.kill(draft.getId(), userId);
    }

    public void setKilled() {
        killed = true;
    }

    public DraftPickView sendCardPick(UUID cardId, Set<UUID> hiddenCards) {
        cancelTimeout();
        if (draft.addPick(playerId, cardId, hiddenCards)) {
            return getDraftPickView(0);
        }
        return null;
    }

    public void removeDraft() {
        UserManager.instance.getUser(userId).ifPresent(user -> user.removeDraft(playerId));

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

}
