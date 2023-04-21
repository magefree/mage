package mage.filter;

import mage.abilities.Ability;
import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author North
 */
public class FilterPermanent extends FilterObject<Permanent> implements FilterInPlay<Permanent> {

    protected final List<ObjectSourcePlayerPredicate<Permanent>> extraPredicates = new ArrayList<>();

    public FilterPermanent() {
        super("permanent");
    }

    public FilterPermanent(String name) {
        super(name);
    }

    public FilterPermanent(SubType subtype, String name) {
        super(name);
        this.add(subtype.getPredicate());
    }

    public FilterPermanent(Set<SubType> subtypesList, String name) {
        super(name);
        for (SubType subtype : subtypesList) {
            this.add(subtype.getPredicate());
        }
    }

    public FilterPermanent(final FilterPermanent filter) {
        super(filter);
        this.extraPredicates.addAll(filter.extraPredicates);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }

    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (!this.match(permanent, game) || !permanent.isPhasedIn()) {
            return false;
        }
        ObjectSourcePlayer<Permanent> osp = new ObjectSourcePlayer<>(permanent, playerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
    }

    public final void add(ObjectSourcePlayerPredicate predicate) {
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
