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
package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ShamenEnKorTest extends CardTestPlayerBase {

    /**
     * Tests that 2 of 3 damage is redirected while 1 damage is still dealt to
     * original target
     */
    @Test
    public void testFirstAbilityNonCombatDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        addCard(Zone.BATTLEFIELD, playerB, "Shaman en-Kor"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Shaman en-Kor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: The next 1 damage", "Silvercoat Lion", "Lightning Bolt");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: The next 1 damage", "Silvercoat Lion", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Shaman en-Kor", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testSecondAbilityNonCombatDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        addCard(Zone.BATTLEFIELD, playerB, "Shaman en-Kor"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{W}: The next time", "Silvercoat Lion", "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Shaman en-Kor", 1);

    }

}
