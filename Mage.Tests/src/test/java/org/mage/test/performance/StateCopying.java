package org.mage.test.performance;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * These tests are used to benchmark the performance of the copying of the state.
 * <p>
 * Leave the tests comment out when pushing, uncomment only when testing locally.
 *
 * @author Alex-Vasile
 */
public class StateCopying extends CardTestPlayerBase {

    @Test
    @Ignore
    public void copyingBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Sapphire Medallion", 10);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        for (int i = 0; i < 100000; i++) {
            currentGame.getBattlefield().reset(currentGame);
        }
    }
}
