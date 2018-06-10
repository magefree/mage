
package mage.remote.interfaces;

import java.util.UUID;

/**
 * @author noxx
 */
public interface Replays {

    boolean replayGame(UUID gameId);

    boolean startReplay(UUID gameId);

    boolean stopReplay(UUID gameId);

    boolean nextPlay(UUID gameId);

    boolean previousPlay(UUID gameId);

    boolean skipForward(UUID gameId, int moves);
}
