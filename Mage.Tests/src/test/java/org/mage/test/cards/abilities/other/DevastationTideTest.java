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
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DevastationTideTest extends CardTestPlayerBase {

    @Test
    public void testReturnNonLandPermanents() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Devastation Tide"); // {3}{U}{U}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerA, "Vampiric Rites"); // Enchantment
        addCard(Zone.BATTLEFIELD, playerA, "Hedron Archive"); // Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated"); // Planeswalker
        addCard(Zone.BATTLEFIELD, playerA, "Nimbus Maze", 1); // Land

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // Creature
        addCard(Zone.BATTLEFIELD, playerB, "Vampiric Rites"); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Hedron Archive"); // Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Karn Liberated"); // Planeswalker
        addCard(Zone.BATTLEFIELD, playerB, "Nimbus Maze", 1); // Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devastation Tide");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Vampiric Rites", 1);
        assertHandCount(playerA, "Hedron Archive", 1);
        assertHandCount(playerA, "Karn Liberated", 1);
        assertHandCount(playerA, "Nimbus Maze", 0);

        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Vampiric Rites", 1);
        assertHandCount(playerB, "Hedron Archive", 1);
        assertHandCount(playerB, "Karn Liberated", 1);
        assertHandCount(playerB, "Nimbus Maze", 0);

    }

}
