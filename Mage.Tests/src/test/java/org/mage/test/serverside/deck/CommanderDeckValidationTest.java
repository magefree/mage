package org.mage.test.serverside.deck;

import mage.deck.Commander;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 * @author TheElk801
 */
public class CommanderDeckValidationTest extends MageTestBase {

    @Test
    public void testGristCommander() {
        // Grist, the Hunger Tide can be your commander as its first ability applies during deck construction.
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 49);
        deckTester.addMaindeck("Swamp", 50);

        deckTester.addSideboard("Grist, the Hunger Tide", 1);

        deckTester.validate("Grist should be legal as a commander");
    }
}
