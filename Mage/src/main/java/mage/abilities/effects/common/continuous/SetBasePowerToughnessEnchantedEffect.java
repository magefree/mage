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
public class SetBasePowerToughnessEnchantedEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public SetBasePowerToughnessEnchantedEffect() {
        this(0, 2);
    }

    public SetBasePowerToughnessEnchantedEffect(int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "Enchanted creature has base power and toughness " + power + "/" + toughness;
        this.power = power;
        this.toughness = toughness;
    }

    public SetBasePowerToughnessEnchantedEffect(final SetBasePowerToughnessEnchantedEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public SetBasePowerToughnessEnchantedEffect copy() {
        return new SetBasePowerToughnessEnchantedEffect(this);
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
        enchanted.getToughness().setModifiedBaseValue(toughness);
        return true;
    }

}
