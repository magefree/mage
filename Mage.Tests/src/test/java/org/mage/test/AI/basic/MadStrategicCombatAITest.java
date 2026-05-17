package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestComputerPlayerMadStrategic;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class MadStrategicCombatAITest extends CardTestCommander4Players {

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        TestPlayer testPlayer = new TestPlayer(new TestComputerPlayerMadStrategic(name, rangeOfInfluence, 6));
        testPlayer.setAIPlayer(false);
        return testPlayer;
    }

    @Test
    public void strategicAiReservesBlockerForTappedOpponentThreatThatWillUntap() {
        setLife(playerA, 5);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Craw Wurm", 1, true);

        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);
        checkAttackers("keep one blocker for crackback", 1, playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Balduvian Bears", true, 1);
        assertTappedCount("Balduvian Bears", false, 1);
    }
}
