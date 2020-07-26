package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BrashTaunterTest extends CardTestPlayerBase {

    @Test
    public void testTriggerDamageToOpponent(){
        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Brash Taunter");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // Shock deals 2 damage to any target
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Brash Taunter");
        addTarget(playerA, playerB);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 18);
    }

    @Test
    public void testFightAbility(){
        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        // {2}{R}, {T}: Brash Taunter fights another target creature.
        addCard(Zone.BATTLEFIELD, playerA, "Brash Taunter");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}", "Serra Angel");
        addTarget(playerA, playerB);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 16);
    }
}
