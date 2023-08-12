
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Styxo
 */
public class LoseHalfLifeTargetEffect extends OneShotEffect {

    public LoseHalfLifeTargetEffect() {
        super(Outcome.Damage);
        staticText = "that player loses half their life, rounded up";
    }

    protected LoseHalfLifeTargetEffect(final LoseHalfLifeTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseHalfLifeTargetEffect copy() {
        return new LoseHalfLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }
}
