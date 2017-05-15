/*
 * Copyright 2017 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.single.wwk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx
 */
public class MindbreakTrapTest extends CardTestPlayerBase {

    /*
      Mindbreak Trap {2}{U}{U}
      Instant
      If an opponent cast three or more spells this turn, you may pay {0} rather than pay Mindbreak Trap's mana cost.
      Exile any number of target spells.
     */
    private final String mindBreakTrap = "Mindbreak Trap";
    private final String shock = "Shock"; // card for counters {R}
    private final String grapeShot = "Grapeshot"; // storm card 1{R}

    /**
     * Play 2 Shock and then Grapeshot (with Storm) to trigger twice
     * Afterwards use Mindbreak Tap to exile all Storm spells from stack
     */
    @Test
    public void mindBreakTrap_Exile_All_Spells() {

        removeAllCardsFromHand(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        addCard(Zone.HAND, playerA, mindBreakTrap);
        addCard(Zone.HAND, playerB, shock, 2);
        addCard(Zone.HAND, playerB, grapeShot);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, shock, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, shock, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, grapeShot, playerA);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, mindBreakTrap, "Grapeshot^Grapeshot^Grapeshot");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, shock, 2);
        assertGraveyardCount(playerB, grapeShot, 0); // exiled by Mindbreak Trap
        assertGraveyardCount(playerA, mindBreakTrap, 1);
        assertLife(playerA, 16); // 2x2 from two Shock  = 4 and 3 (Storm twice) from Grapeshot get exiled
    }


}
