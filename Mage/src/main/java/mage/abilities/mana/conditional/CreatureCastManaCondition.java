
package mage.abilities.mana.conditional;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.game.Game;

/**
 * @author noxx
 */
public class CreatureCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            if (object != null && object.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
