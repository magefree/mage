package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
    public GainAllCreatureTypesAttachedEffect copy() {
        return new GainAllCreatureTypesAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(equipment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        permanent.setIsAllCreatureTypes(game, true);
        return true;
    }
}
