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

    private Map<Permanent, List<Permanent>> combat = new HashMap<Permanent, List<Permanent>>();

    public void addPair(Permanent attacker, Permanent blocker) {
        List<Permanent> blockers = combat.get(attacker);
        if (blockers == null) {
            blockers = new ArrayList<Permanent>();
            combat.put(attacker, blockers);
        }
        blockers.add(blocker);
    }

    public Map<Permanent, List<Permanent>> getCombat() {
        return combat;
    }
}
