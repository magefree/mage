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
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NaturesClaimTest extends CardTestPlayerBase {

    @Test
    public void testTargetDestroyable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Destroy target artifact or enchantment. Its controller gains 4 life.
        addCard(Zone.HAND, playerA, "Nature's Claim");

        // Flying
        addCard(Zone.BATTLEFIELD, playerA, "Gold-Forged Sentinel"); // 4/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Claim", "Gold-Forged Sentinel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Nature's Claim", 1);

        assertGraveyardCount(playerA, "Gold-Forged Sentinel", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }

    @Test
    public void testTargetUndestroyable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Destroy target artifact or enchantment. Its controller gains 4 life.
        addCard(Zone.HAND, playerA, "Nature's Claim");

        // Flying
        // Darksteel Gargoyle is indestructible. ("Destroy" effects and lethal damage don't destroy it.)
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Claim", "Darksteel Gargoyle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Nature's Claim", 1);

        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }
}
