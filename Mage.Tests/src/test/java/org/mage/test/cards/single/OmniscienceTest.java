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
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OmniscienceTest extends CardTestPlayerBase {

    /**
     * Omniscience {7}{U}{U}{U}
     *
     * Enchantment You may cast nonland cards from your hand without paying
     * their mana costs.
     *
     */
    @Test
    public void testCastingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        /* player.getPlayable does not take alternate
         casting costs in account, so for the test the mana has to be available
         but won't be used
         */
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", false);
    }

    @Test
    public void testCastingSplitCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        addCard(Zone.HAND, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fire // Ice", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertTapped("Island", false);
        assertTapped("Mountain", false);
    }

    @Test
    public void testCastingShrapnelBlast() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");

        /* player.getPlayable does not take alternate
         casting costs in account, so for the test the mana has to be available
         but won't be used
         */
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        addCard(Zone.HAND, playerA, "Shrapnel Blast", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrapnel Blast");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);

        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertTapped("Mountain", false);
    }

    /**
     * Spell get cast for 0 if Omniscience is being in play. But with
     * Trinisphere it costs at least {3}. Cost/alternate cost (Omniscience) +
     * additional costs - cost reductions + minimum cost (Trinishpere) = total
     * cost.
     */
    @Test
    public void testCastingWithTrinisphere() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // As long as Trinisphere is untapped, each spell that would cost less than three mana
        // to cast costs three mana to cast. (Additional mana in the cost may be paid with any
        // color of mana or colorless mana. For example, a spell that would cost {1}{B} to cast
        // costs {2}{B} to cast instead.)
        addCard(Zone.BATTLEFIELD, playerB, "Trinisphere", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertTapped("Plains", true); // plains have to be tapped because {3} have to be paid
    }

    /**
     * Omniscience is not allowing me to cast spells for free. I'm playing a
     * Commander game against the Computer, if that helps.
     *
     * Edit: It's not letting me cast fused spells for free. Others seems to be
     * working.
     */
    @Test
    @Ignore  // targeting of fused/split spells not supported by thestplayer
    public void testCastingFusedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        /*
         * Instant
         * Far {1}{U} Return target creature to its owner's hand.
         * Away{2}{B} Target player sacrifices a creature.
         * Fuse (You may cast one or both halves of this card from your hand.)
         */
        addCard(Zone.HAND, playerA, "Far // Away");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Far // Away", "Silvercoat Lion^targetPlayer=PlayerB");
        playerB.addTarget("Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerB, 0);

        assertGraveyardCount(playerA, "Far // Away", 1);

        assertPermanentCount(playerB, "Pillarfield Ox", 0);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }

}
