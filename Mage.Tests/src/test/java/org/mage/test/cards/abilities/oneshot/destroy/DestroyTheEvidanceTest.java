/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class DestroyTheEvidanceTest extends CardTestPlayerBase {

    // Destroy the Evidence - Sorcery {4}{B}
    // Destroy target land. Its controller reveals cards from the top of his
    // or her library until he or she reveals a land card, then puts those cards into his or her graveyard.
    
    /**
     * The target land is destroyed
     */
    @Test
    public void testLandIsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Mountain", 1);
        
        assertLife(playerB, 20);
        
    }
    /**
     * The target land is Indestructible
     */
    @Test
    public void testLandIsIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel", 2);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Darksteel Citadel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Darksteel Citadel", 0);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
        assertPermanentCount(playerB, "Darksteel Citadel", 2);
        
        assertLife(playerB, 20);
        
    }    
    
    /**
     * The target land is blinked meanwhile -> spell has no more valid target, spell has to fizzle
     */
   @Test
    public void testLandIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Destroy the Evidence");
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Ghostly Flicker", 1);
        
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        
        skipInitShuffling();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Destroy the Evidence", "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ghostly Flicker", "Mountain^Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Destroy the Evidence", 1);
        assertGraveyardCount(playerB, "Ghostly Flicker", 1);
        assertPermanentCount(playerB, "Mountain", 2);
        assertPermanentCount(playerB, "Island", 1);
        
        assertGraveyardCount(playerB, 1);
        
        assertLife(playerB, 20);
        
    }    

}
