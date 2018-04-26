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
package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TransformTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // First strike, lifelink
        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        // BACK: It That Rides as One
        // Creature 4/4 First strike, lifelink
        addCard(Zone.HAND, playerA, "Lone Rider"); // Creature {1}{W} 1/1
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Venerable Monk"); // Creature {2}{W} 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lone Rider");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Venerable Monk");

        attack(3, playerA, "Lone Rider");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 19);

        assertPermanentCount(playerA, "Venerable Monk", 1);
        assertPermanentCount(playerA, "It That Rides as One", 1);

    }

    @Test
    public void testRollbackWithTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // First strike, lifelink
        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        // BACK: It That Rides as One
        // Creature 4/4 First strike, lifelink
        addCard(Zone.HAND, playerA, "Lone Rider"); // Creature {1}{W} 1/1
        // When Venerable Monk enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Venerable Monk"); // Creature {2}{W} 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lone Rider");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Venerable Monk");

        attack(3, playerA, "Lone Rider");

        rollbackTurns(3, PhaseStep.END_TURN, playerA, 0);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 19);

        assertPermanentCount(playerA, "Venerable Monk", 1);
        assertPermanentCount(playerA, "It That Rides as One", 1);

    }

}
