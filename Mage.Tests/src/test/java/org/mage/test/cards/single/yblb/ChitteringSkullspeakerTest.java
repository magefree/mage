package org.mage.test.cards.single.yblb;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChitteringSkullspeakerTest extends CardTestPlayerBase {

    @Test
    public void testCopyIntensifiesUsingItsCurrentName() {
        addCard(Zone.BATTLEFIELD, playerB, "Chittering Skullspeaker");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.GRAVEYARD, playerA, "Chittering Skullspeaker");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Chittering Skullspeaker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testAllOwnedCopiesIntensifyAcrossZones() {
        addCard(Zone.HAND, playerA, "Chittering Skullspeaker", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerA, "Chittering Skullspeaker");
        addCard(Zone.HAND, playerB, "Chittering Skullspeaker");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chittering Skullspeaker");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chittering Skullspeaker");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertHandCount(playerA, 3);
        assertPermanentCount(playerA, "Chittering Skullspeaker", 2);
    }

    @Test
    public void testReturnedCopyKeepsIntensity() {
        addCard(Zone.HAND, playerA, "Chittering Skullspeaker");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Unsummon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chittering Skullspeaker");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Chittering Skullspeaker");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chittering Skullspeaker");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 17);
        assertHandCount(playerA, 3);
    }

}
