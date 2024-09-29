
package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;

/**
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

        this.add(CardType.CREATURE.getPredicate());
        if (subtype != null) {
            this.add(subtype.getPredicate());
        }
    }

    protected FilterControlledCreaturePermanent(final FilterControlledCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledCreaturePermanent copy() {
        return new FilterControlledCreaturePermanent(this);
    }

}
