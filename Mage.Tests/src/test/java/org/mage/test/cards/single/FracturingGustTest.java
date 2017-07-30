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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FracturingGustTest extends CardTestPlayerBase {

    @Test
    public void testWithStaticAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // Players can't gain life.
        // At the beginning of your upkeep, Witch Hunt deals 4 damage to you.
        // At the beginning of your end step, target opponent chosen at random gains control of Witch Hunt.
        addCard(Zone.BATTLEFIELD, playerB, "Witch Hunt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerB, "Witch Hunt", 1);

        // + 2 from destroyed Witch Hunt
        assertLife(playerA, 22);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithTriggerdAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // When Guardian Automaton dies, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Automaton", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerA, "Guardian Automaton", 1);

        // + 2 from destroyed Guardian Automaton
        assertLife(playerA, 25);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // Flying
        // Indestructible (Damage and effects that say "destroy" don't destroy this creature.)
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Gargoyle", 1); // Artifact Creature - Gargoyle

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertPermanentCount(playerB, "Darksteel Gargoyle", 1);

        // No life because Darksteel Gargoyle is Indestructible
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithRestInPeace() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1); // Artifact Creature 2/2

        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Rest in Peace"); // Artifact

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerB, "Ornithopter", 0);
        assertExileCount(playerB, "Ornithopter", 1);

        assertLife(playerA, 24);
        assertLife(playerB, 20);

    }
}
