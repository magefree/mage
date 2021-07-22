
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AttacksIfAbleTargetEffect extends RequirementEffect {

    public AttacksIfAbleTargetEffect(Duration duration) {
        super(duration);
    }

    public AttacksIfAbleTargetEffect(final AttacksIfAbleTargetEffect effect) {
        super(effect);
    }

    @Override
    public AttacksIfAbleTargetEffect copy() {
        return new AttacksIfAbleTargetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (this.duration == Duration.EndOfTurn) {
            return "target " + mode.getTargets().get(0).getTargetName() + " attacks this turn if able";
        } else {
            return "target " + mode.getTargets().get(0).getTargetName() + " attacks each combat if able";
        }
    }

}
