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
        setStrictChooseMode(true);
        
        // ({B/P} can be paid with either {B} or 2 life.)        
        // Lifelink
        // For each {B} in a cost, you may pay 2 life rather than pay that mana.
        // Whenever you cast a black spell, put a +1/+1 counter on K'rrik, Son of Yawgmoth.        
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.HAND, playerA, "Banehound");
        
        // Lifelink, haste        
        addCard(Zone.HAND, playerB, "Banehound"); // Creature {B} 1/1

        checkPlayableAbility("pay 2 life for Banehound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Banehound", true);  
        
        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banehound");

        checkPlayableAbility("no Mana for Banehound", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Banehound", false);            

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        //PlayerA pays life but PlayerB cannot
        assertLife(playerA, 18);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Banehound", 1);
        assertPermanentCount(playerB, "Banehound", 0);
    }

    @Test
    public void testKrrikTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        addCard(Zone.HAND, playerA, "Banehound");
        addCard(Zone.HAND, playerA, "Crypt Ghast");
        
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        setChoice(playerA, true); //yes to pay 2 life to cast Crypt Ghast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crypt Ghast", true); //3 mana used, 2 life paid (18 life total)
        setChoice(playerA, true); //yes to pay 2 life to cast Banehound
        setChoice(playerA, true); //yes to Extort
        setChoice(playerA, true); //yes to pay 2 life to Extort
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

        setChoice(playerA, true); //yes to pay 2 life to activate Frozen Shade's +1/+1 ability
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
        
        setChoice(playerA, false); //don't pay 2 life for Dismember's Phyrexian cost
        setChoice(playerA, false); //don't pay 2 life for Dismember's Phyrexian cost
        setChoice(playerA, true); //yes to pay 2 life for Dismember's {B} cost via K'rrik
        setChoice(playerA, true); //yes to pay 2 life for Dismember's {B} cost via K'rrik
        
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

   @Test
    public void testPlayerCanCastBanehoundWithoutAvailableBlackMana() {
        setStrictChooseMode(true);
        
        // ({B/P} can be paid with either {B} or 2 life.)        
        // Lifelink
        // For each {B} in a cost, you may pay 2 life rather than pay that mana.
        // Whenever you cast a black spell, put a +1/+1 counter on K'rrik, Son of Yawgmoth.        
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth"); // Creature {4}{B/P}{B/P}{B/P} 2/2
        addCard(Zone.HAND, playerA, "Banehound");
                        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banehound");
        setChoice(playerA, true); // Pay 2 life for {B}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

       //PlayerA pays life
        assertLife(playerA, 18);
        assertLife(playerB, 20);
        
        
        assertPermanentCount(playerA, "Banehound", 1);
    }    
    
   @Test
    public void testPlayerEffectNotUsableIfKrrikNotOnBattlefield() {
        setStrictChooseMode(true);
        
        // addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // ({B/P} can be paid with either {B} or 2 life.)        
        // Lifelink
        // For each {B} in a cost, you may pay 2 life rather than pay that mana.
        // Whenever you cast a black spell, put a +1/+1 counter on K'rrik, Son of Yawgmoth.        
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth"); // Creature {4}{B/P}{B/P}{B/P} 2/2
        addCard(Zone.HAND, playerA, "Banehound");
        
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        
        castSpell(1, PhaseStep.UPKEEP, playerB, "Lightning Bolt", "K'rrik, Son of Yawgmoth");
                
        checkPlayableAbility("no Mana for Banehound", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Banehound", false);                

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

       assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "K'rrik, Son of Yawgmoth", 1);
        
        assertHandCount(playerA, "Banehound", 1);
        //PlayerA pays life
        assertLife(playerA, 20);
        assertLife(playerB, 20);
       
    }    
}
