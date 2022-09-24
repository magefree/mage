package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * To be used with ChooseACardNameEffect
 *
 * @author weirddan455
 */
public enum ChosenNamePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        String cardName = (String) game.getState().getValue(
                input.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY
        );
        return CardUtil.haveSameNames(input.getObject().getName(), cardName);
    }

    @Override
    public String toString() {
        return "Chosen name";
    }
}
