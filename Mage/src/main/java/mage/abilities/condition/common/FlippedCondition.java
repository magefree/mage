
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

public enum FlippedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = source.getSourcePermanentIfItStillExists(game);
        if (p != null) {
            return p.isFlipped();
        }
        return false;
    }
}
