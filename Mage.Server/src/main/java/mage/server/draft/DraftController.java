/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

import java.io.File;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.MageException;
import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.server.TableManager;
import mage.server.UserManager;
import mage.server.game.GameController;
import mage.server.util.ThreadExecutor;
import mage.view.DraftPickView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftController {

    private static final Logger logger = Logger.getLogger(GameController.class);
    public static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";

    private ConcurrentHashMap<UUID, DraftSession> draftSessions = new ConcurrentHashMap<UUID, DraftSession>();
    private ConcurrentHashMap<UUID, UUID> userPlayerMap;
    private UUID draftSessionId;
    private Draft draft;
    private UUID tableId;

    public DraftController(Draft draft, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        draftSessionId = UUID.randomUUID();
        this.userPlayerMap = userPlayerMap;
        this.draft = draft;
        this.tableId = tableId;
        init();
    }

    private void init() {
        draft.addTableEventListener(
            new Listener<TableEvent> () {
                @Override
                public void event(TableEvent event) {
                    try {
                        switch (event.getEventType()) {
                            case UPDATE:
                                updateDraft();
                                break;
                            case END:
                                endDraft();
                                break;
                        }
                    }
                    catch (MageException ex) {
                        logger.fatal("Table event listener error", ex);
                    }
                }
            }
        );
        draft.addPlayerQueryEventListener(
            new Listener<PlayerQueryEvent> () {
                @Override
                public void event(PlayerQueryEvent event) {
                    try {
                        switch (event.getQueryType()) {
                            case PICK_CARD:
                                pickCard(event.getPlayerId(), event.getMax());
                                break;
                        }
                    }
                    catch (MageException ex) {
                        logger.fatal("Table event listener error", ex);
                    }
                }
            }
        );
        for (DraftPlayer player: draft.getPlayers()) {
            if (!player.getPlayer().isHuman()) {
                player.setJoined();
                logger.info("player " + player.getPlayer().getId() + " has joined draft " + draft.getId());
            }
        }
        checkStart();
    }

    private UUID getPlayerId(UUID userId) {
        return userPlayerMap.get(userId);
    }

    public void join(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        DraftSession draftSession = new DraftSession(draft, userId, playerId);
        draftSessions.put(playerId, draftSession);
        UserManager.getInstance().getUser(userId).addDraft(playerId, draftSession);
        logger.info("User " + UserManager.getInstance().getUser(userId).getName() + " has joined draft " + draft.getId());
        draft.getPlayer(playerId).setJoined();
        checkStart();
    }

    private synchronized void startDraft() {
        for (final Entry<UUID, DraftSession> entry: draftSessions.entrySet()) {
            if (!entry.getValue().init()) {
                logger.fatal("Unable to initialize client");
                //TODO: generate client error message
                return;
            }
        }
        draft.start();
    }

    private void checkStart() {
        if (allJoined()) {
            ThreadExecutor.getInstance().getCallExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        startDraft();
                    }
            });
        }
    }

    private boolean allJoined() {
        if (!draft.allJoined())
            return false;
        for (DraftPlayer player: draft.getPlayers()) {
            if (player.getPlayer().isHuman() && draftSessions.get(player.getPlayer().getId()) == null) {
                return false;
            }
        }
        return true;
    }

    private void leave(UUID userId) {
        draft.leave(getPlayerId(userId));
    }

    private void endDraft() throws MageException {
        for (final DraftSession draftSession: draftSessions.values()) {
            draftSession.draftOver();
            draftSession.removeDraft();
        }
        TableManager.getInstance().endDraft(tableId, draft);
    }

    public void kill(UUID userId) {
        if (userPlayerMap.containsKey(userId)) {
            draftSessions.get(userPlayerMap.get(userId)).setKilled();
            draftSessions.remove(userPlayerMap.get(userId));
            leave(userId);
            userPlayerMap.remove(userId);
        }
    }

    public void timeout(UUID userId) {
        if (userPlayerMap.containsKey(userId)) {
            draft.autoPick(userPlayerMap.get(userId));
            logger.info("Draft pick timeout - autopick for player: " + userPlayerMap.get(userId));
        }
    }

    public UUID getSessionId() {
        return this.draftSessionId;
    }

    public DraftPickView sendCardPick(UUID userId, UUID cardId) {
        return draftSessions.get(userPlayerMap.get(userId)).sendCardPick(cardId);
    }

    private synchronized void updateDraft() throws MageException {
        for (final Entry<UUID, DraftSession> entry: draftSessions.entrySet()) {
            entry.getValue().update();
        }
    }

    private synchronized void pickCard(UUID playerId, int timeout) throws MageException {
        if (draftSessions.containsKey(playerId))
            draftSessions.get(playerId).pickCard(timeout);
    }

}
