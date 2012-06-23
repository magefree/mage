package mage.server.challenge;

import mage.Constants;
import mage.game.match.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Loads challenges from scenarios.
 * Configure games by initializing starting game board.
 */
public class ChallengeManager {

    public static final ChallengeManager fInstance = new ChallengeManager();

    public static ChallengeManager getInstance() {
        return fInstance;
    }

    public void prepareChallenge(UUID playerId, Match match) {
        Map<Constants.Zone, String> commands = new HashMap<Constants.Zone, String>();
        commands.put(Constants.Zone.OUTSIDE, "life:3");
        match.getGame().cheat(playerId, commands);
    }
}
