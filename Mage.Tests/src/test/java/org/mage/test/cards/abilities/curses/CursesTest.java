package org.mage.test.cards.abilities.curses;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CursesTest extends CardTestPlayerBase {

    @Test
    public void testCurseOfBloodletting() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfEchoes() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Enchant player
        // Whenever enchanted player casts an instant or sorcery spell, each other player may copy that
        // spell and may choose new targets for the copy he or she controls.  
        addCard(Zone.HAND, playerA, "Curse of Echoes");
        // Draw three cards.
        addCard(Zone.HAND, playerB, "Jace's Ingenuity");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Jace's Ingenuity");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
    }

    @Test
    public void testCurseOfExhaustion1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Enchant player
        // Enchanted player can't cast more than one spell each turn.
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertLife(playerA, 17);
    }

    @Test
    public void testCurseOfExhaustion2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);


        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    /**
     * Checks if Copy Enchantment works for player auras
     */
    @Test
    public void testCurseOfExhaustion3() {        
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);        
        
        // Enchant player
        // Enchanted player can't cast more than one spell each turn.
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);        
        
        addCard(Zone.HAND, playerB, "Copy Enchantment", 1);
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, "Yes");
        setChoice(playerB, "Curse of Exhaustion");
        setChoice(playerB, "targetPlayer=PlayerA");
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        


        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Copy Enchantment", 0);
        assertGraveyardCount(playerB, "Copy Enchantment", 0);
        
        assertPermanentCount(playerA, "Curse of Exhaustion", 1);        
        assertPermanentCount(playerB, "Curse of Exhaustion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
    
    // returng curse enchantment from graveyard to battlefield
    @Test
    public void testCurseOfExhaustion4() {        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        
        addCard(Zone.GRAVEYARD, playerB, "Curse of Exhaustion", 1);
        addCard(Zone.HAND, playerB, "Obzedat's Aid", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Obzedat's Aid", "Curse of Exhaustion");
        setChoice(playerB, "PlayerA");
        
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);


        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Obzedat's Aid", 0);
        assertGraveyardCount(playerB, "Obzedat's Aid", 1);
        assertGraveyardCount(playerB, "Curse of Exhaustion", 0);
        
        assertPermanentCount(playerB, "Curse of Exhaustion", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }    
    
    @Test
    public void testCurseOfThirst1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCurseOfThirst2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

    @Test
    public void testCurseOfMisfortune1() {
        removeAllCardsFromLibrary(playerA);

        // At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same name as a
        // Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library.
        addCard(Zone.LIBRARY, playerA, "Curse of Misfortunes", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
    }

    @Test
    public void testCurseOfMisfortune2() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curse of Bloodletting", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
        assertPermanentCount(playerA, "Curse of Bloodletting", 1);    
    }
    
    
    @Test
    public void testCurseOfDeathsHold() {
        // Creatures enchanted player controls get -1/-1.
        addCard(Zone.HAND, playerA, "Curse of Death's Hold");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Curse of Death's Hold", 1);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
    }
    
    @Test
    public void testCurseOfDeathsHold2() {
        // Creatures enchanted player controls get -1/-1.
        addCard(Zone.HAND, playerA, "Curse of Death's Hold");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tasigur, the Golden Fang", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.HAND, playerB, "Reclamation Sage");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Reclamation Sage");
        addTarget(playerB, "Curse of Death's Hold");
        
        // {2}{G/U}{G/U}: Put the top two cards of your library into your graveyard, then return a nonland card of an opponent's choice from your graveyard to your hand.
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{G/U}{G/U}: Put the top two cards");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);
               
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerB, "Reclamation Sage", 1);
        assertPermanentCount(playerA, "Curse of Death's Hold", 1);
        assertGraveyardCount(playerA, 2);
        
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
    }   
    
}
