package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CloudMidgarMercenaryTest extends CardTestPlayerBase {

    // As long as Cloud is equipped, if a triggered ability of Cloud or an Equipment attached to it triggers,
    // that ability triggers an additional time.
    private static final String cloud = "Cloud, Midgar Mercenary"; // 2/1

    @Test
    public void test_EquipmentDiesTrigger_TriggersAdditionalTime() {
        addCard(Zone.BATTLEFIELD, playerA, cloud);
        // Equipped creature gets +1/-1.
        // Whenever equipped creature dies, draw two cards.
        // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Skullclamp");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);

        // Cloud becomes 3/0 and dies
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", cloud);
        setChoice(playerA, "Whenever equipped creature dies"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, cloud, 1);
        assertHandCount(playerA, 4);
    }

    @Test
    public void test_EquipmentDiesTrigger_OtherCreature_TriggersOnce() {
        addCard(Zone.BATTLEFIELD, playerA, cloud);
        // Equipped creature gets +3/+0 and has trample and lifelink.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Loxodon Warhammer");
        // Equipped creature gets +1/-1.
        // Whenever equipped creature dies, draw two cards.
        // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Skullclamp");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", cloud);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Memnite becomes 2/0 and dies
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, cloud, 5, 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_EquipmentCombatDamageTrigger_TriggersAdditionalTime() {
        addCard(Zone.BATTLEFIELD, playerA, cloud);
        // Whenever equipped creature deals combat damage, put two charge counters on Umezawa's Jitte.
        // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Umezawa's Jitte");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", cloud);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, cloud);
        setChoice(playerA, "Whenever equipped creature deals combat damage"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertCounterCount(playerA, "Umezawa's Jitte", CounterType.CHARGE, 4);
    }
}
