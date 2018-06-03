
package mage.abilities.effects.common.enterAttribute;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class EnterAttributeAddChosenSubtypeEffect extends OneShotEffect {

    public EnterAttributeAddChosenSubtypeEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} is the chosen type in addition to its other types";
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
            MageObject mageObject = permanent.getBasicMageObject(game);
            if (!mageObject.getSubtype(null).contains(subtype)) {
                mageObject.getSubtype(null).add(subtype);
            }
            if (!permanent.getSubtype(null).contains(subtype)) {
                permanent.getSubtype(null).add(subtype);
            }
            return true;
        }
        return false;
    }
}
