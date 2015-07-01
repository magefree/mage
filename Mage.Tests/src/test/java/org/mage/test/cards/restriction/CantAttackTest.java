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
package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantAttackTest extends CardTestPlayerBase {

    /**
     * Tests "If all other elves get the Forestwalk ability and can't be blockt
     * from creatures whose controler has a forest in game"
     */
    @Test
    public void testAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer"); // 4/4

        // Except for creatures named Akron Legionnaire and artifact creatures, creatures you control can't attack.
        addCard(Zone.BATTLEFIELD, playerB, "Akron Legionnaire"); // 8/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Myr Enforcer"); // 4/4

        attack(2, playerB, "Akron Legionnaire");
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Myr Enforcer");

        attack(3, playerA, "Silvercoat Lion");
        attack(3, playerA, "Myr Enforcer");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 8); // 8 + 4
        assertLife(playerB, 14); // 4 + 2
    }

    @Test
    public void testAttackHarborSerpent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        // Islandwalk (This creature is unblockable as long as defending player controls an Island.)
        // Harbor Serpent can't attack unless there are five or more Islands on the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Harbor Serpent"); // 5/5
        addCard(Zone.HAND, playerA, "Island");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Harbor Serpent"); // 5/5

        attack(2, playerB, "Harbor Serpent");
        attack(2, playerB, "Silvercoat Lion");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        attack(3, playerA, "Harbor Serpent");
        attack(3, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 13);
        assertLife(playerA, 18);
    }

    @Test
    public void testBlazingArchon() {
        // Flying
        // Creatures can't attack you.
        addCard(Zone.BATTLEFIELD, playerA, "Blazing Archon");

        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // Planeswalker 4 loyality counter

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        attack(2, playerB, "Pillarfield Ox", "Ajani Goldmane");
        attack(2, playerB, "Silvercoat Lion", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertTapped("Silvercoat Lion", false);
        assertTapped("Pillarfield Ox", true);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 2);
    }

    @Test
    public void testCowedByWisdom() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Enchant creature
        // Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand.
        addCard(Zone.HAND, playerA, "Cowed by Wisdom"); // Planeswalker 4 loyality counter

        // Bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        // Battle-Mad Ronin attacks each turn if able.
        addCard(Zone.BATTLEFIELD, playerB, "Battle-Mad Ronin");
        addCard(Zone.HAND, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cowed by Wisdom", "Battle-Mad Ronin");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertTapped("Battle-Mad Ronin", false);
    }
}
