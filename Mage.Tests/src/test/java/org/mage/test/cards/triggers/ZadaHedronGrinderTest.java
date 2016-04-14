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

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ZadaHedronGrinderTest extends CardTestPlayerBase {

    /**
     * Playing Zada edh, strive cards such as Rouse the Mob do not copy when
     * targeting only Zada.
     */
    @Test
    public void testWithStriveSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Strive — Rouse the Mob costs {2}{R} more to cast for each target beyond the first.
        // Any number of target creatures each get +2/+0 and gain trample until end of turn.
        addCard(Zone.HAND, playerA, "Rouse the Mob", 1); // Instant - {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rouse the Mob", "Zada, Hedron Grinder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Rouse the Mob", 1);

        assertPowerToughness(playerA, "Zada, Hedron Grinder", 5, 3);
        assertAbility(playerA, "Zada, Hedron Grinder", TrampleAbility.getInstance(), true);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 2);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);

    }

}
