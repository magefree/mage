
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class SwitchPowerToughnessSourceEffect extends ContinuousEffectImpl {

    public SwitchPowerToughnessSourceEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, Outcome.BoostCreature);
        staticText = "switch {this}'s power and toughness " + duration.toString();
    }

    public SwitchPowerToughnessSourceEffect(final SwitchPowerToughnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public SwitchPowerToughnessSourceEffect copy() {
        return new SwitchPowerToughnessSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); // To change body of generated methods, choose Tools | Templates.
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            if (duration.isOnlyValidIfNoZoneChange()) {
                discard();
            }
            return false;
        }

        sourcePermanent.switchPowerToughness();
        return true;
    }
}
