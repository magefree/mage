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
package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class WatcherInTheWebMultipleBlocks extends CardTestPlayerBase {

    @Test
    public void testCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Watcher in the Web", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Ulrich, Uncontested Alpha", 1); // 6/6
        addCard(Zone.BATTLEFIELD, playerB, "Kessig Dire Swine", 1); // 6/6 (trample if delirium)
        addCard(Zone.BATTLEFIELD, playerB, "Howlpack Wolf", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Incorrigible Youths", 1); // 4/3

        // Trample requirement for Kessig Dire Swine
        addCard(Zone.GRAVEYARD, playerB, "Forest", 1);
        addCard(Zone.GRAVEYARD, playerB, "Memnite", 1);
        addCard(Zone.GRAVEYARD, playerB, "Flight", 1);
        addCard(Zone.GRAVEYARD, playerB, "Drain Life", 1);

        // Attack with all 4 creatures and block all with the Watcher in the Web
        attack(2, playerB, "Ulrich, Uncontested Alpha");
        attack(2, playerB, "Kessig Dire Swine");
        attack(2, playerB, "Howlpack Wolf");
        attack(2, playerB, "Incorrigible Youths");

        block(2, playerA, "Watcher in the Web", "Ulrich, Uncontested Alpha");
        block(2, playerA, "Watcher in the Web", "Kessig Dire Swine");
        block(2, playerA, "Watcher in the Web", "Howlpack Wolf");
        block(2, playerA, "Watcher in the Web", "Incorrigible Youths");

        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertLife(playerA, 19);

    }

}
