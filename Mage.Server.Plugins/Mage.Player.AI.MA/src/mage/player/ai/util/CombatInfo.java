package mage.player.ai.util;

import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author noxx
 */
public class CombatInfo {

    private Map<Permanent, List<Permanent>> combat = new HashMap<>();

    public void addPair(Permanent attacker, Permanent blocker) {
        List<Permanent> blockers = combat.computeIfAbsent(attacker, k -> new ArrayList<>());
        blockers.add(blocker);
    }

    public Map<Permanent, List<Permanent>> getCombat() {
        return combat;
    }
}
