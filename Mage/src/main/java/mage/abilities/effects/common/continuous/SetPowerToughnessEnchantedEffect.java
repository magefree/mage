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

    private final int power;
    private final int toughness;

    public SetPowerToughnessEnchantedEffect() {
        this(0, 2);
    }

    public SetPowerToughnessEnchantedEffect(int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "Enchanted creature has base power and toughness " + power + "/" + toughness;
        this.power = power;
        this.toughness = toughness;
    }

    public SetPowerToughnessEnchantedEffect(final SetPowerToughnessEnchantedEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
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
                enchanted.getPower().setValue(power);
                enchanted.getToughness().setValue(toughness);
            }
            return true;
        }
        return false;
    }

}
