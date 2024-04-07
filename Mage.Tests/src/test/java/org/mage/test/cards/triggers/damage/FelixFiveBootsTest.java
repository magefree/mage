package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 * https://scryfall.com/card/otc/42/felix-five-boots
 */
public class FelixFiveBootsTest extends CardTestPlayerBase {

    // Trample. Whenever Belligerent Guest deals combat damage to a player, create a Blood token.
    private static final String vampire = "Belligerent Guest";

    @Test
    public void testBasicFelixFunctionality() {
        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, vampire);

        attack(1, playerA, vampire, playerB);
        checkStackSize("two triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);
        execute();
    }

    @Test
    public void testDoubleTriggerDeadAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, vampire);
        addCard(Zone.BATTLEFIELD, playerB, "Moss Viper"); // 1/1 Deathtouch

        attack(1, playerA, vampire, playerB);
        block(1, playerB, "Moss Viper", vampire);
        checkStackSize("two triggers", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);
        execute();
    }

    @Test
    public void testNoBonusTriggerForEnemy() {
        addCard(Zone.BATTLEFIELD, playerA, vampire);
        addCard(Zone.BATTLEFIELD, playerB, "Felix Five-Boots");

        attack(1, playerA, vampire, playerB);
        checkStackSize("one trigger", 1, PhaseStep.COMBAT_DAMAGE, playerA, 1);
        execute();
    }

    @Test
    public void testNoTriggerOnNonCombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Felix Five-Boots");
        addCard(Zone.BATTLEFIELD, playerA, "Nettle Drone"); // {T}: {this} deals 1 damage to each opponent
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Curiosity"); // Whenever enchanted creature deals damage to an opponent, you may draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curiosity", "Nettle Drone", true);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{T}: {this} deals 1 damage to each opponent");
        checkStackSize("one trigger", 1, PhaseStep.BEGIN_COMBAT, playerA, 1);
        execute();
    }
}
