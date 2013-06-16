package mage.abilities.effects.common.continious;

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
public class ControlEnchantedEffect extends ContinuousEffectImpl<ControlEnchantedEffect> {

    public ControlEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.GainControl);
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ControlChangingEffects_2:
                        if (sublayer == SubLayer.NA) {
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
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ControlChangingEffects_2;
    }

}
