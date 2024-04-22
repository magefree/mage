package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CounterTargetEffect extends OneShotEffect {

    public CounterTargetEffect() {
        super(Outcome.Detriment);
    }

    protected CounterTargetEffect(final CounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public CounterTargetEffect copy() {
        return new CounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean countered = false;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            if (game.getStack().counter(targetId, source, game)) {
                countered = true;
            }
        }
        return countered;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "counter " + getTargetPointer().describeTargets(mode.getTargets(), "that spell");
    }
}
