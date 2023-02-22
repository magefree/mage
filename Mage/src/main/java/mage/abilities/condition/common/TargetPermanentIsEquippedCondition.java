package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class TargetPermanentIsEquippedCondition implements Condition {
    public TargetPermanentIsEquippedCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                return permanent.getAttachments().size() > 0;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it's equipped";
    }
}
