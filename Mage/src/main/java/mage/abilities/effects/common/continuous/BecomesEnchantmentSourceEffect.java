package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.removeAllCardTypes(game);
            permanent.addCardType(game, CardType.ENCHANTMENT);
            permanent.retainAllEnchantmentSubTypes(game);
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return affectedObjectList.stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
