
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author EikePeace
 */
public class ExileAndGainLifeEqualToughnessTargetEffect extends OneShotEffect {

    public ExileAndGainLifeEqualToughnessTargetEffect() {
        super(Outcome.GainLife);
        staticText = "Exile target creature. Its controller gains life equal to its toughness";
    }

    public ExileAndGainLifeEqualToughnessTargetEffect(final ExileAndGainLifeEqualToughnessTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExileAndGainLifeEqualToughnessTargetEffect copy() {
        return new ExileAndGainLifeEqualToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                int creatureToughness = permanent.getToughness().getValue();
                permanent.moveToExile(null, null, source.getSourceId(), game);
                game.applyEffects();
                player.gainLife(creatureToughness, game, source);
            }
            return true;
        }
        return false;
    }
}
