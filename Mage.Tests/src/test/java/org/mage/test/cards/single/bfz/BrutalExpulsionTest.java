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
package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class BrutalExpulsionTest extends CardTestPlayerBase {

    /**
     * Brutal Expulsion targeting Gideon, Ally of Zendikar. Gideon has 3 loyalty. Brutal Expulsion resolves,
     * leaves 1 loyalty. I attack Gideon for 1 with a Scion token, Gideon dies. Instead of going to graveyard,
     * Expulsion sends Gideon to exile. However, in game Gideon went to graveyard.
     */
    @Test
    public void testPlaneswalkerExile() {
        // Choose one or both
        // - Return target spell or creature to its owner's hand;
        // or Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Brutal Expulsion");
        // Shock deals 2 damage to target creature or player.
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Planeswalker with 4 loyalty.
        addCard(Zone.BATTLEFIELD, playerB, "Gideon, Ally of Zendikar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brutal Expulsion", playerB);
        setModeChoice(playerA, "2");
        setModeChoice(playerA, null);
        setChoice(playerA, "Yes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Gideon, Ally of Zendikar", 0);
        assertGraveyardCount(playerB, "Gideon, Ally of Zendikar", 0);
        assertExileCount("Gideon, Ally of Zendikar", 1);
    }

}
