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
 *
 * @author jeffwadsworth
 */
public class SetPowerEnchantedEffect extends ContinuousEffectImpl {

    private final int power;

    public SetPowerEnchantedEffect(int power) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.Neutral);
        staticText = "Enchanted creature has base power " + power;
        this.power = power;
    }

    public SetPowerEnchantedEffect(final SetPowerEnchantedEffect effect) {
        super(effect);
        this.power = effect.power;
    }

    @Override
    public SetPowerEnchantedEffect copy() {
        return new SetPowerEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                enchanted.getPower().setValue(power);
            }
            return true;
        }
        return false;
    }

}
