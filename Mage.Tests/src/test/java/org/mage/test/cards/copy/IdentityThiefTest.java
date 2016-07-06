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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class IdentityThiefTest extends CardTestPlayerBase {

    /**
     * This is probably a narrow case of a wider problem base. Identity Thief
     * copied Molten Sentry and died immediately (should have been either a 5/2
     * or a 2/5, whatever the original Molten Sentry was).
     */
    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // As Molten Sentry enters the battlefield, flip a coin. If the coin comes up heads, Molten Sentry enters the battlefield as a 5/2 creature with haste.
        // If it comes up tails, Molten Sentry enters the battlefield as a 2/5 creature with defender.
        addCard(Zone.HAND, playerA, "Molten Sentry"); // {3}{R}

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Identity Thief"); // {2}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Molten Sentry");

        attack(2, playerB, "Identity Thief");
        addTarget(playerB, "Molten Sentry");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertExileCount("Molten Sentry", 1);

        assertPermanentCount(playerB, "Identity Thief", 0);
        assertPermanentCount(playerB, "Molten Sentry", 1);
    }

    @Test
    public void testCopyPrimalClay() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        addCard(Zone.HAND, playerA, "Primal Clay"); // {4}

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerB, "Identity Thief"); // {2}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Primal Clay");

        attack(2, playerB, "Identity Thief");
        addTarget(playerB, "Primal Clay");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertExileCount("Primal Clay", 1);

        assertPermanentCount(playerB, "Identity Thief", 0);
        assertPermanentCount(playerB, "Primal Clay", 1);
    }

}
