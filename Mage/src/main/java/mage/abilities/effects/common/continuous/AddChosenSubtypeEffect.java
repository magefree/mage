package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addSubType(game, subType);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }

    @Override
    public AddChosenSubtypeEffect copy() {
        return new AddChosenSubtypeEffect(this);
    }
}
