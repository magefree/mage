package mage.filter;

import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.permanent.Permanent;

import java.util.Set;

/**
 * @author North
 */
public class FilterPermanent extends FilterObject<Permanent> {

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

    protected FilterPermanent(final FilterPermanent filter) {
        super(filter);
    }

    @Override
    public FilterPermanent copy() {
        return new FilterPermanent(this);
    }

    @Override
    public void add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Permanent.class);
        this.addExtra(predicate);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }
}
