package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class SharesNamePredicate implements Predicate<MageObject> {

    private final MageObject mageObject;

    public SharesNamePredicate(MageObject mageObject) {
        this.mageObject = mageObject;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.sharesName(mageObject, game);
    }
}
