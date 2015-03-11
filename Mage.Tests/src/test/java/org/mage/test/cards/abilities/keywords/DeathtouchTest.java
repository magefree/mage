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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DeathtouchTest extends CardTestPlayerBase {
 
    @Test
    public void simpleDeathtouchDuringCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Archangel of Thune");
        // Creature - Rat     1/1
        // Deathtouch
        addCard(Zone.BATTLEFIELD, playerB, "Typhoid Rats");
        
                
        attack(2, playerB, "Typhoid Rats");
        block(2, playerA, "Archangel of Thune", "Typhoid Rats");
        
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertLife(playerA, 23);
        assertLife(playerB, 20);
        
        assertGraveyardCount(playerA, "Archangel of Thune", 1);
        assertGraveyardCount(playerB, "Typhoid Rats", 1);
    }
    
    /**
     * Checks if a creature getting damage from Marath abilitity dies
     * from Deathtouch, if Marath is equiped with Deathtouch giving Equipment
     * and Marath dies from removing the +1/+1 counters.
     */
    @Test
    public void testMarathWillOfTheWild() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        // Equipped creature has deathtouch and lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Basilisk Collar");
        /*
        {R}{G}{W} Legendary Creature - Elemental Beast
        Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on 
        it equal to the amount of mana spent to cast it.
        {X}, Remove X +1/+1 counters from Marath: Choose one - 
            * Put X +1/+1 counters on target creature
            * Marath deals X damage to target creature or player
            * Put an X/X green Elemental creature token onto the battlefield. X can't be 0        
        */
        addCard(Zone.HAND, playerA, "Marath, Will of the Wild", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Archangel of Thune");

        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN , playerA, "Marath, Will of the Wild");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Marath, Will of the Wild");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X},Remove X +1/+1 counters from Marath", "Archangel of Thune");
        setChoice(playerA, "X=3");
        setModeChoice(playerA, "2"); // Marath deals X damage to target creature or player

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Marath, Will of the Wild", 0); // died because all +1/+1 counters are removed
        assertPermanentCount(playerB, "Archangel of Thune", 0); // died from deathtouch

        assertLife(playerA, 23); // +3 from lifelink doing 3 damage with Marath to Archangel
        assertLife(playerB, 20);

    }
    

}
