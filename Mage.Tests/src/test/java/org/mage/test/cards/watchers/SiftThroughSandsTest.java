package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SiftThroughSandsTest extends CardTestPlayerBase {

    /*
     * Sift Through Sands
     * Instant — Arcane, 1UU (3)
     * Draw two cards, then discard a card.
     * If you've cast a spell named Peer Through Depths and a spell named Reach
     * Through Mists this turn, you may search your library for a card named
     * The Unspeakable, put it onto the battlefield, then shuffle your library.
     *
     * Peer Through Depths
     * Instant — Arcane, 1U (2)
     * Look at the top five cards of your library. You may reveal an instant or
     * sorcery card from among them and put it into your hand. Put the rest on
     * the bottom of your library in any order.
     *
     * Reach Through Mists
     * Instant — Arcane, U (1)
     * Draw a card.
     *
     * The Unspeakable
     * Legendary Creature — Spirit 6/7, 6UUU (9)
     * Flying, trample
     * Whenever The Unspeakable deals combat damage to a player, you may return
     * target Arcane card from your graveyard to your hand.
     *
     */

    @Test
    public void testTheUnspeakable() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Sift Through Sands");
        addCard(Zone.HAND, playerA, "Peer Through Depths");
        addCard(Zone.HAND, playerA, "Reach Through Mists");

        addCard(Zone.LIBRARY, playerA, "The Unspeakable", 4); // So that at least one will be in library even if you draw 2 with Sift and 1 with Reach

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reach Through Mists");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Peer Through Depths");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sift Through Sands");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "The Unspeakable", 1);
        assertHandCount(playerA, 2);

    }

    // test that The Unspeakable is not put onto the battlefield
    @Test
    public void testNotTheUnspeakable() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Sift Through Sands");
        addCard(Zone.HAND, playerA, "Peer Through Depths");
        addCard(Zone.HAND, playerA, "Reach Through Mists");
        addCard(Zone.LIBRARY, playerA, "The Unspeakable", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reach Through Mists");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sift Through Sands");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "The Unspeakable", 0);
        assertHandCount(playerA, 3);

    }

}
