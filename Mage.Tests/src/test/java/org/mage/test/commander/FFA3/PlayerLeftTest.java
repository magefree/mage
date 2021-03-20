package org.mage.test.commander.FFA3;

import mage.constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class PlayerLeftTest extends CardTestCommander3PlayersFFA {

    /**
     * Check that if a player left the game, it's commander is also removed
     */
    @Test
    public void TestCommanderRemoved() {

        concede(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertCommandZoneCount(playerB, "Ob Nixilis of the Black Oath", 1);
        assertCommandZoneCount(playerC, "Ob Nixilis of the Black Oath", 1);
        assertCommandZoneCount(playerA, "Ob Nixilis of the Black Oath", 0);

    }

}
