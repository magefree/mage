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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StarfieldOfNyxTest extends CardTestPlayerBase {

    /**
     * I had Starfield of Nyx out. With the upkeep trigger, I brought back a
     * Cloudform, which was the fifth enchantment. I had another Cloudform, and
     * Starfield of Nyx not only turned both of them into creatures (it
     * shouldn't, because they're auras), but it also destroyed them. The
     * manifests stayed on the battlefield without Flying or Hexproof.
     *
     */
    @Test
    public void testCloudform() {
        // At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield.
        // Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Thopter Spy Network", 2); // {2}{U}{U}  - added to come to 5 enchantments on the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // At the beginning of your upkeep, you may return target enchantment card from your graveyard to the battlefield.
        // As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in
        // addition to its other types and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Starfield of Nyx"); // "{4}{W}"
        // When Cloudform enters the battlefield, it becomes an Aura with enchant creature. Manifest the top card of your library and attach Cloudform to it.
        // Enchanted creature has flying and hexproof.
        addCard(Zone.HAND, playerA, "Cloudform"); // {1}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Cloudform");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Starfield of Nyx");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudform");

        addTarget(playerA, "Cloudform");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Thopter Spy Network", 0);
        assertPowerToughness(playerA, "", 2, 2, Filter.ComparisonScope.All); // the manifested cards
        assertPermanentCount(playerA, "Starfield of Nyx", 1);
        assertPowerToughness(playerA, "Thopter Spy Network", 4, 4, Filter.ComparisonScope.All);
        assertPermanentCount(playerA, "Cloudform", 2);
    }

    /**
     * Regarding Starfield of Nyx: I tried to bring back a Singing Bell Strike
     * to enchant Silumgar, the Drifting Death. I chose the Aura, but instead of
     * enchanting Silumgar, it stayed in the graveyard. This shouldn't be the
     * case, because Auras only target when they're cast as spells.
     */
    @Test
    public void testHexproof() {
        // At the beginning of your upkeep, if you control an artifact, put a 1/1 colorless Thopter artifact creature token with flying onto the battlefield.
        // Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // At the beginning of your upkeep, you may return target enchantment card from your graveyard to the battlefield.
        // As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in
        // addition to its other types and has base power and base toughness each equal to its converted mana cost.
        addCard(Zone.HAND, playerA, "Starfield of Nyx"); // "{4}{W}"
        // Enchant creature
        // When Singing Bell Strike enters the battlefield, tap enchanted creature.
        // Enchanted creature doesn't untap during its controller's untap step.
        // Enchanted creature has "{6}: Untap this creature."
        addCard(Zone.GRAVEYARD, playerA, "Singing Bell Strike");

        addCard(Zone.BATTLEFIELD, playerB, "Silumgar, the Drifting Death", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Starfield of Nyx");

        addTarget(playerA, "Singing Bell Strike");
        setChoice(playerA, "Silumgar, the Drifting Death");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Starfield of Nyx", 1);
        assertPermanentCount(playerA, "Singing Bell Strike", 1);

        Permanent enchantment = getPermanent("Singing Bell Strike", playerA);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = currentGame.getPermanent(enchantment.getAttachedTo());
            Assert.assertEquals("Silumgar was enchanted", enchanted.getName().equals("Silumgar, the Drifting Death"), true);
        } else {
            Assert.assertEquals("Singing Bell Strike not on the battlefield", false, true);
        }
    }
}
