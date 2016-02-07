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
package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class NeedleDropTest extends CardTestPlayerBase {

    @Test
    public void testTargetShouldBeDamaged() {
        // Needle Drop deals 1 damage to target creature or player that was dealt damage this turn.
        addCard(Zone.HAND, playerA, "Needle Drop", 4);
        addCard(Zone.HAND, playerA, "Shock", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerB, "Flying Men");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Needle Drop", playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Needle Drop", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Needle Drop", "Hill Giant");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Needle Drop", "Flying Men");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Hill Giant", 0);
        assertPermanentCount(playerB, "Flying Men", 1);
    }

    @Test
    public void testTargetIsNotConsideredDamagedNextTurn() {
        // Needle Drop deals 1 damage to target creature or player that was dealt damage this turn.
        addCard(Zone.HAND, playerA, "Needle Drop", 2);
        addCard(Zone.HAND, playerA, "Shock", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Needle Drop", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Needle Drop", "Hill Giant");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerB, "Hill Giant", 1);
    }

}
