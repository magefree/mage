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
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class CastCreaturesTest extends CardTestPlayerBaseAI {

    /**
     * Tests that the creature is cast if enough mana is available
     */
    @Test
    public void testSimpleCast() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void testSimpleCast2() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
    }

    @Test
    public void testSimpleCast3() {
        // Affinity for artifacts (This spell costs less to cast for each artifact you control.)
        addCard(Zone.HAND, playerA, "Myr Enforcer");
        // {T}: Add to your mana pool.
        // {T}, {1}, Sacrifice Mind Stone: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Myr Enforcer", 1);
    }

    @Test
    public void testSimpleCast4() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.HAND, playerA, "Fireshrieker");
        addCard(Zone.HAND, playerA, "Blazing Specter"); // {2}{R}{B}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Fireshrieker", 0);
        assertPermanentCount(playerA, "Blazing Specter", 1);
    }

    @Test
    public void testCast4Creature() {
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Loyal Sentry");       // {W}     1/1
        addCard(Zone.HAND, playerA, "Silvercoat Lion");    // {1}{W}  2/2
        addCard(Zone.HAND, playerA, "Rootwater Commando"); // {2}{U}  2/2
        addCard(Zone.HAND, playerA, "Bog Wraith");         // {3}{B}  3/3

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Swamp", 1);

        // assertLife(playerB, 11);  // 1 + 1+2 +  1+2+2 =
        assertPermanentCount(playerA, "Loyal Sentry", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Rootwater Commando", 1);
        assertPermanentCount(playerA, "Bog Wraith", 1);

    }

    @Test
    public void testCast4Creature2() {
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 1);

        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Loyal Sentry");       // {W}     1/1
        addCard(Zone.HAND, playerA, "Steadfast Guard");    // {W}{W}  2/2
        addCard(Zone.HAND, playerA, "Rootwater Commando"); // {2}{U}  2/2
        addCard(Zone.HAND, playerA, "Bog Wraith");         // {3}{B}  3/3

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Swamp", 1);

        // assertLife(playerB, 11);  // 1 + 1+2 +  1+2+2 =
        assertPermanentCount(playerA, "Loyal Sentry", 1);
        assertPermanentCount(playerA, "Steadfast Guard", 1);
        assertPermanentCount(playerA, "Rootwater Commando", 1);
        assertPermanentCount(playerA, "Bog Wraith", 1);

    }
}
