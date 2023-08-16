package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BargainAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * Checks if the spell was cast with the alternate Bargain cost
 *
 * @author Susucr
 */
public enum BargainedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: replace by Tag Cost Tracking.
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof BargainAbility) {
                    return ((BargainAbility) ability).wasBargained(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} was Bargained";
    }

}
