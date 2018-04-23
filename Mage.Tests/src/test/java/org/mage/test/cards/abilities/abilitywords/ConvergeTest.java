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
package org.mage.test.cards.abilities.abilitywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ConvergeTest extends CardTestPlayerBase {

    /**
     * Test with only red mana
     */
    @Test
    public void testOnlyOneColor() {
        // Converge — Radiant Flames deals X damage to each creature, where X is the number of colors of mana spent to cast Radiant Flames.
        addCard(Zone.HAND, playerA, "Radiant Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Akroan Jailer", 1); // 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Akroan Jailer", 1); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Flames");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroan Jailer", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Akroan Jailer", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void testWithTwoColors() {
        // Converge — Radiant Flames deals X damage to each creature, where X is the number of colors of mana spent to cast Radiant Flames.
        addCard(Zone.HAND, playerA, "Radiant Flames", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Akroan Jailer", 1); // 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Akroan Jailer", 1); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Flames");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Akroan Jailer", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Akroan Jailer", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    // Painful Truths (maybe converge mechanic) is bugged with signets. I played the card with Izzet+Orzhov signets and ended up getting 0 cards and losing 0 life.
    @Test
    public void testWithArtifactManaSources() {
        // Converge - You draw X cards and lose X life, where X is the number of colors of mana spent to cast Painful Truths.
        addCard(Zone.HAND, playerA, "Painful Truths", 1); // {2}{B}

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // {1}, {T}: Add {U}{R}.
        addCard(Zone.BATTLEFIELD, playerA, "Izzet Signet", 1);
        // {1}, {T}: Add {W}{B}.
        addCard(Zone.BATTLEFIELD, playerA, "Orzhov Signet", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Add {U}{R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Add {W}{B}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painful Truths");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Izzet Signet", true);
        assertTapped("Orzhov Signet", true);
        assertGraveyardCount(playerA, "Painful Truths", 1);
        assertLife(playerA, 17);
        assertHandCount(playerA, 3);
    }
}
