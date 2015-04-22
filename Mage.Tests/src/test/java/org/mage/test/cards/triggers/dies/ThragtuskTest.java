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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Thragtusk - Beast {4}{G}
 * When Thragtusk enters the battlefield, you gain 5 life.
 * When Thragtusk leaves the battlefield, put a 3/3 green Beast creature token onto the battlefield.
 * 
 * @author LevelX2
 */
public class ThragtuskTest extends CardTestPlayerBase {

    /**
     * Test if a Thragtusk is copied by a PhyrexianMetamorph
     * that both triggers cotrrect work
     */
    @Test
    public void testPhyrexianMetamorph() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.HAND, playerB, "Public Execution", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Thragtusk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Yes");        
        setChoice(playerA, "Thragtusk");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Public Execution", "Thragtusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Thragtusk", 1);

        assertGraveyardCount(playerA,"Phyrexian Metamorph", 1);
        assertGraveyardCount(playerB,"Public Execution", 1);

        assertLife(playerA, 25);
        assertLife(playerB, 20); // Thragtusk ETB ability does not trigger if set to battlefield on test game start 
        
        assertPermanentCount(playerA, "Beast", 1);                

    }
    /**
     * Test if a Thragtusk is copied by a Phyrexian Metamorph
     * that leave battlefield ability does not work, if 
     * the copy left all abilities by Turn to Frog
     */
    
    @Test
    @Ignore  // test fails because of bug
    public void testPhyrexianMetamorphTurnToFrog() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.HAND, playerB, "Public Execution", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Thragtusk", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Yes");        
        setChoice(playerA, "Thragtusk");
        
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Turn to Frog", "Thragtusk");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Public Execution", "Thragtusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Thragtusk", 1);

        assertGraveyardCount(playerA,"Phyrexian Metamorph", 1);
        assertGraveyardCount(playerB,"Public Execution", 1);

        assertLife(playerA, 25);
        assertLife(playerB, 20); // Thragtusk ETB ability does not trigger if set to battlefield on test game start 
        
        assertPermanentCount(playerA, "Beast", 0);                

    }  
    
}