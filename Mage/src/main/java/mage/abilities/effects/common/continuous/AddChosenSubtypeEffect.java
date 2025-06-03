package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AddChosenSubtypeEffect extends ContinuousEffectImpl {

    public AddChosenSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} is the chosen type in addition to its other types";
    }

    protected AddChosenSubtypeEffect(final AddChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(permanent.getId(), game);
            if (subType != null) {
                permanent.addSubType(game, subType);
            }
        }
        return true;
    }

    @Override
    public AddChosenSubtypeEffect copy() {
        return new AddChosenSubtypeEffect(this);
    }
}
