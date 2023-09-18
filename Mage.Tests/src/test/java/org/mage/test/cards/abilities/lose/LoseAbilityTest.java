package org.mage.test.cards.abilities.lose;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class LoseAbilityTest extends CardTestPlayerBase {

    @Test
    public void testLoseFlyingByEnchantCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grounded", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grounded", "Air Elemental");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertNotNull(eliteVanguard);
        Assert.assertFalse(eliteVanguard.getAbilities().contains(FlyingAbility.getInstance()));

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);
        // should NOT have flying
        Assert.assertFalse(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests that first losing ability and then gaining it will results in Flying existence
     */
    @Test
    public void testLoseVsGainAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grounded");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Drake Umbra");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Air Elemental");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);

        Assert.assertTrue(airElemental.getAttachments().size() == 2);
        // should have flying
        Assert.assertTrue(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }

    /**
     * Tests that first gaining multiple copies of ability and then losing it will results in Flying not existence
     */
    @Test
    public void testMultiGainVsLoseAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.HAND, playerA, "Grounded");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Drake Umbra", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Umbra", "Air Elemental");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grounded", "Air Elemental");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent airElemental = getPermanent("Air Elemental", playerA.getId());
        Assert.assertNotNull(airElemental);

        Assert.assertEquals(3, airElemental.getAttachments().size());
        // should NOT have flying
        Assert.assertFalse(airElemental.getAbilities().contains(FlyingAbility.getInstance()));
    }
    
    /**
     * Tests that gaining two times a triggered ability and losing one will result in only one triggering
     */
    @Test
    public void testMultiGainTriggeredVsLoseAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Sublime Archangel",2);
        /*
         * Sublime Archangel English
         * Creature — Angel 4/3, 2WW
         * Flying
         * Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
         * Other creatures you control have exalted. (If a creature has multiple instances of exalted, each triggers separately.)
        */
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Turn to Frog");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Turn to Frog", "Sublime Archangel");
        
        attack(3, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }
}
