package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).changeControllerId(source.getControllerId(), game, source);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null) {
            return Collections.emptyList();
        }
        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        return enchanted != null ? Collections.singletonList(enchanted) : Collections.emptyList();
    }

    @Override
    public ControlEnchantedEffect copy() {
        return new ControlEnchantedEffect(this);
    }
}
