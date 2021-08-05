package mage.server.managers;

import java.util.UUID;

public interface ReplayManager {
    void replayGame(UUID gameId, UUID userId);

    void startReplay(UUID gameId, UUID userId);

    void stopReplay(UUID gameId, UUID userId);

    void nextPlay(UUID gameId, UUID userId);

    void previousPlay(UUID gameId, UUID userId);

    void skipForward(UUID gameId, UUID userId, int moves);

    void endReplay(UUID gameId, UUID userId);
}
