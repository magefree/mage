package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class ImproviseTest extends CardTestPlayerBaseWithAIHelps {

    // no simple playable tests for improvise, it's same as ConvokeTest

    @Test
    public void test_PlayImprovise_Manual() {
        // {5}{U} creature
        // Improvise (Your artifacts can help cast this spell. Each artifact you tap after you’re done activating mana abilities pays for {1}.)
        addCard(Zone.HAND, playerA, "Bastion Inventor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2); // improvise pay

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bastion Inventor");
        setChoice(playerA, "Blue", 4); // pay 1-4
        // improvise pay by one card
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Alpha Myr"); // pay 5 as improvise
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Alpha Myr"); // pay 6 as improvise

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bastion Inventor", 1);
    }

    @Test
    public void test_PlayImprovise_AI_AutoPay() {
        // {5}{U} creature
        // Improvise (Your artifacts can help cast this spell. Each artifact you tap after you’re done activating mana abilities pays for {1}.)
        addCard(Zone.HAND, playerA, "Bastion Inventor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2); // improvise pay

        // AI must use special actions to pay as delve
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bastion Inventor");

        //setStrictChooseMode(true); AI must choose targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bastion Inventor", 1);
    }

    @Test
    public void test_PlayImprovise_AI_FullPlay() {
        // {5}{U} creature
        // Improvise (Your artifacts can help cast this spell. Each artifact you tap after you’re done activating mana abilities pays for {1}.)
        addCard(Zone.HAND, playerA, "Bastion Inventor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 2); // improvise pay

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bastion Inventor", 1);
    }
}