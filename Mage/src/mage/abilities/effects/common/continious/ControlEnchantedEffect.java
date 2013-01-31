package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class ControlEnchantedEffect extends ContinuousEffectImpl<ControlEnchantedEffect> {

    public ControlEnchantedEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.GainControl);
        staticText = "You control enchanted creature";
    }

    public ControlEnchantedEffect(final ControlEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public ControlEnchantedEffect copy() {
        return new ControlEnchantedEffect(this);
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ControlChangingEffects_2:
                        if (sublayer == Constants.SubLayer.NA) {
                            permanent.changeControllerId(source.getControllerId(), game);
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.ControlChangingEffects_2;
    }

}
