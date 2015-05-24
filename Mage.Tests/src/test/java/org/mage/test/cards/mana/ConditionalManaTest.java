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
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ConditionalManaTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        // Instant {G}{W}
        // Target player gains 7 life.
        addCard(Zone.HAND, playerA, "Heroes' Reunion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroes' Reunion", playerA);        
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Heroes' Reunion", 1);
        assertHandCount(playerA, "Heroes' Reunion", 0); // player A could not cast it
        assertLife(playerA, 27);
    }

    @Test
    public void testNotAllowedUse() {
        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");        
        playerA.addChoice("White");
        playerA.addChoice("White");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1); // player A could not cast Silvercoat Lion because the conditional mana is not available
    }
    
    @Test
    public void testWorkingWithReflectingPool() {
        addCard(Zone.BATTLEFIELD, playerA, "Cavern of Souls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // can create white mana without restriction from the Cavern
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
    }
    
    @Test
    public void testWorkingWithReflectingPool2() {
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // can create white mana without restriction from the Hive
        addCard(Zone.BATTLEFIELD, playerA, "Sliver Hive", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {1} to your mana pool");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add to your mana pool one mana of any type");
        setChoice(playerA, "White");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
                
        assertPermanentCount(playerA, "Silvercoat Lion", 1); 
    }    
}
