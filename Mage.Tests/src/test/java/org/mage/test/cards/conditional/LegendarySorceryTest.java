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
package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LegendarySorceryTest extends CardTestPlayerBase {

    @Test
    public void testCastSuccessful() {
        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        // Exile all nonland permanents that aren’t legendary.
        addCard(Zone.HAND, playerA, "Urza's Ruinous Blast"); // Sorcery Legendary  {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // non Legendary
        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Angel of Wrath", 1); // Legendary

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // non Legendary
        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        addCard(Zone.BATTLEFIELD, playerB, "Akroma, Angel of Wrath", 1); // Legendary

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Ruinous Blast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Urza's Ruinous Blast", 1);

        assertPermanentCount(playerA, "Plains", 5);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertPermanentCount(playerA, "Akroma, Angel of Wrath", 1);
        assertPermanentCount(playerB, "Akroma, Angel of Wrath", 1);

    }

    @Test
    public void testCastNotSuccessful() {
        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        // Exile all nonland permanents that aren’t legendary.
        addCard(Zone.HAND, playerA, "Urza's Ruinous Blast"); // Sorcery Legendary  {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // non Legendary

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // non Legendary
        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        addCard(Zone.BATTLEFIELD, playerB, "Akroma, Angel of Wrath", 1); // Legendary

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Ruinous Blast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Urza's Ruinous Blast", 0);

        assertPermanentCount(playerA, "Plains", 5);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertPermanentCount(playerA, "Akroma, Angel of Wrath", 0);
        assertPermanentCount(playerB, "Akroma, Angel of Wrath", 1);

    }

    @Test
    public void testCastSuccessfulFromExile() {
        // First strike
        // When this enters the battlefield, exile target instant or sorcery card from an opponent's graveyard.
        // You may cast that card this turn and you may spend mana as though it were mana of any color.
        // If that card would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Dire Fleet Daredevil"); // Creature  {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // non Legendary
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Angel of Wrath", 1); // Legendary

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1); // non Legendary
        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        addCard(Zone.BATTLEFIELD, playerB, "Akroma, Angel of Wrath", 1); // Legendary
        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        // Exile all nonland permanents that aren’t legendary.
        addCard(Zone.GRAVEYARD, playerB, "Urza's Ruinous Blast"); // Sorcery Legendary  {4}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dire Fleet Daredevil");
        addTarget(playerA, "Urza's Ruinous Blast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Ruinous Blast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Urza's Ruinous Blast", 1);

        assertHandCount(playerA, "Dire Fleet Daredevil", 0);
        assertPermanentCount(playerA, "Dire Fleet Daredevil", 0);

        assertExileCount(playerA, "Dire Fleet Daredevil", 1);
        assertExileCount(playerA, "Silvercoat Lion", 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);

        assertPermanentCount(playerA, "Plains", 5);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);

        assertPermanentCount(playerA, "Akroma, Angel of Wrath", 1);
        assertPermanentCount(playerB, "Akroma, Angel of Wrath", 1);

    }
}
