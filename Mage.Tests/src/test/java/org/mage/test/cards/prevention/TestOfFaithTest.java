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

package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class TestOfFaithTest extends CardTestPlayerBase {

    @Test
    public void testOneAttackerOneBlockerUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Blur Sliver");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");

        attack(2, playerB, "Blur Sliver");
        block(2, playerA, "Soulmender", "Blur Sliver");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 3, 3);

        assertPermanentCount(playerB, "Blur Sliver", 1);
    }

    @Test
    public void testOneAttackerTwoBlockerOneUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");

        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Tusker");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");

        attack(2, playerB, "Kalonian Tusker");
        block(2, playerA, "Elvish Mystic", "Kalonian Tusker");
        block(2, playerA, "Soulmender", "Kalonian Tusker");


        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 2, 2); // one damage was prevented so Soulmender got +1/+1
        assertPermanentCount(playerA, "Elvish Mystic", 0);

        assertPermanentCount(playerB, "Kalonian Tusker", 1); // only 2 damage to Kalonian Tusker so he still lives
    }

    @Test
    public void testOneAttackerTwoBlockerTwoUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");

        addCard(Zone.HAND, playerA, "Test of Faith", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Tusker");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Elvish Mystic");

        attack(2, playerB, "Kalonian Tusker");
        block(2, playerA, "Elvish Mystic", "Kalonian Tusker");
        block(2, playerA, "Soulmender", "Kalonian Tusker");


        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 2, 2); // one damage was prevented so Soulmender got +1/+1
        assertPermanentCount(playerA, "Elvish Mystic", 1);
        assertPowerToughness(playerA, "Elvish Mystic", 2, 2); // two damage were prevented so Elvish Mystic got +2/+2

        assertPermanentCount(playerB, "Kalonian Tusker", 1); // only 2 damage to Kalonian Tusker so he still lives
    }
}
