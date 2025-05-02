package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 * AI: fake effect to pass priority in game simulations
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PassEffect extends OneShotEffect {

    public PassEffect() {
        super(Outcome.Neutral);
    }

    protected PassEffect(final PassEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        player.pass(game);
        return true;
    }

    @Override
    public PassEffect copy() {
        return new PassEffect(this);
    }

}
