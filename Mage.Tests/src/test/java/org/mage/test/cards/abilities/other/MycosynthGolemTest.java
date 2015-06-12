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

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class MycosynthGolemTest extends CardTestPlayerBase {

    /** 
     * Mycosynth Golem
     * Artifact Creature — Golem 4/5, 11 (11)
     * Affinity for artifacts (This spell costs {1} less to cast for each 
     * artifact you control.)
     * Artifact creature spells you cast have affinity for artifacts. (They cost
     * {1} less to cast for each artifact you control.)
     *
     */

    // @Ignore // at this time player.getPlayable() does not account for spells that gain abilities
    @Test
    public void testSpellsAffinity() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Golem");
        addCard(Zone.HAND, playerA, "Alpha Myr");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Myr");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Alpha Myr", 1);
        assertHandCount(playerA, "Alpha Myr", 0);
        
        Permanent mountain = getPermanent("Mountain", playerA);
        Permanent forest = getPermanent("Forest", playerA);
        int tappedLands = 0;
        if (mountain.isTapped()) {
            tappedLands++;
        }
        if (forest.isTapped()) {
            tappedLands++;
        }
        Assert.assertEquals("only one land may be tapped because the cost reduction", 1, tappedLands);

    }
   
}
