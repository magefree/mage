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
public class MirrorworksTest extends CardTestPlayerBase {

    /**
     * If you play Mox Diamond, with Mirrorworks in play, and create a token
     * copy, and you have no lands in hand, the Mox will enter the battlefield
     * as usual instead of the graveyard.
     */
    @Test
    public void TestCopyWithoutLand() {
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerA, "Mox Diamond", 1); // Artifact {0}

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, create a token that's a copy of that artifact.
        addCard(Zone.BATTLEFIELD, playerA, "Mirrorworks", 1); // Artifact {5}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Diamond");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mox Diamond", 1);
        assertTappedCount("Island", true, 2);
        assertGraveyardCount(playerA, "Mountain", 1);

    }

    @Test
    public void TestCorrectCopy() {
        addCard(Zone.HAND, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerA, "Mox Diamond", 1); // Artifact {0}

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, create a token that's a copy of that artifact.
        addCard(Zone.BATTLEFIELD, playerA, "Mirrorworks", 1); // Artifact {5}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Diamond");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mox Diamond", 2);
        assertGraveyardCount(playerA, "Mountain", 2);

    }
}
