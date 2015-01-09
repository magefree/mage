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
package org.mage.test.cards.replacement;

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

public class RegenerateTest extends CardTestPlayerBase {

    @Test
    public void testRegenerateAbilityGainedByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");

        addCard(Zone.BATTLEFIELD, playerB, "Sagu Archer");
        addCard(Zone.HAND, playerB, "Molting Snakeskin");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);


        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Molting Snakeskin", "Sagu Archer");
        attack(2, playerB, "Sagu Archer");
        block(2, playerA, "Underworld Cerberus", "Sagu Archer");
        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerB, "{2}{B}: Regenerate {this}.");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // attacker has to regenerat because of damage
        
        assertPermanentCount(playerA, "Underworld Cerberus", 1);
        assertPermanentCount(playerB, "Sagu Archer", 1);
        assertPermanentCount(playerB, "Molting Snakeskin", 1);

    }
}
