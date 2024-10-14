package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author notgreat
 * Based on ImproviseTest by JayDi85
 */

public class HeirloomEpicTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_PlayHeirloomEpic_Manual() {
        // {4} to activate (payable by tapping)
        addCard(Zone.BATTLEFIELD, playerA, "Heirloom Epic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}:");
        setChoice(playerA, "Blue", 2); // pay 1-2
        setChoice(playerA, "For each");
        addTarget(playerA, "Alpha Myr"); // pay 3 as tap
        setChoice(playerA, "For each");
        addTarget(playerA, "Alpha Myr"); // pay 4 as tap

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertTappedCount("Heirloom Epic", true, 1);
        assertTappedCount("Island", true, 2);
        assertTappedCount("Alpha Myr", true, 2);
    }

    @Test
    public void test_PlayHeirloomEpic_AI_AutoPay() {
        // {4} to activate (payable by tapping)
        addCard(Zone.BATTLEFIELD, playerA, "Heirloom Epic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2);

        // AI must use special actions to pay
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}:");

        setStrictChooseMode(false); // AI must choose targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertTappedCount("Heirloom Epic", true, 1);
        assertTappedCount("Island", true, 2);
        assertTappedCount("Alpha Myr", true, 2);
    }

    @Test
    public void test_PlayHeirloomEpic_AI_FullPlay() {
        // {1} to cast, {4} to activate (payable by tapping)
        addCard(Zone.HAND, playerA, "Heirloom Epic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA); //Cast the Heirloom Epic
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA); //Activate the Heirloom Epic

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertTappedCount("Heirloom Epic", true, 1);
        assertTappedCount("Island", true, 3);
        assertTappedCount("Alpha Myr", true, 2);
    }
}
