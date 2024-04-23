package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.multiplayer.MultiplayerTriggerTest;

/**
 * @author Susucr
 */
public class SowerOfDiscordTest extends MultiplayerTriggerTest {

    /**
     * {@link mage.cards.s.SowerOfDiscord Sower of Discord} {4}{B}{B}
     * Creature — Demon
     * Flying
     * As Sower of Discord enters the battlefield, choose two players.
     * Whenever damage is dealt to one of the chosen players, the other chosen player also loses that much life.
     * 6/6
     */
    private static final String sower = "Sower of Discord";

    @Test
    public void test_Trigger_Bolt_First() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sower);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sower, true);
        addTarget(playerA, playerB, playerD);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40 - 3);
        assertLife(playerC, 40);
        assertLife(playerD, 40 - 3);
    }

    @Test
    public void test_Trigger_Bolt_Second() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sower);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sower, true);
        addTarget(playerA, playerB, playerD);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerD);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40 - 3);
        assertLife(playerC, 40);
        assertLife(playerD, 40 - 3);
    }

    @Test
    public void test_NoTrigger_Bolt_Other() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sower);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sower, true);
        addTarget(playerA, playerB, playerD);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40 - 3);
        assertLife(playerB, 40);
        assertLife(playerC, 40);
        assertLife(playerD, 40);
    }

    @Test
    public void test_Trigger_Attack_Both() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sower);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Eager Cadet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sower);
        addTarget(playerA, playerB, playerD);

        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Eager Cadet", playerD);

        // No ruling on that, but current implementation has 1 trigger per damaged player.
        setChoice(playerA, "Whenever damage is dealt");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40 - 2 - 1);
        assertLife(playerC, 40);
        assertLife(playerD, 40 - 1 - 2);
    }

    @Test
    public void test_Trigger_NonCombat_Both() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sower);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerC, "Flame Rift"); // 4 damage to each player
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sower);
        addTarget(playerA, playerB, playerD);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Flame Rift");

        // No ruling on that, but current implementation has 1 trigger per damaged player.
        setChoice(playerA, "Whenever damage is dealt");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40 - 4);
        assertLife(playerB, 40 - 4 - 4);
        assertLife(playerC, 40 - 4);
        assertLife(playerD, 40 - 4 - 4);
    }
}
