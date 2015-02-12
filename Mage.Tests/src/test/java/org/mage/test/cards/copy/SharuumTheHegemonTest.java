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
package org.mage.test.cards.copy;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SharuumTheHegemonTest extends CardTestPlayerBase {

    /**
     * 
     */
    @Test
    public void testCloneTriggered() {
        // When Sharuum the Hegemon enters the battlefield, you may return target artifact card from your graveyard to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Sharuum the Hegemon", 1);
        addCard(Zone.HAND, playerA, "Clone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Sharuum the Hegemon"); // what creature to clone

        addTarget(playerA, "Sharuum the Hegemon[no copy]"); // which legend to sacrifice
        
        addTarget(playerA, "Sharuum the Hegemon[no copy]"); // which legend to sacrifice
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 19);



    }

}