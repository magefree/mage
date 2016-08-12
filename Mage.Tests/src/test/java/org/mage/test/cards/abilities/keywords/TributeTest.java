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
public class TributeTest extends CardTestPlayerBase {

    /**
     * When returning a Pharagax Giant from the graveyard to the battlefield via
     * the ability of Ink-Eyes, Servant of Oni the Tribute mechanic did not
     * trigger, even though it says 'enters the battlefield' and not 'cast' on
     * the card.
     */
    @Test
    public void testPharagaxGiant() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Ninjutsu {3}{B}{B} ({3}{B}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        // Whenever Ink-Eyes, Servant of Oni deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        // {1}{B}: Regenerate Ink-Eyes.
        addCard(Zone.BATTLEFIELD, playerA, "Ink-Eyes, Servant of Oni"); // 5/4

        // Tribute 2 ((As this creature enters the battlefield, an opponent of your choice may put two +1/+1 counter on it.)
        // When Pharagax Giant enters the battlefield, if tribute wasn't paid, Pharagax Giant deals 5 damage to each opponent.
        addCard(Zone.GRAVEYARD, playerB, "Pharagax Giant"); // Creature 3/3

        attack(1, playerA, "Ink-Eyes, Servant of Oni");
        setChoice(playerB, "Yes"); // pay tribute

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Ink-Eyes, Servant of Oni", true);
        assertLife(playerB, 15);

        assertPermanentCount(playerA, "Pharagax Giant", 1);
        assertPowerToughness(playerA, "Pharagax Giant", 5, 5);

    }

}
