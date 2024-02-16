package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9
 */
public class DecimatorBeetleTest extends CardTestPlayerBase {

    /*    
Decimator Beetle {3}{B}{G}
Creature - Insect 4/5
When Decimator Beetle enters the battlefield, put a -1/-1 counter on target creature you control.
Whenever Decimator Beetle attacks, remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls.
    */
    private final String decimator = "Decimator Beetle";

    @Test
    public void targetOpponentCreatureWithDecimator() {

        String grizzly = "Grizzly Bears"; // {1}{G} 2/2
        String hillGiant = "Hill Giant"; // {3}{R} 3/3

        addCard(Zone.HAND, playerA, decimator);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, grizzly);
        addCard(Zone.BATTLEFIELD, playerB, hillGiant);

        // put -1/-1 on own creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, decimator);
        addTarget(playerA, grizzly);

        // remove -1/-1 from own creature and put to defender control
        attack(3, playerA, decimator);
        addTarget(playerA, grizzly); // remove
        addTarget(playerA, hillGiant); // put

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, grizzly, 2, 2); // had -1/-1 counter, but removed on attack
        assertPowerToughness(playerB, hillGiant, 2, 2); // gets -1/-1 counter from decimator attack ability
        assertCounterCount(playerA, grizzly, CounterType.M1M1, 0);
        assertCounterCount(playerB, hillGiant, CounterType.M1M1, 1);
        assertLife(playerB, 16);
    }
}
