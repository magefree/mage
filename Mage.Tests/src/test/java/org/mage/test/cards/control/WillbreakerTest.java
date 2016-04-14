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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WillbreakerTest extends CardTestPlayerBase {

    /**
     * http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=17664&start=120#p186736
     *
     * I tried to activate Retribution of the Ancients while I only controlled
     * Nantuko Husk and Willbreaker without +1/+1 counters on them. The program
     * didn't let me activate the RotA, even though the creature you choose
     * doesn't need to have any counters on it (X can be 0, doesn't say
     * otherwise on the card and it isn't a target requirement). It asked me to
     * pay B, then choose a creature I controlled, which I did... and then
     * nothing happened.
     *
     */
    @Test
    public void testRetributionOfTheAncientsZeroCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // {B}, Remove X +1/+1 counters from among creatures you control: Target creature gets -X/-X until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Retribution of the Ancients", 1); // Enchantment {B}
        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, gain control of that creature for as long as you control Willbreaker.
        addCard(Zone.BATTLEFIELD, playerA, "Willbreaker", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B},Remove", "Silvercoat Lion");
        setChoice(playerA, "X=0");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Willbreaker", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

}
