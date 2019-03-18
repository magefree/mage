

package mage.server.game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.server.UserManager;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum ReplayManager {
    instance;

    private final ConcurrentHashMap<String, ReplaySession> replaySessions = new ConcurrentHashMap<>();

    public void replayGame(UUID gameId, UUID userId) {
        ReplaySession replaySession = new ReplaySession(gameId, userId);
        replaySessions.put(gameId.toString() + userId.toString(), replaySession);
        UserManager.instance.getUser(userId).ifPresent(user->user.ccReplayGame(gameId));
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

    public void endReplay(UUID gameId, UUID userId) {
        replaySessions.remove(gameId.toString() + userId.toString());
    }

}
