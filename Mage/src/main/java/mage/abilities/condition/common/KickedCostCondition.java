package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * Describes condition when specific KickerCosts were paid.
 *
 * @author LevelX2
 */
public class KickedCostCondition implements Condition {

    protected String kickerCostText;

    public  KickedCostCondition(String kickerCostText) {
        this.kickerCostText = kickerCostText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof KickerAbility) {
                    return ((KickerAbility) ability).isKicked(game, source, kickerCostText);
                }
            }
        }
        return false;
    }
}
