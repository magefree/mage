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
 * @author BetaSteward
 */
public class ReplicateTest extends CardTestPlayerBase {

    /**
     * 702.55. Replicate
     * 702.55a Replicate is a keyword that represents two abilities. The first is a static ability that
     * functions while the spell with replicate is on the stack. The second is a triggered ability that
     * functions while the spell with replicate is on the stack. “Replicate [cost]” means “As an
     * additional cost to cast this spell, you may pay [cost] any number of times” and “When you cast
     * this spell, if a replicate cost was paid for it, copy it for each time its replicate cost was paid. If
     * the spell has any targets, you may choose new targets for any of the copies.” Paying a spell’s
     * replicate cost follows the rules for paying additional costs in rules 601.2b and 601.2e–g.
     * 702.55b If a spell has multiple instances of replicate, each is paid separately and triggers based on
     * the payments made for it, not any other instance of replicate.
     * 
     */

    /** 
     * 	Train of Thought  
     *  Sorcery, 1U (2)
     *  Replicate {1}{U} (When you cast this spell, copy it for each time you paid its replicate cost.)
     *  Draw a card.
     *
     */
    
    @Test
    public void testReplicate1Time() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 2);

    }
    
    @Test
    public void testReplicate2Times() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 3);

    }

    @Test
    public void testNotReplicate() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 1);

    }
    
}
