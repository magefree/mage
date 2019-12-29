package mage.player.ai;

import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentEvaluator {

    private final Map<UUID, Integer> values = new HashMap<>();
    private final CombatEvaluator combat = new CombatEvaluator();

    public int evaluate(Permanent permanent, Game game) {
        // more score -- more valueable/powerfull permanent
        if (!values.containsKey(permanent.getId())) {
            int value = 0;
            if (permanent.isCreature()) {
                value += combat.evaluate(permanent, game);
            }
            if (permanent.isPlaneswalker()) {
                value += 2 * permanent.getCounters(game).getCount(CounterType.LOYALTY); // planeswalker is more valuable
            }
            value += permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD).size();
            value += permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).size();
            values.put(permanent.getId(), value);
        }
        return values.get(permanent.getId());
    }

}
