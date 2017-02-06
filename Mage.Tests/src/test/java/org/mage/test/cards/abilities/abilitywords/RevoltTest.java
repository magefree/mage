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
package org.mage.test.cards.abilities.abilitywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RevoltTest extends CardTestPlayerBase {

    /**
     * In a duel commander match, I played a turn 1 Narnam Renegade off a basic
     * forest, and it entered the battlefield with a +1/+1 counter (it shouldn't
     * have).
     */
    @Test
    public void testFalseCondition() {
        // Deathtouch
        // <i>Revolt</i> &mdash; Narnam Renegade enters the battlefield with a +1/+1 counter on it if a permanent you controlled left this battlefield this turn.
        addCard(Zone.HAND, playerA, "Narnam Renegade", 1); // Creature 1/2 {G}
        addCard(Zone.HAND, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Narnam Renegade");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Narnam Renegade", 1, 2);
    }

    @Test
    public void testTrueCondition() {
        // Deathtouch
        // <i>Revolt</i> &mdash; Narnam Renegade enters the battlefield with a +1/+1 counter on it if a permanent you controlled left this battlefield this turn.
        addCard(Zone.HAND, playerA, "Narnam Renegade", 1); // Creature 1/2 {G}
        // {T}, Sacrifice Terramorphic Expanse: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Terramorphic Expanse", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Narnam Renegade");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Terramorphic Expanse", 1);
        assertPowerToughness(playerA, "Narnam Renegade", 2, 3);
    }

}
