
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
 * @author LevelX2
 */
public class SetPowerToughnessEnchantedEffect extends ContinuousEffectImpl {

    public SetPowerToughnessEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "Enchanted creature has base power and toughness 0/2";
    }

    public SetPowerToughnessEnchantedEffect(final SetPowerToughnessEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public SetPowerToughnessEnchantedEffect copy() {
        return new SetPowerToughnessEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                enchanted.getPower().setValue(0);
                enchanted.getToughness().setValue(2);
            }
            return true;
        }
        return false;
    }

}
