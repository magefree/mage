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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SoulFoundryTest extends CardTestPlayerBase {

    /**
     * Soul Foundry imprinted with Bloodline Keeper costs 8 colorless mana to
     * use the ability instead of 4 (the converted mana cost of Bloodline
     * Keeper).
     */
    @Test
    public void testBloodlineKeeper() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        // Imprint - When Soul Foundry enters the battlefield, you may exile a creature card from your hand.
        // {X}, {T}: Put a token that's a copy of the exiled card onto the battlefield. X is the converted mana cost of that card.        
        addCard(Zone.HAND, playerA, "Soul Foundry"); // {4}
        addCard(Zone.HAND, playerA, "Bloodline Keeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Foundry");
        setChoice(playerA, "Yes");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X},{T}: Put a token");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soul Foundry", 1);
        
        assertExileCount("Bloodline Keeper", 1);
        assertPermanentCount(playerA, "Bloodline Keeper", 1);
    }
}