
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class GreatestPowerAmongControlledCreaturesValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            int amount = 0;
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
                if (p.getPower().getValue() > amount) {
                    amount = p.getPower().getValue();
                }
            }
            return amount;
        }
        return 0;
    }

    @Override
    public GreatestPowerAmongControlledCreaturesValue copy() {
        return new GreatestPowerAmongControlledCreaturesValue();
    }

    @Override
    public String getMessage() {
        return "the greatest power among creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }

}
