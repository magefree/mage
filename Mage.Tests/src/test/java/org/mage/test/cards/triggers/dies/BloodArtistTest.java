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
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 * Whenever Blood Artist or another creature dies, target player loses 1 life
 * and you gain 1 life.
 */
public class BloodArtistTest extends CardTestPlayerBase {

    /**
     * Tests that whenever Blood Artist goes to graveyard, it would trigger its
     * ability. Tests that after Blood Artist went to graveyard, his ability
     * doesn't work anymore.
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 2);
        addCard(Zone.GRAVEYARD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Bloodflow Connoisseur", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blood Artist");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Bloodflow Connoisseur");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 17);
    }

    @Test
    public void testWithBoneSplinters() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }

    @Test
    public void testWithBoneSplinters2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Blood Artist");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Blood Artist", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertLife(playerA, 21); // For sacrifice both Blood Artist trigger, for destoy effect only one ist left
        assertLife(playerB, 19);
    }

    @Test
    public void testWithBoneSplinters3() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Bone Splinters", 1); // Sorcery - {B}
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terror", 1); // Instant - {1}{B}
        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerA, "Blood Artist");
        // Blood Artist may no longer trigger from destroyed creature because already in the graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", "Silvercoat Lion", "Bone Splinters");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bone Splinters", 1);
        assertGraveyardCount(playerA, "Terror", 1);
        assertGraveyardCount(playerA, "Blood Artist", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertLife(playerA, 21); // For sacrifice both Blood Artist trigger, for destoy effect only one ist left
        assertLife(playerB, 19);
    }

}
