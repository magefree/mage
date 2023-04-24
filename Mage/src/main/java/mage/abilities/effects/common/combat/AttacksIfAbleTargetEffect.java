
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.EnumSet;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AttacksIfAbleTargetEffect extends RequirementEffect {

    TargetController mustAttacks;

    public AttacksIfAbleTargetEffect(Duration duration) {
        this(duration, TargetController.ANY);
    }

    public AttacksIfAbleTargetEffect(Duration duration, TargetController mustAttacks) {
        super(duration);
        this.mustAttacks = mustAttacks;

        if (!EnumSet.of(
                TargetController.YOU,
                TargetController.ANY
        ).contains(this.mustAttacks)) {
            throw new IllegalArgumentException("Unsupported type in mustAttacks");
        }
    }

    public AttacksIfAbleTargetEffect(final AttacksIfAbleTargetEffect effect) {
        super(effect);
        this.mustAttacks = effect.mustAttacks;
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
    public UUID mustAttackDefender(Ability source, Game game) {
        switch (this.mustAttacks) {
            case YOU:
                return source.getControllerId();
            case ANY:
            default:
                return null;
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append("target ");
        sb.append(mode.getTargets().get(0).getTargetName());
        switch (this.mustAttacks) {
            case YOU:
                sb.append(" attacks you");
                break;
            case ANY:
            default:
                sb.append(" attacks");
                break;
        }
        if (this.duration == Duration.EndOfTurn) {
            sb.append(" this turn if able");
        } else {
            sb.append(" each combat if able");
        }
        return sb.toString();
    }
}
