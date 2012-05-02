package org.mage.test.cards.abilities.activated;

import junit.framework.Assert;
import mage.Constants;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class LevelUpAbilityTest extends CardTestPlayerBase {

    /**
     * Tests that putting level up counters really makes effect
     */
    @Test
    public void testLevelChanged() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        for (int i = 0; i < 12; i++) {
            activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertNotNull(master);

        Assert.assertNotNull(master.getCounters());
        Assert.assertFalse(master.getCounters().isEmpty());
        Assert.assertEquals(12, master.getCounters().getCount(CounterType.LEVEL));
        
        Assert.assertEquals("Power different", 9, master.getPower().getValue());
        Assert.assertEquals("Toughness different", 9, master.getToughness().getValue());
        Assert.assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertTrue(master.getAbilities().contains(IndestructibleAbility.getInstance()));
    }
}
