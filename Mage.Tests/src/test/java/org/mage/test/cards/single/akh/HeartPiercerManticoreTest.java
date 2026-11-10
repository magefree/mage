package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HeartPiercerManticoreTest extends CardTestPlayerBase {

    // When this creature enters, you may sacrifice another creature. When you do, this creature deals damage equal to that creature's power to any target.
    // Embalm {5}{R}
    private static final String manticore = "Heart-Piercer Manticore"; // {2}{R}{R} 4/3
    private static final String bears = "Grizzly Bears"; // 2/2

    private void castManticoreAndSacrifice(String toSacrifice) {
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, manticore);
        setChoice(playerA, true); // sacrifice another creature
        setChoice(playerA, toSacrifice);
        addTarget(playerA, playerB);
    }

    @Test
    public void test_Sacrifice() {
        addCard(Zone.HAND, playerA, manticore);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bears);

        castManticoreAndSacrifice(bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bears, 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Decline() {
        addCard(Zone.HAND, playerA, manticore);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bears);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, manticore);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_SacrificeGermWithLivingWeapon() {
        addCard(Zone.HAND, playerA, manticore);
        // Living weapon
        // Equipped creature gets +4/+4 and has vigilance and lifelink.
        // {3}: Return Batterskull to its owner's hand.
        // Equip {5}
        addCard(Zone.HAND, playerA, "Batterskull"); // {5}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4 + 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Batterskull");
        castManticoreAndSacrifice("Phyrexian Germ Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Phyrexian Germ Token", 0);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_SacrificeWithCounters() {
        addCard(Zone.HAND, playerA, manticore);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bears);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears, CounterType.P1P1, 3);
        castManticoreAndSacrifice(bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_SacrificeWithAnthem() {
        addCard(Zone.HAND, playerA, manticore);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        // Creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");

        castManticoreAndSacrifice(bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_SacrificeWithPump() {
        addCard(Zone.HAND, playerA, manticore);
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", bears);
        castManticoreAndSacrifice(bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
    }
}
