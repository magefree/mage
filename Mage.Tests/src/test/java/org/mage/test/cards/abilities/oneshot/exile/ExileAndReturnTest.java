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
package org.mage.test.cards.abilities.oneshot.exile;

import mage.abilities.keyword.ReachAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ExileAndReturnTest extends CardTestPlayerBase {

    @Test
    public void testExileAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3},{T}", "Silvercoat Lion");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testExileAndReturnWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        // Put a +1/+1 counter on target creature.
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.HAND, playerB, "Battlegrowth"); // Instant {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", "Silvercoat Lion");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3},{T}", "Silvercoat Lion");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);
        assertGraveyardCount(playerB, "Battlegrowth", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 3, 3);
    }

    @Test
    public void testExileAndReturnWithCountersAndAuras() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        // Whenever an Aura becomes attached to Bramble Elemental, put two 1/1 green Saproling creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Bramble Elemental"); // Creature 4/4
        // Put a +1/+1 counter on target creature.
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 5);
        addCard(Zone.HAND, playerB, "Battlegrowth"); // Instant {G}
        // Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)
        // Enchanted creature gets +1/+1 for each Forest you control.
        addCard(Zone.HAND, playerB, "Blanchwood Armor"); // Enchantment Aura {2}{G}
        // Enchant creature
        // When Frog Tongue enters the battlefield, draw a card.
        // Enchanted creature has reach. (It can block creatures with flying.)
        addCard(Zone.HAND, playerB, "Frog Tongue"); // Enchantment Aura {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", "Bramble Elemental");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Blanchwood Armor", "Bramble Elemental");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Frog Tongue", "Bramble Elemental");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3},{T}", "Bramble Elemental");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);
        assertGraveyardCount(playerB, "Battlegrowth", 1);
        assertPermanentCount(playerB, "Bramble Elemental", 1);
        assertGraveyardCount(playerB, "Blanchwood Armor", 0);
        assertPermanentCount(playerB, "Blanchwood Armor", 1);
        assertGraveyardCount(playerB, "Frog Tongue", 0);
        assertPermanentCount(playerB, "Frog Tongue", 1);
        assertPermanentCount(playerB, "Saproling", 8);
        assertPowerToughness(playerB, "Bramble Elemental", 10, 10);
        assertAbility(playerB, "Bramble Elemental", ReachAbility.getInstance(), true);

        assertHandCount(playerB, 3); // 1 from Turn 2 and 2 from Frog Tongue
    }
}
