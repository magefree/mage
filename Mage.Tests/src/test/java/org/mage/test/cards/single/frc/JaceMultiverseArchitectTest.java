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

    /**
     * Jace, Multiverse Architect {1}{W}{U}{B}{R}
     * Legendary Planeswalker — Jace, loyalty 4
     * At the beginning of combat on each opponent's turn, they may pay {2}. If they don't, creatures they control
     * can't attack Jaces you control this turn.
     * +1: Draw two cards, then put a card from your hand on the bottom of your library.
     * −3: Exile another target planeswalker or creature you control. Reveal cards from the top of your library until
     * you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your
     * library in a random order.
     * Jace, Multiverse Architect can be your commander.
     */
    private static final String jace = "Jace, Multiverse Architect";

    @Test
    public void test_Combat_NotPaid_CantAttackJace() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2); // can pay, but doesn't

        setChoice(playerB, false); // don't pay {2}
        attack(2, playerB, "Grizzly Bears", jace);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            Assert.assertEquals("Missing ATTACKER def for turn 2, step DECLARE_ATTACKERS, PlayerB\n"
                    + "select attackers must use attack command but don't", e.getMessage());
        }

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4);
        assertTappedCount("Mountain", false, 2);
    }

    @Test
    public void test_Combat_Paid_CanAttackJace() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        setChoice(playerB, true); // pay {2}
        attack(2, playerB, "Grizzly Bears", jace);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4 - 2);
        assertTappedCount("Mountain", true, 2);
    }

    @Test
    public void test_Combat_NotPaid_CanAttackPlayerAndOtherPlaneswalkers() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Torch of Defiance"); // loyalty 4
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear"); // 2/2

        setChoice(playerB, false); // don't pay {2}
        attack(2, playerB, "Grizzly Bears", playerA);
        attack(2, playerB, "Runeclaw Bear", "Chandra, Torch of Defiance");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertCounterCount(playerA, "Chandra, Torch of Defiance", CounterType.LOYALTY, 4 - 2);
        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4);
    }

    @Test
    public void test_Combat_NotPaid_CantAttackOtherJaces() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Jace Beleren"); // loyalty 3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        setChoice(playerB, false); // don't pay {2}
        attack(2, playerB, "Grizzly Bears", "Jace Beleren");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            Assert.assertEquals("Missing ATTACKER def for turn 2, step DECLARE_ATTACKERS, PlayerB\n"
                    + "select attackers must use attack command but don't", e.getMessage());
        }

        assertCounterCount(playerA, "Jace Beleren", CounterType.LOYALTY, 3);
    }

    @Test
    public void test_Combat_NotPaid_RestrictionEndsThisTurn() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        // turn 2 - restricted
        setChoice(playerB, false); // don't pay {2}

        // turn 4 - the old restriction is gone
        setChoice(playerB, true); // pay {2}
        attack(4, playerB, "Grizzly Bears", jace);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4 - 2);
    }

    @Test
    public void test_PlusOne_DrawAndPutOnBottom() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.HAND, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Draw two cards");
        addTarget(playerA, "Island"); // can put any card from hand, not only a drawn one

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4 + 1);
        assertHandCount(playerA, 1 + 2 - 1);
        Assert.assertEquals("Island", playerA.getLibrary().getFromBottom(currentGame).getName());
    }

    @Test
    public void test_MinusThree_FirstCreatureAndRestOnBottom() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // not revealed, must stay on top
        addCard(Zone.LIBRARY, playerA, "Serra Angel"); // revealed, battlefield
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // revealed, bottom

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, jace, CounterType.LOYALTY, 4 - 3);
        assertExileCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Serra Angel", 1);
        Assert.assertEquals("Hill Giant", playerA.getLibrary().getFromTop(currentGame).getName());
        Assert.assertEquals("Lightning Bolt", playerA.getLibrary().getFromBottom(currentGame).getName());
    }

    @Test
    public void test_MinusThree_PlaneswalkerForPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Torch of Defiance");
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Teferi, Hero of Dominaria"); // loyalty 4
        addCard(Zone.LIBRARY, playerA, "Mountain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile", "Chandra, Torch of Defiance");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Chandra, Torch of Defiance", 1);
        assertCounterCount(playerA, "Teferi, Hero of Dominaria", CounterType.LOYALTY, 4);
        Assert.assertEquals("Mountain", playerA.getLibrary().getFromBottom(currentGame).getName());
    }

    @Test
    public void test_MinusThree_CantTargetItselfOrOpponentCreature() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        checkPlayableAbility("no another own creature or planeswalker", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_MinusThree_BlinkedTarget_NothingRevealed() {
        addCard(Zone.BATTLEFIELD, playerA, jace);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Ephemerate"); // {W}, exile, then return it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Serra Angel");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", "Grizzly Bears", "Exile another target planeswalker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1); // new object, not exiled
        assertPermanentCount(playerA, "Serra Angel", 0);
        assertLibraryCount(playerA, "Serra Angel", 1);
    }
}
