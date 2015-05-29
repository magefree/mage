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

public class UnearthTest extends CardTestPlayerBase {

    /**
     * Hellspark Elemental (and probably other cards with the unearth ability) - If I unearth the elemental, 
     * attack, and then go to the end of my turn both the "sacrifice" and "exile" clauses will trigger and 
     * the game will ask me which one I want to put on the stack first. If I choose "sacrifice" first and 
     * "exile" second, all good, the exile part resolves first and the elemental is exiled, the sacrifice 
     * part does nothing afterwards. But if I choose "exile" first and "sacrifice" second then the elemental 
     * will be sacrificed and placed on my graveyard and after that the "exile" resolves but does nothing, as 
     * I'm guessing it can't "find" the elemental anymore and so it stays in my graveyard, despite the fact 
     * that because I use its unearth ability it should always be exiled once leaving the battlefield no matter what. 
     * The bug should be easy to reproduce if following the order I mention above (click the exile part, 
     * so the sacrifice goes on the top of the stack).
     */
    @Test
    public void testUnearthAttackExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // 3/1 - Trample, haste
        // At the beginning of the end step, sacrifice Hellspark Elemental.
        // Unearth {1}{R} ({1}{R}: Return this card from your graveyard to the battlefield. 
        // It gains haste. Exile it at the beginning of the next end step or if it would 
        // leave the battlefield. Unearth only as a sorcery.)        
        addCard(Zone.GRAVEYARD, playerA, "Hellspark Elemental", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        
        attack(1, playerA, "Hellspark Elemental");
        
        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertGraveyardCount(playerA, "Hellspark Elemental", 0);
        assertLife(playerB, 17);

        assertPermanentCount(playerA, "Hellspark Elemental", 0);
        assertExileCount("Hellspark Elemental", 1);
    }

}