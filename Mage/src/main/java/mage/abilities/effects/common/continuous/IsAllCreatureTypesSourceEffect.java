package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

import java.util.Collections;
import java.util.List;

/**
 * @author TheElk801
 */
public class IsAllCreatureTypesSourceEffect extends ContinuousEffectImpl {

    public IsAllCreatureTypesSourceEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "{this} is every creature type <i>(even if this card isn't on the battlefield)</i>.";
    }

    private IsAllCreatureTypesSourceEffect(final IsAllCreatureTypesSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof MageObject)) {
                continue;
            }
            ((MageObject) object).setIsAllCreatureTypes(game, true);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        return sourceObject != null ? Collections.singletonList(sourceObject) : Collections.emptyList();
    }

    @Override
    public IsAllCreatureTypesSourceEffect copy() {
        return new IsAllCreatureTypesSourceEffect(this);
    }
}
