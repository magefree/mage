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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class KickerTest extends CardTestPlayerBase {

    /**
     * 702.32. Kicker
     * 702.32a Kicker is a static ability that functions while the spell with kicker is on the stack. “Kicker
     * [cost]” means “You may pay an additional [cost] as you cast this spell.” Paying a spell’s kicker
     * cost(s) follows the rules for paying additional costs in rules 601.2b and 601.2e–g.
     * 702.32b The phrase “Kicker [cost 1] and/or [cost 2]” means the same thing as “Kicker [cost 1],
     * kicker [cost 2].”
     * 702.32c Multikicker is a variant of the kicker ability. “Multikicker [cost]” means “You may pay an
     * additional [cost] any number of times as you cast this spell.” A multikicker cost is a kicker cost.
     * 702.32d If a spell’s controller declares the intention to pay any of that spell’s kicker costs, that spell
     * has been “kicked.” If a spell has two kicker costs or has multikicker, it may be kicked multiple
     * times. See rule 601.2b.
     * 702.32e Objects with kicker or multikicker have additional abilities that specify what happens if
     * they are kicked. These abilities are linked to the kicker or multikicker abilities printed on that
     * object: they can refer only to those specific kicker or multikicker abilities. See rule 607, “Linked
     * Abilities.”
     * 702.32f Objects with more than one kicker cost have abilities that each correspond to a specific
     * kicker cost. They contain the phrases “if it was kicked with its [A] kicker” and “if it was kicked
     * with its [B] kicker,” where A and B are the first and second kicker costs listed on the card,
     * respectively. Each of those abilities is linked to the appropriate kicker ability.
     * 702.32g If part of a spell’s ability has its effect only if that spell was kicked, and that part of the
     * ability includes any targets, the spell’s controller chooses those targets only if that spell was
     * kicked. Otherwise, the spell is cast as if it did not have those targets. See rule 601.2c.
     * 
     */

    /**
     * AEther Figment
     * Creature — Illusion 1/1, 1U (2)
     * Kicker {3} (You may pay an additional {3} as you cast this spell.)
     * AEther Figment can't be blocked.
     * If AEther Figment was kicked, it enters the battlefield with two +1/+1 counters on it.
     * 
    */
    @Test
    public void testUseKicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "AEther Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "AEther Figment");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "AEther Figment", 1);
        assertCounterCount("AEther Figment", CounterType.P1P1, 2);
        assertPowerToughness(playerA,  "AEther Figment", 3, 3);
        
    }
    
    @Test
    public void testDontUseKicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "AEther Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "AEther Figment");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "AEther Figment", 1);
        assertCounterCount("AEther Figment", CounterType.P1P1, 0);
        assertPowerToughness(playerA,  "AEther Figment", 1, 1);
        
    }
    
    /**
     * Apex Hawks
     * Creature — Bird 2/2, 2W (3)
     * Multikicker {1}{W} (You may pay an additional {1}{W} any number of times as you cast this spell.)
     * Flying
     * Apex Hawks enters the battlefield with a +1/+1 counter on it for each time it was kicked.
     * 
     */
    @Test
    public void testUseMultikickerOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 1);
        assertPowerToughness(playerA,  "Apex Hawks", 3, 3);
        
    }

    @Test
    public void testUseMultikickerTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 2);
        assertPowerToughness(playerA,  "Apex Hawks", 4, 4);
        
    }
    
    @Test
    public void testDontUseMultikicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Apex Hawks");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apex Hawks");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Apex Hawks", 1);
        assertCounterCount("Apex Hawks", CounterType.P1P1, 0);
        assertPowerToughness(playerA,  "Apex Hawks", 2, 2);
        
    }
 
    /**
     * When I cast Orim's Chant with Kicker cost, the player can play spells anyway during the turn. 
     * It seems like the kicker cost trigger an "instead" creatures can't attack.
     */
    @Test
    public void testOrimsChantskicker() {        
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1); // Haste   1/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        // Target player can't cast spells this turn.
        // If Orim's Chant was kicked, creatures can't attack this turn.
        addCard(Zone.HAND, playerA, "Orim's Chant");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Orim's Chant", playerB);
        setChoice(playerA, "Yes");
        
        attack(1, playerA, "Raging Goblin");
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Orim's Chant", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 0);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
    }    
}
