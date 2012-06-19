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

package mage.server.game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.server.UserManager;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReplayManager {
    private final static ReplayManager INSTANCE = new ReplayManager();

    public static ReplayManager getInstance() {
        return INSTANCE;
    }

    private ReplayManager() {}

    private ConcurrentHashMap<String, ReplaySession> replaySessions = new ConcurrentHashMap<String, ReplaySession>();

    public void replayGame(UUID gameId, UUID userId) {
        ReplaySession replaySession = new ReplaySession(gameId, userId);
        replaySessions.put(gameId.toString() + userId.toString(), replaySession);
        UserManager.getInstance().getUser(userId).replayGame(gameId);
    }

    public void startReplay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).replay();
    }

    public void stopReplay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).stop();
    }

    public void nextPlay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).next();
    }

    public void previousPlay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).previous();
    }

    public void skipForward(UUID gameId, UUID userId, int moves) {
        replaySessions.get(gameId.toString() + userId.toString()).next(moves);
    }


}
