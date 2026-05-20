package org.mage.test.cards.single.ltc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TauntFromTheRampartTest extends CardTestPlayerBase {

    /**
     * Goad all creatures your opponents control. Until your next turn, those creatures can’t block.
     */
    private static final String taunt = "Taunt from the Rampart";
    private static final String guard = "Maritime Guard"; // 1/3
    private static final String bear = "Ashcoat Bear"; // 2/2 flash
    private static final String crab = "Fortress Crab"; // 1/6


    @Test
    public void testBlock() {
        addCard(Zone.HAND, playerA, taunt, 1);
        addCard(Zone.BATTLEFIELD, playerA, guard); // 1/3
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);

        addCard(Zone.HAND, playerB, bear, 1); // 2/2 Flash
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, crab);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, taunt);

        attack(1, playerA, guard);
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, bear);
        block(1, playerB, bear, guard);
        block(1, playerB, crab, guard); // can't block, ignored

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, guard, 2);
        assertDamageReceived(playerB, bear, 1);
        assertDamageReceived(playerB, crab, 0);
    }

}
