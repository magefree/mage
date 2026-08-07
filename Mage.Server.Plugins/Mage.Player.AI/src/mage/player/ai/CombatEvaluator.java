
package mage.player.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatEvaluator {

    //preserve calculations for efficiency
    private Map<UUID, Integer> values = new HashMap<>();

    public int evaluate(Permanent creature, Game game) {
        if (!values.containsKey(creature.getId())) {
            int value = 0;
            if (creature.canAttack(null, game)) {
                value += 2;
            }
            value += creature.getPower().getValue();
            value += creature.getToughness().getValue();
            value += creature.getAbilities().getEvasionAbilities().size();
            value += creature.getAbilities().getProtectionAbilities().size();
            value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) ? 1 : 0;
            value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId()) ? 2 : 0;
            value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId()) ? 1 : 0;
            values.put(creature.getId(), value);
        }
        return values.get(creature.getId());
    }

}
