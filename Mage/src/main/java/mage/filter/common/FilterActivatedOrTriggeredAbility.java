package mage.filter.common;

import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ActivatedOrTriggeredAbilityPredicate;

/**
 * Used to find and choose objects on stack so extends FilterStackObject (no needs in mana abilities and FilterAbility)
 * 
 * @author TheElk801
 */
public class FilterActivatedOrTriggeredAbility extends FilterStackObject {

    public FilterActivatedOrTriggeredAbility() {
        this("activated or triggered ability");
    }

    public FilterActivatedOrTriggeredAbility(String name) {
        super(name);
        this.add(ActivatedOrTriggeredAbilityPredicate.instance); // this predicate is required to work correctly on the stack
    }

    protected FilterActivatedOrTriggeredAbility(final FilterActivatedOrTriggeredAbility filter) {
        super(filter);
    }

    @Override
    public FilterActivatedOrTriggeredAbility copy() {
        return new FilterActivatedOrTriggeredAbility(this);
    }
}
