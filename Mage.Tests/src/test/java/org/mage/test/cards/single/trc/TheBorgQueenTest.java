package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TheBorgQueenTest extends CardTestPlayerBase {

    @Test
    public void test_Enters_ExilesUntilFirstCreature_AssimilatesItAndRestToBottom() {
        skipInitShuffling();
        // Artifact creatures you control get +2/+2.
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // stays in library below first creature
        addCard(Zone.LIBRARY, playerB, "Island", 1);
        addCard(Zone.LIBRARY, playerB, "Hill Giant"); // first creature card from top
        addCard(Zone.LIBRARY, playerB, "Island", 2); // top two noncreature cards

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // target opponent for ETB trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Borg Queen", 1);
        assertPowerToughness(playerA, "The Borg Queen", 4 + 2, 4 + 2);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertType("Hill Giant", CardType.CREATURE, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertNotSubtype("Hill Giant", SubType.GIANT);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1 + 2, 3 + 1 + 2);
        // stops at first creature; noncreature cards return to bottom of library
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertLibraryCount(playerB, "Grizzly Bears", 1);
        assertLibraryCount(playerB, "Island", 1 + 2); // 1 untouched + 2 returned from exile
        assertExileCount(playerB, "Hill Giant", 0);
        assertExileCount(playerB, "Island", 0);
    }

    @Test
    public void test_Attacks_AssimilatesSecondCreature() {
        skipInitShuffling();
        // Artifact creatures you control get +2/+2.
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Hill Giant"); // assimilated by attack trigger
        addCard(Zone.LIBRARY, playerB, "Island", 2); // exiled on attack (plus 1 drawn on turn 2)
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // top card, assimilated by ETB trigger

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target

        attack(3, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // attack trigger target

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1 + 2, 2 + 1 + 2);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1 + 2, 3 + 1 + 2);
        assertSubtype("Hill Giant", SubType.BORG);
        assertExileCount(playerB, 0);
    }

    @Test
    public void test_ExilesOneCardAtATime_TriggersLaeliaForEachCard() {
        // Laelia, the Blade Reforged ruling:
        // If a player is instructed to exile cards from their library "until" a certain event occurs
        // (for example, because of the triggered ability of a spell with cascade or discover), that
        // player exiles those cards one at a time. Laelia's last ability will trigger that many times.
        // (2024-06-07)
        skipInitShuffling();
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        // Whenever one or more cards are put into exile from your library and/or your graveyard,
        // put a +1/+1 counter on Laelia.
        addCard(Zone.BATTLEFIELD, playerB, "Laelia, the Blade Reforged"); // 2/2
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // 3rd card exiled
        addCard(Zone.LIBRARY, playerB, "Island", 2); // 1st and 2nd cards exiled

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target
        setChoice(playerB, "Whenever one or more cards"); // order 3 Laelia triggers
        setChoice(playerB, "Whenever one or more cards"); // order remaining 2 Laelia triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerB, "Laelia, the Blade Reforged", CounterType.P1P1, 3);
    }

    @Test
    public void test_NoCreatureCardInLibrary_AllExiledReturnedToBottom() {
        skipInitShuffling();
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Island", 2); // no creature card in library

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Borg Queen", 1);
        assertLibraryCount(playerB, "Island", 2);
        assertExileCount(playerB, "Island", 0);
    }

    @Test
    public void test_EmptyLibrary_NothingAssimilated() {
        skipInitShuffling();
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Borg Queen", 1);
        assertLibraryCount(playerB, 0);
        assertExileCount(playerB, 0);
    }

    @Test
    public void test_ExilesModalDoubleFacedCreature_AssimilatesFrontFace() {
        skipInitShuffling();
        // Artifact creatures you control get +2/+2.
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Kazandu Mammoth"); // MDFC Creature - Elephant 3/3 // Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
        assertType("Kazandu Mammoth", CardType.ARTIFACT, true);
        assertType("Kazandu Mammoth", CardType.CREATURE, true);
        assertSubtype("Kazandu Mammoth", SubType.BORG);
        assertNotSubtype("Kazandu Mammoth", SubType.ELEPHANT);
        assertCounterCount(playerA, "Kazandu Mammoth", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Kazandu Mammoth", 3 + 1 + 2, 3 + 1 + 2);
    }

    @Test
    public void test_OpponentControlsTheBorgQueen_AssimilatesFromYourLibrary() {
        skipInitShuffling();
        // Artifact creatures you control get +2/+2.
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerB, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        //
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // top card of playerA's library

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "The Borg Queen");
        addTarget(playerB, playerA); // ETB trigger targets playerA

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2 + 1 + 2, 2 + 1 + 2);
    }

    @Test
    public void test_EntryReplacedByContainmentPriest_CreatureStaysExiledAndRestToBottom() {
        skipInitShuffling();
        // Whenever The Borg Queen enters or attacks, target opponent exiles cards from the top of their library
        // until they exile a creature card. Assimilate that card. They put the rest on the bottom in a random order.
        addCard(Zone.HAND, playerA, "The Borg Queen"); // {4}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // If a nontoken creature would enter and it wasn't cast, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Containment Priest"); // 2/2
        //
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // creature card, replaced with exile by Containment Priest
        addCard(Zone.LIBRARY, playerB, "Island", 1); // noncreature card, returned to bottom

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Borg Queen");
        addTarget(playerA, playerB); // ETB trigger target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerB, "Grizzly Bears", 1);
        assertExileCount(playerB, "Island", 0);
        assertLibraryCount(playerB, "Island", 1);
    }
}
