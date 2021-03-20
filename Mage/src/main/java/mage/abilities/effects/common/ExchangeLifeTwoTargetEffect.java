package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class ExchangeLifeTwoTargetEffect extends OneShotEffect {

    public ExchangeLifeTwoTargetEffect() {
        super(Outcome.Neutral);
        staticText = "two target players exchange life totals";
    }

    private ExchangeLifeTwoTargetEffect(final ExchangeLifeTwoTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExchangeLifeTwoTargetEffect copy() {
        return new ExchangeLifeTwoTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().get(0).getTargets().size() < 2) {
            return false;
        }
        Player player1 = game.getPlayer(source.getTargets().get(0).getTargets().get(0));
        Player player2 = game.getPlayer(source.getTargets().get(0).getTargets().get(1));
        if (player1 == null || player2 == null) {
            return false;
        }
        player1.exchangeLife(player2, source, game);
        return true;
    }
}
