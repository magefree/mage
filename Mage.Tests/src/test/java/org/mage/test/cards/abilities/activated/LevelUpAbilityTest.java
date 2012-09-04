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
     * Tests creature without any level up counter
     */
    @Test
    public void testFirstLevel() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertTrue(master.getCounters().isEmpty());

        Assert.assertEquals(3, master.getPower().getValue());
        Assert.assertEquals(3, master.getToughness().getValue());
        Assert.assertFalse(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(master.getAbilities().contains(new IndestructibleAbility()));
    }

    /**
     * Tests that putting level up counters really makes effect
     */
    @Test
    public void testFirstLevelWithOneCounter() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertEquals(1, master.getCounters().getCount(CounterType.LEVEL));

        Assert.assertEquals(3, master.getPower().getValue());
        Assert.assertEquals(3, master.getToughness().getValue());
        Assert.assertFalse(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(master.getAbilities().contains(new IndestructibleAbility()));
    }

    /**
     * Tests second level that gives Lifelink as well as 6/6
     */
    @Test
    public void testSecondLevel() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        for (int i = 0; i < 6; i++) {
            activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertEquals(6, master.getCounters().getCount(CounterType.LEVEL));

        Assert.assertEquals(6, master.getPower().getValue());
        Assert.assertEquals(6, master.getToughness().getValue());
        // since now Lifelink will appear
        Assert.assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        // but still no Indestructible
        Assert.assertFalse(master.getAbilities().contains(new IndestructibleAbility()));
    }

    /**
     * Tests third level that gives both Lifelink and Indestructible as well as 9/9
     */
    @Test
    public void testThirdLevel() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        for (int i = 0; i < 12; i++) {
            activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertEquals(12, master.getCounters().getCount(CounterType.LEVEL));

        Assert.assertEquals("Power different", 9, master.getPower().getValue());
        Assert.assertEquals("Toughness different", 9, master.getToughness().getValue());
        Assert.assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertTrue(master.getAbilities().contains(new IndestructibleAbility()));
    }

    /**
     * Tests that extra counters won't make any effect over third level
     */
    @Test
    public void testExtraCounters() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 15);

        for (int i = 0; i < 15; i++) {
            activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Assert.assertEquals(15, master.getCounters().getCount(CounterType.LEVEL));

        Assert.assertEquals("Power different", 9, master.getPower().getValue());
        Assert.assertEquals("Toughness different", 9, master.getToughness().getValue());
        Assert.assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertTrue(master.getAbilities().contains(new IndestructibleAbility()));
    }

}
