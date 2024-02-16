package org.mage.test.serverside.deck;

import mage.deck.Commander;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 * @author TheElk801
 */
public class CompanionDeckValidationTest extends MageTestBase {

    @Test
    public void testGyrudaTrue() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Island", 96);
        deckTester.addMaindeck("Fact or Fiction", 1);
        deckTester.addMaindeck("Counterspell", 1);
        deckTester.addMaindeck("Pact of Negation", 1);

        deckTester.addSideboard("Ramirez DePietro", 1);
        deckTester.addSideboard("Gyruda, Doom of Depths", 1);

        deckTester.validate("Deck must have all even mana value with Gyruda as a companion");
    }

    @Test
    public void testGyrudaFalseMaindeck() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Island", 96);
        deckTester.addMaindeck("Fact or Fiction", 1);
        deckTester.addMaindeck("Counterspell", 1);
        deckTester.addMaindeck("Merfolk of the Pearl Trident", 1);

        deckTester.addSideboard("Ramirez DePietro", 1);
        deckTester.addSideboard("Gyruda, Doom of Depths", 1);

        deckTester.validate("Maindeck must have all even mana value with Gyruda as a companion", false);
    }

    @Test
    public void testGyrudaFalseCommander() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Island", 96);
        deckTester.addMaindeck("Fact or Fiction", 1);
        deckTester.addMaindeck("Counterspell", 1);
        deckTester.addMaindeck("Pact of Negation", 1);

        deckTester.addSideboard("Sivitri Scarzam", 1);
        deckTester.addSideboard("Gyruda, Doom of Depths", 1);

        deckTester.validate("Commander must have even mana value with Gyruda as a companion", false);
    }

    @Test
    public void testKaheeraFalseMaindeck() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 98);
        deckTester.addMaindeck("Cylian Elf", 1);

        deckTester.addSideboard("Morophon, the Boundless", 1);
        deckTester.addSideboard("Kaheera, the Orphanguard", 1);

        deckTester.validate("Maindeck must have all legal creature types with Kaheera as a companion", false);
    }

    @Test
    public void testKaheeraFalseCommander() {
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 98);
        deckTester.addMaindeck("Silvercoat Lion", 1);

        deckTester.addSideboard("Jasmine Boreal", 1);
        deckTester.addSideboard("Kaheera, the Orphanguard", 1);

        deckTester.validate("Commander must be valid with Kaheera as a companion", false);
    }

    @Test
    public void testKaheeraChangeling() {
        // Changelings can be in a deck Kaheera as companion as their first abilities apply during deck construction.
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 98);
        deckTester.addMaindeck("Woodland Changeling", 1);

        deckTester.addSideboard("Morophon, the Boundless", 1);
        deckTester.addSideboard("Kaheera, the Orphanguard", 1);

        deckTester.validate("Changelings are legal with Kaheera as a companion");
    }

    @Test
    public void testGristKaheera() {
        // Grist, the Hunger Tide can't be in a deck with Kaheera as companion as its first ability applies during deck construction.
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 98);
        deckTester.addMaindeck("Grist, the Hunger Tide", 1);

        deckTester.addSideboard("Doran, the Siege Tower", 1);
        deckTester.addSideboard("Kaheera, the Orphanguard", 1);

        deckTester.validate("Grist is not legal with Kaheera as a companion", false);
    }

    @Test
    public void testGristUmoriCreature() {
        // Grist, the Hunger Tide can be in a creature deck with Umori as companion as its first ability applies during deck construction.
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 97);
        deckTester.addMaindeck("Grizzly Bears", 1);
        deckTester.addMaindeck("Grist, the Hunger Tide", 1);

        deckTester.addSideboard("Nath of the Gilt-Leaf", 1);
        deckTester.addSideboard("Umori, the Collector", 1);

        deckTester.validate("Grist is legal as a creature with Umori as a companion");
    }

    @Test
    public void testGristUmoriPlaneswalker() {
        // Grist, the Hunger Tide can be in a planeswalker deck with Umori as companion as it's still a planeswalker
        DeckTester deckTester = new DeckTester(new Commander());
        deckTester.addMaindeck("Forest", 97);
        deckTester.addMaindeck("Garruk Wildspeaker", 1);
        deckTester.addMaindeck("Grist, the Hunger Tide", 1);

        deckTester.addSideboard("Lord Windgrace", 1);
        deckTester.addSideboard("Umori, the Collector", 1);

        deckTester.validate("Grist is legal as a planeswalker with Umori as a companion");
    }
}
