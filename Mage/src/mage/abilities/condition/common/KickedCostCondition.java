package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.KickerManaCost;
import mage.cards.Card;
import mage.game.Game;

/**
 * Describes condition when specific KickerCosts were paid.
 *
 * @author LevelX2
 */
public class KickedCostCondition implements Condition {

    protected KickerManaCost kickerManaCost;

    public  KickedCostCondition(KickerManaCost kickerManaCost) {
        this.kickerManaCost = kickerManaCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card p = game.getCard(source.getSourceId());

        boolean kicked = false;
        if (p != null) {
            for (Object cost : p.getSpellAbility().getOptionalCosts()) {
                if (cost.equals(kickerManaCost)) {
                    if (((KickerManaCost) cost).isPaid()) {
                        kicked = true;
                    }
                }
            }
        }
        return kicked;
    }
}
