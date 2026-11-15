package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DimirStrandcatcherTest extends CardTestPlayerBase {

    @Test
    public void test_AttackPlaneswalkerOnly_OpponentIsNotAttacked() {
        // Whenever you attack, surveil X, where X is the number of opponents being attacked.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Strandcatcher"); // 3/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // 3 loyalty

        attack(1, playerA, "Dimir Strandcatcher", "Jace Beleren");

        // 802.4 - a player who controls an attacked planeswalker isn't attacked, so surveil 0 - no surveil dialog
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Jace Beleren", 1); // 3 damage
        assertGraveyardCount(playerA, 0);
        assertLife(playerB, 20);
    }
}
