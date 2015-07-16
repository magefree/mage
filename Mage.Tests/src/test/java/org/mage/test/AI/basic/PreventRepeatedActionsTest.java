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
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class PreventRepeatedActionsTest extends CardTestPlayerBaseAI {

    /**
     * Check that an equipment is not switched again an again between creatures
     *
     */
    @Test
    public void testEquipOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // Equipped creature gets +1/+1.
        // Equip {1}({1}: Attach to target creature you control. Equip only as a sorcery.)
        addCard(Zone.BATTLEFIELD, playerA, "Fireshrieker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        int tappedLands = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            if (permanent.isTapped()) {
                tappedLands++;
            }
        }
        Assert.assertEquals("AI should only used Equipment once", 2, tappedLands);
    }

}
