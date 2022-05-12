/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class RanarTheEverWatchfulTest extends CardTestPlayerBase {

    @Test
    public void testRanarWithCurseOfTheSwine() {
        setStrictChooseMode(true);
        // Curse of the Swine targets 4 creatures; 2 non-tokens and 2 tokens with Ranar, the Ever-Watchful on the battlefield
        // Result: 2 boar tokens and 1 spirit token for the playerA.  2 boar tokens for playerB
        
        addCard(Zone.BATTLEFIELD, playerA, "Ranar the Ever-Watchful", 1);
        
        /*
        Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or 
        more permanents from the battlefield, create a 1/1 white Spirit creature token with flying.
        */
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1); // Creature 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1); // Creature 4/4 Flyer
        addCard(Zone.BATTLEFIELD, playerA, "Bog Imp", 1); // Creature 2/1 Flyer
        addCard(Zone.BATTLEFIELD, playerB, "Sengir Vampire", 1); // Creature 4/4 Flyer
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10); // handle mana for Curse of the Swine
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 10); // handle mana for Call the Calvary
        addCard(Zone.HAND, playerB, "Call the Cavalry", 1);  // generate 2 tokens for playerB
        addCard(Zone.HAND, playerA, "Curse of the Swine", 1); // exile target cards and create a boar token for each one exiled

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Call the Cavalry");  // create 2 Knight tokens
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of the Swine");
        setChoice(playerA, "X=6");
        addTarget(playerA, "Memnite");
        addTarget(playerA, "Serra Angel");
        addTarget(playerA, "Bog Imp");
        addTarget(playerA, "Sengir Vampire");
        addTarget(playerA, "Knight Token");
        addTarget(playerA, "Knight Token");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Boar Token", 3); // created token by Curse
        assertPermanentCount(playerA, "Spirit Token", 1); // created token by Ranar
        assertPermanentCount(playerB, "Boar Token", 3); // created token by Curse
        assertPermanentCount(playerB, "Spirit Token", 0);  // no Spirit token for playerB
    }
    
    @Test
    public void testRanarExiledCardsFromHand() {
        setStrictChooseMode(true);
        // A card is exiled from playerA's hand
        // Result: Due to the card being exiled from playerA's hand, a Spirit token is created for playerA
        
        addCard(Zone.BATTLEFIELD, playerA, "Ranar the Ever-Watchful", 1);
        
        /*
        Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or 
        more permanents from the battlefield, create a 1/1 white Spirit creature token with flying.
        */
        
        addCard(Zone.HAND, playerA, "Psychic Theft", 1); // exile instant/sorcery card from target player's hand
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // handle mana for Psychic Theft
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // instant spell card
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Theft", playerA);
        setChoice(playerA, "Lightning Bolt");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Spirit Token", 1); // created token by Ranar
        assertPermanentCount(playerB, "Spirit Token", 0); // no Spirit tokens for the other player
    }
    
}
