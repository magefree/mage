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
 * @author nantuko
 */
public class ControlEnchantedEffect extends ContinuousEffectImpl {

    public ControlEnchantedEffect() {
        this("creature");
    }

    public ControlEnchantedEffect(String targetDescription) {
        super(Duration.WhileOnBattlefield, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "You control enchanted " + targetDescription;
    }

    protected ControlEnchantedEffect(final ControlEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public ControlEnchantedEffect copy() {
        return new ControlEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.changeControllerId(source.getControllerId(), game, source);
        return true;
    }
}
