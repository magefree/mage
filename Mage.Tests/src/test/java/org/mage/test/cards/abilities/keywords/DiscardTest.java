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
 * @author LevelX2
 */
public class DiscardTest extends CardTestPlayerBase {

    /*
     * If Rest in Peace is in play, every card going to the graveyard goes to exile instead.
     * If a card is discarded while Rest in Peace is on the battlefield, abilities that function
     * when a card is discarded (such as madness) still work, even though that card never reaches
     * a graveyard.
     */
    @Test
    public void testRestInPeaceAndCycle() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Tranquil Thicket");
        addCard(Zone.BATTLEFIELD, playerB, "Rest in Peace", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {G}"); //cycling ability
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertExileCount("Tranquil Thicket", 1); //exiled by Rest in Peace
        assertHandCount(playerA, "Tranquil Thicket", 0); //should be exiled
        assertHandCount(playerA, 1); // the card drawn by Cycling
    }

    @Test
    public void AmnesiaTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Amnesia");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Amnesia", playerA);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertHandCount(playerA, 0);
    }

    /**
     * With Bazaar of Baghdad, if you use it when you have no cards in hand, you
     * draw 2, it asks for you to discard 3, but you can't. So the game can't
     * progress and you lose on time.
     */
    @Test
    public void testBazaarOfBaghdad() {
        // {T}: Draw two cards, then discard three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Bazaar of Baghdad", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw two cards, then discard three cards");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 2);

    }

    /**
     * "When using the enchantment "Liliana's Caress" in a vs. human match, it
     * seems to count cards that read "you choose a card, enemy discards them"
     * as the choosing playing making the discard. This means that any spell
     * that lets you pick what your opponent discards doesn't trigger the Caress
     * like it should."
     */
    @Test
    public void testLilianasCaress() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Whenever an opponent discards a card, that player loses 2 life.
        addCard(Zone.HAND, playerA, "Liliana's Caress", 1); // ENCHANTMENT {1}{B}
        // Target opponent reveals his or her hand. You choose a card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Coercion", 1); // SORCERY {2}{B}

        addCard(Zone.HAND, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana's Caress");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coercion", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Liliana's Caress", 1);

        assertGraveyardCount(playerA, "Coercion", 1);
        assertGraveyardCount(playerB, "Island", 1);
        assertHandCount(playerB, "Island", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }
}
