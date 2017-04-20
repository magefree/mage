package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by IGOUDT on 30-3-2017.
 */
public class KiraGreatGlassSpinnerTest extends CardTestPlayerBase {
    
    private final String kira = "Kira, Great Glass-Spinner";

    @Test
    public void counterFirst() {
        
        String ugin = "Ugin, the Spirit Dragon";
        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyality counters

        /*
        Kira, Great Glass-Spinner  {1}{U}{U}
        Legendary Creature - Spirit 2/2
        Flying
        Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability."
        */
        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {source} deals 3 damage to target creature or player.", kira); // Ugin ability

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
    }
}
