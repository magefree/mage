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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AnimateDeadTest extends CardTestPlayerBase {

    @Test
    public void testAnimateOpponentsCreature() {
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Enchant creature card in a graveyard
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        // Enchanted creature gets -1/-0.
        addCard(Zone.HAND, playerA, "Animate Dead"); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 1, 2);
        assertPermanentCount(playerA, "Animate Dead", 1);
    }

    @Test
    public void testAnimateEternalWitness() {
        // When Eternal Witness enters the battlefield, you may return target card from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerB, "Eternal Witness", 1);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Enchant creature card in a graveyard
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        // Enchanted creature gets -1/-0.
        addCard(Zone.HAND, playerA, "Animate Dead"); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Eternal Witness");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Eternal Witness", 1, 1);
        assertPermanentCount(playerA, "Animate Dead", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Buddy animated an Eternal Witness. I killed the Eternal Witness. Animate
     * Dead stayed on the battlefield. Using Meren, Eternal Witness came back to
     * the battlefield and immediately got enchanted by Animate Dead.
     *
     * Very weird. Animate Dead should've hit the graveyard the first time its
     * creature died, right?
     *
     */
    @Test
    public void testAnimateAndKillEternalWitness() {
        // When Eternal Witness enters the battlefield, you may return target card from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerB, "Eternal Witness", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Enchant creature card in a graveyard
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        // Enchanted creature gets -1/-0.
        addCard(Zone.HAND, playerA, "Animate Dead"); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Eternal Witness");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Eternal Witness");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Eternal Witness", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Animate Dead", 1);
        assertPermanentCount(playerA, "Animate Dead", 0);
    }
}
