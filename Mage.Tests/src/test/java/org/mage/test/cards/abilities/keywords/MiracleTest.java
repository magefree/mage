package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MiracleTest extends CardTestPlayerBase {

    /**
     * Tests miracle alternative cost
     */
    @Test
    public void testMiracleCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Put all creatures on the bottom of their owners' libraries.
        addCard(Zone.LIBRARY, playerA, "Terminus");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Tests working on extra turn
     */
    @Test
    public void testMiracleOnExtraTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Terminus");
        addCard(Zone.LIBRARY, playerA, "Temporal Mastery");
        addCard(Zone.HAND, playerA, "Think Twice");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // check Terminus was played
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Test that you can cast a card by miracle if you don't put it back to library before casting
     */
    @Test
    public void testMiracleWillWorkFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Thunderous Wrath"); // must be the top most card
        addCard(Zone.HAND, playerA, "Brainstorm");
        skipInitShuffling();

        castSpell(1, PhaseStep.UPKEEP, playerA, "Brainstorm");
        addTarget(playerA, "Forest");
        addTarget(playerA, "Plains");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Brainstorm", 1);
        assertHandCount(playerA, "Thunderous Wrath", 0);
        assertGraveyardCount(playerA, "Thunderous Wrath", 1);
        assertHandCount(playerA, 0);
        // check Thunderous Wrath was played
        assertLife(playerA, 20);
        assertLife(playerB, 15);

    }

    @Test
    public void testGrantedMiracle() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");

        // can successfully cast the bears when we draw our first card
        setChoice(playerB, true);
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Balduvian Bears", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);

        // opponent did not get to cast their bears
        assertHandCount(playerA, "Balduvian Bears", 1);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
    }

    @Test
    public void testDoubleGrantedMiracle() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");

        // can successfully cast the bears when we draw our first card
        setChoice(playerB, true);
        setChoice(playerB, true);

        // opponent also gets miracle
        setChoice(playerA, true);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Balduvian Bears", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);

        assertHandCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void testGrantedWithRegularMiracle() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Thunderous Wrath");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");

        setChoice(playerB, true);
        setChoice(playerB, true);

        setChoice(playerA, true);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Balduvian Bears", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertLife(playerB, 15);
        assertTapped("Mountain", true);
    }

    @Test
    public void testRemoved() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        addCard(Zone.HAND, playerB, "Fell");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        setChoice(playerB, true);
        setChoice(playerB, true);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Fell");
        addTarget(playerB, "Molecule Man");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void testControlChange() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Molecule Man");
        addCard(Zone.LIBRARY, playerB, "Bear Cub");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");
        addCard(Zone.LIBRARY, playerA, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Entrancing Melody");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.HAND, playerA, "Ephemerate");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // can successfully cast the bears when we draw our first card, turn 2
        setChoice(playerB, true);
        setChoice(playerB, true);

        // turn 3, take control
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Entrancing Melody");
        setChoice(playerA, "X=6");
        addTarget(playerA, "Molecule Man");

        // turn 4, playerB does not get miracle
        // turn 5, playerA does get miracle
        setChoice(playerA, true);
        setChoice(playerA, true);

        // flicker, then turn 6 playerB should get miracle again
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ephemerate");
        addTarget(playerA, "Molecule Man");

        setChoice(playerB, true);
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Bear Cub", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void testNoGrantedMiracleSecondCard() {
        skipInitShuffling();
        addCard(Zone.HAND, playerB, "Molecule Man");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);
        addCard(Zone.HAND, playerB, "Reach Through Mists");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Balduvian Bears");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Molecule Man");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Reach Through Mists");
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        checkPlayableAbility("can't cast bears", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Balduvian Bears", false);
        checkPlayableAbility("can't cast grizzlies", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);
        assertHandCount(playerB, "Balduvian Bears", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
    }
}
