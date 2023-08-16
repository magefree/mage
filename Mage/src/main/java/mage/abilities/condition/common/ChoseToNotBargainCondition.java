package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BargainAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * Checks if the choice for the Bargain spell was to not Bargain
 *
 * @author Susucr
 */
public enum ChoseToNotBargainCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof BargainAbility) {
                    return ((BargainAbility) ability).choseToNotBargain(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is bargaining";
    }

}
