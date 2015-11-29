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
public class PhasingTest extends CardTestPlayerBase {

    /**
     * Test that abilities of phased out cards do not trigger or apply their
     * effects
     */
    @Test
    public void TestAbilitiesOfPhasedOutAreNotApplied() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // At the beginning of each player's upkeep, that player chooses artifact, creature, land, or non-Aura enchantment.
        // All nontoken permanents of that type phase out.
        addCard(Zone.HAND, playerA, "Teferi's Realm", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Crusade", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Realm");

        setChoice(playerB, "Non-Aura enchantment");
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Crusade", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
    }

    /**
     * I had Fireshrieker equipped to Taniwha. When Taniwha phased out, the
     * Fireshrieker remained visible on the battlefield, appearing to be
     * attached to a Coldsteel Heart. The Fireshrieker should have been phased
     * out indirectly.
     *
     * 502.15i When a permanent phases out, any local enchantments or Equipment
     * attached to that permanent phase out at the same time. This alternate way
     * of phasing out is known as phasing out "indirectly." An enchantment or
     * Equipment that phased out indirectly won't phase in by itself, but
     * instead phases in along with the card it's attached to.
     */
    @Test
    public void TestIndirectPhasing() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Flying
        // Phasing (This phases in or out before you untap during each of your untap steps. While it's phased out, it's treated as though it doesn't exist.)
        // All nontoken permanents of that type phase out.
        addCard(Zone.HAND, playerA, "Tolarian Drake", 1);
        // Enchant creature
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing", 1); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tolarian Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Tolarian Drake");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tolarian Drake", 0);
        assertPermanentCount(playerA, "Firebreathing", 0);
    }

    @Test
    public void TestIndirectPhasingAgainPhasedIn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Flying
        // Phasing (This phases in or out before you untap during each of your untap steps. While it's phased out, it's treated as though it doesn't exist.)
        // All nontoken permanents of that type phase out.
        addCard(Zone.HAND, playerA, "Tolarian Drake", 1);
        // Enchant creature
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing", 1); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tolarian Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Tolarian Drake");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tolarian Drake", 1);
        assertPermanentCount(playerA, "Firebreathing", 1);
    }
}
