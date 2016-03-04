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
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ItThatBetraysTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/796
     *
     * When an opponent sacrifices a fetchland and you have an It That Betrays
     * in play, sacrificing the fetchland that comes under your control from its
     * ability returns it to play under your control, allowing you to fetch
     * infinite lands.
     *
     */
    @Test
    public void testPermanentControlEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Flooded Strand", 1);

        addCard(Zone.BATTLEFIELD, playerB, "It That Betrays");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 1 life, Sacrifice");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Pay 1 life, Sacrifice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 19);

        // Going to graveyard if player B sacrifices it
        assertGraveyardCount(playerA, "Flooded Strand", 1);
    }

    //It That Betrays doesn't care what zone the card is when the effect resolves. It will return the card regardless.
    @Test
    public void testExileItThatBetraysEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Flooded Strand", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace", 1);

        addCard(Zone.BATTLEFIELD, playerB, "It That Betrays");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 1 life, Sacrifice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        // Player B now controls a Flooded Strand, even though it went to exile
        assertPermanentCount(playerB, "Flooded Strand", 1);
    }

    /**
     * I just sacrificed a Spreading Seas to an attacking It That Betrays, and
     * it returned the Spreading Seas under my control. It made me choose a land
     * to enchant, and I drew a card.
     */
    @Test
    public void testExileItThatBetraysEffectEnchantment() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // {1}{U}

        // Annihilator 2 (Whenever this creature attacks, defending player sacrifices two permanents.)
        // Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerB, "It That Betrays"); // 11/11
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Mountain");

        attack(2, playerB, "It That Betrays");
        setChoice(playerA, "Spreading Seas");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 9);
        assertLife(playerB, 20);

        assertHandCount(playerA, "Spreading Seas", 0);

        // Player B now controls a Silvercoat Lion and Spreading Seas
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Spreading Seas", 0);
        assertGraveyardCount(playerA, "Spreading Seas", 0);
        assertPermanentCount(playerB, "Spreading Seas", 1);
    }

    /**
     * It That Betrays had a strange bug. Attacked opponent's planeswalker with
     * him (I think it was Venser, the Sojourner), then opponent sacrificed said
     * planeswalker to ITB (It That Betrays) annihilator ability, ITB ability
     * triggered and Venser came over to my control, but ITB was still attacking
     * my own planeswalker and killed it. Shouldn't happen because that's an
     * entirely new planeswalker, not the one I was attacking. That one died,
     * therefore the attack was invalid.
     */
    @Test
    public void testExileAttackedPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        // -1: Creatures can't be blocked this turn.
        // -8: You get an emblem with "Whenever you cast a spell, exile target permanent."
        addCard(Zone.BATTLEFIELD, playerA, "Venser, the Sojourner", 1);

        // Annihilator 2 (Whenever this creature attacks, defending player sacrifices two permanents.)
        // Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerB, "It That Betrays"); // 11/11

        attack(2, playerB, "It That Betrays", "Venser, the Sojourner");
        setChoice(playerA, "Venser, the Sojourner");
        setChoice(playerA, "Mountain");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Player B now controls a Silvercoat Lion and Spreading Seas
        assertPermanentCount(playerB, "Venser, the Sojourner", 1);
        assertPermanentCount(playerB, "Mountain", 1);
    }
}
