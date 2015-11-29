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
public class InfernalScarringTest extends CardTestPlayerBase {

    /**
     * The 'draw a card, when this creature dies' didn't trigger after i
     * sacrificed a creature enchanted with infernal scarring (enchanted
     * creature was Fetid Imp and the sac trigger came from a Nantuko Husk).
     *
     */
    @Test
    public void testDiesTrigger() {
        // Sacrifice a creature: Nantuko Husk gets +2/+2 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Nantuko Husk", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Enchant creature
        // Enchanted creature gets +2/+0 and has "When this creature dies, draw a card."
        addCard(Zone.HAND, playerA, "Infernal Scarring"); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infernal Scarring", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice a creature");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Nantuko Husk", 4, 4);
        assertGraveyardCount(playerA, "Infernal Scarring", 1);
        assertHandCount(playerA, 1);
    }

}
