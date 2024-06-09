
package mage.abilities.mana.conditional;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.game.Game;

import java.util.UUID;

/**
 * @author jgray1206
 */
public class PlaneswalkerCastManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility && !source.isActivated()) {
            MageObject object = game.getObject(source);
            return object != null && object.isPlaneswalker(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
