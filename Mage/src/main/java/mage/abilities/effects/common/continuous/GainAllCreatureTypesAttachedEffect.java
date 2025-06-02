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
 * @author TheElk801
 */
public class GainAllCreatureTypesAttachedEffect extends ContinuousEffectImpl {

    public GainAllCreatureTypesAttachedEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "and is every creature type";
    }

    protected GainAllCreatureTypesAttachedEffect(final GainAllCreatureTypesAttachedEffect effect) {
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
            ((Permanent) object).setIsAllCreatureTypes(game, true);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public GainAllCreatureTypesAttachedEffect copy() {
        return new GainAllCreatureTypesAttachedEffect(this);
    }
}
