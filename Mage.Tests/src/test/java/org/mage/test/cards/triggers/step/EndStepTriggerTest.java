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
package org.mage.test.cards.triggers.step;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EndStepTriggerTest extends CardTestPlayerBase {

    /**
     * The text is rendered incorrect.
     *
     * First ability should trigger each end step but is triggering only on
     * controller's end step.
     *
     */
    @Test
    public void testTriggersForEachPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        addCard(Zone.HAND, playerA, "Bloodchief Ascension"); // Enchantment {B}
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodchief Ascension");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Bloodchief Ascension", 1);

        assertGraveyardCount(playerA, "Lightning Bolt", 2);

        assertLife(playerA, 20);
        assertLife(playerB, 14);

        assertCounterCount("Bloodchief Ascension", CounterType.QUEST, 2);
    }

    /**
     * Hey, I don't know how to submit bugs but in a game I played today I
     * sacrificed Child of Alara by casting Bound at the end step of my previous
     * opponent's turn, then chose Child as one of the cards to return to my
     * hand. My graveyard was empty so that was the only card I chose. Child
     * returned to my hand but it did NOT trigger for some reason. Nothing was
     * destroyed
     */
    @Test
    public void testSacrificeChildOfAlara() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); //Creature

        // Trample
        // When Child of Alara dies, destroy all nonland permanents. They can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerB, "Icy Manipulator", 1); //Creature
        addCard(Zone.BATTLEFIELD, playerB, "Child of Alara", 1); //Creature
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);
        // Bound
        // Sacrifice a creature. Return up to X cards from your graveyard to your hand, where X is the number of colors that creature was. Exile this card.
        // Determined
        // Other spells you control can't be countered by spells or abilities this turn.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Bound // Determined"); // Instant {3}{B}{G} // {G}{U}

        castSpell(1, PhaseStep.END_TURN, playerB, "Bound");
        addTarget(playerB, "Child of Alara");
        setChoice(playerB, "Child of Alara");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Bound // Determined", 1);

        assertHandCount(playerB, "Child of Alara", 1);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Icy Manipulator", 1);
    }
}
