

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreaturePermanent extends FilterPermanent {

    public FilterCreaturePermanent() {
        this("creature");
    }

    public FilterCreaturePermanent(String name) {
        super(name);
        this.add(CardType.CREATURE.getPredicate());
    }

    public FilterCreaturePermanent(SubType subtype, String name) {
        super(name);
        this.add(CardType.CREATURE.getPredicate());
        this.add(subtype.getPredicate());
    }

    protected FilterCreaturePermanent(final FilterCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterCreaturePermanent copy() {
        return new FilterCreaturePermanent(this);
    }
}
