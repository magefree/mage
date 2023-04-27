
package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class CastCommanderTest extends CardTestCommanderDuelBase {
    
    @Test
    public void testCastCommander() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ob Nixilis of the Black Oath");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertPermanentCount(playerA, "Ob Nixilis of the Black Oath", 1);
    }     
    
    @Test
    public void testCastKestiaCommander() {
        // Bestow {3}{G}{W}{U}
        // Enchanted creature gets +4/+4.
        // Whenever an enchanted creature or enchantment creature you control attacks, draw a card.        
        addCard(Zone.COMMAND, playerA, "Kestia, the Cultivator", 1); // Creature  {1}{G}{W}{U} 4/4
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kestia, the Cultivator using bestow", "Silvercoat Lion");

        setStrictChooseMode(true);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        
        assertPermanentCount(playerA, "Kestia, the Cultivator", 1);
        
        Permanent kestia = getPermanent("Kestia, the Cultivator", playerA);
        Assert.assertNotEquals("Kestia may not be an creature", true, kestia.isCreature(currentGame));
        Assert.assertEquals("Kestia has to be an enchantment", true, kestia.isEnchantment(currentGame));
        
        assertPowerToughness(playerA, "Silvercoat Lion", 6, 6);
    }     
    
   
    @Test
    public void testCastPatronOfTheOrochiCommander() {
        // Snake offering (You may cast this card any time you could cast an instant 
        // by sacrificing a Snake and paying the difference in mana costs between this 
        // and the sacrificed Snake. Mana cost includes color.)
        // {T}: Untap all Forests and all green creatures. Activate this ability only once each turn.    
        addCard(Zone.COMMAND, playerA, "Patron of the Orochi", 1); // Creature  {6}{G}{G} 7/7
        
        // First strike
        addCard(Zone.BATTLEFIELD, playerA, "Coiled Tinviper", 1); // Creature Snake {3} 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8); // Cost reduction does not work for getPlayable so you need to have 8 mana available
       
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Patron of the Orochi");
        setChoice(playerA, true);
        addTarget(playerA, "Coiled Tinviper");

        setStrictChooseMode(true);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);
       
        assertPermanentCount(playerA, "Patron of the Orochi", 1);        
        assertGraveyardCount(playerA, "Coiled Tinviper", 1);
        assertTappedCount("Forest", false, 3);
    }  
    
    // https://github.com/magefree/mage/issues/6952
    
    @Test
    public void testCastCommanderAndUnexpectedlyAbsent() {
        setStrictChooseMode(true);
        
        playerA.getLibrary().clear();
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // Put target nonland permanent into its owner's library just beneath the top X cards of that library.
        addCard(Zone.HAND, playerA, "Unexpectedly Absent"); // Instant {X}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ob Nixilis of the Black Oath", true); // {3}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unexpectedly Absent", "Ob Nixilis of the Black Oath");
        setChoice(playerA, "X=0");
        setChoice(playerA, true); // Move Commander to command zone instead library?

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ob Nixilis of the Black Oath", 0);
        assertLibraryCount(playerA, "Ob Nixilis of the Black Oath", 0);
        assertCommandZoneCount(playerA, "Ob Nixilis of the Black Oath", 1);

        assertLibraryCount(playerA, 0);

        assertGraveyardCount(playerA, "Unexpectedly Absent", 1);

        assertLife(playerA, 40);
        assertLife(playerB, 40);        
    }        
}
