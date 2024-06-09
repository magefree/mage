package mage.filter.common;

import mage.abilities.Ability;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ActivatedOrTriggeredAbilityPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class FilterActivatedOrTriggeredAbility extends FilterStackObject {

    public FilterActivatedOrTriggeredAbility() {
        this("activated or triggered ability");
        this.add(ActivatedOrTriggeredAbilityPredicate.instance); // this predicate is required to work correctly on the stack
    }

    public FilterActivatedOrTriggeredAbility(String name) {
        super(name);
    }

    protected FilterActivatedOrTriggeredAbility(final FilterActivatedOrTriggeredAbility filter) {
        super(filter);
    }

    @Override
    public boolean match(StackObject stackObject, UUID playerId, Ability source, Game game) {

        if (!super.match(stackObject, playerId, source, game)
                || !(stackObject instanceof Ability)) {
            return false;
        }
        Ability ability = (Ability) stackObject;
        return ability.isTriggeredAbility() || ability.isActivatedAbility();
    }

    @Override
    public boolean match(StackObject stackObject, Game game) {

        if (!super.match(stackObject, game)
                || !(stackObject instanceof Ability)) {
            return false;
        }
        Ability ability = (Ability) stackObject;
        return ability.getAbilityType().isTriggeredAbility() || ability.isActivatedAbility();
    }
}
