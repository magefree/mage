package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AuroraShifterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AuroraShifter Aurora Shifter} {1}{U}
     * Creature - Shapeshifter
     * Changeling
     * Whenever Aurora Shifter deals combat damage to a player, you get that many {E}.
     * At the beginning of combat on your turn, you may pay {E}{E}.
     * When you do, Aurora Shifter becomes a copy of another target creature you control,
     * except it has this ability and "Whenever this creature deals combat damage to a player, you get that many {E}."
     *
     * 1/3
     */
    private static final String shifter = "Aurora Shifter";

    private static void checkEnergyCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCountersCount(CounterType.ENERGY));
    }

    @Test
    public void test_4_combats() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, shifter);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

//        Combat 1
        attack(1, playerA, shifter, playerB);
        runCode("energy counter is 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 1));
        setStopAt(1, PhaseStep.END_TURN);

//        Combat 2
        attack(3, playerA, shifter, playerB);
        runCode("energy counter is 2", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));
        setStopAt(3, PhaseStep.END_TURN);

//        Combat 3 - can pay for copy
        addTarget(playerA, "Grizzly Bears"); // Aurora Shifter becomes a copy of another target creature you control
        setChoice(playerA, true); // you may pay {E}{E}.
        showBattlefield("BEGIN_COMBAT", 5, PhaseStep.BEGIN_COMBAT, playerA);
        showBattlefield("DECLARE_ATTACKERS", 5, PhaseStep.DECLARE_ATTACKERS, playerA);
        attack(5, playerA, "Grizzly Bears", playerB);
        runCode("energy counter is 0", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> checkEnergyCount(info, player, 2));
        setStopAt(5, PhaseStep.END_TURN);
        execute();
        assertLife(playerB, 20 - 1 - 1 - 2); // 1 damage from being shifter, 1 from shifter, 2 from transforming into Grizzly Bears
        assertCounterCount(playerA, CounterType.ENERGY, 2); // 2 Energy from being a Grizzly Bears
    }

}
