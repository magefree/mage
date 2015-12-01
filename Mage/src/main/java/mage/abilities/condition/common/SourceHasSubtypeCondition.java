package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class SourceHasSubtypeCondition implements Condition {

    private final String subtype;
    
    public SourceHasSubtypeCondition(String subtype) {
        this.subtype = subtype;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.hasSubtype(subtype);
        }
        return false;
    }
}
