
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class BecomesAuraSourceEffect extends ContinuousEffectImpl {

    private final Ability newAbility;
    private final Target target;

    public BecomesAuraSourceEffect(Target target) {
        super(Duration.Custom, Outcome.AddAbility);
        this.target = target;
        newAbility = new EnchantAbility(target);
        newAbility.setRuleAtTheTop(true);
        staticText = "it becomes an Aura with enchant " + target.getTargetName();
        dependencyTypes.add(DependencyType.AuraAddingRemoving);

    }

    protected BecomesAuraSourceEffect(final BecomesAuraSourceEffect effect) {
        super(effect);
        this.target = effect.target;
        this.newAbility = effect.newAbility;
    }

    @Override
    public BecomesAuraSourceEffect copy() {
        return new BecomesAuraSourceEffect(this);
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
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.AURA);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(newAbility, source.getSourceId(), game);
                    permanent.getSpellAbility().getTargets().clear();
                    permanent.getSpellAbility().getTargets().add(target);
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        this.discard();
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.AbilityAddingRemovingEffects_6 == layer || Layer.TypeChangingEffects_4 == layer;
    }

}
