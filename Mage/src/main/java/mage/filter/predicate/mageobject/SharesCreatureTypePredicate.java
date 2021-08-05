package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class SharesCreatureTypePredicate implements Predicate<MageObject> {

    private final MageObject mageObject;

    public SharesCreatureTypePredicate(Permanent permanent) {
        this.mageObject = permanent;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return mageObject != null && mageObject.shareCreatureTypes(game, input);
    }
}
