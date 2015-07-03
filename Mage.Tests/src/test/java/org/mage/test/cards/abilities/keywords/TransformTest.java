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
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TransformTest extends CardTestPlayerBase {

    @Test
    public void NissaVastwoodSeerTest() {

        addCard(Zone.LIBRARY, playerA, "Forest");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.

        addCard(Zone.HAND, playerA, "Nissa, Vastwood Seer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nissa, Vastwood Seer");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Forest", 7);

        assertPermanentCount(playerA, "Nissa, Vastwood Seer", 0);
        assertPermanentCount(playerA, "Nissa, Sage Animist", 1);
        assertCounterCount("Nissa, Sage Animist", CounterType.LOYALTY, 3);
    }

    @Test
    public void LilianaHereticalHealer() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Liliana, Heretical Healer");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana, Heretical Healer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPermanentCount(playerA, "Liliana, Heretical Healer", 0);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount("Liliana, Defiant Necromancer", CounterType.LOYALTY, 3);
    }

}
