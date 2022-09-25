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
public class SetBasePowerEnchantedEffect extends ContinuousEffectImpl {

    private final int power;

    public SetBasePowerEnchantedEffect(int power) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, (power > 0 ? Outcome.BoostCreature : Outcome.Neutral));
        staticText = "Enchanted creature has base power " + power;
        this.power = power;
    }

    public SetBasePowerEnchantedEffect(final SetBasePowerEnchantedEffect effect) {
        super(effect);
        this.power = effect.power;
    }

    @Override
    public SetBasePowerEnchantedEffect copy() {
        return new SetBasePowerEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        if (enchanted == null) {
            return false;
        }

        enchanted.getPower().setModifiedBaseValue(power);
        return true;
    }
}
