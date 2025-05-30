package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class PreventDamageToTargetTest extends CardTestPlayerBase {

    private static final String redeem = "Redeem"; // 1W Instant
    // Prevent all damage that would be dealt this turn to up to two target creatures.

    private static final String pyroclasm = "Pyroclasm"; // 1R Sorcery
    // Pyroclasm deals 2 damage to each creature.

    private static final String hatchling = "Kraken Hatchling"; // 0/4
    private static final String turtle = "Aegis Turtle"; // 0/5
    private static final String crab = "Fortress Crab"; // 1/6
    private static final String golem = "Hexplate Golem"; // 5/7

    @Test
    public void testDamagePreventedBothTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerA, turtle);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.BATTLEFIELD, playerB, golem);
        addCard(Zone.HAND, playerA, pyroclasm);
        addCard(Zone.HAND, playerB, redeem);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroclasm);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, redeem, turtle + "^" + crab);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, hatchling, 2);
        assertDamageReceived(playerA, turtle, 0);
        assertDamageReceived(playerB, crab, 0);
        assertDamageReceived(playerB, golem, 2);
    }

}
