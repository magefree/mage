package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class BlightControllerEffect extends OneShotEffect {

    private final int amount;

    public BlightControllerEffect(int amount) {
        super(Outcome.Detriment);
        this.amount = amount;
        staticText = "blight " + amount;
    }

    private BlightControllerEffect(final BlightControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BlightControllerEffect copy() {
        return new BlightControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return BlightCost.doBlight(game.getPlayer(source.getControllerId()), amount, game, source) != null;
    }
}
