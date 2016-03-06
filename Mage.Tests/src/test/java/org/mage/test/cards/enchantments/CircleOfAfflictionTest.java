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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 
 *  @author LevelX2
 */
public class CircleOfAfflictionTest extends CardTestPlayerBase {

    /**
     * 
     */
    @Test
    public void testOneAttackerDamage() {
        
        // Enchantment - {1}{B}
        // As Circle of Affliction enters the battlefield, choose a color.
        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
                
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "Red");
        
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB); // Circle of Affliction drain ability
        setChoice(playerA, "Yes");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
            
        assertLife(playerA, 18);
        assertLife(playerB, 19);
    }
    
    /**
     * 
     */
    @Test
    public void testTwoAttackersDamage() {
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2); // {1}{W} 2/2

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // As Circle of Affliction enters the battlefield, choose a color.
        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "White");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Silvercoat Lion");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 18);
    }
    
    /**
     * 
     */
    @Test
    public void testMixOfSpellsAndCombatDamage() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // As Circle of Affliction enters the battlefield, choose a color.
        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}
                
        addCard(Zone.HAND, playerB, "Lava Spike", 2); // {R} deals 3 damage to target player
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 2); // {3}{R} 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "Red");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lava Spike", playerA);        
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");

        attack(2, playerB, "Hill Giant");
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lava Spike", playerA);
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 12); // 12 damage - 4 drains = 8 net life total loss
        assertLife(playerB, 16); // 4 drains
    }
    
    /**
     * 
     */
    @Test
    public void testTwoAttackersDamageDifferentColors() {
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // {1}{W} 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant", 1); // {3}{R} 3/3

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // As Circle of Affliction enters the battlefield, choose a color.
        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        addCard(Zone.HAND, playerA, "Circle of Affliction", 1);// {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Circle of Affliction");
        setChoice(playerA, "White");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Hill Giant");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB); // should not be able to drain Hill Giant with white selected
        setChoice(playerA, "Yes");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Circle of Affliction", 1);

        assertLife(playerA, 16); // 5 life loss from combat - 1 drain = 4 net life total loss
        assertLife(playerB, 19);
    }
}
