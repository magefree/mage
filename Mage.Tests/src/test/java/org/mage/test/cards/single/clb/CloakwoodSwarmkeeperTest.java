package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CloakwoodSwarmkeeperTest extends CardTestPlayerBase {

    @Test
    public void test_TwoTokensEnter_OneCounter() {
        // Gathered Swarm - Whenever one or more tokens enter the battlefield under your control, put a +1/+1 counter on Cloakwood Swarmkeeper.
        addCard(Zone.BATTLEFIELD, playerA, "Cloakwood Swarmkeeper"); // 1/1
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 2);
        assertCounterCount(playerA, "Cloakwood Swarmkeeper", CounterType.P1P1, 1);
    }

    @Test
    public void test_OpponentTokensEnter_NoTrigger() {
        // Gathered Swarm - Whenever one or more tokens enter the battlefield under your control, put a +1/+1 counter on Cloakwood Swarmkeeper.
        addCard(Zone.BATTLEFIELD, playerA, "Cloakwood Swarmkeeper"); // 1/1
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerB, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Raise the Alarm");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Soldier Token", 2);
        assertCounterCount(playerA, "Cloakwood Swarmkeeper", CounterType.P1P1, 0);
    }
}
