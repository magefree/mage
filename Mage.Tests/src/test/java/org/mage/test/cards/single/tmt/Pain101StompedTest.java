package org.mage.test.cards.single.tmt;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class Pain101StompedTest extends CardTestPlayerBase {
    @Test
    public void test_ReturnedCreatureIsNotHitAgainByTheBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Squirrelanoids");
        addCard(Zone.HAND, playerA, "Pain 101");
        addCard(Zone.HAND, playerA, "Stomped by the Foot");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pain 101", "Squirrelanoids");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomped by the Foot", "Squirrelanoids");
        setChoice(playerA, false); // not kicked: -2/-2
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertPermanentCount(playerA, "Squirrelanoids", 1);
        assertTapped("Squirrelanoids", true);
        assertPowerToughness(playerA, "Squirrelanoids", 1, 1);
        assertGraveyardCount(playerA, "Squirrelanoids", 0);
        assertGraveyardCount(playerA, "Pain 101", 1);
        assertGraveyardCount(playerA, "Stomped by the Foot", 1);
    }
}
