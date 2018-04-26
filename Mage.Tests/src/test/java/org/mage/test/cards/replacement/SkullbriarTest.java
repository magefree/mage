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
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Dilnu
 */
public class SkullbriarTest extends CardTestPlayerBase {

    /**
     * Skullbriar retains counters even when Humility is on the field.
     */
    @Test
    public void testSkullbriarCloudshift() {
        // Counters remain on Skullbriar as it moves to any zone other than a player's hand or library.
        addCard(Zone.BATTLEFIELD, playerB, "Skullbriar, the Walking Grave");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerB, "Battlegrowth");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Battlegrowth", "Skullbriar, the Walking Grave");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloudshift", "Skullbriar, the Walking Grave");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Skullbriar, the Walking Grave", CounterType.P1P1, 1);
    }

    /**
     * Skullbriar should not retain counters when Humility is on the field.
     */
    @Test
    public void testHumilityAndSkullbriarCloudshift() {
        // Counters remain on Skullbriar as it moves to any zone other than a player's hand or library.
        addCard(Zone.BATTLEFIELD, playerB, "Skullbriar, the Walking Grave");

        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerB, "Battlegrowth");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Battlegrowth", "Skullbriar, the Walking Grave");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloudshift", "Skullbriar, the Walking Grave");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Skullbriar, the Walking Grave", CounterType.P1P1, 0);
    }
}
