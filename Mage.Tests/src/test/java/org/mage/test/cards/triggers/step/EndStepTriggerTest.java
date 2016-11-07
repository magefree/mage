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
package org.mage.test.cards.triggers.step;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EndStepTriggerTest extends CardTestPlayerBase {

    /**
     * The text is rendered incorrect.
     *
     * First ability should trigger each end step but is triggering only on
     * controller's end step.
     *
     */
    @Test
    public void testTriggersForEachPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        addCard(Zone.HAND, playerA, "Bloodchief Ascension"); // Enchantment {B}
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodchief Ascension");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Bloodchief Ascension", 1);

        assertGraveyardCount(playerA, "Lightning Bolt", 2);

        assertLife(playerA, 20);
        assertLife(playerB, 14);

        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2);
    }
}
