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
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PreventAttachedEffectTest extends CardTestPlayerBase {

    /**
     * Kaervek the Merciless still deals damage with the triggered ability when
     * enchanted with Temporal Isolation.
     */
    @Test
    public void testDamageToPlayerPrevented() {
        // Whenever an opponent casts a spell, Kaervek the Merciless deals damage to target creature or player equal to that spell's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Kaervek the Merciless");
        // Flash
        // Enchant creature
        // Enchanted creature has shadow.
        // Prevent all damage that would be dealt by enchanted creature.
        addCard(Zone.HAND, playerA, "Temporal Isolation", 1); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1); // {1}{W}
        addCard(Zone.HAND, playerB, "Pillarfield Ox", 1); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temporal Isolation", "Kaervek the Merciless");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        addTarget(playerA, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pillarfield Ox");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kaervek the Merciless", 1);
        assertPermanentCount(playerA, "Temporal Isolation", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
