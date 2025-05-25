package org.mage.test.cards.single.mom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GhaltaAndMavrenTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GhaltaAndMavren Ghalta and Mavren} {3}{G}{G}{W}{W}
     * Legendary Creature — Dinosaur Vampire
     * Trample
     * Whenever you attack, choose one —
     * • Create a tapped and attacking X/X green Dinosaur creature token with trample, where X is the greatest power among other attacking creatures.
     * • Create X 1/1 white Vampire creature tokens with lifelink, where X is the number of other attacking creatures.
     * 12/12
     */
    private static final String ghalta = "Ghalta and Mavren";

    @Test
    public void test_ghalta_attack_alone_dinosaur() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1); // to trigger on token death

        attack(1, playerA, ghalta, playerB);
        setModeChoice(playerA, "1"); // create 0/0 Dinosaur
        addTarget(playerA, playerB); // Blood Artist trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 0);
        assertPermanentCount(playerA, "Vampire Token", 0);
        assertLife(playerB, 20 - 12 - 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void test_ghalta_attack_alone_vampire() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1); // to trigger on token death

        attack(1, playerA, ghalta, playerB);
        setModeChoice(playerA, "2"); // create 0 1/1 Vampire

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 0);
        assertPermanentCount(playerA, "Vampire Token", 0);
        assertLife(playerB, 20 - 12);
        assertLife(playerA, 20);
    }

    @Test
    public void test_vanguard_attack_alone_dinosaur() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1); // 2/1

        attack(1, playerA, "Elite Vanguard", playerB);
        setModeChoice(playerA, "1"); // create 2/2 Dinosaur

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 1);
        assertPowerToughness(playerA, "Dinosaur Token", 2, 2);
        assertPermanentCount(playerA, "Vampire Token", 0);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_bear_attack_alone_vampire() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1); // 2/1

        attack(1, playerA, "Elite Vanguard", playerB);
        setModeChoice(playerA, "2"); // create 1 1/1 Vampire

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 0);
        assertPermanentCount(playerA, "Vampire Token", 1);
        assertLife(playerB, 20 - 2);
    }


    @Test
    public void test_vanguard_and_ghalta_attack_dinosaur() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1); // 2/1

        attack(1, playerA, ghalta, playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        setModeChoice(playerA, "1"); // create 2/2 Dinosaur

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 1);
        assertPowerToughness(playerA, "Dinosaur Token", 2, 2);
        assertPermanentCount(playerA, "Vampire Token", 0);
        assertLife(playerB, 20 - 12 - 2 - 2);
    }

    @Test
    public void test_vanguard_and_ghalta_attack_vampire() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1); // 2/1

        attack(1, playerA, ghalta, playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        setModeChoice(playerA, "2"); // create 1 1/1 Vampire

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 0);
        assertPermanentCount(playerA, "Vampire Token", 1);
        assertLife(playerB, 20 - 12 - 2);
    }


    @Test
    public void test_varied_attackers_dinosaur() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Barony Vampire"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Armored Wolf-Rider"); // 4/6

        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Barony Vampire", playerB);
        attack(1, playerA, "Armored Wolf-Rider", playerB);
        setModeChoice(playerA, "1"); // create a 4/4 Dinosaur

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 1);
        assertPowerToughness(playerA, "Dinosaur Token", 4, 4);
        assertPermanentCount(playerA, "Vampire Token", 0);
        assertLife(playerB, 20 - 4 - 3 - 2 - 4);
    }

    @Test
    public void test_5_attackers_vampire() {
        addCard(Zone.BATTLEFIELD, playerA, ghalta);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 5); // 2/1

        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);
        setModeChoice(playerA, "2"); // create 5 1/1 Vampire

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Dinosaur Token", 0);
        assertPermanentCount(playerA, "Vampire Token", 5);
        assertLife(playerB, 20 - 2 * 5);
    }
}
