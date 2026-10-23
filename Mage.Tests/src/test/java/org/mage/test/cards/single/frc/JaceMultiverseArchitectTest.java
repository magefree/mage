package org.mage.test.cards.single.frc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Riley Jones
 */
public class JaceMultiverseArchitectTest extends CardTestPlayerBase {

    private static final String JACE = "Jace, Multiverse Architect";

    @Test
    public void testCastAndLoyalty() {
        addCard(Zone.HAND, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, JACE);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, JACE, 1);
        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 4);
    }

    @Test
    public void testCombatAbility_OpponentDoesNotPay() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // Turn 2 is Player B's turn. Player B has no mana, chooses not to pay {2}
        setChoice(playerB, false); // Don't pay {2}

        // Grizzly Bears tries to attack Jace, but cannot.
        attack(2, playerB, "Grizzly Bears", JACE);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute because Grizzly Bears cannot attack Jace");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, JACE, 1);
        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 4); // Did not take damage
        assertTapped("Grizzly Bears", false);
    }

    @Test
    public void testCombatAbility_OpponentPays() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        // Turn 2 is Player B's turn. Player B pays {2}
        setChoice(playerB, true); // Pay {2}

        attack(2, playerB, "Grizzly Bears", JACE);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, JACE, 1);
        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 2); // 4 - 2 combat damage = 2
        assertTappedCount("Mountain", true, 2);
    }

    @Test
    public void testPlusOneAbility() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);

        // +1: Draw two cards, then put a card from your hand on the bottom of your library.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Draw two cards");
        addTarget(playerA, "Island"); // Card to put on bottom of library

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 5);
        // Started with 1 in hand, drew 2, put 1 on bottom -> 2 in hand
        assertHandCount(playerA, 2);
        // Island should be on the bottom of the library
        Assert.assertEquals("Island", playerA.getLibrary().getFromBottom(currentGame).getName());
    }

    @Test
    public void testMinusThreeAbility() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        // Set up library: Top has non-creatures, then a creature (Serra Angel), then more cards
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Serra Angel");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Counterspell");

        // -3: Exile another target planeswalker or creature you control. Reveal cards...
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile another target planeswalker or creature you control.", "Memnite");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 1); // 4 - 3 = 1
        assertExileCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Serra Angel", 1);
    }

    @Test
    public void testCombatAbility_CanStillAttackPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        setChoice(playerB, false); // Don't pay {2}

        // Grizzly Bears attacks playerA directly
        attack(2, playerB, "Grizzly Bears", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, JACE, 1);
        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 4);
        assertLife(playerA, 18); // 20 - 2 = 18
    }

    @Test
    public void testCombatAbility_ProtectsOtherJaces() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        setChoice(playerB, false); // Don't pay {2}

        // Grizzly Bears tries to attack Jace Beleren, but cannot
        attack(2, playerB, "Grizzly Bears", "Jace Beleren");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute because Grizzly Bears cannot attack Jace Beleren");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about not being able to attack, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Jace Beleren", 1);
        assertCounterCount(playerA, "Jace Beleren", CounterType.LOYALTY, 3); // 3 starting loyalty untouched
        assertTapped("Grizzly Bears", false);
    }

    @Test
    public void testCombatAbility_DoesNotProtectNonJacePlaneswalkers() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Torch of Defiance", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        setChoice(playerB, false); // Don't pay {2}

        // Grizzly Bears attacks Chandra, Torch of Defiance
        attack(2, playerB, "Grizzly Bears", "Chandra, Torch of Defiance");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Chandra, Torch of Defiance", 1);
        assertCounterCount(playerA, "Chandra, Torch of Defiance", CounterType.LOYALTY, 2); // 4 - 2 = 2
    }

    @Test
    public void testMinusThreeExilesPlaneswalkerAndFindsPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, JACE);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Torch of Defiance", 1);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Teferi, Hero of Dominaria");
        addCard(Zone.LIBRARY, playerA, "Plains");

        // -3: Exile another target planeswalker or creature you control...
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile another target planeswalker or creature you control.", "Chandra, Torch of Defiance");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, JACE, CounterType.LOYALTY, 1);
        assertExileCount(playerA, "Chandra, Torch of Defiance", 1);
        assertPermanentCount(playerA, "Teferi, Hero of Dominaria", 1);
        assertCounterCount(playerA, "Teferi, Hero of Dominaria", CounterType.LOYALTY, 4);
    }
}
