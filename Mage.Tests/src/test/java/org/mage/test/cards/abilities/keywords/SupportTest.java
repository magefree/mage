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
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SupportTest extends CardTestPlayerBase {

    /**
     * Support Ability can target its source. Its cannot really.
     */
    @Test
    public void testCreatureSupport() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // When Gladehart Cavalry enters the battlefield, support 6.
        // Whenever a creature you control with a +1/+1 counter on it dies, you gain 2 life.
        addCard(Zone.HAND, playerA, "Gladehart Cavalry"); // {5}{G}{G} 6/6

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gladehart Cavalry");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox^Gladehart Cavalry");// Gladehart Cavalry should not be allowed

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertPowerToughness(playerA, "Pillarfield Ox", 3, 5);
        assertPowerToughness(playerA, "Gladehart Cavalry", 6, 6);
    }

}
