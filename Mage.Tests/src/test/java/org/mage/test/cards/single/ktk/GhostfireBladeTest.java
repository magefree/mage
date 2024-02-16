package org.mage.test.cards.single.ktk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class GhostfireBladeTest extends CardTestPlayerBase {

    @Test
    public void test_CanPlayWithCostReduce() {
        // Equipped creature gets +2/+2.
        // Equip {3}
        // Ghostfire Blade’s equip ability costs {2} less to activate if it targets a colorless creature.
        addCard(Zone.BATTLEFIELD, playerA, "Ghostfire Blade", 1);
        //
        addCard(Zone.HAND, playerA, "Alpha Myr", 1); // {2}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        checkPlayableAbility("can't play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", false);

        // add creature and activate cost reduce
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Myr");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't play wit no mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", false); // no mana after creature cast

        // can play on next turn
        checkPlayableAbility("can play", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Alpha Myr");

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Alpha Myr", 2 + 2, 1 + 2);
    }

}
