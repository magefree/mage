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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfectTest extends CardTestPlayerBase {

    /**
     *
     * 702.89. Infect 702.89a Infect is a static ability. 702.89b Damage dealt
     * to a player by a source with infect doesn’t cause that player to lose
     * life. Rather, it causes the player to get that many poison counters. See
     * rule 119.3. 702.89c Damage dealt to a creature by a source with infect
     * isn’t marked on that creature. Rather, it causes that many -1/-1 counters
     * to be put on that creature. See rule 119.3. 702.89d If a permanent leaves
     * the battlefield before an effect causes it to deal damage, its last known
     * information is used to determine whether it had infect. 702.89e The
     * infect rules function no matter what zone an object with infect deals
     * damage from. 702.89f Multiple instances of infect on the same object are
     * redundant.
     *
     */
    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerB, "Tine Shrike"); // 2/1 Infect

        attack(2, playerB, "Tine Shrike");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 2);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testLoseInfectUse() {
        // Creatures your opponents control lose infect.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast");

        addCard(Zone.BATTLEFIELD, playerB, "Tine Shrike"); // 2/1 Infect

        attack(2, playerB, "Tine Shrike");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 0);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

    /**
     * Inkmoth Nexus has no effect it he attacks becaus it has infect but there
     * are no counters added
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/296553-melira-sylvok-outcast-vs-inkmoth-nexus
     */
    @Test
    public void testInkmothNexusLoseInfect() {
        // Creatures your opponents control lose infect.
        // Creatures you control can't have -1/-1 counters placed on them.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast");
        // Put a -1/-1 counter on target creature. When that creature dies this turn, its controller gets a poison counter.
        addCard(Zone.HAND, playerA, "Virulent Wound"); // Instant {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Inkmoth Nexus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Virulent Wound", "Melira, Sylvok Outcast");
        // {1}: Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land.
        // (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}: {this} becomes");
        attack(2, playerB, "Inkmoth Nexus");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Virulent Wound", 1);
        assertPowerToughness(playerA, "Melira, Sylvok Outcast", 2, 2);
        assertTapped("Plains", true);
        assertTapped("Inkmoth Nexus", true);
        assertCounterCount(playerA, CounterType.POISON, 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
