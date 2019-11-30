package org.mage.test.cards.mana.phyrexian;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class PhyrexianManaTest extends CardTestPlayerBase {

    @Test
    public void testNoManaToCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Apostle's Blessing");

        setChoice(playerA, "Black");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        int life = playerA.getLife();
        int hand = playerA.getHand().size();
        // can be played only through life pay
        Assert.assertTrue(life == 20 && hand == 1 || life == 18 && hand == 0);
    }
    
    @Test
    public void testKrrikOnlyUsableByController() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.HAND, playerA, "Banehound");
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Banehound");

        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banehound");
        setChoice(playerB, "Yes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banehound");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        //PlayerA pays life but PlayerB cannot
        assertLife(playerA, 18);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Banehound", 1);
        assertPermanentCount(playerB, "Banehound", 1);
    }

    @Test
    public void testKrrikTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.HAND, playerA, "Banehound");
        addCard(Zone.HAND, playerA, "Crypt Ghast");
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        setChoice(playerA, "Yes"); //yes to pay 2 life to cast Crypt Ghast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crypt Ghast"); //3 mana used, 2 life paid (18 life total)
        setChoice(playerA, "Yes"); //yes to pay 2 life to cast Banehound
        setChoice(playerA, "Yes"); //yes to Extort
        setChoice(playerA, "Yes"); //yes to pay 2 life to Extort
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banehound"); //0 mana used, 4 life paid, 1 life gained (15 life total)
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Banehound", 1);
        assertPermanentCount(playerA, "Crypt Ghast", 1);
    }
    
    @Test
    public void testKrrikActivatedAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.BATTLEFIELD, playerA, "Frozen Shade");
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        setChoice(playerA, "Yes"); //yes to pay 2 life to activate Frozen Shade's +1/+1 ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: {this} gets +1/+1 until end of turn.");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, "Frozen Shade", 1, 2);
    }

    @Test
    public void testKrrikTrinispherePostPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");
        addCard(Zone.HAND, playerA, "Dismember");
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Banehound");
        
        setChoice(playerA, "No"); //don't pay 2 life for Dismember's Phyrexian cost
        setChoice(playerA, "No"); //don't pay 2 life for Dismember's Phyrexian cost
        setChoice(playerA, "Yes"); //yes to pay 2 life for Dismember's {B} cost via K'rrik
        setChoice(playerA, "Yes"); //yes to pay 2 life for Dismember's {B} cost via K'rrik
        
        //Dismember costs {1} now + life paid. Normally this would be {3} + life paid with true Phyrexian mana and Trinisphere active.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dismember", "Banehound");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 20);
        assertTappedCount("Swamp", true, 1);
        assertGraveyardCount(playerA, "Dismember", 1);
        assertGraveyardCount(playerB, "Banehound", 1);
    }
}
