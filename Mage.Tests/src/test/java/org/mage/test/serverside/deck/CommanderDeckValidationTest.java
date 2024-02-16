package org.mage.test.serverside.deck;

import mage.deck.Commander;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 * @author TheElk801
 */
public class CommanderDeckValidationTest extends MageTestBase {

    private static final String piper = "The Prismatic Piper";

    @Test
    public void testGristCommander() {
        // Grist, the Hunger Tide can be your commander as its first ability applies during deck construction.
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 49);
        deckTester.addMaindeck("Swamp", 50);

        deckTester.addSideboard("Grist, the Hunger Tide", 1);

        deckTester.validate("Grist should be legal as a commander");
    }

    @Test(expected = AssertionError.class)
    public void testTwoInvalidCommanders() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Wastes", 99);

        deckTester.addSideboard("Tiana, Ship's Caretaker", 1);
        deckTester.addSideboard("Mazzy, Truesword Paladin", 1);

        deckTester.validate("These commanders don't have partner");
    }

    @Test
    public void testPrismaticPiperOneCopy() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 99);

        deckTester.addSideboard(piper, 1);

        deckTester.validate();
    }

    @Test
    public void testPrismaticPiper2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 49);
        deckTester.addMaindeck("Island", 49);

        deckTester.addSideboard(piper, 1);
        deckTester.addSideboard("Anara, Wolvid Familiar", 1);

        deckTester.validate();
    }

    @Test
    public void testPrismaticPiper3() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 48);
        deckTester.addMaindeck("Island", 48);
        deckTester.addMaindeck("Mountain", 2);

        deckTester.addSideboard(piper, 1);
        deckTester.addSideboard("Thrasios, Triton Hero", 1);

        deckTester.validate();
    }

    @Test(expected = AssertionError.class)
    public void testPrismaticPiper4() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 48);
        deckTester.addMaindeck("Island", 48);
        deckTester.addMaindeck("Mountain", 1);
        deckTester.addMaindeck("Plains", 1);

        deckTester.addSideboard(piper, 1);
        deckTester.addSideboard("Thrasios, Triton Hero", 1);

        deckTester.validate();
    }

    @Test(expected = AssertionError.class)
    public void testBackgrounds() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 98);

        deckTester.addSideboard("Thrasios, Triton Hero", 1);
        deckTester.addSideboard("Haunted One", 1);

        deckTester.validate(
                "Commanders without the 'Choose a Background' ability should not be able to have a background as an additional commander");
    }

    @Test()
    public void testBackgrounds2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 98);

        deckTester.addSideboard("Abdel Adrian, Gorion's Ward", 1);
        deckTester.addSideboard("Haunted One", 1);

        deckTester.validate(
                "Commanders with the 'Choose a Background' ability should be able to have a background as an additional commander");
    }
}
