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
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ConvokeTest extends CardTestPlayerBase {

    /*
    Test are set to Ignore because the new way to handle this alternate mana payment methods 
    are not supported yet from AI and getPlayable logic.
    */

    @Test
    @Ignore
    public void testConvokeTwoCreatures() {
        /**
         * Ephemeral Shields   {1}{W}
         * Instant
         * Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
         * Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
         */

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // must be added because getPlayable does not take Convoke into account
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Oreskos Swiftclaw", 1);

        addCard(Zone.HAND, playerA, "Ephemeral Shields");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion^Oreskos Swiftclaw");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Ephemeral Shields", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); // was indestructible
        assertPermanentCount(playerA, "Oreskos Swiftclaw", 1);

        for (Permanent permanent: currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            Assert.assertTrue(permanent.getName() + " may not be tapped", !permanent.isTapped());
        }
    }


    @Test
    @Ignore
    public void testConvokeTwoCreaturesOneWithProtection() {
        /**
         * Ephemeral Shields   {1}{W}
         * Instant
         * Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
         * Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
         */

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // must be added because getPlayable does not take Convoke into account

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 1);

        addCard(Zone.HAND, playerA, "Ephemeral Shields");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion^Black Knight");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Ephemeral Shields", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); // was indestructible
        assertPermanentCount(playerA, "Black Knight", 1);
        assertTapped("Silvercoat Lion", true);
        assertTapped("Black Knight", true);

        for (Permanent permanent: currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            Assert.assertTrue(permanent.getName() + " may not be tapped", !permanent.isTapped());
        }
    }

}