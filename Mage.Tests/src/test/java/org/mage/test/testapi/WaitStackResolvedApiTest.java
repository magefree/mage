package org.mage.test.testapi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class WaitStackResolvedApiTest extends CardTestPlayerBase {

    @Test
    public void test_Spells() {
        addCard(Zone.HAND, playerA, "Firebolt", 1); // sorcery
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // instant
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkStackSize("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // prepare - cast 3 spells
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkStackSize("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        checkStackObject("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firebolt", 1);
        checkStackObject("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 2);

        // skip 1 (bolt)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        checkStackObject("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firebolt", 1);
        checkStackObject("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 1);

        // skip 2 (bolt)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firebolt", 1);
        checkStackObject("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 0);

        // skip 3 (firebolt)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkStackObject("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Firebolt", 0);
        checkStackObject("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Abilities() {
        // {1}{R}, Sacrifice Pyromania: Pyromania deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Pyromania", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // {2}: Goblin Cannon deals 1 damage to any target. Sacrifice Goblin Cannon.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Cannon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * 2);

        String goblinAbility = "{2}:";
        String pyroAbility = "{1}{R}, Sacrifice";

        checkStackSize("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // prepare - activate 3 abilities
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroAbility, playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, playerB);
        checkStackSize("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        checkStackObject("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroAbility, 1);
        checkStackObject("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, 2);
        showStack("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // skip 1 (goblin)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        checkStackObject("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroAbility, 1);
        checkStackObject("after 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, 1);

        // skip 2 (pyro)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroAbility, 0);
        checkStackObject("after 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, 1);

        // skip 3 (goblin)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkStackObject("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, pyroAbility, 0);
        checkStackObject("after 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, goblinAbility, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
