package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author North
 */
public class AbilityPredicate implements Predicate<MageObject> {

    private final Class<? extends Ability> abilityClass;

    public AbilityPredicate(Class<? extends Ability> abilityClass) {
        this.abilityClass = abilityClass;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities;
        if (input instanceof Card) {
            abilities = ((Card) input).getAbilities(game);
        } else {
            abilities = input.getAbilities();
        }
        return abilities.stream().anyMatch(abilityClass::isInstance);

    }

    @Override
    public String toString() {
        return "Ability(" + abilityClass.toString() + ')';
    }
}
