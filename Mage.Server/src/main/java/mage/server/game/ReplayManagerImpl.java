package mage.server.game;

import mage.server.managers.ReplayManager;
import mage.server.managers.ManagerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReplayManagerImpl implements ReplayManager {

    private final ConcurrentHashMap<String, ReplaySession> replaySessions = new ConcurrentHashMap<>();
    private final ManagerFactory managerFactory;

    public ReplayManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public void replayGame(UUID gameId, UUID userId) {
        ReplaySession replaySession = new ReplaySession(managerFactory, gameId, userId);
        replaySessions.put(gameId.toString() + userId.toString(), replaySession);
        managerFactory.userManager().getUser(userId).ifPresent(user -> user.ccReplayGame(gameId));
    }

    @Override
    public void startReplay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).replay();
    }

    @Override
    public void stopReplay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).stop();
    }

    @Override
    public void nextPlay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).next();
    }

    @Override
    public void previousPlay(UUID gameId, UUID userId) {
        replaySessions.get(gameId.toString() + userId.toString()).previous();
    }

    @Override
    public void skipForward(UUID gameId, UUID userId, int moves) {
        replaySessions.get(gameId.toString() + userId.toString()).next(moves);
    }

    @Override
    public void endReplay(UUID gameId, UUID userId) {
        replaySessions.remove(gameId.toString() + userId.toString());
    }

}
