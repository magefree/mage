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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ServantOfTheScaleTest extends CardTestPlayerBase {

    /**
     * Tests that the dies triggered ability distributes
     * the +1/+1 counters on Servant of the Scale
     *
     */
    @Test
    public void testDiesTriggeredAbility() {
        // Servant of the Scale enters the battlefield with a +1/+1 counter on it.
        // When Servant of the Scale dies, put X +1/+1 counters on target creature you control, where X is the number of +1/+1 counter on Servant of the Scale.
        addCard(Zone.HAND, playerA, "Servant of the Scale");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Servant of the Scale");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Servant of the Scale");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Servant of the Scale", 1);


        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3,3);

    }  

}
