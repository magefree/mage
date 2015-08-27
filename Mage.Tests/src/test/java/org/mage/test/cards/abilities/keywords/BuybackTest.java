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
public class BuybackTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertHandCount(playerA, "Elvish Fury", 1);
    }

    /**
     * It seems that a spell with it's buyback cost paid returned to hand after
     * it fizzled (by failing to target) when it should go to graveyard.
     *
     * "Q: If I pay a spell's buyback cost, but it fizzles, do I get the card
     * back anyway? A: If you pay a buyback cost, you would get the card back
     * during the spell's resolution. So if it never resolves (i.e., something
     * counters it or it fizzles against all of its targets), you don't get the
     * card back."
     */
    @Test
    public void testBuybackSpellFizzles() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Silvercoat Lion", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }

    @Test
    public void testBuybackSpellWasCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Elvish Fury", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }
}
