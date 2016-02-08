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
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OverloadTest extends CardTestPlayerBase {

    /**
     * My opponent cast an overloaded Vandalblast, and Xmage would not let me
     * cast Mental Misstep on it.
     *
     * The CMC of a card never changes, and Vandalblast's CMC is always 1.
     *
     * 4/15/2013 Casting a spell with overload doesn't change that spell's mana
     * cost. You just pay the overload cost instead.
     */
    @Test
    public void testCastByOverloadDoesNotChangeCMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Destroy target artifact you don't control.
        // Overload {4}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Vandalblast");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Counter target spell with converted mana cost 1.
        addCard(Zone.HAND, playerB, "Mental Misstep", 1);
        addCard(Zone.BATTLEFIELD, playerB, "War Horn", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vandalblast with overload");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mental Misstep", "Vandalblast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Vandalblast", 1);
        assertGraveyardCount(playerB, "Mental Misstep", 1);

        assertPermanentCount(playerB, "War Horn", 2);

    }

}
