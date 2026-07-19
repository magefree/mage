package org.mage.test.cards.rules;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author magenoxx_at_googlemail.com
 */
public class AdditionalCostRuleTest extends CardTestPlayerBase {

    @Test
    public void testAdditionalCostDisplayed() {
        addCard(Zone.GRAVEYARD, playerA, "Silvergill Adept");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Card firewildBorderpost = playerA.getGraveyard().getCards(currentGame).iterator().next();
        boolean found = false;
        for (String rule : firewildBorderpost.getRules(currentGame)) {
            if (rule.startsWith("As an additional cost to cast")) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Couldn't find rule text for additional cost on a card: " + firewildBorderpost.getName());
    }


}
