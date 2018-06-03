
package mage.filter.common;

import java.util.UUID;
import mage.constants.TargetController;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class FilterControlledCreatureInPlay extends FilterImpl<Object> implements FilterInPlay<Object> {

    protected final FilterCreaturePermanent creatureFilter;

    public FilterControlledCreatureInPlay() {
        this("creature");
    }

    public FilterControlledCreatureInPlay(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        creatureFilter.add(new ControllerPredicate(TargetController.YOU));
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }

    public FilterControlledCreatureInPlay(final FilterControlledCreatureInPlay filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(Object o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    @Override
    public FilterControlledCreatureInPlay copy() {
        return new FilterControlledCreatureInPlay(this);
    }

}
