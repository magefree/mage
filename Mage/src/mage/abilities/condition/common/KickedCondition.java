package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.KickerManaCost;
import mage.cards.Card;
import mage.game.Game;

/**
 * Describes condition when spell was kicked.
 *
 * @author nantuko
 */
public class KickedCondition implements Condition {

    private static KickedCondition fInstance = null;

    private KickedCondition() {}

    public static Condition getInstance() {
        if (fInstance == null) {
            fInstance = new KickedCondition();
        }
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card p = game.getCard(source.getSourceId());

        boolean kicked = false;
        if (p != null) {
            for (Object cost : p.getSpellAbility().getOptionalCosts()) {
                if (cost instanceof KickerManaCost) {
                    if (((KickerManaCost) cost).isPaid()) {
                        kicked = true;
                    }
                }
            }
        }

        return kicked;
    }
}
