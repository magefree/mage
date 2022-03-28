
package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class MeldCondition implements Condition {

    private final String meldWithName;

    public MeldCondition(String meldWithName) {
        this.meldWithName = meldWithName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceMageObject = source.getSourceObjectIfItStillExists(game);
        if (sourceMageObject instanceof Permanent) {
            Permanent sourcePermanent = (Permanent) sourceMageObject;
            if (sourcePermanent.isControlledBy(source.getControllerId())
                    && sourcePermanent.isOwnedBy(source.getControllerId())) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                filter.add(new NamePredicate(this.meldWithName));
                filter.add(new OwnerIdPredicate(source.getControllerId()));
                return game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0;
            }
        }
        return false;
    }
}
