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
public class HeartbeatOfSpringTest extends CardTestPlayerBase {

    /**
     * Heartbeat of Spring does not function on urza's
     */
    @Test
    public void testWorksForUrzasLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // {T}: Add {C} to your mana pool. If you control an Urza's Power-Plant and an Urza's Tower, add {C}{C} to your mana pool instead.
        addCard(Zone.HAND, playerA, "Urza's Mine", 1);
        // Whenever a player taps a land for mana, that player adds one mana to his or her mana pool of any type that land produced.
        addCard(Zone.HAND, playerA, "Heartbeat of Spring"); // {2}{G}
        // Whenever a player casts a white spell, you may gain 1 life.
        addCard(Zone.HAND, playerA, "Angel's Feather"); // {2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heartbeat of Spring");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Mine");

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}:");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Angel's Feather");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Heartbeat of Spring", 1);
        assertPermanentCount(playerA, "Angel's Feather", 1);
    }
}
