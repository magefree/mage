package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author notgreat
 */
public class TolarianContemptTest extends CardTestCommander4Players {

    @Test
    public void testEachOpponent() {
        addCard(Zone.HAND, playerA, "Tolarian Contempt");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerD, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerC, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tolarian Contempt", true);

        addTarget(playerA, "Raging Goblin"); //target playerD
        addTarget(playerA, "Memnite"); //target playerC
        addTarget(playerA, TestPlayer.TARGET_SKIP); //target playerB
        setChoice(playerD, true);
        setChoice(playerC, true);

        setStopAt(2, PhaseStep.UNTAP);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerD, 0);
        assertLibraryCount(playerD, "Raging Goblin", 1);
        assertPermanentCount(playerC, 1);
        assertCounterCount(playerC, "Memnite", CounterType.REJECTION, 1);
        assertLibraryCount(playerC, "Memnite", 1);
    }

}
