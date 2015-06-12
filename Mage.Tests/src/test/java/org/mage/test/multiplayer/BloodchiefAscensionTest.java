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
package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodchiefAscensionTest extends CardTestMultiPlayerBase {
    
   @Test
    public void testBloodchiefAscensionAllPlayers() {
        // Enchantment
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.        
        addCard(Zone.BATTLEFIELD, playerA, "Bloodchief Ascension");
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",3);
        addCard(Zone.HAND, playerA, "Fireball");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain",3);
        addCard(Zone.HAND, playerD, "Fireball");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain",3);
        addCard(Zone.HAND, playerC, "Fireball");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain",3);
        addCard(Zone.HAND, playerB, "Fireball");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");
        
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=2");
        
        // Player order: A -> D -> C -> B

        setStopAt(4, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 18);
        assertLife(playerB, 18);
        assertLife(playerC, 18);
        assertLife(playerD, 18);
        
        assertGraveyardCount(playerA, "Fireball", 1);
        assertGraveyardCount(playerB, "Fireball", 1);
        assertGraveyardCount(playerC, "Fireball", 1);
        assertGraveyardCount(playerD, "Fireball", 1);
        

        
        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2); // 1 opponent out of range

    }    
   /**
    * One of my opponents in a multiplayer game had a Bloodchief Ascension in play. I took lethal damage on my turn, 
    * but he didn't get a counter on Bloodchief Ascension at my end step. I think he should, even though I had left 
    * the game from dying, because of:
    * 
    * 800.4g. If a player leaves the game during his or her turn, that turn continues to its completion without an 
    * active player. If the active player would receive priority, instead the next player in turn order receives priority,
    * or the top object on the stack resolves, or the phase or step ends, whichever is appropriate.
    */
    @Test
    public void testBloodchiefAscension() {
        // Enchantment
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.        
        addCard(Zone.BATTLEFIELD, playerA, "Bloodchief Ascension");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",3);
        addCard(Zone.HAND, playerA, "Fireball");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain",3);
        addCard(Zone.HAND, playerD, "Fireball");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain",3);
        addCard(Zone.HAND, playerC, "Fireball");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain",21);
        addCard(Zone.HAND, playerB, "Fireball");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball", playerA);
        setChoice(playerA, "X=2");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Fireball", playerD);
        setChoice(playerD, "X=2");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Fireball", playerC);
        setChoice(playerC, "X=2");
        
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Fireball", playerB);
        setChoice(playerB, "X=20");
        
        // Player order: A -> D -> C -> B

        setStopAt(4, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 18);
        assertLife(playerB, 0);
        assertLife(playerC, 18);
        assertLife(playerD, 18);
        
        Assert.assertTrue("playerB has lost", playerB.hasLost());
        
        assertGraveyardCount(playerA, "Fireball", 1);
        assertGraveyardCount(playerC, "Fireball", 1);
        assertGraveyardCount(playerD, "Fireball", 1);        
        
        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2); // 1 opponent out of range 

    }
}
