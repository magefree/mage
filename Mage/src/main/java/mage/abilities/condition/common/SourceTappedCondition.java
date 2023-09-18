package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */


public enum SourceTappedCondition implements Condition {
    TAPPED(true),
    UNTAPPED(false);
    private final boolean tapped;

    SourceTappedCondition(boolean tapped) {
        this.tapped = tapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        return permanent != null && permanent.isTapped() == tapped;
    }
}
