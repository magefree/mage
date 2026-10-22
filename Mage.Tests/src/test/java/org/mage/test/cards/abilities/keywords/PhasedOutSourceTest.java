package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PhasedOutSourceTest extends CardTestPlayerBase {

    // Target artifact, creature, or land phases out.
    private static final String ripple = "Reality Ripple";

    @Test
    public void test_OneShot_PhasedOutSource_PutOnLibrary() {
        // When Saprazzan Outrigger attacks or blocks, put it on top of its owner's library at end of combat.
        addCard(Zone.BATTLEFIELD, playerA, "Saprazzan Outrigger"); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, ripple);

        attack(1, playerA, "Saprazzan Outrigger");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, ripple, "Saprazzan Outrigger");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Outrigger phases in on turn 3
        execute();

        // it was phased out at end of combat, so the delayed trigger can't move it
        assertLibraryCount(playerA, "Saprazzan Outrigger", 0);
        assertPermanentCount(playerA, "Saprazzan Outrigger", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_OneShot_PhasedOutSource_PutOnLibrary_TeferisVeil() {
        // When Saprazzan Outrigger attacks or blocks, put it on top of its owner's library at end of combat.
        addCard(Zone.BATTLEFIELD, playerA, "Saprazzan Outrigger"); // 5/5
        // Whenever a creature you control attacks, it phases out at end of combat.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi's Veil");

        attack(1, playerA, "Saprazzan Outrigger");
        setChoice(playerA, "Teferi's Veil"); // order attack triggers
        setChoice(playerA, "Saprazzan Outrigger"); // order end of combat triggers, the phase out resolves first

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Saprazzan Outrigger", 0);
        assertPermanentCount(playerA, "Saprazzan Outrigger", 1);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_OneShot_PhasedOutSource_Monstrosity() {
        // {6}{G}: Monstrosity 4.
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Asp"); // 4/5
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, ripple);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{G}: Monstrosity 4");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, ripple, "Nessian Asp");

        // phased out when monstrosity resolved, so it didn't become monstrous and can do it again
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{G}: Monstrosity 4");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Nessian Asp", CounterType.P1P1, 4);
        assertPowerToughness(playerA, "Nessian Asp", 4 + 4, 5 + 4);
    }

    @Test
    public void test_OneShot_PhasedOutSource_CountersOnSource() {
        // Whenever a creature dies, if it had power greater than Drizzt's power,
        // put a number of +1/+1 counters on Drizzt equal to the difference.
        addCard(Zone.BATTLEFIELD, playerA, "Drizzt Do'Urden"); // 3/3
        // Whenever one or more +1/+1 counters are put on a creature you control,
        // put that many growth counters on Simic Ascendancy.
        addCard(Zone.BATTLEFIELD, playerA, "Simic Ascendancy");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm"); // 6/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Craw Wurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve Murder, keep the Drizzt trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Drizzt Do'Urden");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Drizzt phases in on turn 3
        execute();

        // the counters can't be put on a phased-out Drizzt, so Simic Ascendancy doesn't trigger
        assertCounterCount(playerA, "Drizzt Do'Urden", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Drizzt Do'Urden", 3, 3);
        assertCounterCount(playerA, "Simic Ascendancy", CounterType.GROWTH, 0);
    }

    @Test
    public void test_ForAsLongAsYouControl_PhasedOutSource() {
        // When Master Thief enters, gain control of target artifact for as long as you control Master Thief.
        addCard(Zone.HAND, playerA, "Master Thief"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, ripple);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master Thief");
        addTarget(playerA, "Ornithopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("stolen", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ripple, "Master Thief");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Master Thief phases in on turn 3
        execute();

        // the effect ended when Master Thief phased out (702.26f) and doesn't come back when it phases in
        assertPermanentCount(playerA, "Master Thief", 1);
        assertPermanentCount(playerB, "Ornithopter", 1);
        assertPermanentCount(playerA, "Ornithopter", 0);
    }

    @Test
    public void test_ExileUntilSourceLeaves_PhasedOutSource_StillExiles() {
        // When Alabaster Host Intercessor enters the battlefield, exile target creature an opponent controls
        // until Alabaster Host Intercessor leaves the battlefield.
        addCard(Zone.HAND, playerA, "Alabaster Host Intercessor"); // {5}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, ripple);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alabaster Host Intercessor");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve the creature, keep the trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, ripple, "Alabaster Host Intercessor");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN); // Intercessor phases in on turn 3
        execute();

        // phasing out isn't leaving the battlefield, so the creature is still exiled
        assertPermanentCount(playerA, "Alabaster Host Intercessor", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_ControlChange_PhasedOutSource_KeepsControl() {
        // When Sleeper Agent enters, target opponent gains control of it.
        // At the beginning of your upkeep, Sleeper Agent deals 2 damage to you.
        addCard(Zone.HAND, playerA, "Sleeper Agent"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, ripple);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleeper Agent");
        addTarget(playerA, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("given", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sleeper Agent", 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, ripple, "Sleeper Agent");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN); // Sleeper Agent phases in on playerB's turn 2
        execute();

        // phasing doesn't end the control-changing effect
        assertPermanentCount(playerB, "Sleeper Agent", 1);
        assertLife(playerB, 20 - 2);
        assertLife(playerA, 20);
    }
}
