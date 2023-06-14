package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author jeffwadsworth
 */
public class BecomesEnchantmentSourceEffect extends ContinuousEffectImpl {

    public BecomesEnchantmentSourceEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} becomes an Enchantment";
        dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
    }

    public BecomesEnchantmentSourceEffect(final BecomesEnchantmentSourceEffect effect) {
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
    public boolean apply(Game game, Ability source) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, CardType.ENCHANTMENT);
        permanent.retainAllEnchantmentSubTypes(game);
        permanent.setIsAllCreatureTypes(game, false);
        return true;
    }
}
