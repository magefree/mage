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

package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

/*
 *  Vaporkin
 *  Creature — Elemental 2/1, 1U
 *  Flying
 *  Vaporkin can block only creatures with flying.
 */

public class CanBlockOnlyFlyingTest extends CardTestPlayerBase {

    /**
     * Tests if Vaporkin can't block a creature without flying
     */

    @Test
    public void testCannotBlockCreatureWithoutFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Vaporkin", "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Vaporkin", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

    /**
     * Tests if Vaporkin can block a creature with flying
     */

    @Test
    public void testCanBlockCreatureWithFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Wingsteed Rider");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Wingsteed Rider");
        block(3, playerB, "Vaporkin", "Wingsteed Rider");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Wingsteed Rider", 0);
        assertPermanentCount(playerB, "Vaporkin", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Tests if Vaporkin can't block a flying creature after loosing Flying
     */

    @Test
    public void testCantBlockFlyerAfterLosingFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Archetype of Imagination");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Archetype of Imagination");
        block(3, playerB, "Vaporkin", "Archetype of Imagination");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Archetype of Imagination", 1);
        assertPermanentCount(playerB, "Vaporkin", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

/**
     * Tests if Vaporkin can block a creature whicj gained flying
     */

    @Test
    public void testCanBlockCreatureWhichGainedFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Jump");
        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Jump", "Silvercoat Lion");
        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Vaporkin", "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Vaporkin", 0);


    }



}
