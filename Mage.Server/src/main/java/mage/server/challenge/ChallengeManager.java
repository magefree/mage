package mage.server.challenge;

import mage.constants.Zone;
import mage.game.match.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * C U R R E N T L Y    U N U S E D 
 *
 * Loads challenges from scenarios.
 * Configure games by initializing starting game board.
 */
public enum ChallengeManager {

    instance;

    public void prepareChallenge(UUID playerId, Match match) {
        Map<Zone, String> commands = new HashMap<>();
        commands.put(Zone.OUTSIDE, "life:3");
        match.getGame().cheat(playerId, commands);
    }
}
