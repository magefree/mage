package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * Created by Alexsandro.
 */
public class NoAbilityPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        boolean isFaceDown = false;
        Abilities<Ability> abilities;
        if (input instanceof Card){
            abilities = ((Card)input).getAbilities(game);

            isFaceDown = ((Card)input).isFaceDown(game);
        } else {
            abilities = input.getAbilities();
        }
        if (isFaceDown) {
            for (Ability ability : abilities){
                if(ability.getSourceId() != input.getId()) {
                    return false;
                }
            }
            return true;
        }

        for (Ability ability : abilities){
            if (ability.getClass() != SpellAbility.class){

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