package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FightTargetsEffect extends OneShotEffect {

    protected boolean showEffectHint;

    public FightTargetsEffect() {
        this(true);
    }

    public FightTargetsEffect(boolean showEffectHint) {
        super(Outcome.Benefit);
        this.showEffectHint = showEffectHint;
    }

    protected FightTargetsEffect(final FightTargetsEffect effect) {
        super(effect);
        this.showEffectHint = effect.showEffectHint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target1Id = null;
        UUID target2Id = null;
        // first target is in target pointer, second target is a normal target
        if (source.getTargets().size() < 2) {
            if (!source.getTargets().get(0).isLegal(source, game)) {
                return false;
            }
            target1Id = getTargetPointer().getFirst(game, source);
            target2Id = source.getTargets().getFirstTarget();
            // two normal targets available, only if both targets are legal the effect will be applied
        } else if (source.getTargets().get(0).isLegal(source, game) && source.getTargets().get(1).isLegal(source, game)) {
            target1Id = source.getTargets().get(0).getFirstTarget();
            target2Id = source.getTargets().get(1).getFirstTarget();
        }
        if (Objects.equals(target1Id, target2Id)) {
            return false;
        }
        Permanent creature1 = game.getPermanent(target1Id);
        Permanent creature2 = game.getPermanent(target2Id);
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null && creature1.isCreature(game) && creature2.isCreature(game)) {
            return creature1.fight(creature2, source, game);
        }

        return false;
    }

    @Override
    public FightTargetsEffect copy() {
        return new FightTargetsEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        Target target = mode.getTargets().get(1);
        StringBuilder sb = new StringBuilder("target ");
        sb.append(mode.getTargets().get(0).getTargetName());
        sb.append(" fights ");
        if (!target.getTargetName().contains("other")) {
            sb.append("target ");
        }
        sb.append(target.getTargetName());

        if (showEffectHint) {
            sb.append(". <i>(Each deals damage equal to its power to the other.)</i>");
        }

        return sb.toString();
    }
}
