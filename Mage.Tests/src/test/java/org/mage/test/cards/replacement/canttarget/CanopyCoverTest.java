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
package org.mage.test.cards.replacement.canttarget;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CanopyCoverTest extends CardTestPlayerBase {

    /**
     * Test spell
     */
    @Test
    public void testCantBeTargetedWithSpells() {
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void testCantBeTargetedWithAbilities() {
        // {U},Sacrifice AEther Spellbomb: Return target creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerB, "AEther Spellbomb");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{U},Sacrifice", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "AEther Spellbomb", 0);
        assertPermanentCount(playerB, "AEther Spellbomb", 1);
    }
}
