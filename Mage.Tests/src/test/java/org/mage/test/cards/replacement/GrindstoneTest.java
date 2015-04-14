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
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class GrindstoneTest extends CardTestPlayerBase {

    /**
     * Tests that Grindstone mills all cards to graveyard while Painter's Servant is in play
     * Leaving one Progenius in play
     */
    @Test
    public void testGrindstoneProgenius() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant");
        // {3}, {T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.
        addCard(Zone.BATTLEFIELD, playerA, "Grindstone");

        // Protection from everything
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        addCard(Zone.LIBRARY, playerB, "Progenitus", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Blue");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3},{T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        Assert.assertEquals("Progenitus has to be in the libarary", 1, playerB.getLibrary().size()); 
        assertPermanentCount(playerA, "Painter's Servant", 1);
    }
    
    /**
     * Tests that Grindstone mills all cards to graveyard while Painter's Servant is in play
     * Iterating with two Progenius for a draw
     */
    @Test
    public void testGrindstoneProgeniusDraw() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant");
        // {3}, {T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.
        addCard(Zone.BATTLEFIELD, playerA, "Grindstone");

        // Protection from everything
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        addCard(Zone.LIBRARY, playerB, "Progenitus", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Blue");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3},{T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        Assert.assertTrue("Has to be a draw because of endless iteration", currentGame.isADraw());
        assertPermanentCount(playerA, "Painter's Servant", 1);
    }
    
/**
     * Tests that Grindstone mills all cards to graveyard while Painter's Servant is in play
     * Iterating with two Progenius for a draw
     */
    @Test
    public void testGrindstoneUlamog() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant");
        // {3}, {T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.
        addCard(Zone.BATTLEFIELD, playerA, "Grindstone");

        // When you cast Ulamog, the Infinite Gyre, destroy target permanent.
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        // Ulamog is indestructible.
        // When Ulamog is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        addCard(Zone.LIBRARY, playerB, "Ulamog, the Infinite Gyre", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Blue");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3},{T}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        // No cards in graveyard because Ulamog shuffle all cards back to Lib
        assertGraveyardCount(playerB, 0);
        assertPermanentCount(playerA, "Painter's Servant", 1);
    }    
}


