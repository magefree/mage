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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CopySpellTest extends CardTestPlayerBase {

    @Test
    public void copyChainOfVapor() {
        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.
        addCard(Zone.HAND, playerA, "Chain of Vapor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chain of Vapor", "Pillarfield Ox");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Island", 1);
        assertHandCount(playerB, "Pillarfield Ox", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
    }

}
