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
package org.mage.test.cards.cost.splitcards;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastSplitCardsFromOtherZonesTest extends CardTestPlayerBase {

    /**
     * I attempted to cast Wear // Tear from my opponent's hand with Mindclaw
     * Shaman - the card is selectable, but doesn't do anything at all after it
     * gets selected to cast. To my best knowledge, Mindclaw Shaman should be
     * able to cast one side, the other or both (only in the case of Fuse
     * cards).
     */
    @Test
    public void testCastTearFromOpponentsHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // When Mindclaw Shaman enters the battlefield, target opponent reveals his or her hand.
        // You may cast an instant or sorcery card from it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Mindclaw Shaman"); // Creature {4}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Sanguine Bond", 1); // Enchantment to destroy
        // Wear
        // Destroy target artifact.
        // Tear
        // Destroy target enchantment.
        addCard(Zone.HAND, playerB, "Wear // Tear"); // Instant {1}{R} // {W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindclaw Shaman");
        addTarget(playerA, "Sanguine Bond");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mindclaw Shaman", 1);
        assertGraveyardCount(playerB, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Sanguine Bond", 1);

    }

    @Test
    public void testCastFearFromOpponentsHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // When Mindclaw Shaman enters the battlefield, target opponent reveals his or her hand.
        // You may cast an instant or sorcery card from it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Mindclaw Shaman"); // Creature {4}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Icy Manipulator", 1); // Artifact to destroy
        // Wear
        // Destroy target artifact.
        // Tear
        // Destroy target enchantment.
        addCard(Zone.HAND, playerB, "Wear // Tear"); // Instant {1}{R} // {W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindclaw Shaman");
        addTarget(playerA, "Icy Manipulator");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mindclaw Shaman", 1);
        assertGraveyardCount(playerB, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Icy Manipulator", 1);

    }

    @Test
    public void testCastFusedFromOpponentsHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // When Mindclaw Shaman enters the battlefield, target opponent reveals his or her hand.
        // You may cast an instant or sorcery card from it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Mindclaw Shaman"); // Creature {4}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Sanguine Bond", 1); // Enchantment to destroy
        addCard(Zone.BATTLEFIELD, playerB, "Icy Manipulator", 1); // Artifact to destroy
        // Wear
        // Destroy target artifact.
        // Tear
        // Destroy target enchantment.
        addCard(Zone.HAND, playerB, "Wear // Tear"); // Instant {1}{R} // {W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindclaw Shaman");
        addTarget(playerA, "Sanguine Bond");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mindclaw Shaman", 1);
        assertGraveyardCount(playerB, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Sanguine Bond", 1);
        assertGraveyardCount(playerB, "Icy Manipulator", 1);

    }
}
