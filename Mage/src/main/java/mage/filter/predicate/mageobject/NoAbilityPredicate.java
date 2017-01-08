package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * Created by Alexsandro.
 */
public class NoAbilityPredicate implements Predicate<MageObject> {
    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities;
        if (input instanceof Card){
            abilities = ((Card)input).getAbilities(game);
        } else {
            abilities = input.getAbilities();
        }

        for (Ability a : abilities){
            if (a.getClass() != SpellAbility.class){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "with no abilities";
    }
}