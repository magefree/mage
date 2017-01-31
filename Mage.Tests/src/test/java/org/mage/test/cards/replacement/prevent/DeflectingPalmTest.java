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
package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Deflecting Palm Instant {R}{W} The next time a source of your choice would
 * deal damage to you this turn, prevent that damage. If damage is prevented
 * this way, Deflecting Palm deals that much damage to that source's controller.
 *
 * @author LevelX2
 */
public class DeflectingPalmTest extends CardTestPlayerBase {

    /**
     * Tests if a damage spell is selected as source the damage is prevented and
     * is dealt to the controller of the damage spell
     */
    @Test
    public void testPreventDamageFromSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerB, "Deflecting Palm");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Deflecting Palm", null, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Deflecting Palm", 1);
    }

    /**
     * I tried to prevent damage dealt by Deflecting Palm using a Drokoma's
     * Command and it seems it's not working properly. According to this, it
     * should work.
     */
    @Test
    public void testPreventDamageWithDromokasCommand() {

        // Choose two -
        // - Prevent all damage target instant or sorcery spell would deal this turn;
        // - or Target player sacrifices an enchantment;
        // - Put a +1/+1 counter on target creature;
        // - or Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Dromoka's Command");// Instant {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature 2/2

        // The next time a source of your choice would deal damage to you this turn, prevent that damage.
        // If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        addCard(Zone.HAND, playerB, "Deflecting Palm"); // Instant {R}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Deflecting Palm");
        setChoice(playerB, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dromoka's Command", "Deflecting Palm");
        addTarget(playerA, "Silvercoat Lion");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "3");

        attack(1, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerB, "Deflecting Palm", 1);
        assertGraveyardCount(playerA, "Dromoka's Command", 1);

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
