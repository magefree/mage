package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 3); // improvise pay

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bastion Inventor");
        setChoice(playerA, "Blue", 3); // pay 1-3
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Alpha Myr^Alpha Myr"); // pay 4 and 5 as improvise
        addTarget(playerA, TestPlayer.TARGET_SKIP); // choose only 2 of 3 possible permanents
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Alpha Myr"); // pay 6 as improvise

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bastion Inventor", 1);
        assertTappedCount("Alpha Myr", true, 3);
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

        setStrictChooseMode(false); // AI must choose targets
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

    @Test
    public void test_PlayImprovise_CantPayColorless() {
        // {2}{C}
        addCard(Zone.HAND, playerA, "Matter Reshaper", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Inspiring Statuary", 1); // Nonartifact spells you cast have improvise.
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1); // makes it castable
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 3);

        disableManaAutoPayment(playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Matter Reshaper");
        setChoice(playerA, "Improvise");
        addTarget(playerA, "Memnite^Memnite"); // {2}
        setChoice(playerA, "Improvise"); // only {C} is left
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
            Assert.fail("improvise must not pay {C}");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't use choice in play mana: Improvise")) {
                Assert.fail("must not offer improvise for {C}, but got:\n" + e.getMessage());
            }
        }
        assertTappedCount("Memnite", true, 2);
    }

    @Test
    public void test_PlayImprovise_CantPayCostOnResolution() {
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);
        // {1}{U} Instant
        // Counter target spell unless its controller pays {3}.
        addCard(Zone.HAND, playerA, "Mana Leak", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Inspiring Statuary", 1); // Nonartifact spells you cast have improvise.
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mana Leak", "Grizzly Bears");
        // the lands pay for both spells. Improvise can help cast a spell, but it can't pay the {3} Mana Leak asks for
        setChoice(playerA, true); // pay {3}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Memnite", false, 3);
    }
}
