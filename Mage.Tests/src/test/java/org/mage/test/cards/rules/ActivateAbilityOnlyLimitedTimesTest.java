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

package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ActivateAbilityOnlyLimitedTimesTest extends CardTestPlayerBase {

    /**
     * Wall of Roots    {1}{G}
     * Creature - Plant Wall
     * 0/5
     * Defender
     * Put a -0/-1 counter on Wall of Roots: Add {G} to your mana pool. Activate this ability only once each turn.
     *
     */
    @Test
    public void testAbilityCanBeActivatedTwice() {

        addCard(Zone.HAND, playerA, "Runeclaw Bear");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertPermanentCount(playerA, "Runeclaw Bear", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testAbilityCantBeActivatedTwice() {

        addCard(Zone.HAND, playerA, "Garruk's Companion");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots",2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Garruk's Companion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertHandCount(playerA, "Garruk's Companion", 0);
        assertPermanentCount(playerA, "Garruk's Companion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Momentary Blink {1}{W}
     * Instant
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     * Flashback (You may cast this card from your graveyard for its flashback cost. Then exile it.)
     */
    @Test
    public void testAbilityCanBeActivatedTwiceIfBlinked() {

        addCard(Zone.HAND, playerA, "Wall of Wood",2);
        addCard(Zone.HAND, playerA, "Momentary Blink");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Wood");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentary Blink", "Wall of Roots", "Cast Wall of Wood");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Wood");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertHandCount(playerA, "Momentary Blink", 0);
        assertPermanentCount(playerA, "Wall of Wood", 2);

        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}