package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author jeffwadsworth
 */
public enum ActivatedOrTriggeredAbilityPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (!(input instanceof Ability)) {
            return false;
        }
        Ability ability = ((Ability) input);
        return ability.isTriggeredAbility() || ability.isActivatedAbility();
    }

    @Override
    public String toString() {
        return "an activated or triggered ability";
    }
}
