package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveFromCombatTargetEffect extends OneShotEffect {

    public RemoveFromCombatTargetEffect() {
        super(Outcome.Detriment);
    }

    protected RemoveFromCombatTargetEffect(final RemoveFromCombatTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.removeFromCombat(game);
            }
        }
        return true;
    }

    @Override
    public RemoveFromCombatTargetEffect copy() {
        return new RemoveFromCombatTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "remove " + getTargetPointer().describeTargets(mode.getTargets(), "that creature") + " from combat";
    }

}
