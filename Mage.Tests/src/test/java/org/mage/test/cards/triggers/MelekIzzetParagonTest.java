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
 * @author LevelX2
 */
public class MelekIzzetParagonTest extends CardTestPlayerBase {

    /**
     * Wenn Melek, Izzet Paragon liegt und man einen Red/Blue Sun's Zenith von
     * der Bib spielt, wird er nicht kopiert, auch wenn der Effekt auf dem Stack
     * sichtbar ist.
     *
     * Meine Theorie ist, dass die Kopie beim in die Bib mischen den Originalen
     * nimmt und er daher nicht mehr dem Stack ist um selbst verrechnet zu
     * werden
     *
     */
    @Test
    public void testCopyZenith() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Play with the top card of your library revealed.
        // You may cast the top card of your library if it's an instant or sorcery card.
        // Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Melek, Izzet Paragon");

        // Red Sun's Zenith deals X damage to target creature or player.
        // If a creature dealt damage this way would die this turn, exile it instead.
        // Shuffle Red Sun's Zenith into its owner's library.
        addCard(Zone.LIBRARY, playerA, "Red Sun's Zenith"); // {X}{R}
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Red Sun's Zenith", playerB);
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Red Sun's Zenith", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 12);

    }
}
