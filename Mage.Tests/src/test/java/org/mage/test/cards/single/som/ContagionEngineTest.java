
package org.mage.test.cards.single.som;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ContagionEngineTest extends CardTestPlayerBase {

    // When Contagion Engine enters the battlefield, put a -1/-1 counter on each creature target player controls.
    // {4}, {T}: Proliferate, then proliferate again. (You choose any number of permanents and/or players with
    //           counters on them, then give each another counter of a kind already there. Then do it again.)
    @Test
    public void testCountersArePut() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",6);
        addCard(Zone.HAND, playerA, "Contagion Engine");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pincher Beetles"); // 3/1 + Shroud
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Wolf"); // 3/1 + Hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Beloved Chaplain"); // 3/1 + Protection from creatures

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Contagion Engine");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);

        assertGraveyardCount(playerB, "Sacred Wolf",1);
        assertGraveyardCount(playerB, "Beloved Chaplain",1);
        assertGraveyardCount(playerB, "Pincher Beetles",1);

    }

    @Test
    public void testCountersDoubledByProliferate() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",6);
        addCard(Zone.HAND, playerA, "Contagion Engine");

        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane");

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Frost"); // 0/7
        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Behemoth"); // 9/9 + Shroud
        addCard(Zone.BATTLEFIELD, playerB, "Plated Slagwurm"); // 8/8 + Hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Teysa, Envoy of Ghosts"); // 4/4 + Protection from creatures

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Contagion Engine");
        addTarget(playerA, playerB);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}: Proliferate");
        setChoice(playerA, "Wall of Frost^Kalonian Behemoth^Plated Slagwurm^Teysa, Envoy of Ghosts^Ajani Goldmane");
        setChoice(playerA, "Wall of Frost^Kalonian Behemoth^Plated Slagwurm^Teysa, Envoy of Ghosts^Ajani Goldmane");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 6);
        
        assertPowerToughness(playerB, "Kalonian Behemoth", 6, 6);
        assertPowerToughness(playerB, "Plated Slagwurm", 5, 5);
        assertPowerToughness(playerB, "Teysa, Envoy of Ghosts", 1, 1);
        assertPowerToughness(playerB, "Wall of Frost", -3, 4);

        
    }

}