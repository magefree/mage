
package mage.filter.common;

import java.util.ArrayList;
import java.util.List;

import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author tschroeder
 */

public class FilterOtherCreatureSharingCreatureSubtype extends FilterCreaturePermanent {

    public FilterOtherCreatureSharingCreatureSubtype(Permanent creature, Game game) {
        super("creature sharing a creature type with " + creature.toString());

        List<SubtypePredicate> subtypePredicates = new ArrayList<>();
        for (SubType subtype : creature.getSubtype(game)) {
            if (subtype.getSubTypeSet() == SubTypeSet.CreatureType) {
                subtypePredicates.add(new SubtypePredicate(subtype));
            }
        }
        this.add(Predicates.and(
            Predicates.or(subtypePredicates),
            Predicates.not(new PermanentIdPredicate(creature.getId()))
        ));
    }

    public FilterOtherCreatureSharingCreatureSubtype(final FilterOtherCreatureSharingCreatureSubtype filter) {
        super(filter);
    }

    @Override
    public FilterOtherCreatureSharingCreatureSubtype copy() {
        return new FilterOtherCreatureSharingCreatureSubtype(this);
    }
}
