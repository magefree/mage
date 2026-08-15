package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class KarlovOfTheGhostCouncilTest extends CardTestPlayerBase {

    @Test
    public void test_GainLifeAddsCounters() {
        // Whenever you gain life, put two +1/+1 counters on Karlov of the Ghost Council.
        addCard(Zone.BATTLEFIELD, playerA, "Karlov of the Ghost Council"); // 2/2

        // Chaplain's Blessing -- You gain 5 life.
        addCard(Zone.HAND, playerA, "Chaplain's Blessing"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaplain's Blessing");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 25); // 20 starting + 5

        // boost +2/+2 from x2 counters
        assertPowerToughness(playerA, "Karlov of the Ghost Council", 2 + 2, 2 + 2);
    }
}