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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnOnlyFromGraveyardTest extends CardTestPlayerBase {

    /**
     * Effects like Gift of Immortality and Fool's Demise are able to return
     * Academy Rector to the battlefield after the exile trigger if they are put
     * on the stack after.
     */
    @Test
    public void testFoolsDemise() {
        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector", 1); // Creature 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, "Primal Rage", 2);
        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under your control.
        // When Fool's Demise is put into a graveyard from the battlefield, return Fool's Demise to its owner's hand.
        addCard(Zone.HAND, playerA, "Fool's Demise"); // Enchantment {4}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fool's Demise", "Academy Rector");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Academy Rector");

        setChoice(playerA, "When enchanted creature dies"); // Select triggered ability to execute last
        setChoice(playerA, "Yes");
        addTarget(playerA, "Primal Rage");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertHandCount(playerA, "Fool's Demise", 1);
        assertPermanentCount(playerA, "Primal Rage", 1);
        assertExileCount("Academy Rector", 1);

    }

    @Test
    public void testGiftOfImmortality() {
        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector", 1); // Creature 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.LIBRARY, playerA, "Primal Rage", 2);

        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under its owner's control.
        // Return Gift of Immortality to the battlefield attached to that creature at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Gift of Immortality"); // Enchantment {2}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Immortality", "Academy Rector");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Academy Rector");

        setChoice(playerA, "When enchanted creature dies"); // Select triggered ability to execute last
        setChoice(playerA, "Yes"); // May exile it
        addTarget(playerA, "Primal Rage");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Gift of Immortality", 1);
        assertPermanentCount(playerA, "Primal Rage", 1);
        assertExileCount("Academy Rector", 1);

    }
}
