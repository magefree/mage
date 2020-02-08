package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LifelinkInCombatTest extends CardTestPlayerBase {
    @Test
    public void testHeliodTriggersOnceOnBlockerTrample() {
        // 4/4 Trample Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Elderwood Scion");
        // Whenever you gain life, put a +1/+1 counter on target creature or enchantment you control.
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, Sun-Crowned");
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Elderwood Scion");
        block(1, playerB, "Grizzly Bears", "Elderwood Scion");
        setChoice(playerA, "X=2"); // Damage to Grizzly Bears
        addTarget(playerA, "Elderwood Scion");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Elderwood Scion", 5, 5);
        assertLife(playerA, 24);
        assertLife(playerB, 18);
    }
}
