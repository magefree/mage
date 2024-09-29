package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Styxo
 */
public class ExchangeLifeControllerTargetEffect extends OneShotEffect {

    public ExchangeLifeControllerTargetEffect() {
        super(Outcome.Neutral);
    }

    private ExchangeLifeControllerTargetEffect(final ExchangeLifeControllerTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExchangeLifeControllerTargetEffect copy() {
        return new ExchangeLifeControllerTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        controller.exchangeLife(player, source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "exchange life totals with " + getTargetPointer().describeTargets(mode.getTargets(), "that player");
    }
}
