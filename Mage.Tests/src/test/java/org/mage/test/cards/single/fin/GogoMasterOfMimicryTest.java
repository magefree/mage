package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GogoMasterOfMimicryTest extends CardTestPlayerBase {

    @Test
    public void test_NotPlayableWithoutEnoughManaForMinX() {
        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets
        // for the copies. This ability can't be copied and X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Gogo, Master of Mimicry");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // only {1}, need {2} for X=1
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkPlayableAbility("not enough mana for X=1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}:", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_CopyActivatedAbility_X2() {
        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets
        // for the copies. This ability can't be copied and X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Gogo, Master of Mimicry");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4); // {2}{2} for X=2
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}:", "stack ability ({T}:");
        setChoice(playerA, "X=2"); // {2}{2} = {4}
        setChoice(playerA, false); // keep original target playerB for first copy
        setChoice(playerA, false); // keep original target playerB for second copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1 - 2);
        assertTappedCount("Island", true, 4);
    }

    @Test
    public void test_CopyActivatedAbility_NewTargets() {
        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets
        // for the copies. This ability can't be copied and X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Gogo, Master of Mimicry");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // {1}{1} for X=1
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}:", "stack ability ({T}:");
        setChoice(playerA, "X=1"); // {1}{1} = {2}
        setChoice(playerA, true); // choose new target for copy
        addTarget(playerA, "Llanowar Elves"); // redirect copy to Llanowar Elves

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1);
        assertGraveyardCount(playerB, "Llanowar Elves", 1);
        assertTappedCount("Island", true, 2);
    }

    @Test
    public void test_AbilityCantBeCopied() {
        // {X}{X}, {T}: Copy target activated or triggered ability you control X times. You may choose new targets
        // for the copies. This ability can't be copied and X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Gogo, Master of Mimicry");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // {1}{1} for X=1
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        // {2}, {T}: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Lithoform Engine");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2); // {2} for Lithoform Engine

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}:", "stack ability ({T}:");
        setChoice(playerA, "X=1"); // {1}{1} = {2}
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:", "stack ability ({X}{X}");
        setChoice(playerA, false); // keep original target playerB for Gogo's single copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Lithoform Engine cannot copy Gogo's ability, so only original + 1 copy resolve
        assertLife(playerB, 20 - 1 - 1);
        assertTapped("Lithoform Engine", true);
    }
}
