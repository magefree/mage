package mage.abilities.effects.common.enterAttribute;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * IMPORTANT: This only sets the chosen color while the source permanent is entering the battlefield.
 * You should also use @link{mage.abilities.effects.common.continuous.SetChosenColorEffect} so that the color persists.
 *
 * @author xenohedron
 */

public class EnterAttributeSetChosenColorEffect extends OneShotEffect {


    public EnterAttributeSetChosenColorEffect() {
        super(Outcome.Neutral);
    }

    public EnterAttributeSetChosenColorEffect(final EnterAttributeSetChosenColorEffect effect) {
        super(effect);
    }

    @Override
    public EnterAttributeSetChosenColorEffect copy() {
        return new EnterAttributeSetChosenColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color") ;
        if (permanent != null && color != null) {
            permanent.getColor().setColor(color);
            return true;
        }
        return false;
    }

}
