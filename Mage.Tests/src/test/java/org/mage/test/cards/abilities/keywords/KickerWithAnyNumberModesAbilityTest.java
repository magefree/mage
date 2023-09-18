package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class KickerWithAnyNumberModesAbilityTest extends CardTestPlayerBase {

    @Test
    public void test_WithoutKicker() {
        // Kicker {2}{G}
        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        // • Target player gains X life, where X is the greatest power among creatures they control.
        // • Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Inscription of Abundance", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inscription of Abundance");
        setChoice(playerA, false); // no kicker
        setModeChoice(playerA, "1");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        assertLife(playerA, 20);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void test_Kicker_Normal() {
        // Kicker {2}{G}
        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        // • Target player gains X life, where X is the greatest power among creatures they control.
        // • Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Inscription of Abundance", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 3);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inscription of Abundance");
        setChoice(playerA, true); // use kicker
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        addTarget(playerA, playerA); // gain x life
        addTarget(playerA, "Balduvian Bears"); // get counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        assertLife(playerA, 20 + 4);
        assertTappedCount("Forest", true, 2 + 3);
    }

    @Test
    public void test_Kicker_CostReduction() {
        addCustomEffect_SpellCostModification(playerA, -4);

        // Kicker {2}{G}
        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        // • Target player gains X life, where X is the greatest power among creatures they control.
        // • Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Inscription of Abundance", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 3 - 3); // -3 by cost reduction
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inscription of Abundance");
        setChoice(playerA, true); // use kicker
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        addTarget(playerA, playerA); // gain x life
        addTarget(playerA, "Balduvian Bears"); // get counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        assertLife(playerA, 20 + 4);
        assertTappedCount("Forest", true, 2 + 3 - 3);
    }

    @Test
    public void test_Kicker_CostIncreasing() {
        addCustomEffect_SpellCostModification(playerA, 5);

        // Kicker {2}{G}
        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        // • Target player gains X life, where X is the greatest power among creatures they control.
        // • Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Inscription of Abundance", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 3 + 5); // +5 by cost increase
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inscription of Abundance");
        setChoice(playerA, true); // use kicker
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        addTarget(playerA, playerA); // gain x life
        addTarget(playerA, "Balduvian Bears"); // get counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        assertLife(playerA, 20 + 4);
        assertTappedCount("Forest", true, 2 + 3 + 5);
    }

    @Test
    public void test_Kicker_FreeFromHand() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        // Kicker {2}{G}
        // Choose one. If this spell was kicked, choose any number instead.
        // • Put two +1/+1 counters on target creature.
        // • Target player gains X life, where X is the greatest power among creatures they control.
        // • Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Inscription of Abundance", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3); // -2 by free cast
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inscription of Abundance");
        setChoice(playerA, true); // use free cast
        setChoice(playerA, true); // use kicker
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "1");
        addTarget(playerA, playerA); // gain x life
        addTarget(playerA, "Balduvian Bears"); // get counters

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 2, 2 + 2);
        assertLife(playerA, 20 + 4);
        assertTappedCount("Forest", true, 3);
    }
}
