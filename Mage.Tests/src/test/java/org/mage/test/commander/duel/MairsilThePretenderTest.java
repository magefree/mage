package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import static org.mage.test.player.TestPlayer.NO_TARGET;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class MairsilThePretenderTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // When Mairsil, the Pretender enters the battlefield, you may exile an artifact or creature card from your hand
        // or graveyard and put a cage counter on it.
        // Mairsil, the Pretender has all activated abilities of all cards you own in exile with cage counters on them. 
        // You may activate each of those abilities only once each turn.               
        setDecknamePlayerA("CommanderDuel_Mairisil_UBR.dck"); // Commander = Mairsil, the Pretender {1}{U}{B}{R}
        return super.createNewGameAndPlayers();
    }

    /**
     * Basic Test
     */
    @Test
    public void useShifitingShadowTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);        
        skipInitShuffling();
        
        // Enchant creature
        // Enchanted creature has haste and “At the beginning of your upkeep, destroy this creature. 
        // Reveal cards from the top of your library until you reveal a creature card.
        // Put that card onto the battlefield and attach Shifting Shadow to it, 
        // then put all other cards revealed this way on the bottom of your library in a random order.”
        addCard(Zone.HAND, playerA, "Shifting Shadow", 1); // {2}{R}
        
        // Tap: Draw three cards.
        // {2}{U}{U}: Return Arcanis the Omnipotent to its owner's hand.        
        addCard(Zone.HAND, playerA, "Arcanis the Omnipotent", 1); // Creature {3}{U}{U}{U}

        setChoice(playerA, true);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mairsil, the Pretender");        
        setChoice(playerA, true); // Exile a card
        setChoice(playerA, "Arcanis the Omnipotent");
        
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shifting Shadow", "Mairsil, the Pretender");

        setChoice(playerA, true); // Move commander to command zone
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertExileCount("Arcanis the Omnipotent", 1);

        assertCommandZoneCount(playerA, "Mairsil, the Pretender", 1);
        
        assertPermanentCount(playerA, "Shifting Shadow", 1);        
    }
    /**
     * I tried playing it in Mairsil the Pretender commander deck and found 2
     * cases where the creature is not properly destroyed:
     *
     * Using Arcanis the Omnipotent ability to return my commander to hand while
     * trigger is on the stack I got Mairsil to hand then got asked whether I
     * want to put him in gy so I answered yes assuming it cannot be destroyed
     * while in my hand. He got put in graveyard straight from my hand.
     * According to Oracle rules I should get a creature from top of the deck
     * and still have my commander in hand. After giving my commander undying
     * with shifting shadow trigger on the stack he got put in gy with undying
     * not triggering.
     *
     * All this points to the card being hardcoded to put the creature into
     * graveyard instead of simply destroying it
     */
    @Test
    public void useShifitingShadowAndArcanisTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        skipInitShuffling();
        // Enchant creature
        // Enchanted creature has haste and “At the beginning of your upkeep, destroy this creature. 
        // Reveal cards from the top of your library until you reveal a creature card.
        // Put that card onto the battlefield and attach Shifting Shadow to it, 
        // then put all other cards revealed this way on the bottom of your library in a random order.”
        addCard(Zone.HAND, playerA, "Shifting Shadow", 1); // {2}{R}
        
        // Tap: Draw three cards.
        // {2}{U}{U}: Return Arcanis the Omnipotent to its owner's hand.        
        addCard(Zone.HAND, playerA, "Arcanis the Omnipotent", 1); // Creature {3}{U}{U}{U}

        setChoice(playerA, true);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mairsil, the Pretender");        
        setChoice(playerA, true); // Exile a card
        setChoice(playerA, "Arcanis the Omnipotent");
        
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shifting Shadow", "Mairsil, the Pretender");

        activateAbility(5, PhaseStep.UPKEEP, playerA, "{2}{U}{U}: Return", NO_TARGET, "At the beginning of your upkeep");
        setChoice(playerA, false); // Don't move commander to command zone because it goes to hand

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertExileCount("Arcanis the Omnipotent", 1);

        assertCommandZoneCount(playerA, "Mairsil, the Pretender", 0);
        assertHandCount(playerA, "Mairsil, the Pretender", 1);
       
        
        assertGraveyardCount(playerA, "Shifting Shadow", 1);  // Goes to graveyard because commander left battlefield to hand from Arcanis ability     
        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
    }
    
    @Test
    public void useShifitingShadowAndEndlingTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        skipInitShuffling();
        // Enchant creature
        // Enchanted creature has haste and “At the beginning of your upkeep, destroy this creature. 
        // Reveal cards from the top of your library until you reveal a creature card.
        // Put that card onto the battlefield and attach Shifting Shadow to it, 
        // then put all other cards revealed this way on the bottom of your library in a random order.”
        addCard(Zone.HAND, playerA, "Shifting Shadow", 1); // {2}{R}
        // Tap: Draw three cards.
        // {2}{U}{U}: Return Arcanis the Omnipotent to its owner's hand.        
        addCard(Zone.HAND, playerA, "Arcanis the Omnipotent", 1); // Creature {3}{U}{U}{U}        
        // {B}: Endling gains menace until end of turn.
        // {B}: Endling gains deathtouch until end of turn.
        // {B}: Endling gains undying until end of turn.
        // {1}: Endling gets +1/-1 or -1/+1 until end of turn.   
        addCard(Zone.HAND, playerA, "Endling", 1); // Creature {3}{U}{U}{U}

        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mairsil, the Pretender");                
        setChoice(playerA, true); // Exile a card
        setChoice(playerA, true); // Exile from Hand
        setChoice(playerA, "Endling");
        
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shifting Shadow", "Mairsil, the Pretender");

        activateAbility(5, PhaseStep.UPKEEP, playerA, "{B}: {this} gains undying", NO_TARGET, "At the beginning of your upkeep");
        setChoice(playerA, false); // Don't move commander to command zone because can come back by Undying
        
        setChoice(playerA, true); // Exile a card (Mairsil comes back from Undying)
        setChoice(playerA, true); // Exile from hand
        setChoice(playerA, "Arcanis the Omnipotent");
        
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertExileCount("Endling", 1);
        assertExileCount("Arcanis the Omnipotent", 1);

        assertCommandZoneCount(playerA, "Mairsil, the Pretender", 0);
        assertGraveyardCount(playerA, "Mairsil, the Pretender", 0);
        assertPermanentCount(playerA, "Mairsil, the Pretender", 1); // Returns from Undying
        assertPowerToughness(playerA, "Mairsil, the Pretender", 5, 5);
        
        assertPermanentCount(playerA, "Shifting Shadow", 1);  // Enchants Silvercoat Lion    
        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
    }

}
