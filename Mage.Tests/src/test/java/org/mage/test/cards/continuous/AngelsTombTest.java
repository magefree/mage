package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class AngelsTombTest extends CardTestPlayerBase {

    /**
     * Tests BecomesCreatureSourceEffect with unsummon
     * Wait until Angel's Tomb becomes creature, then (in post combat phase) cast unsummon and cast it again.
     */
    @Test
    public void testUnsummonToAnimatedArtifact() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Angel's Tomb");

        addCard(Zone.HAND, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Unsummon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Unsummon", "Angel's Tomb");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Angel's Tomb");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Unsummon", 1);

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Angel's Tomb", 1);
        assertPowerToughness(playerA, "Angel's Tomb", 0, 0);
    }

    /**
     * Tests BecomesCreatureSourceEffect with unsummon
     * This time unsummon in response to "becomes creature" trigger
     */
    //FIXME: this test can't be implemented so that AI casts unsummon in response
    // but this test case is a buggy in real life
    // it was fixed but should be checked manually
    /*@Test
    public void testUnsummonToAnimatedArtifact2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Angel's Tomb");

        addCard(Constants.Zone.HAND, playerA, "Llanowar Elves");
        addCard(Constants.Zone.HAND, playerA, "Unsummon");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Angel's Tomb");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Angel's Tomb");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Unsummon", 1);

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Angel's Tomb", 1);
        assertPowerToughness(playerA, "Angel's Tomb", 0, 0);
    }*/
}
