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
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SharuumTheHegemonTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=16732&p=172937&hilit=Sharuum+the+Hegemon#p172920
     * 
     * My Sharuum EDH deck uses the standard Sharuum + Clone Effect + Blood Artist as one of the win 
     * conditions, but when I have Sharuum in plan and play a Clever Impersonator, targetting Sharuum 
     * and choose to keep the Clever Impersonator and send the original Sharuum to the graveyard Xmage 
     * never gives me the option to use the Sharuum Ability that the Clever Impersonator should get, 
     * making the combo not work.
     * 
     * I run a Sharuum EDH deck that wins by cloning Sharuum for infinite death triggers. I know the rules 
     * check out on this combo irl, but no matter how I stack the triggers for cloning Sharuum and her enter
     * the battlefield effect it does not work. It either ends with Sharuum in my graveyard or the reanimate
     * effect hits the stack before the legend rule applies
     * 
        [1] Sharuum the Hegemon is on the battlefield.
        [2] You cast Clone (or any other Clone-like card).
        [3] When Clone resolves, you choose Sharuum for the replacement effect.
        [4] Since fake-Sharuum entered the battlefield, its EtB ability triggers.
        [5] State-based actions are checked and you are prompted to keep one Sharuum. You sacrifice real-Sharuum.
            * 116.2a Triggered abilities can trigger at any time, including while a spell is being cast, an ability is being activated, or a spell or
            * ability is resolving. (See rule 603, “Handling Triggered Abilities.”) However, nothing actually happens at the time an ability triggers. 
            * Each time a player would receive priority, each ability that has triggered but hasn’t yet been put on the stack is put on the stack. See rule 116.5
            * 116.5. Each time a player would get priority, the game first performs all applicable state-based actions as a single event (see rule 704,
            * “State-Based Actions”), then repeats this process until no state-based actions are performed. Then triggered abilities are put on the stack
            * (see rule 603, “Handling Triggered Abilities”). These steps repeat in order until no further state-based actions are performed and no abilities
            * trigger. Then the player who would have received priority does so.
        [6] Once State-based actions are finished, triggered abilities go on the stack. You put the EtB from [4] choosing real-Sharuum.
        [7] Real-Sharuum enters the battlefield.
        [8] Rinse and repeat.
     * 
     */
    @Test
    public void testCloneTriggered() {
        // When Sharuum the Hegemon enters the battlefield, you may return target artifact card from your graveyard to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Sharuum the Hegemon", 1);
        addCard(Zone.HAND, playerA, "Clone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Sharuum the Hegemon"); // what creature to clone

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Yes");
        
        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Yes");

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "Yes");

        addTarget(playerA, "Sharuum the Hegemon[only copy]"); // which legend to keep
        setChoice(playerA, "No"); // Don't use it anymore
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 16);



    }

}