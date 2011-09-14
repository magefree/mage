package mage.abilities.condition.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * Describes condition when spell was kicked.
 *
 * @author nantuko
 */
public class KickedCondition implements Condition {

    private static KickedCondition fInstance = new KickedCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card p = game.getCard(source.getSourceId());

        boolean kicked = false;
        if (p != null) {
            for (Object cost : p.getSpellAbility().getOptionalCosts()) {
                if (cost instanceof ManaCost) {
                    if (((ManaCost) cost).isPaid()) {
                        kicked = true;
                    }
                }
            }
        }

        return kicked;
    }
}
