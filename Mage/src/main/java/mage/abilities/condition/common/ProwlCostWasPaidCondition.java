package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * Checks if a the spell was cast with the alternate prowl costs
 *
 * @author LevelX2
 */
public enum ProwlCostWasPaidCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability instanceof ProwlAbility) {
                    if (((ProwlAbility) ability).isActivated(source, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "this spell's prowl cost was paid";
    }

}
