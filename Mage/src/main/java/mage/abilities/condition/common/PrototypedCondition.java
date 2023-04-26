
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

public enum PrototypedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            return p.isPrototyped();
        }

        Spell s = game.getSpell(source.getSourceId());
        if (s != null) {
            return s.isPrototyped();
        }
        return false;
    }
}
