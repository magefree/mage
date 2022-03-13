

package mage.abilities.condition.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */


public class EnchantedCreatureColorCondition implements Condition {
    
    private final FilterPermanent filter = new FilterCreaturePermanent();

    public EnchantedCreatureColorCondition(ObjectColor color){
        filter.add(new ColorPredicate(color));
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if(filter.match(creature, enchantment.getControllerId(), source, game)){
                return true;
            }
        }
        return false;
    }
}
