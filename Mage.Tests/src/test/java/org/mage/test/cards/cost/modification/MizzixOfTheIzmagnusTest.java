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
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MizzixOfTheIzmagnusTest extends CardTestPlayerBase {

    @Test
    public void testSpellsFixedCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        // Incinerate deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.
        addCard(Zone.HAND, playerA, "Incinerate"); // {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incinerate", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Incinerate", 1);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 14);

    }

    /**
     * Does not reduce the cost of {X} spells
     */
    @Test
    public void testSpellsVariableCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");
        // Blaze deals X damage to target creature or player.
        addCard(Zone.HAND, playerA, "Blaze", 2); // Sorcery - {X}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Blaze", 2);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * Test to reduce Buyback costs
     */
    @Test
    public void testSpellsBuybackCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");// 2/2
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        // Target creature gets +3/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Seething Anger"); // {R} Buyback {3}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seething Anger", "Mizzix of the Izmagnus");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertHandCount(playerA, "Seething Anger", 1);

        assertPowerToughness(playerA, "Mizzix of the Izmagnus", 5, 2);
        assertCounterCount(playerA, CounterType.EXPERIENCE, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }
}
