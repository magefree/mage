package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

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
/**
 *
 * @author LevelX2
 */
public class ChronozoaTest extends CardTestPlayerBase {

    /**
     * Chronozoa's duplicating ability is triggering whenever any card is put
     * into a graveyard from play, not just Chronozoa itself. Here's an excerpt
     * from the log: As you can see, I sacrificed Viscera Seer and for some
     * reason that triggered Chronozoa's ability.
     */
    @Test
    public void testTriggerOtherCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Flying
        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        // When Chronozoa is put into a graveyard from play, if it had no time counters on it, create two tokens that are copies of it.
        addCard(Zone.HAND, playerA, "Chronozoa"); // {3}{U}
        addCard(Zone.GRAVEYARD, playerA, "Chronozoa");
        // Sacrifice a creature: Scry 1. (To scry 1, look at the top card of your library, then you may put that card on the bottom of your library.)
        addCard(Zone.BATTLEFIELD, playerA, "Viscera Seer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Viscera Seer");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Viscera Seer", 1);
        assertGraveyardCount(playerA, "Chronozoa", 1);
        assertPermanentCount(playerA, "Chronozoa", 1);
        assertPermanentCount(playerA, 5);

        assertHandCount(playerA, 0);
    }

}
