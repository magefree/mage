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
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BaneOfProgessTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        // You may play land cards from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Put a token onto the battlefield that's a copy of target artifact or creature.
        // Cipher (Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        addCard(Zone.HAND, playerA, "Stolen Identity"); // {4}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // When Bane of Progress enters the battlefield, destroy all artifacts and enchantments. Put a +1/+1 counter on Bane of Progress for each permanent destroyed this way.
        addCard(Zone.HAND, playerB, "Bane of Progress");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stolen Identity", "Crucible of Worlds");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bane of Progress");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Stolen Identity", 1);
        assertPermanentCount(playerA, "Crucible of Worlds", 0);

        assertPermanentCount(playerB, "Bane of Progress", 1);
        assertPowerToughness(playerB, "Bane of Progress", 4, 4);
    }
}
