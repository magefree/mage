package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author jeffwadsworth
 */
public class BecomesEnchantmentSourceEffect extends ContinuousEffectImpl {

    public BecomesEnchantmentSourceEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} becomes an enchantment";
        dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
    }

    protected BecomesEnchantmentSourceEffect(final BecomesEnchantmentSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesEnchantmentSourceEffect copy() {
        return new BecomesEnchantmentSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                affectedObjects.add(permanent);
            }
        }
        if (affectedObjects.isEmpty()) {
            this.discard();
            return false;
        }
        return true;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.removeAllCardTypes(game);
            permanent.addCardType(game, CardType.ENCHANTMENT);
            permanent.retainAllEnchantmentSubTypes(game);
        }
    }
}
