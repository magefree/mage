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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ParadoxHazeTest extends CardTestPlayerBase {

    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // Enchant player
        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        addCard(Zone.HAND, playerA, "Paradox Haze", 1); // {2}{U}
        // At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Verdant Force", 1); // {5}{G}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Paradox Haze", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verdant Force");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Paradox Haze", 1);
        assertPermanentCount(playerA, "Verdant Force", 1);
        assertPermanentCount(playerA, "Saproling", 3);// 1 from turn 2 and 2 from turn 3
    }

    @Test
    public void testCopied() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // Enchant player
        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        addCard(Zone.HAND, playerA, "Paradox Haze", 1); // {2}{U}

        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        addCard(Zone.HAND, playerA, "Copy Enchantment", 1); // {2}{U}

        // At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Verdant Force", 1); // {5}{G}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Paradox Haze", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Enchantment");
        setChoice(playerA, "Paradox Haze");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verdant Force");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Paradox Haze", 2);
        assertPermanentCount(playerA, "Verdant Force", 1);
        assertPermanentCount(playerA, "Saproling", 4); // 1 from turn 2 and 3 from turn 3
    }
}
