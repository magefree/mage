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
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NotOfThisWorldTest extends CardTestPlayerBase {

    /**
     * Not of This World doesn't work when trying to counter a triggerd ability
     * from The Abyss targeting your owned and controlled Ruhan of the Fomori .
     * At the time I didn't have any mana open, but Ruhan of the Fomori should
     * qualify for the alternative casting cost (7 less) or Not of This World.
     */
    @Test
    public void testCounterFirstSpell() {
        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of his or her choice. It can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "The Abyss", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);
        // Counter target spell or ability that targets a permanent you control.
        // Not of This World costs {7} less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.
        addCard(Zone.HAND, playerB, "Not of This World");

        // At the beginning of combat on your turn, choose an opponent at random. Ruhan of the Fomori attacks that player this combat if able.
        addCard(Zone.BATTLEFIELD, playerB, "Ruhan of the Fomori", 1); // 7/7

        addTarget(playerB, "Ruhan of the Fomori");
        castSpell(2, PhaseStep.UPKEEP, playerB, "Not of This World", "stack ability (At the beginning of each player's upkeep");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Not of This World", 1);
        assertPermanentCount(playerB, "Ruhan of the Fomori", 1);

        assertTapped("Island", false);
    }

}
