package org.mage.test.cards.rules;

import mage.Constants;
import mage.cards.Card;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author magenoxx_at_googlemail.com
 */
public class AlternativeCostRuleTest extends CardTestPlayerBase {

    @Test
    public void testAlternativeCostDisplayed() {
        addCard(Constants.Zone.GRAVEYARD, playerA, "Firewild Borderpost");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Card firewildBorderpost = playerA.getGraveyard().getCards(currentGame).iterator().next();
        boolean found = false;
        for (String rule : firewildBorderpost.getRules()) {
            if (rule.startsWith("You may pay")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Couldn't find rule text for alternative cost on a card: " + firewildBorderpost.getName(), found);
    }


}
