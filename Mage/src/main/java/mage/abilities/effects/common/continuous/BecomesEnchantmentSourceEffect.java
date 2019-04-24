/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class BecomesEnchantmentSourceEffect extends ContinuousEffectImpl implements SourceEffect {

    public BecomesEnchantmentSourceEffect() {
        super(Duration.Custom, Outcome.AddAbility);
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getCardType().clear();
                        permanent.getSubtype(game).clear();
                        if (!permanent.getCardType().contains(CardType.ENCHANTMENT)) {
                            permanent.getCardType().add(CardType.ENCHANTMENT);
                        }
                    }
                    break;
            }
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.TypeChangingEffects_4 == layer;
    }

}
