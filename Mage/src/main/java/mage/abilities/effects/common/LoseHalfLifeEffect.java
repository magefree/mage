package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author magenoxx
 */
public class LoseHalfLifeEffect extends OneShotEffect {

    public LoseHalfLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "you lose half your life, rounded up";
    }

    protected LoseHalfLifeEffect(final LoseHalfLifeEffect effect) {
        super(effect);
    }

    @Override
    public LoseHalfLifeEffect copy() {
        return new LoseHalfLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = (player.getLife() + 1) / 2;
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }
}
