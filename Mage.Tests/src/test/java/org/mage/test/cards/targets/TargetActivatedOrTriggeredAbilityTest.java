package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Target activated or triggered ability: a spell on the stack is not an ability and can't be chosen
 *
 * @author JayDi85
 */
public class TargetActivatedOrTriggeredAbilityTest extends CardTestPlayerBase {

    @Test
    public void test_CopyAbilityNotSpell_LithoformEngine() {
        // {2}, {T}: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Lithoform Engine");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Copy target activated", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // ability on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Copy target activated", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_CopyAbilityNotSpell_AdricMathematicalGenius() {
        // {2}{U}, {T}: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Adric, Mathematical Genius");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}, {T}: Copy target activated", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // ability on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}, {T}: Copy target activated", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_CopyAbilityNotSpell_VantressVisions() {
        // Vantress Visions {1}{U}, instant - adventure
        // Copy target activated or triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Virtue of Knowledge");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Vantress Visions", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // ability on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Vantress Visions", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_CopyAbilityNotSpell_GogoMasterOfMimicry() {
        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets
        // for the copies. This ability can't be copied and X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Gogo, Master of Mimicry");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}: Copy target activated", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // ability on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}: Copy target activated", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_CopyAbilityNotSpell_PeterParkersCamera() {
        // This artifact enters with three film counters on it.
        // {2}, {T}, Remove a film counter from this artifact: Copy target activated or triggered ability you control.
        // You may choose new targets for the copy.
        addCard(Zone.HAND, playerA, "Peter Parker's Camera"); // {1}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // prepare camera with counters
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Peter Parker's Camera");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Remove a film counter", false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // ability on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Remove a film counter", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_CounterAbility_NimbleObstructionist() {
        // Cycling {2}{U}
        // When you cycle this card, counter target activated or triggered ability you don't control.
        addCard(Zone.HAND, playerA, "Nimble Obstructionist");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        // ability on the stack - counter it
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals 1 damage", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {2}{U}");
        addTarget(playerA, "stack ability ({T}"); // counter pyromancer's ability

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Nimble Obstructionist", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void test_CounterAbilityNotSpell_NimbleObstructionist() {
        // Cycling {2}{U}
        // When you cycle this card, counter target activated or triggered ability you don't control.
        addCard(Zone.HAND, playerA, "Nimble Obstructionist");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // spell on the stack - cycle without trigger target
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {2}{U}");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Nimble Obstructionist", 1);
        assertLife(playerA, 20 - 3);
    }
}
