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
package org.mage.test.cards.continuous;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SwarmSurgeTest extends CardTestPlayerBase {

    @Test
    public void testSwarmSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Devoid
        // When Birthing Hulk enters the battlefield, put two 1/1 colorless Eldrazi Scion creature tokens onto the battlefield. They have "Sacrifice this creature: Add {C} to your mana pool."
        // {1}{C}: Regenerate Birthing Hulk.
        addCard(Zone.HAND, playerA, "Birthing Hulk"); // {6}{G}  5/4
        // Devoid
        // Creatures you control get +2/+0 until end of turn.
        // Colorless creatures you control also gain first strike until end of turn.
        addCard(Zone.HAND, playerA, "Swarm Surge"); // {2}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthing Hulk");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swarm Surge");

        attack(1, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Swarm Surge", 1);

        assertPowerToughness(playerA, "Birthing Hulk", 7, 4);
        assertAbility(playerA, "Birthing Hulk", FirstStrikeAbility.getInstance(), true);
        assertPowerToughness(playerA, "Eldrazi Scion", 3, 1, Filter.ComparisonScope.All);
        assertAbility(playerA, "Eldrazi Scion", FirstStrikeAbility.getInstance(), true, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 2);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), false);

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

}
