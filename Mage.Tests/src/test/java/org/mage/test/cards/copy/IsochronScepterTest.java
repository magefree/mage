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

package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class IsochronScepterTest extends CardTestPlayerBase {


    /**
     * Isochron Scepter
     * Artifact, 2 (2)
     * Imprint — When Isochron Scepter enters the battlefield, you may exile an 
     * instant card with converted mana cost 2 or less from your hand.
     * {2}, {T}: You may copy the exiled card. If you do, you may cast the copy 
     * without paying its mana cost.
     * 
    */
    @Test
    public void testImprint() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertLife(playerB, 20);
        
    }
    
    @Test
    public void testCopyCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        addTarget(playerA, "Lightning Bolt");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2},{T}:");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 17);
        
    }

    @Test
    public void testCopyCardButDontCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        addTarget(playerA, "Lightning Bolt");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2},{T}:");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 20);
        
    }
    
    @Test
    public void testAngelsGrace() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Angel's Grace");

        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        addTarget(playerA, "Angel's Grace");
        
        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");
        attack(2, playerB, "Dross Crocodile");

        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerA, "{2},{T}:");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Angel's Grace", 1);
        assertGraveyardCount(playerA, "Angel's Grace", 0);

        assertLife(playerA, 1);
        assertLife(playerB, 20);

    }
    
    /**
     * Resolving a Silence cast from exile via Isochron Scepter during my opponent's upkeep does 
     * not prevent that opponent from casting spells that turn.
     * 
     */
    
    @Test
    public void testSilence() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Isochron Scepter");
        addCard(Zone.HAND, playerA, "Silence");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isochron Scepter");
        addTarget(playerA, "Silence");
        
        activateAbility(2, PhaseStep.UPKEEP, playerA, "{2},{T}:");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Isochron Scepter", 1);
        assertExileCount("Silence", 1);

        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        
    }    
}
