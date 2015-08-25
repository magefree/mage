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
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BoostEnchantedTest extends CardTestPlayerBase {

    @Test
    public void testFirebreathingNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Firebreathing", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 2);
    }

    /**
     * On Ghitu Firebreathing (and probably other similar cards), when you
     * activate the ability to give +1/0 to the enchanted creature and the
     * return Ghitu Firebreathing to your hand, the +1/0 goes away on the
     * creature. If you re-cast Ghitu Firebreathing onto the creature, the boost
     * returns.
     *
     * Gatherer Rulings: 9/25/2006 If you return Ghitu Firebreathing to its
     * owner's hand while the +1/+0 ability is on the stack, that ability will
     * still give the creature that was last enchanted by Ghitu Firebreathing
     * +1/+0.
     *
     */
    @Test
    public void testFirebreathingReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang"); // {U}{U} Instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Firebreathing");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature", NO_TARGET, "Boomerang");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Firebreathing", 1);
        assertGraveyardCount(playerB, "Boomerang", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 2);
    }

    /**
     * If the aura moves between activation and resolution, the new enchanted
     * creature should be boosted, not the old one.
     */

    @Test
    public void testFirebreathingWithAuraGraft() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        // Gain control of target Aura that's attached to a permanent. Attach it to another permanent it can enchant.
        addCard(Zone.HAND, playerB, "Aura Graft"); // {1}{U} Instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Aura Graft", "Firebreathing");
        setChoice(playerB, "Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Aura Graft", 1);
        assertPermanentCount(playerB, "Firebreathing", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 3, 4);

    }

}
