package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.GameImpl;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

import java.util.Map;

/**
 * Make sure AI uses level up ability, but not too much (over the max useful level - Issue 441).
 *
 * @author ayratn
 */
public class LevelUpAbilityTest extends CardTestBase {

    @Test
    public void testLevelUpAbilityUsage() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 15);
        setStopOnTurn(3);

        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertNotNull(master);

        System.out.println("Results:");
        for (Map.Entry<String, Counter> counter : master.getCounters().entrySet()) {
            System.out.println(counter.getKey() + " : " + counter.getValue().getName() + " : " + counter.getValue().getCount());
        }

        Assert.assertNotNull(master.getCounters());
        Assert.assertFalse(master.getCounters().isEmpty());
        Assert.assertEquals(12, master.getCounters().getCount(CounterType.LEVEL));

        System.out.println("Copy count: " + GameImpl.copyCount);
        System.out.println("Copy time: " + GameImpl.copyTime + " ms");
    }
}
