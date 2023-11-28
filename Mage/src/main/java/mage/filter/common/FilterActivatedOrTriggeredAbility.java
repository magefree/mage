package mage.filter.common;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class FilterActivatedOrTriggeredAbility extends FilterStackObject {

    public FilterActivatedOrTriggeredAbility() {
        this("activated or triggered ability");
    }

    public FilterActivatedOrTriggeredAbility(String name) {
        super(name);
    }

    protected FilterActivatedOrTriggeredAbility(final FilterActivatedOrTriggeredAbility filter) {
        super(filter);
    }

    @Override
    public boolean match(StackObject stackObject, UUID playerId, Ability source, Game game) {
        if (!super.match(stackObject, playerId, source, game) || !(stackObject instanceof Ability)) {
            return false;
        }
        Ability ability = (Ability) stackObject;
        return ability.getAbilityType() == AbilityType.TRIGGERED
                || ability.getAbilityType() == AbilityType.ACTIVATED;
    }

    @Override
    public boolean match(StackObject stackObject, Game game) {
        if (!super.match(stackObject, game) || !(stackObject instanceof Ability)) {
            return false;
        }
        Ability ability = (Ability) stackObject;
        return ability.getAbilityType() == AbilityType.TRIGGERED
                || ability.getAbilityType() == AbilityType.ACTIVATED;
    }
}
