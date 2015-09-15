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
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TargetedTriggeredTest extends CardTestPlayerBase {

    /**
     * Tests that the first spell that targets Kira, Great Glass-Spinner is
     * countered.
     *
     */
    @Test
    @Ignore
    // this does currently not work in test, because the target event will be fired earlier during tests,
    // so the zone change counter for the fixed target of the counterspell will not work
    public void testKiraGreatGlassSpinnerFirstSpellTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Kira, Great Glass-Spinner", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Kira, Great Glass-Spinner");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertPermanentCount(playerB, "Kira, Great Glass-Spinner", 1);
    }

    /**
     * With Ashenmoor Liege on the battlefield, my opponent casts Claustrophobia
     * on it without losing 4hp.
     */
    @Test
    public void testAshenmoorLiege() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Claustrophobia"); // {1}{U}{U}

        // Other black creatures you control get +1/+1.
        // Other red creatures you control get +1/+1.
        // Whenever Ashenmoor Liege becomes the target of a spell or ability an opponent controls, that player loses 4 life.
        addCard(Zone.BATTLEFIELD, playerB, "Ashenmoor Liege", 1);  // 4/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Claustrophobia", "Ashenmoor Liege");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 16);

        assertPermanentCount(playerA, "Claustrophobia", 1);
        assertPowerToughness(playerB, "Ashenmoor Liege", 4, 1);
    }

}
