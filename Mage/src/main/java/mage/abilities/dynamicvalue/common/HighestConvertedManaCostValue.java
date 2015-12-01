package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author nigelzor
 */
public class HighestConvertedManaCostValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int highCMC = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (permanent.getSpellAbility() != null) {
                    int cmc = permanent.getSpellAbility().getManaCosts().convertedManaCost();
                    if (cmc > highCMC) {
                        highCMC = cmc;
                    }
                }
            }
        }
        return highCMC;
    }

    @Override
    public DynamicValue copy() {
        return new HighestConvertedManaCostValue();
    }

    @Override
    public String getMessage() {
        return "the highest converted mana cost among permanents you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
