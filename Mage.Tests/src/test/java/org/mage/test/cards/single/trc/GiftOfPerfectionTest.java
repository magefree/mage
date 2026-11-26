package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class GiftOfPerfectionTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void test_Multiplayer_ChoosesOneRevealedCreature_AssimilatesItAndRestToBottom() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Each opponent reveals cards from the top of their library until they reveal a creature card.
        // Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards
        // they revealed this way on the bottom of their library in a random order.
        addCard(Zone.HAND, playerA, "Gift of Perfection"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        // playerB library (bottom to top): Plains -> Grizzly Bears -> Island
        addCard(Zone.LIBRARY, playerB, "Plains"); // stays on top
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 2/2 Bear, revealed (unchosen)
        addCard(Zone.LIBRARY, playerB, "Island"); // revealed
        // playerC library (bottom to top): Forest -> Hill Giant -> Swamp
        addCard(Zone.LIBRARY, playerC, "Forest"); // stays on top
        addCard(Zone.LIBRARY, playerC, "Hill Giant"); // 3/3 Giant, revealed and chosen
        addCard(Zone.LIBRARY, playerC, "Swamp"); // revealed
        // playerD library: Mountain only (no creature card)
        addCard(Zone.LIBRARY, playerD, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Perfection");
        setChoice(playerA, "Hill Giant"); // choose Hill Giant over Grizzly Bears

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        // gain
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertType("Hill Giant", CardType.CREATURE, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1, 3 + 1);
        // lost
        assertNotSubtype("Hill Giant", SubType.GIANT);
        // libraries: unrevealed cards remain on top, other revealed cards go to bottom
        assertLibraryCount(playerB, 3);
        Assert.assertEquals("Plains", playerB.getLibrary().getFromTop(currentGame).getName());
        assertLibraryCount(playerC, 2);
        Assert.assertEquals("Forest", playerC.getLibrary().getFromTop(currentGame).getName());
        assertLibraryCount(playerD, 1);
        Assert.assertEquals("Mountain", playerD.getLibrary().getFromTop(currentGame).getName());
    }

    @Test
    public void test_OnlyOneOpponentHasCreature_AssimilatesWithoutChoice() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Each opponent reveals cards from the top of their library until they reveal a creature card.
        // Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards
        // they revealed this way on the bottom of their library in a random order.
        addCard(Zone.HAND, playerA, "Gift of Perfection"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.LIBRARY, playerB, "Island"); // top
        addCard(Zone.LIBRARY, playerC, "Swamp"); // no creature
        addCard(Zone.LIBRARY, playerD, "Mountain"); // no creature

        // Only one creature card revealed across all opponents, so assimilated without choice dialog
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Perfection");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertLibraryCount(playerB, 1);
        assertLibraryCount(playerC, 1);
        assertLibraryCount(playerD, 1);
    }

    @Test
    public void test_NoOpponentHasCreature_AllRevealedPutOnBottom() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Each opponent reveals cards from the top of their library until they reveal a creature card.
        // Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards
        // they revealed this way on the bottom of their library in a random order.
        addCard(Zone.HAND, playerA, "Gift of Perfection"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        addCard(Zone.LIBRARY, playerB, "Island", 2);
        addCard(Zone.LIBRARY, playerC, "Swamp", 2);
        addCard(Zone.LIBRARY, playerD, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Perfection");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLibraryCount(playerB, 2);
        assertLibraryCount(playerC, 2);
        assertLibraryCount(playerD, 1);
        assertGraveyardCount(playerA, "Gift of Perfection", 1);
    }

    @Test
    public void test_RevealsDoubleFacedCreature_AssimilatesFrontFace() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Each opponent reveals cards from the top of their library until they reveal a creature card.
        // Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards
        // they revealed this way on the bottom of their library in a random order.
        addCard(Zone.HAND, playerA, "Gift of Perfection"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        addCard(Zone.LIBRARY, playerB, "Delver of Secrets"); // TDFC 1/1 Human Wizard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Perfection");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        // gain
        assertType("Delver of Secrets", CardType.ARTIFACT, true);
        assertType("Delver of Secrets", CardType.CREATURE, true);
        assertSubtype("Delver of Secrets", SubType.BORG);
        assertCounterCount(playerA, "Delver of Secrets", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Delver of Secrets", 1 + 1, 1 + 1);
        // lost
        assertNotSubtype("Delver of Secrets", SubType.HUMAN);
        assertNotSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_RevealingDoesNotExileCards_DoesNotTriggerLaelia() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Each opponent reveals cards from the top of their library until they reveal a creature card.
        // Assimilate one of the creature cards revealed this way. Then each opponent puts the other cards
        // they revealed this way on the bottom of their library in a random order.
        addCard(Zone.HAND, playerA, "Gift of Perfection"); // {4}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        // Whenever one or more cards are put into exile from your library and/or your graveyard, put a +1/+1 counter on Laelia.
        addCard(Zone.BATTLEFIELD, playerB, "Laelia, the Blade Reforged"); // 2/2 Spirit Warrior
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.LIBRARY, playerB, "Island", 2); // top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Perfection");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerB, "Laelia, the Blade Reforged", CounterType.P1P1, 0);
        assertExileCount(playerB, 0);
    }
}
