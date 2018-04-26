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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VengefulPharaohTest extends CardTestPlayerBase {

    @Test
    public void controlledByOtherBeforeGraveyardTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        addCard(Zone.HAND, playerA, "Vengeful Pharaoh", 1); // Creature 5/4 {2}{B}{B}{B}
        // Destroy target permanent.
        addCard(Zone.HAND, playerA, "Vindicate", 1); // Sorcery {1}{W}{B}

        addCard(Zone.HAND, playerB, "Control Magic", 1); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vengeful Pharaoh");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Control Magic", "Vengeful Pharaoh");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vindicate", "Vengeful Pharaoh");

        attack(4, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Vindicate", 1);
        assertGraveyardCount(playerB, "Control Magic", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Vengeful Pharaoh", 0);
        assertLibraryCount(playerA, "Vengeful Pharaoh", 1);

        assertLife(playerA, 18);
    }
}
