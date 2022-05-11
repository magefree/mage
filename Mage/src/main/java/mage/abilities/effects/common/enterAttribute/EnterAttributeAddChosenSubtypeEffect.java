
package mage.abilities.effects.common.enterAttribute;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * IMPORTANT: This only adds the chosen subtype while the source permanent is entering the battlefield.
 * You should also use @link{mage.abilities.effects.common.continuous.AddChosenSubtypeEffect} to make the subtype persist.
 *
 * @author LevelX2
 */
public class EnterAttributeAddChosenSubtypeEffect extends OneShotEffect {

    public EnterAttributeAddChosenSubtypeEffect() {
        super(Outcome.Benefit);
    }

    public EnterAttributeAddChosenSubtypeEffect(final EnterAttributeAddChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public EnterAttributeAddChosenSubtypeEffect copy() {
        return new EnterAttributeAddChosenSubtypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
        if (permanent != null && subtype != null) {
            permanent.addSubType(game, subtype);
            return true;
        }
        return false;
    }
}
