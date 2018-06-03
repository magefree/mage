

package mage.player.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentEvaluator {

    //preserve calculations for efficiency
    private final Map<UUID, Integer> values = new HashMap<>();
    private final CombatEvaluator combat = new CombatEvaluator();

    public int evaluate(Permanent permanent, Game game) {
        if (!values.containsKey(permanent.getId())) {
            int value = 0;
            if (permanent.getCardType().contains(CardType.CREATURE)) {
                value += combat.evaluate(permanent, game);
            }
            value += permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD).size();
            value += permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).size();
            values.put(permanent.getId(), value);
        }
        return values.get(permanent.getId());
    }

}
