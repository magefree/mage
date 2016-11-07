
/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test the funtions of Feldon of the Third Path - {1}{R}{R} Legendary Creature
 * - Human Artificer 2/3 {2}{R}, {T} : Create a tokenonto the battlefield that's
 * a copy of target creature card in your graveyard, except it's an artifact in
 * addition to its other types. It gains haste. Sacrifice it at the beginning of
 * the next end step.
 *
 *
 * @author LevelX2
 */
public class FeldonOfTheThirdPathTest extends CardTestPlayerBase {

    /**
     * Checking that enters the battlefield abilities of the copied creature
     * card works.
     *
     */
    @Test
    public void testETBEffect() {
        // When Highway Robber enters the battlefield, target opponent loses 2 life and you gain 2 life.
        addCard(Zone.GRAVEYARD, playerA, "Highway Robber", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R},{T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                "Highway Robber");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Highway Robber", 1);
        assertPermanentCount(playerA, "Feldon of the Third Path", 1);

        assertLife(playerA, 22); // +2 from Robber
        assertLife(playerB, 18); // -2 from Robber

    }

    @Test
    public void testETB2Effect() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}{R},{T}: Create a token that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.",
                "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Feldon of the Third Path", 1);
        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
