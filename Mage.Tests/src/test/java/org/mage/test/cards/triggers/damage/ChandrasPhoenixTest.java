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
package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ChandrasPhoenixTest extends CardTestPlayerBase {

    @Test
    public void testReturnByInstantSpell() {
        // Flying
        // Haste (This creature can attack and as soon as it comes under your control.)
        // Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Chandra's Phoenix", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, "Chandra's Phoenix", 1);

    }

    @Test
    public void testReturnByPlaneswalkerDamage() {
        // Flying
        // Haste (This creature can attack and as soon as it comes under your control.)
        // Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Chandra's Phoenix", 1);

        // +1: Chandra Nalaar deals 1 damage to target player.
        // -X: Chandra Nalaar deals X damage to target creature.
        // -8: Chandra Nalaar deals 10 damage to target player and each creature he or she controls.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Nalaar", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);

        assertHandCount(playerA, "Chandra's Phoenix", 1);

    }

}
