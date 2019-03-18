

package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreaturePermanent extends FilterPermanent {

    public FilterCreaturePermanent() {
        this("creature");
    }

    public FilterCreaturePermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.CREATURE));
    }    
    
    public FilterCreaturePermanent(SubType subtype, String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.CREATURE));
        this.add(new SubtypePredicate(subtype));
    }

    public FilterCreaturePermanent(final FilterCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterCreaturePermanent copy() {
        return new FilterCreaturePermanent(this);
    }
}
