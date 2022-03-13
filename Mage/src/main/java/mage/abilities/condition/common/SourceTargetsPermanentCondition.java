package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.Iterator;

/**
 * @author TheElk801
 */
public class SourceTargetsPermanentCondition implements Condition {

    private final FilterPermanent filter;

    public SourceTargetsPermanentCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell == null) {
            return false;
        }
        Iterator<Target> targets = sourceSpell.getStackAbility().getTargets().iterator();
        while (targets.hasNext()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(targets.next().getFirstTarget());
            if (filter.match(permanent, source.getControllerId(), source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets " + filter.getMessage();
    }

}
