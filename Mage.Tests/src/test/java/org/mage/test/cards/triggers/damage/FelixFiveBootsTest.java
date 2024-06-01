package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar, Susucr
 * https://scryfall.com/card/otc/42/felix-five-boots
 */
public class FelixFiveBootsTest extends CardTestPlayerBase {

    // Trample. Whenever Belligerent Guest deals combat damage to a player, create a Blood token.
    private static final String vampire = "Belligerent Guest";

    @Test
    public void testBasicFelixFunctionality() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, vampire);

        attack(1, playerA, vampire, playerB);
        setChoice(playerA, "Whenever {this} deals combat damage to a player, create a Blood token."); // need to order the triggers
        checkStackSize("two triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testDoubleTriggerDeadAttacker() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, vampire);
        addCard(Zone.BATTLEFIELD, playerB, "Moss Viper"); // 1/1 Deathtouch

        attack(1, playerA, vampire, playerB);
        block(1, playerB, "Moss Viper", vampire);

        setChoice(playerA, "X=1"); // assign damage to Moss Viper
        setChoice(playerA, "Whenever {this} deals combat damage to a player, create a Blood token."); // need to order the triggers
        checkStackSize("two triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testNoBonusTriggerForEnemy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, vampire);
        addCard(Zone.BATTLEFIELD, playerB, "Felix Five-Boots");

        attack(1, playerA, vampire, playerB);
        checkStackSize("one trigger", 1, PhaseStep.COMBAT_DAMAGE, playerA, 1);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testNoTriggerOnNonCombatDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, "Nettle Drone"); // {T}: {this} deals 1 damage to each opponent
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Curiosity"); // Whenever enchanted creature deals damage to an opponent, you may draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curiosity", "Nettle Drone", true);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{T}: {this} deals 1 damage to each opponent");
        checkStackSize("one trigger", 1, PhaseStep.BEGIN_COMBAT, playerA, 1);
        setChoice(playerA, true); // yes to Curiosity "you may draw"

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testBatchEvent() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        // Whenever Olivia's Attendants deals damage, create that many Blood tokens. 6/6
        addCard(Zone.BATTLEFIELD, playerA, "Olivia's Attendants");

        attack(1, playerA, "Olivia's Attendants", playerB);
        setChoice(playerA, "Whenever {this} deals damage, create that many Blood tokens."); // need to order the triggers
        checkStackSize("two triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Blood Token", 6 + 6);
    }

    @Test
    @Ignore // see #12095
    public void testSelectRightPartOfBatch() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        // Whenever equipped creature deals combat damage, put two charge counters on Umezawa’s Jitte.
        addCard(Zone.BATTLEFIELD, playerA, "Umezawa's Jitte");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Blossoms");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Elite Vanguard");

        attack(1, playerA, "Elite Vanguard", playerB);
        attack(1, playerA, "Raging Goblin", playerB);
        block(1, playerB, "Wall of Blossoms", "Elite Vanguard");

        checkStackSize("only one Jitte triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 1);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Umezawa's Jitte", CounterType.CHARGE, 2);
    }
}
