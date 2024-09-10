package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CombatDamageByToughnessTest extends CardTestPlayerBase {

    @Test
    public void testByToughnessAll() {
        addCard(Zone.BATTLEFIELD, playerA, "Doran, the Siege Tower", 1); // 0/5
        // Each creature assigns combat damage equal to its toughness rather than its power.
        addCard(Zone.BATTLEFIELD, playerB, "Kraken Hatchling", 1); // 0/4

        attack(1, playerA, "Doran, the Siege Tower");
        attack(2, playerB, "Kraken Hatchling");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 4);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void testByToughnessControlled() {
        addCard(Zone.BATTLEFIELD, playerA, "Belligerent Brontodon", 1); // 4/6
        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        addCard(Zone.BATTLEFIELD, playerB, "Maritime Guard", 1); // 1/3

        attack(1, playerA, "Belligerent Brontodon");
        attack(2, playerB, "Maritime Guard");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1);
        assertLife(playerB, 20 - 6);
    }

    @Test
    public void testByToughnessFilter() {
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Lumberknot", 1); // 1/4
        // Each creature you control with toughness greater than its power assigns combat damage equal to its toughness rather than its power.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker", 1); // 2/1

        attack(1, playerA, "Ancient Lumberknot");
        attack(1, playerA, "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4 - 2);
    }

    @Test
    public void testByToughnessTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Walking Bulwark", 1); // 0/3 Defender
        // {2}: Until end of turn, target creature with defender gains haste, can attack as though it didn't have defender,
        // and assigns combat damage equal to its toughness rather than its power. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Wild-Field Scarecrow", 1); // 1/4 Defender
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Serra's Blessing", 1); // Creatures you control have vigilance
        addCard(Zone.BATTLEFIELD, playerB, "Barony Vampire", 1); // 3/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Until", "Wild-Field Scarecrow");
        attack(1, playerA, "Wild-Field Scarecrow");
        attack(2, playerB, "Barony Vampire");
        block(2, playerA, "Wild-Field Scarecrow", "Barony Vampire");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4);
        assertDamageReceived(playerB, "Barony Vampire", 1);
        assertDamageReceived(playerA, "Wild-Field Scarecrow", 3);
    }

}
