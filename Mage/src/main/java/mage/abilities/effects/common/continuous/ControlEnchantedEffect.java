package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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
        Player controllerOfEnchantment = game.getPlayer(source.getControllerId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && controllerOfEnchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ControlChangingEffects_2:
                        if (sublayer == SubLayer.NA) {
                            permanent.changeControllerId(enchantment.getControllerId(), game, source);
                            permanent.getAbilities().forEach((ability) -> {
                                ability.setControllerId(enchantment.getControllerId());
                            });
                        }
                        break;
                }
                return true;
            } else { //remove effect if the aura or attachedTo permanent or controller of the enchantment is null
                discard();
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
