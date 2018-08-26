package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import mage.constants.SubType;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class FilterPermanent extends FilterObject<Permanent> implements FilterInPlay<Permanent> {

    protected List<ObjectPlayerPredicate<ObjectPlayer<Permanent>>> extraPredicates = new ArrayList<>();

    public FilterPermanent() {
        super("permanent");
    }

    public FilterPermanent(String name) {
        super(name);
    }

    public FilterPermanent(SubType subtype, String name) {
        super(name);
        this.add(new SubtypePredicate(subtype));
    }

    public FilterPermanent(Set<SubType> subtypesList, String name) {
        super(name);
        for (SubType subtype : subtypesList) {
            this.add(new SubtypePredicate(subtype));
        }
    }

    public FilterPermanent(final FilterPermanent filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!permanent.isPhasedIn() || !this.match(permanent, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(permanent, sourceId, playerId), game);
    }

    public final void add(ObjectPlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
    }

    @Override
    public FilterPermanent copy() {
        return new FilterPermanent(this);
    }

}
