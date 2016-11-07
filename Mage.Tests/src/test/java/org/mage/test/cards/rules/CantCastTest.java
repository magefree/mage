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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantCastTest extends CardTestPlayerBase {

    /**
     * I control Void Winnower. But my opponent can cast Jayemdae Tome (that's
     * converted mana cost is even) He can cast other even spell. Test casting
     * cost 4
     */
    @Test
    public void testVoidWinnower1() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.HAND, playerA, "Jayemdae Tome", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jayemdae Tome"); // {4}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Jayemdae Tome", 1);

        assertPermanentCount(playerA, "Jayemdae Tome", 0);

    }

    /**
     * Test with X=3
     */
    @Test
    public void testVoidWinnower2() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Blaze deals X damage to target creature or player.
        addCard(Zone.HAND, playerA, "Blaze", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerA);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Blaze", 1);

        assertLife(playerB, 20);

    }

    /**
     * Test with X=4
     */
    @Test
    public void testVoidWinnower3() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Blaze deals X damage to target creature or player.
        addCard(Zone.HAND, playerA, "Blaze", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Blaze", 0);
        assertGraveyardCount(playerA, "Blaze", 1);

        assertLife(playerB, 16);

    }

    @Test
    public void testVoidWinnowerWithMorph() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");
        /*
         Pine Walker
         Creature - Elemental
         5/5
         Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
         Whenever Pine Walker or another creature you control is turned face up, untap that creature.
         */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "", 0);
        assertHandCount(playerA, "Pine Walker", 1);

    }

    /**
     * Test with casting cost = {0}
     */
    @Test
    public void testVoidWinnowerZero() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        // Metalcraft - {T}: Add one mana of any color to your mana pool. Activate this ability only if you control three or more artifacts.
        addCard(Zone.HAND, playerA, "Mox Opal", 1); // {0}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Opal");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Mox Opal", 1);

        assertLife(playerB, 20);

    }

    /**
     * Test that panic can only be cast during the correct pahse/steü
     */
    @Test
    public void testPanic() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Cast Panic only during combat before blockers are declared.
        // Target creature can't block this turn.
        // Draw a card at the beginning of the next turn's upkeep.
        addCard(Zone.HAND, playerA, "Panic", 4); // Instant - {R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Panic", "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Panic", 3);
        assertHandCount(playerA, 4);
        assertGraveyardCount(playerA, "Panic", 1);

    }

    /**
     * I "Aether Vialed" (Aether Vial) an Ethersworn Canonist into the
     * battlefield in response to a colored spell(an Elf). The Canonist entered
     * the battlefield. Then, my oponente used Abrupt Decay to destroy it and
     * continue to play spells normaly. Ethersworn Canonist effect should
     * imediately effect the game as it entered the battlefield, and still count
     * spells cast earlier in the turn. In other words, my oponent shouldn't be
     * able to cast anymore colored spells.
     */
    @Test
    public void testEtherswornCanonist() {
        // Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells.
        addCard(Zone.HAND, playerA, "Ethersworn Canonist", 4); // Creaturre - {1}{W}

        // At the beginning of your upkeep, you may put a charge counter on Aether Vial.
        // {T}: You may put a creature card with converted mana cost equal to the number of charge counters on Aether Vial from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Aether Vial", 1);
        // addCounters(1, PhaseStep.UPKEEP, playerA, "Aether Vial", CounterType.CHARGE, 1);

        addCard(Zone.HAND, playerB, "Llanowar Elves", 1); // Creature {G}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // Abrupt Decay can't be countered by spells or abilities.
        // Destroy target nonland permanent with converted mana cost 3 or less.
        addCard(Zone.HAND, playerB, "Abrupt Decay", 1); // {B}{G}

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Llanowar Elves");
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You");
        setChoice(playerB, "Ethersworn Canonist");
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Abrupt Decay", "Ethersworn Canonist");
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Aether Vial", CounterType.CHARGE, 2);
        assertPermanentCount(playerB, "Llanowar Elves", 1);

        assertPermanentCount(playerA, "Ethersworn Canonist", 1);
        assertHandCount(playerB, "Abrupt Decay", 1);

    }

}
