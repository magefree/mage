package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

public class AddChosenSubtypeEffect extends ContinuousEffectImpl {

    public AddChosenSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} is the chosen type in addition to its other types";
    }

    protected AddChosenSubtypeEffect(final AddChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(permanent.getId(), game);
            permanent.addSubType(game, subType);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(permanent.getId(), game);
            if (subType != null) {
                affectedObjects.add(permanent);
            }
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public AddChosenSubtypeEffect copy() {
        return new AddChosenSubtypeEffect(this);
    }
}
