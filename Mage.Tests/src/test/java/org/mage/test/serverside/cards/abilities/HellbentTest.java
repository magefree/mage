package org.mage.test.serverside.cards.abilities;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

public class HellbentTest extends CardTestBase {

    @Test
    public void testWithCardsInHand() {
        addCard(Constants.Zone.BATTLEFIELD, computerA, "Rakdos Pit Dragon", 1);
		removeAllCardsFromLibrary(computerA);
		addCard(Constants.Zone.LIBRARY, computerA, "Mountain", 10);

		addCard(Constants.Zone.BATTLEFIELD, computerB, "Plains", 2);
		addCard(Constants.Zone.HAND, computerB, "Plains", 2);
		removeAllCardsFromLibrary(computerB);
		addCard(Constants.Zone.LIBRARY, computerB, "Plains", 10);


    }
}
