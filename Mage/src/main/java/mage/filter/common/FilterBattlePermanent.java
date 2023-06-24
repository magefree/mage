package mage.filter.common;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 * @author TheElk801
 */
public class FilterBattlePermanent extends FilterPermanent {

    public FilterBattlePermanent() {
        this("battle");
    }

    public FilterBattlePermanent(String name) {
        super(name);
        this.add(CardType.BATTLE.getPredicate());
    }

    public FilterBattlePermanent(SubType subtype, String name) {
        super(name);
        this.add(CardType.BATTLE.getPredicate());
        this.add(subtype.getPredicate());
    }

    public FilterBattlePermanent(final FilterBattlePermanent filter) {
        super(filter);
    }

    @Override
    public FilterBattlePermanent copy() {
        return new FilterBattlePermanent(this);
    }
}
