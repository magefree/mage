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
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ArtisanOfFormsTest extends CardTestPlayerBase {

    /**
     * Targeting a Artisan of Forms triggers it Heroic ability. So it can copy a
     * target creature. If Cackling Counterpart later resolves, it should copy
     * the creature that Artisan of Forms copies, not the Artisan itself.
     */
    @Test
    public void testCopyTriggeredByCracklingCounterpart() {
        // Heroic - Whenever you cast a spell that targets Artisan of Forms, you may have Artisan of Forms become a copy of target creature and gain this ability.
        addCard(Zone.HAND, playerA, "Artisan of Forms"); // {1}{U}
        // {1}{U}{U} Put a token onto the battlefield that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artisan of Forms");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cackling Counterpart", "Artisan of Forms");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Cackling Counterpart", 1);
        assertPermanentCount(playerA, "Artisan of Forms", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        // 1 + 2 Silvercoat Lion at the end
        assertPermanentCount(playerA, "Silvercoat Lion", 2);

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals("Creature has to have Cast + Heroic ability", 2, permanent.getAbilities().size());
            }
        }
    }

    /**
     * Targeting a Artisan of Forms triggers it Heroic ability. So it can copy a
     * target creature. If populate spell later resolves, it should copy the
     * creature that Artisan of Forms copies, not the Artisan itself.
     */
    @Test
    public void testCopyTriggeredByPopulate() {
        // Heroic - Whenever you cast a spell that targets Artisan of Forms, you may have
        // Artisan of Forms become a copy of target creature and gain this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Artisan of Forms");
        // {1}{U}{U} Put a token onto the battlefield that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Eyes in the Skies");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Artisan of Forms");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Eyes in the Skies");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Cackling Counterpart", 1);
        assertPermanentCount(playerA, "Artisan of Forms", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Bird", 1);
        // 3 Silvercoat Lion at the end
        assertPermanentCount(playerA, "Silvercoat Lion", 3);

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals("Creature has to have Cast + Heroic ability", 2, permanent.getAbilities().size());
            }
        }
    }

}
