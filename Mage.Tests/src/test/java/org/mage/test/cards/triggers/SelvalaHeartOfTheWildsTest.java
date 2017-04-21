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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
/**
 * Selvala, Heart of the Wilds {1}{G}{G} Whenever another creature enters the
 * battlefield, its controller may draw a card if its power is greater than each
 * other creature's power Add X mana in any combination of colors to your mana
 * pool, where X is the greatest power among creatures you control
 */

public class SelvalaHeartOfTheWildsTest extends CardTestPlayerBase {

    @Test
    public void testTrigger() {
        // No card will be drawn due to the Memnite having a lower power than any other permanent on the battlefield
        addCard(Zone.LIBRARY, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 0); // no cards drawn

    }

    @Test
    public void testTriggerWithGiantGrowth() {
        // After Memnite enters the battlefield, the trigger fires.  In response, 2 Giant Growths targeting the Memnite
        // pumps its power to the highest on the battlefield allowing the controller to draw a card.
        addCard(Zone.LIBRARY, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.HAND, playerA, "Giant Growth", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite"); // a whopping 7/7

        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 2); // 2 cards drawn

    }
}
