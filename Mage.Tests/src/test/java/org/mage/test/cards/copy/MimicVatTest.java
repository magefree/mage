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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MimicVatTest extends CardTestPlayerBase {

    /**
     * All the clone type cards that may enter as a copy of something don't work
     * correctly with Mimic Vat. The only one I found that works (the token
     * being able to clone something) is Phyrexian Metamorph. Phyrexian
     * Metamorph is implemented differently than the rest of similar functioning
     * cards, ie. Clone, Phantasmal Image, Body Double, Clever Impersonator.
     * Also The copy ability on Phyrexian Metamorph is optional but it is forced
     * in game
     */
    @Test
    public void TestClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        // {3}, {T}: Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1); // Artifact {3}
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1);// Creature {3}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}, Sacrifice a creature");
        setChoice(playerA, "Yes");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3},{T}: Put a token onto the battlefield");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Clone", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    @Test
    public void TestPhyrexianMetamorph() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        // {3}, {T}: Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1); // Artifact {3}
        // {2}, {T}, Sacrifice a creature: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Vault", 1);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph", 1);// Creature {3}{UP}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}, Sacrifice a creature");
        setChoice(playerA, "Yes");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3},{T}: Put a token onto the battlefield");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Phyrexian Metamorph", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

}
