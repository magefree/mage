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
package org.mage.test.cards.triggers.combat.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HixusPrisonWardenTest extends CardTestPlayerBase {

    @Test
    public void testCreatureIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Flash
        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        addCard(Zone.HAND, playerA, "Hixus, Prison Warden", 1); // 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Hixus, Prison Warden");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Hixus, Prison Warden", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    @Test
    public void testCreatureIsExiledAndDoesReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Flash
        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        addCard(Zone.HAND, playerA, "Hixus, Prison Warden", 1); // 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Hixus, Prison Warden");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Boomerang", "Hixus, Prison Warden");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertHandCount(playerA, "Hixus, Prison Warden", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        Permanent lion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals("The lion did come into play this turn", true, lion.hasSummoningSickness());
    }

}
