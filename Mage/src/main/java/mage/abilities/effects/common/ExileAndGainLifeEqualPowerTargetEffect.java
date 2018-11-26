
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author JRHerlehy
 */
public class ExileAndGainLifeEqualPowerTargetEffect extends OneShotEffect {

    public ExileAndGainLifeEqualPowerTargetEffect() {
        super(Outcome.Removal);
        staticText = "Exile target creature. Its controller gains life equal to its power";
    }

    public ExileAndGainLifeEqualPowerTargetEffect(final ExileAndGainLifeEqualPowerTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExileAndGainLifeEqualPowerTargetEffect copy() {
        return new ExileAndGainLifeEqualPowerTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                int creaturePower = permanent.getPower().getValue();
                permanent.moveToExile(null, null, source.getSourceId(), game);
                game.applyEffects();
                player.gainLife(creaturePower, game, source);
            }
            return true;
        }
        return false;
    }
}
