
package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AminatousAuguryTest extends CardTestPlayerBase {

    @Test
    public void testCastMultiple() {
        setStrictChooseMode(true);
        
        
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox"); // Creature (2/4)
        // As an additional cost to cast this spell, discard a card.
        // Draw two cards.
        addCard(Zone.LIBRARY, playerA, "Tormenting Voice"); // Sorcery
        // {1}: Adarkar Sentinel gets +0/+1 until end of turn.        
        addCard(Zone.LIBRARY, playerA, "Adarkar Sentinel"); // Artifact Creature {5} (3/3) 
        addCard(Zone.LIBRARY, playerA, "Storm Crow");        
        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        addCard(Zone.LIBRARY, playerA, "Aegis of the Gods"); // Enchantment Creature {1}{W} (2/1)
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // Instant 
        addCard(Zone.LIBRARY, playerA, "Badlands");
        skipInitShuffling();
        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        addCard(Zone.HAND, playerA, "Aminatou's Augury"); // SORCERY {6}{U}{U}
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8); 
                
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aminatou's Augury", true);
        setChoice(playerA, true); // Put a land from among the exiled cards into play?
        setChoice(playerA, "Badlands"); // Select a land card
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adarkar Sentinel", true);
        setChoice(playerA, "Artifact"); // Which card type do you want to consume?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aegis of the Gods", true);
        setChoice(playerA, "Enchantment"); // Which card type do you want to consume?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm Crow", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Silvercoat Lion"); // Select a card (discard cost)
        
        checkPlayableAbility("Cannot cast second creature from exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Pillarfield Ox", Boolean.FALSE); // Type Creature type is already consumed
        execute();

        assertGraveyardCount(playerA, "Aminatou's Augury", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Badlands", 1);
        assertPermanentCount(playerA, "Adarkar Sentinel", 1);
        assertPermanentCount(playerA, "Aegis of the Gods", 1);
        assertPermanentCount(playerA, "Storm Crow", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        
        assertHandCount(playerA, 2);
        assertGraveyardCount(playerA, "Silvercoat Lion",1);
        assertExileCount(playerA, 2);
    }

}
