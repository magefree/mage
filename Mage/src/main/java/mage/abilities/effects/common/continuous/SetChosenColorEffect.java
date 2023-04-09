package mage.abilities.effects.common.continuous;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */

public class SetChosenColorEffect extends ContinuousEffectImpl {

    public SetChosenColorEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "{this} is the chosen color.";
    }

    public SetChosenColorEffect(final SetChosenColorEffect effect) {
        super(effect);
    }

    @Override
    public SetChosenColorEffect copy() {
        return new SetChosenColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color") ;
        if (permanent != null && color != null) {
            permanent.getColor().setColor(color);
            return true;
        }
        return false;
    }

}
