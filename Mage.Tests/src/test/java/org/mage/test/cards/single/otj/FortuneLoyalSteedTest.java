package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FortuneLoyalSteedTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FortuneLoyalSteed Fortune, Loyal Steed} {W}
     * Legendary Creature — Beast Mount
     * When Fortune, Loyal Steed enters the battlefield, scry 2.
     * Whenever Fortune attacks while saddled, at end of combat, exile it and up to one creature that saddled it this turn, then return those cards to the battlefield under their owner’s control.
     * Saddle 1
     * 2/4
     */
    private static final String fortune = "Fortune, Loyal Steed";

    @Test
    public void test_Saddling() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, fortune, playerB);

        setChoice(playerA, "Lone Missionary"); // Choose to blink Lone Missionary

        setChoice(playerA, "When {this} enters the battlefield, you gain 4 life"); // stack triggers
        addTarget(playerA, "Taiga"); // for the scry trigger

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertHandCount(playerA, 0);
        assertTapped(fortune, false);
        assertTapped("Lone Missionary", false);
    }

    @Test
    public void test_Saddling_FortuneDies() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerB, "Ankle Biter"); // 1/1 Deathtouch

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, fortune, playerB);
        block(1, playerB, "Ankle Biter", fortune);

        setChoice(playerA, "Lone Missionary"); // Choose to blink Lone Missionary

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertGraveyardCount(playerA, fortune, 1);
        assertTapped("Lone Missionary", false);
    }

    @Test
    public void test_Saddling_FortuneBlinks() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Ephemerate");
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, fortune, playerB);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        setChoice(playerA, "Lone Missionary"); // Choose to blink Lone Missionary

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertTapped(fortune, false);
        assertTapped("Lone Missionary", false);
    }

    @Test
    public void test_Saddling_FortuneBlinksBefore() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor"); // To give haste
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Ephemerate", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        // Just to check zcc
        castSpell(1, PhaseStep.UPKEEP, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, fortune, playerB);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        setChoice(playerA, "Lone Missionary"); // Choose to blink Lone Missionary

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertTapped(fortune, false);
        assertTapped("Lone Missionary", false);
    }

    @Test
    public void test_Saddling_FortuneBlinksAfterSaddlingBeforeCombat() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor"); // To give haste
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Ephemerate");
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // That would make Fortune no longer saddled, so not trigger at beginning of combat
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        attack(1, playerA, fortune, playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped(fortune, true);
        assertTapped("Lone Missionary", true);
    }

    @Test
    public void test_Saddling_FortuneBlinksInResponseOfSaddling() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor"); // To give haste
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Ephemerate");
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");

        // That would make Fortune no longer saddled, so not trigger at beginning of combat
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        attack(1, playerA, fortune, playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped(fortune, true);
        assertTapped("Lone Missionary", true);
    }

    @Test
    public void test_Saddling_FortuneBlinksTwice() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fortune);
        addCard(Zone.BATTLEFIELD, playerA, "Lone Missionary"); // ETB, gain 4 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Ephemerate", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);

        setChoice(playerA, "Lone Missionary"); // Saddling choice
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, fortune, playerB);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Ephemerate", fortune, true);
        addTarget(playerA, "Taiga"); // for the scry trigger

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Ephemerate", fortune);
        addTarget(playerA, "Taiga"); // for the scry trigger

        setChoice(playerA, "Lone Missionary"); // Choose to blink Lone Missionary

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertTapped(fortune, false);
        assertTapped("Lone Missionary", false);
    }
}
