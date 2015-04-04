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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AmplifyTest extends CardTestPlayerBase {

    /**
     * Tests if +1/+1 counters are added
     */
    @Test
    public void testAmplifyOneCard() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to target creature or player
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }
    /**
     * Tests if +1/+1 counters are added
     */
    @Test
    public void testAmplifyTwoCards() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to target creature or player
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon^Phantasmal Dragon");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 11,11); // 5 + 6 from Amplify
    }
    /**
     * Tests that it works for Clone
     */
    @Test
    public void testAmplifyWithClone() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to target creature or player
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        addCard(Zone.HAND, playerB, "Clone", 1);
        addCard(Zone.HAND, playerB, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, "Kilnmouth Dragon");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Phantasmal Dragon");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify

        assertPermanentCount(playerB, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerB, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }

}
