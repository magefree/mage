package org.mage.test.cards.single.neo;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MindlinkMechTest extends CardTestPlayerBase {

    @Test
    public void test_BecomeCopyOfCrewer() {
        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech
        // becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle
        // artifact in addition to its other types, and it has flying.
        // Crew 1
        addCard(Zone.BATTLEFIELD, playerA, "Mindlink Mech@mech"); // vehicle 4/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // elf 1/1, {T}: Add {G}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Llanowar Elves"); // crew with elves
        addTarget(playerA, "Llanowar Elves"); // copy the elves

        // until end of turn
        checkPermanentCount("copy", 1, PhaseStep.BEGIN_COMBAT, playerA, "Mindlink Mech", 0);
        checkPermanentCount("copy", 1, PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // gain
        assertPowerToughness(playerA, "@mech", 4, 3);
        assertAbility(playerA, "@mech", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "@mech", new GreenManaAbility(), true);
    }

    @Test
    public void test_BecomeCopyOfCrewer_EndsWithTurn() {
        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech
        // becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle
        // artifact in addition to its other types, and it has flying.
        // Crew 1
        addCard(Zone.BATTLEFIELD, playerA, "Mindlink Mech"); // vehicle 4/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // elf 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Llanowar Elves"); // crew with elves
        addTarget(playerA, "Llanowar Elves"); // copy the elves

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Mindlink Mech", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }


    @Test
    public void test_LegendaryCrewerCantBeCopied() {
        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech
        // becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle
        // artifact in addition to its other types, and it has flying.
        // Crew 1
        addCard(Zone.BATTLEFIELD, playerA, "Mindlink Mech"); // vehicle 4/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda"); // legendary dog 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Isamaru, Hound of Konda"); // crew with a legendary creature, no legal target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mindlink Mech", 1);
        assertPermanentCount(playerA, "Isamaru, Hound of Konda", 1);
        assertPowerToughness(playerA, "Mindlink Mech", 4, 3);
    }

    @Test
    public void test_OnlyFirstCrewInTurnTriggers() {
        // The first crew of the turn has no legal target (a legendary crewer), the second crew of the same turn
        // doesn't trigger again, so Mindlink Mech stays itself.

        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech
        // becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle
        // artifact in addition to its other types, and it has flying.
        // Crew 1
        addCard(Zone.BATTLEFIELD, playerA, "Mindlink Mech"); // vehicle 4/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda"); // legendary dog 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // elf 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Isamaru, Hound of Konda"); // first crew with a legendary creature, no legal target
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // only the legendary creature
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Llanowar Elves"); // second crew with elves, no trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mindlink Mech", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPowerToughness(playerA, "Mindlink Mech", 4, 3);
    }

    @Test
    public void test_FirstCrewOfNextTurnTriggers() {
        // "first time each turn" - a crew in an earlier turn doesn't stop the trigger of a later turn.

        // Whenever Mindlink Mech becomes crewed for the first time each turn, until end of turn, Mindlink Mech
        // becomes a copy of target nonlegendary creature that crewed it this turn, except it's 4/3, it's a Vehicle
        // artifact in addition to its other types, and it has flying.
        // Crew 1
        addCard(Zone.BATTLEFIELD, playerA, "Mindlink Mech@mech"); // vehicle 4/3, flying
        //
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda"); // legendary dog 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // elf 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Isamaru, Hound of Konda"); // turn 1 crew with a legendary creature, no legal target
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // only the legendary creature

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Llanowar Elves"); // turn 3 crew with elves
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // only elves
        addTarget(playerA, "Llanowar Elves"); // copy the elves

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mindlink Mech", 0);
        assertPermanentCount(playerA, "Llanowar Elves", 2);
        assertPowerToughness(playerA, "@mech", 4, 3);
    }
}
