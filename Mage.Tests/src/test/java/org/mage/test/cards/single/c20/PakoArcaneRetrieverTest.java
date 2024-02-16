
package org.mage.test.cards.single.c20;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class PakoArcaneRetrieverTest extends CardTestPlayerBase {

    @Test
    public void test_CheckExiled() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerB, "Pillarfield Ox", 1);
        
        skipInitShuffling();
        // Partner with Pako, Arcane Retriever
        // You may play noncreature cards from exile with fetch counters on them if you 
        // exiled them, and you may spend mana as though it were mana of any color to cast those spells.
        addCard(Zone.BATTLEFIELD, playerA, "Haldan, Avid Arcanist", 1);
        // Partner with Haldan, Avid Arcanist
        // Haste
        // Whenever Pako, Arcane Retriever attacks, exile the top card of each player's library and put a fetch counter on each of them. Put a +1/+1 counter on Pako for each noncreature card exiled this way.        
        addCard(Zone.BATTLEFIELD, playerA, "Pako, Arcane Retriever", 1); // Creature {3}{R}{G}  (3/3)

        attack(1, playerA, "Pako, Arcane Retriever");
               
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertExileCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerB, "Pillarfield Ox", 1);

        for(Card card :currentGame.getExile().getAllCards(currentGame)) {
            Assert.assertTrue(card.getName() + " has a fetch counter",card.getCounters(currentGame).getCount(CounterType.FETCH) == 1);
        }        

    }
    
    @Test
    public void test_CastExiled() {
        // setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt", 1); // Instant 3 damge
        // Create a 3/3 green Centaur creature token.
        addCard(Zone.LIBRARY, playerB, "Call of the Conclave", 1); // Sorcery {W}{G} 
        
        skipInitShuffling();
        // Partner with Pako, Arcane Retriever
        // You may play noncreature cards from exile with fetch counters on them if you 
        // exiled them, and you may spend mana as though it were mana of any color to cast those spells.
        addCard(Zone.BATTLEFIELD, playerA, "Haldan, Avid Arcanist", 1);
        // Partner with Haldan, Avid Arcanist
        // Haste
        // Whenever Pako, Arcane Retriever attacks, exile the top card of each player's library and put a fetch counter on each of them. Put a +1/+1 counter on Pako for each noncreature card exiled this way.        
        addCard(Zone.BATTLEFIELD, playerA, "Pako, Arcane Retriever", 1); // Creature {3}{R}{G}  (3/3)

        attack(1, playerA, "Pako, Arcane Retriever");        

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Call of the Conclave");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 12); // 3+2 (attack) + 3 Lighning Bolt
               
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Call of the Conclave", 1);
    }
}
