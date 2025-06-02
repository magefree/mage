package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class SetBasePowerToughnessAttachedEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public SetBasePowerToughnessAttachedEffect(int power, int toughness, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = attachmentType.verb() + " creature has base power and toughness " + power + "/" + toughness;
        this.power = power;
        this.toughness = toughness;
    }

    protected SetBasePowerToughnessAttachedEffect(final SetBasePowerToughnessAttachedEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public SetBasePowerToughnessAttachedEffect copy() {
        return new SetBasePowerToughnessAttachedEffect(this);
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
