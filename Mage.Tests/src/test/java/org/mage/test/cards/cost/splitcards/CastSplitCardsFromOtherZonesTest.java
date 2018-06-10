
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
        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand.
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
        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand.
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
        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand.
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

    /**
     * Cast a split card half from exile
     */
    @Test
    public void testCastSpliHalfFromExile() {
        // Fire Instant {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice Instant {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.LIBRARY, playerA, "Fire // Ice", 1);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature 2/2

        // Whenever Etali, Primal Storm attacks, exile the top card of each player's library, then you may
        // cast any number of nonland cards exiled this way without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Etali, Primal Storm"); // Creature {4}{R} 6/6

        attack(2, playerB, "Etali, Primal Storm");
        setChoice(playerB, "Yes");
        setChoice(playerB, "Cast Fire");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 14);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Fire // Ice", 1);
    }
}
