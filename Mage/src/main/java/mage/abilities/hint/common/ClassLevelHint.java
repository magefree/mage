package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public enum ClassLevelHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Permanent permanent = ability.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return null;
        }
        return "Class level: " + permanent.getClassLevel();
    }

    @Override
    public ClassLevelHint copy() {
        return this;
    }
}