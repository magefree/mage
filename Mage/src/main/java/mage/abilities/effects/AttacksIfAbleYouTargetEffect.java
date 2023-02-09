package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;
/**
 *
 * @author FranzCorsaro
 */
public class AttacksIfAbleYouTargetEffect extends RequirementEffect {

    public AttacksIfAbleYouTargetEffect(Duration duration) {
        super(duration);
    }

    public AttacksIfAbleYouTargetEffect(RequirementEffect effect) {
        super(effect);
    }

    @Override
    public AttacksIfAbleYouTargetEffect copy() {
        return new AttacksIfAbleYouTargetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }


    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getControllerId();
    }

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
            return "target " + mode.getTargets().get(0).getTargetName() + " attacks you this turn if able";
        } else {
            return "target " + mode.getTargets().get(0).getTargetName() + " attacks you each combat if able";
        }
    }
}
