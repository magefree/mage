package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */

public class EntwineTest extends CardTestPlayerBase {

    @Test
    public void test_CastWithoutEntwine() {
        // Choose one —
        //• Barbed Lightning deals 3 damage to target creature.
        //• Barbed Lightning deals 3 damage to target player or planeswalker.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, false); // not use Entwine
        setModeChoice(playerA, "1"); // target creature
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void test_CastEntwine_Normal() {
        // Choose one —
        //• Barbed Lightning deals 3 damage to target creature.
        //• Barbed Lightning deals 3 damage to target player or planeswalker.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, true); // use Entwine
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 3);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertTappedCount("Mountain", true, 3 + 2);
    }

    @Test
    public void test_CastEntwine_CostReduction() {
        addCustomEffect_SpellCostModification(playerA, -4);

        // Choose one —
        //• Barbed Lightning deals 3 damage to target creature.
        //• Barbed Lightning deals 3 damage to target player or planeswalker.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1); // -4 as cost reduction
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, true); // use Entwine
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 3);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_CastEntwine_CostIncreasing() {
        addCustomEffect_SpellCostModification(playerA, 5);

        // Choose one —
        //• Barbed Lightning deals 3 damage to target creature.
        //• Barbed Lightning deals 3 damage to target player or planeswalker.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 2 + 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, true); // use Entwine
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 3);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertTappedCount("Mountain", true, 3 + 2 + 5);
    }

    @Test
    public void test_CastEntwine_FreeFromHand() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        // Choose one —
        //• Barbed Lightning deals 3 damage to target creature.
        //• Barbed Lightning deals 3 damage to target player or planeswalker.
        // Entwine {2} (Choose both if you pay the entwine cost.)
        addCard(Zone.HAND, playerA, "Barbed Lightning", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // only Entwine pay need
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barbed Lightning");
        setChoice(playerA, true); // cast for free
        setChoice(playerA, true); // use Entwine
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 3);
        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void test_ToothAndNail() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        // Choose one -
        // Search your library for up to two creature cards, reveal them, put them into your hand, then shuffle your library;
        // or put up to two creature cards from your hand onto the battlefield.
        // Entwine {2}
        addCard(Zone.HAND, playerA, "Tooth and Nail"); // Sorcery {5}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tooth and Nail");
        setChoice(playerA, true); // Message: Pay Entwine {2} ?
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");
        setChoice(playerA, "Silvercoat Lion^Pillarfield Ox");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
    }
}
    