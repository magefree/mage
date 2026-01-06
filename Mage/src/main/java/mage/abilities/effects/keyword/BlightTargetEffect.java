package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class BlightTargetEffect extends OneShotEffect {

    private final int amount;

    public BlightTargetEffect(int amount) {
        super(Outcome.Detriment);
        this.amount = amount;
        staticText = "blight " + amount;
    }

    private BlightTargetEffect(final BlightTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BlightTargetEffect copy() {
        return new BlightTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return BlightCost.doBlight(game.getPlayer(getTargetPointer().getFirst(game, source)), amount, game, source) != null;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player") + " blights " + amount;
    }
}
