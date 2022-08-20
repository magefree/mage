package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BrashTaunterTest extends CardTestPlayerBase {

    @Test
    public void test_TriggerDamageToOpponent_FromSpell() {
        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Brash Taunter");
        //
        // Shock deals 2 damage to any target
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Brash Taunter");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_TriggerDamageToOpponent_FromFight() {
        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        addCard(Zone.BATTLEFIELD, playerB, "Brash Taunter");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Balduvian Bears");
        block(1, playerB, "Brash Taunter", "Balduvian Bears");
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
    }

    @Test
    public void test_FightAbility() {
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

        assertLife(playerB, 16);
    }
}
