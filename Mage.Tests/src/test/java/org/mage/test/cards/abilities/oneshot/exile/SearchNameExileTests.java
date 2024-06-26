package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SurgicalExtraction Surgical Extraction}
 * {B/P}
 * Instant
 * ({B/P} can be paid with either {B} or 2 life.)
 * Choose target card in a graveyard other than a basic land card.
 * Search its owner’s graveyard, hand, and library for any number of cards with the same name as that card and exile them.
 * Then that player shuffles.
 *
 * @author LevelX2
 */
public class SearchNameExileTests extends CardTestPlayerBase {
    /**
     * I noticed that surgical extraction did not allow me to select any cards
     * to exile when I targeted breaking // entering. It did however allow my
     * opponent to target lingering souls so it could be a split card
     * interaction or just a random glitch.
     */
    @Test
    public void testSearchAndExileSplitCards() {
        addCard(Zone.HAND, playerA, "Surgical Extraction", 1); // Instant {B/P}

        addCard(Zone.GRAVEYARD, playerB, "Breaking // Entering", 2);
        addCard(Zone.HAND, playerB, "Breaking // Entering", 1);
        addCard(Zone.LIBRARY, playerB, "Breaking // Entering", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surgical Extraction", "Breaking // Entering");
        setChoice(playerA, "Yes"); // Pay 2 life to cast instead of {B}
        setChoice(playerA, "Breaking // Entering^Breaking // Entering"); // Graveyard
        setChoice(playerA, "Breaking // Entering"); // Hand
        setChoice(playerA, "Breaking // Entering"); // Library

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Surgical Extraction", 1);

        assertGraveyardCount(playerB, "Breaking // Entering", 0);
        assertLibraryCount(playerB, "Breaking // Entering", 0);
        assertHandCount(playerB, "Breaking // Entering", 0);
        assertHandCount(playerB, 0);

        assertExileCount(playerB, "Breaking // Entering", 4);
    }

    /**
     * {@link mage.cards.t.TestOfTalents Test Of Talents} 1U Instant
     * Counter target instant or sorcery spell. Search its controller’s graveyard, hand, and library for
     * any number of cards with the same name as that spell and exile them. That player shuffles,
     * then draws a card for each card exiled from their hand this way.
     */

    @Test
    public void testSearchAndExileSplitSpell() {
        addCard(Zone.HAND, playerA, "Test of Talents", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.GRAVEYARD, playerB, "Ready // Willing", 1);
        addCard(Zone.HAND, playerB, "Ready // Willing", 2);
        addCard(Zone.LIBRARY, playerB, "Ready // Willing", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "fused Ready // Willing");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Talents", "Ready // Willing", "Ready // Willing");
        setChoice(playerA, "Ready // Willing^Ready // Willing"); // Should be 2 in Graveyard now, take both
        setChoice(playerA, "Ready // Willing"); // Hand
        setChoice(playerA, "Ready // Willing"); // Library

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Test of Talents", 1);

        assertGraveyardCount(playerB, "Ready // Willing", 0);
        assertLibraryCount(playerB, "Ready // Willing", 0);
        assertHandCount(playerB, "Ready // Willing", 0);
        assertHandCount(playerB, 1); //add 2, cast 1, last is exiled+redrawn

        assertExileCount(playerB, "Ready // Willing", 4);
    }

    @Test
    public void testFailSearchAndExileMDFCSpell() {
        addCard(Zone.HAND, playerA, "Test of Talents", 1); // Instant 1U
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.GRAVEYARD, playerB, "Flamescroll Celebrant", 1);
        addCard(Zone.HAND, playerB, "Flamescroll Celebrant", 2);
        addCard(Zone.LIBRARY, playerB, "Flamescroll Celebrant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Revel in Silence");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Talents", "Revel in Silence", "Revel in Silence");

        // Should be no choices in the graveyard available since the back side spell doesn't match the front side name
        // Non-strict mode tries to select all possible targets, no current method to check if choosing is impossible
        // setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();


        assertGraveyardCount(playerB, "Flamescroll Celebrant", 2);
        assertLibraryCount(playerB, "Flamescroll Celebrant", 1);
        assertHandCount(playerB, "Flamescroll Celebrant", 1);
        assertHandCount(playerB, 1); //add 2, cast 1

        assertExileCount(playerB, "Flamescroll Celebrant", 0);
    }

    //Asserts "exile all possible" behavior for MDFC test above
    @Test
    public void testSearchAndExileSplitSpellNonstrict() {
        addCard(Zone.HAND, playerA, "Test of Talents", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.GRAVEYARD, playerB, "Ready // Willing", 1);
        addCard(Zone.HAND, playerB, "Ready // Willing", 2);
        addCard(Zone.LIBRARY, playerB, "Ready // Willing", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "fused Ready // Willing");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Talents", "Ready // Willing", "Ready // Willing");

        // setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Test of Talents", 1);

        assertGraveyardCount(playerB, "Ready // Willing", 0);
        assertLibraryCount(playerB, "Ready // Willing", 0);
        assertHandCount(playerB, "Ready // Willing", 0);
        assertHandCount(playerB, 1); //add 2, cast 1, last is exiled+redrawn

        assertExileCount(playerB, "Ready // Willing", 4);
    }
}
