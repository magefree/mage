package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.10d
 * To double a player’s life total, the player gains or loses an amount of life such that their
 * new life total is twice its current value.
 *
 * @author JayDi85
 */
public class EnduringAngelTest extends CardTestPlayerBase {

    @Test
    public void test_AngelicEnforcer_Attacks_DoubleLifeTotal() {
        addCustomEffect_TargetDamage(playerA, 20);

        // If your life total would be reduced to 0 or less, instead transform Enduring Angel and your life total
        // becomes 3.
        // Angelic Enforcer: Whenever Angelic Enforcer attacks, double your life total.
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Angel");

        // transform, life 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 20", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("transformed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);

        attack(1, playerA, "Angelic Enforcer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 3 + 3);
    }

    @Test
    public void test_AngelicEnforcer_Attacks_DoubleNegativeLifeTotal() {
        addCustomEffect_TargetDamage(playerA, 20);
        addCustomEffect_TargetDamage(playerA, 8);

        // If your life total would be reduced to 0 or less, instead transform Enduring Angel and your life total
        // becomes 3.
        // Angelic Enforcer: Whenever Angelic Enforcer attacks, double your life total.
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Angel");
        //
        // You can't lose the game and your opponents can't win the game.
        addCard(Zone.BATTLEFIELD, playerA, "Platinum Angel");

        // transform, life 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 20", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("transformed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);

        // life -5 with the trigger on the stack (the enforcer dies as a 0 or less toughness creature, the trigger stays)
        attack(1, playerA, "Angelic Enforcer");
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "target damage 8", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Enduring Angel", 1);
        assertLife(playerA, (3 - 8) * 2);
    }
}
