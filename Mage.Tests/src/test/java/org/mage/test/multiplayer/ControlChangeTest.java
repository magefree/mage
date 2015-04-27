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
package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ControlChangeTest extends CardTestMultiPlayerBase {

    /**
     * Checks that the change of max hand size changes if the control
     * of Jin-Gitaxias, Core Augur changes.
     */
    @Test
    public void testSimple() {
        // Each opponent's maximum hand size is reduced by seven.
        addCard(Zone.BATTLEFIELD, playerA, "Jin-Gitaxias, Core Augur");
        
        addCard(Zone.BATTLEFIELD, playerB, "Island",4);
        addCard(Zone.HAND, playerB, "Sower of Temptation");
        addCard(Zone.HAND, playerB, "Leyline of Anticipation");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sower of Temptation");
        addTarget(playerB, "Jin-Gitaxias, Core Augur");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);

        assertPermanentCount(playerB, "Leyline of Anticipation", 1);

        assertHandCount(playerB, "Sower of Temptation", 0);
        assertGraveyardCount(playerB, "Sower of Temptation", 0);

        assertPermanentCount(playerB, "Jin-Gitaxias, Core Augur", 1);
        assertPermanentCount(playerB, "Sower of Temptation", 1);
        
        Assert.assertEquals(0, playerA.getMaxHandSize());
        Assert.assertEquals(7, playerB.getMaxHandSize());
        Assert.assertEquals(0, playerC.getMaxHandSize());
        Assert.assertEquals(7, playerD.getMaxHandSize());
    }

}