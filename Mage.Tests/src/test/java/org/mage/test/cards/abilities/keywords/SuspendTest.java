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

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SuspendTest extends CardTestPlayerBase {

    /**
     * Tests Epochrasite works (give suspend to a exiled card) When Epochrasite
     * dies, exile it with three time counters on it and it gains suspend.
     *
     */
    @Test
    public void testEpochrasite() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Epochrasite", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Epochrasite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Epochrasite");

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Epochrasite", 1); // returned on turn 7 with 3 +1/+1 Counter
        assertPowerToughness(playerA, "Epochrasite", 4, 4);
        assertAbility(playerA, "Epochrasite", HasteAbility.getInstance(), true);

    }

    /**
     * Tests Jhoira of the Ghitu works (give suspend to a exiled card) {2},
     * Exile a nonland card from your hand: Put four time counters on the exiled
     * card. If it doesn't have suspend, it gains suspend.
     *
     */
    @Test
    public void testJhoiraOfTheGhitu() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Jhoira of the Ghitu", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},Exile a nonland card from your hand: Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend <i>(At the beginning of your upkeep, remove a time counter from that card. When the last is removed, cast it without paying its mana cost. If it's a creature, it has haste.)</i>.");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(11, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Jhoira of the Ghitu", 1);
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    /**
     * Tests that a spell countered with delay goes to exile with 3 time
     * counters and can be cast after the 3 counters are removed
     *
     */
    @Test
    public void testDelay() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // Instant {1}{U}
        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend. (At the beginning of its owner's upkeep, remove a counter from that card. When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Delay", "Silvercoat Lion");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Delay", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    @Test
    public void testDeepSeaKraken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Suspend 9-{2}{U}
        // Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.
        addCard(Zone.HAND, playerA, "Deep-Sea Kraken", 1);

        // Instant {1}{U}
        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend. (At the beginning of its owner's upkeep, remove a counter from that card. When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Deep-Sea Kraken", 1);

        assertCounterOnExiledCardCount("Deep-Sea Kraken", CounterType.TIME, 8); // -1 from spell of player B

    }
}
