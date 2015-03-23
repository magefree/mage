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
public class ConspireTest extends CardTestPlayerBase {

    /**
     * 702.77. Conspire
     * 702.77a Conspire is a keyword that represents two abilities. The first is a static ability that functions
     * while the spell with conspire is on the stack. The second is a triggered ability that functions
     * while the spell with conspire is on the stack. “Conspire” means “As an additional cost to cast
     * this spell, you may tap two untapped creatures you control that each share a color with it” and
     * “When you cast this spell, if its conspire cost was paid, copy it. If the spell has any targets, you
     * may choose new targets for the copy.” Paying a spell’s conspire cost follows the rules for
     * paying additional costs in rules 601.2b and 601.2e–g.
     * 
     * 702.77b If a spell has multiple instances of conspire, each is paid separately and triggers based on
     * its own payment, not any other instance of conspire
     * 
     */

    /** 
     * 	Burn Trail
     * 	Sorcery, 3R (4)
     * 	Burn Trail deals 3 damage to target creature or player.
     * 	
     * 	Conspire (As you cast this spell, you may tap two untapped creatures you 
     *  control that share a color with it. When you do, copy it and you may 
     *  choose a new target for the copy.)
     *
     */

    @Test
    public void testConspire() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Burn Trail");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 14);
        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertTapped("Goblin Roughrider", true);
        assertTapped("Raging Goblin", true);

    }

    @Test
    public void testConspireNotUsed() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Burn Trail");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertTapped("Goblin Roughrider", false);
        assertTapped("Raging Goblin", false);

    }

}
