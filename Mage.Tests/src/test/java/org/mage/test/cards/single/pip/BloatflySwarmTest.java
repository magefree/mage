package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BloatflySwarmTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BloatflySwarm Bloatfly Swarm} {3}{B}
     * Creature — Insect Mutant
     * Flying
     * Bloatfly Swarm enters the battlefield with five +1/+1 counters on it.
     * If damage would be dealt to Bloatfly Swarm while it has a +1/+1 counter on it, prevent that damage, remove that many +1/+1 counters from it, then give each player a rad counter for each +1/+1 counter removed this way.
     * 0/0
     */
    private static final String swarm = "Bloatfly Swarm";

    @Test
    public void test_Bolt() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, swarm);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swarm, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", swarm);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, swarm, 2, 2);
        assertCounterCount(playerA, CounterType.RAD, 3);
        assertCounterCount(playerB, CounterType.RAD, 3);
    }

    @Test
    public void test_Combat() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Concordant Crossroads"); // For haste
        addCard(Zone.HAND, playerA, swarm);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Brimstone Dragon"); // 6/6 Flying Haste
        addCard(Zone.BATTLEFIELD, playerB, "Giant Spider"); // 2/4 Reach

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swarm);

        attack(1, playerA, swarm);
        block(1, playerB, "Brimstone Dragon", swarm);
        block(1, playerB, "Giant Spider", swarm);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, swarm, 1);
        assertCounterCount(playerA, CounterType.RAD, 5);
        assertCounterCount(playerB, CounterType.RAD, 5);
    }

    @Test
    public void test_Combat_Small() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Concordant Crossroads"); // For haste
        addCard(Zone.HAND, playerA, swarm);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Wind Drake"); // 2/2 Flying
        addCard(Zone.BATTLEFIELD, playerB, "Giant Spider"); // 2/4 Reach

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swarm);

        attack(1, playerA, swarm);
        block(1, playerB, "Wind Drake", swarm);
        block(1, playerB, "Giant Spider", swarm);
        setChoice(playerA, "X=5"); // damage attribution

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, swarm, 1, 1);
        assertCounterCount(playerA, CounterType.RAD, 4);
        assertCounterCount(playerB, CounterType.RAD, 4);
    }
}
