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

package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class BorosReckonerTest extends CardTestPlayerBase {
    /**
     * Boros Reckoner
     * {R/W}{R/W}{R/W}
     * Creature — Minotaur Wizard
     * Whenever Boros Reckoner is dealt damage, it deals that much damage to target creature or player.
     * {R/W}: Boros Reckoner gains first strike until end of turn..
     */

    /**
     * If damage is dealt to Boros Reckoner - Exactly the same amount of damage
     * can be dealt to target creature or player.
     *
     */
    @Test
    public void testDamageAmountLikeDamageDealt() {
        // When Phytotitan dies, return it to the battlefield tapped under its owner's control at the beginning of his or her next upkeep.
        addCard(Zone.BATTLEFIELD, playerA, "Phytotitan");
        addCard(Zone.BATTLEFIELD, playerB, "Boros Reckoner");

        attack(2, playerB, "Boros Reckoner");
        block(2,playerA, "Phytotitan", "Boros Reckoner");
        addTarget(playerB, playerA);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 13); // got 7 damage from Boros Reckoner
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Phytotitan", 1); // returned at the next upkeep
        assertTapped("Phytotitan", true);

    }

}
