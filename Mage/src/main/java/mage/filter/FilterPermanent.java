package mage.filter;

import mage.abilities.Ability;
import mage.constants.SubType;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Set;
import java.util.UUID;

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
    public FilterPermanent add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Permanent.class);
        this.addExtra(predicate);
        return this;
    }

    @Override
    public boolean match(Permanent permanent, Game game) {
        // TODO: if we can trust the target/checks using FilterPermanent to filter out Phased out permanent,
        //       this overload would be no longer necessary.
        return super.match(permanent, game)
                && permanent.isPhasedIn();
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceControllerId, Ability source, Game game) {
        // TODO: if we can trust the target/checks using FilterPermanent to filter out Phased out permanent,
        //       this overload would be no longer necessary.
        return super.match(permanent, sourceControllerId, source, game)
                && permanent.isPhasedIn();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }
}
