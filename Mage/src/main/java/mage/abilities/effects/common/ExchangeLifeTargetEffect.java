
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class ExchangeLifeTargetEffect extends OneShotEffect {

    public ExchangeLifeTargetEffect() {
        super(Outcome.Neutral);
    }

    public ExchangeLifeTargetEffect(final ExchangeLifeTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExchangeLifeTargetEffect copy() {
        return new ExchangeLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            int lifeController = controller.getLife();
            int lifePlayer = player.getLife();

            if (lifeController == lifePlayer) {
                return false;
            }

            if (!controller.isLifeTotalCanChange() || !player.isLifeTotalCanChange()) {
                return false;
            }

            if (lifeController < lifePlayer && (!controller.isCanGainLife() || !player.isCanLoseLife())) {
                return false;
            }

            if (lifeController > lifePlayer && (!controller.isCanLoseLife() || !player.isCanGainLife())) {
                return false;
            }

            controller.setLife(lifePlayer, game, source);
            player.setLife(lifeController, game, source);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Exchange life totals with target " + mode.getTargets().get(0).getTargetName();
    }
}
