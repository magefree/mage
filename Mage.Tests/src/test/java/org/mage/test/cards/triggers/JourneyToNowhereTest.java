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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LeveX2
 */

public class JourneyToNowhereTest extends CardTestPlayerBase {

    /*
     Journey to Nowhere   Enchantment {1}{W}
        When Journey to Nowhere enters the battlefield, exile target creature.
        When Journey to Nowhere leaves the battlefield, return the exiled card to the battlefield under its owner's control.

        10/1/2009: If Journey to Nowhere leaves the battlefield before its first ability has resolved, its second ability will
                   trigger and do nothing. Then its first ability will resolve and exile the targeted creature forever.
    */

    @Test
    public void testTargetGetsExiled() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Silvercoat Lion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Journey to Nowhere", 1);
        assertExileCount("Silvercoat Lion", 1);
    }
    
    
    @Test
    public void testTargetGetsExiledAndReturns() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    /*
        10/1/2009: If Journey to Nowhere leaves the battlefield before its first ability has resolved, its second ability will
                   trigger and do nothing. Then its first ability will resolve and exile the targeted creature forever.
    */
    @Test
    public void testTargetGetsExiledAndDoesNeverReturn() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);
        assertExileCount("Silvercoat Lion", 1);
    }    

    /*
        Journey is played and targets the creature as it enters the battlefield. 
        The Journey will be returned to hand before the ability resolves.
        The Journey will be played again targeting another creature.
        The Journey will be disenchanted later, so only the second creature has to return to battlefield.
    
    */
    @Test
    public void testTargetGetsExiledAndDoesNeverReturnAndJourneyPlayedAgain() {
        addCard(Zone.HAND, playerA, "Journey to Nowhere");
        addCard(Zone.HAND, playerA, "Boomerang");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        
        addCard(Zone.HAND, playerB, "Disenchant", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boomerang", "Journey to Nowhere");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Journey to Nowhere");
        addTarget(playerA, "Pillarfield Ox");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Journey to Nowhere");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Boomerang", 1);
        assertGraveyardCount(playerA, "Journey to Nowhere", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);        
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertExileCount("Silvercoat Lion", 1);
        
    }       
}
