package org.mage.test.serverside.deck;

import mage.deck.Commander;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

/**
 * @author TheElk801
 */
public class CommanderDeckValidationTest extends MageTestPlayerBase {

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

    @Test()
    public void testDoctorsCompanion() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 98);

        deckTester.addSideboard("The First Doctor", 1);
        deckTester.addSideboard("Barbara Wright", 1);

        deckTester.validate("You can have two commanders if one is a Time Lord Doctor and the other has 'Doctor's companion'");
    }

    @Test(expected = AssertionError.class)
    public void testDoctorsCompanion2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 98);

        deckTester.addSideboard("The First Doctor", 1);
        deckTester.addSideboard("Isamaru, Hound of Konda", 1);

        deckTester.validate("You can't have two commanders if one is a Time Lord Doctor and the other doesn't have 'Doctor's companion'");
    }

    @Test(expected = AssertionError.class)
    public void testDoctorsCompanion3() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 98);

        deckTester.addSideboard("Mistform Ultimus", 1);
        deckTester.addSideboard("Barbara Wright", 1);

        deckTester.validate(
                "You can't have two commanders if one has 'Doctor's companion' " +
                        "but the other has additional creature types in addition to being a Time Lord Doctor"
        );
    }

    @Test
    public void testPartnerVariants() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Swamp", 98);

        deckTester.addSideboard("Ellie, Vengeful Hunter", 1);
        deckTester.addSideboard("Joel, Resolute Survivor", 1);

        deckTester.validate("You can have two commanders if they both have the same Partner variant");
    }

    @Test(expected = AssertionError.class)
    public void testPartnerVariants2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Mountain", 98);

        deckTester.addSideboard("Ellie, Vengeful Hunter", 1);
        deckTester.addSideboard("Atreus, Impulsive Son", 1);

        deckTester.validate("You can't have two commanders if they don't have the same Partner variant");
    }

    @Test()
    public void testVehicles1() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 99);

        deckTester.addSideboard("Parhelion II", 1);

        deckTester.validate("Legendary Vehicles should be able to be a commander");
    }

    @Test(expected = AssertionError.class)
    public void testVehicles2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 99);

        deckTester.addSideboard("Dragonfly Suit", 1);

        deckTester.validate("Nonlegendary Vehicles should not be able to be a commander");
    }

    @Test()
    public void testSpacecraft1() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Plains", 99);

        deckTester.addSideboard("The Seriema", 1);

        deckTester.validate("Legendary Spacecraft should be able to be a commander if they can become a creature");
    }

    @Test(expected = AssertionError.class)
    public void testSpacecraft2() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Wastes", 99);

        deckTester.addSideboard("The Eternity Elevator", 1);

        deckTester.validate("Legendary Spacecraft should not be able to be a commander if they can't become a creature");
    }
}
