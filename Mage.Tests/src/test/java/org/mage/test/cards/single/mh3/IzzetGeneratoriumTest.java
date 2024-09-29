package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class IzzetGeneratoriumTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.i.IzzetGeneratorium Izzet Generatorium} {U}{R}
     * Artifact
     * If you would get one or more {E} (energy counters), you get that many plus one {E} instead.
     * {T}: Draw a card. Activate only if you’ve paid or lost four or more {E} this turn.
     */
    private static final String generator = "Izzet Generatorium";

    private static void checkEnergyCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCountersCount(CounterType.ENERGY));
    }

    @Test
    public void test_Pay_Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, generator);
        // When Bristling Hydra enters the battlefield, you get {E}{E}{E} (three energy counters).
        // Pay {E}{E}{E}: Put a +1/+1 counter on Bristling Hydra. It gains hexproof until end of turn.
        addCard(Zone.HAND, playerA, "Bristling Hydra", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bristling Hydra", true);
        runCode("energy counter is 4", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 4));
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bristling Hydra", true);
        runCode("energy counter is 8", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 8));

        checkPlayableAbility("1: condition not met before activing once", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}{E}");
        runCode("energy counter is 5", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 5));
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("2: condition not met before activing twice", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay {E}{E}{E}");
        runCode("energy counter is 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("3: condition met after activing twice", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_Lose_Energy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, generator);
        // When Bristling Hydra enters the battlefield, you get {E}{E}{E} (three energy counters).
        // Pay {E}{E}{E}: Put a +1/+1 counter on Bristling Hydra. It gains hexproof until end of turn.
        addCard(Zone.HAND, playerA, "Bristling Hydra");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        // Choose one or more —
        //• Destroy all creatures.
        //• Destroy all planeswalkers.
        //• Destroy all battles.
        //• Exile all graveyards.
        //• Each opponent loses all counters.
        addCard(Zone.HAND, playerB, "Final Act");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bristling Hydra", true);
        runCode("energy counter is 4", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 4));

        checkPlayableAbility("1: condition not met before losing counters", 2, PhaseStep.UPKEEP, playerA, "{T}: Draw", false);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Final Act");
        setModeChoice(playerB, "5"); // each opponent loses all counters.
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        runCode("energy counter is 0", 2, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 0));
        checkPlayableAbility("2: condition met after losing counters", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw", true);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }
}
