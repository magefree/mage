
package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterControlledCreaturePermanent extends FilterControlledPermanent {

    public FilterControlledCreaturePermanent() {
        this("creature you control");
    }

    public FilterControlledCreaturePermanent(String name) {
        this(null, name);
    }

    public FilterControlledCreaturePermanent(SubType subtype) {
        this(subtype, subtype.toString() + " you control");
    }

    public FilterControlledCreaturePermanent(SubType subtype, String name) {
        super(name);

        this.add(new CardTypePredicate(CardType.CREATURE));
        if(subtype != null) {
            this.add(new SubtypePredicate(subtype));
        }
    }

    public FilterControlledCreaturePermanent(final FilterControlledCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledCreaturePermanent copy() {
        return new FilterControlledCreaturePermanent(this);
    }

}
