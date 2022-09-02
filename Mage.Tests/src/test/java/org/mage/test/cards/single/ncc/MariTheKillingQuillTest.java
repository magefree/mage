package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MariTheKillingQuill Mari, the Killing Quill} {1}{B}{B}
 * <p>
 * Whenever a creature an opponent controls dies, exile it with a hit counter on it.
 * <p>
 * Assassins, Mercenaries, and Rogues you control have deathtouch and
 *      "Whenever this creature deals combat damage to a player, you may remove a hit counter from a card that player owns in exile.
 *       If you do, draw a card and create two Treasure tokens."
 *
 * @author Alex-Vasile
 */
public class MariTheKillingQuillTest extends CardTestPlayerBase {

    private static final String mari = "Mari, the Killing Quill";
    private static final String lightningBolt = "Lightning Bolt";
    // Sliver with no ability 1/1
    private static final String sliver = "Metallic Sliver";
    // Changeling 1/1
    private static final String automation = "Universal Automaton";

    /**
     * Test that an opponent's creature will get exiled with a hit counter.
     * And that one of ours does not.
     */
    @Test
    public void testExiledWithCounter() {
        addCard(Zone.HAND, playerA, lightningBolt, 2);
        addCard(Zone.BATTLEFIELD, playerA, mari);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, automation);

        addCard(Zone.BATTLEFIELD, playerB, sliver);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, sliver);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, automation);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, automation, 1);

        assertExileCount(playerB, sliver, 1);
        assertCounterOnExiledCardCount(sliver, CounterType.HIT, 1);
    }

    /**
     * Test that an opponent's creature will get exiled with a hit counter.
     * And that one of ours does not.
     */
    @Test
    public void testDrawAndTreasure() {
        addCard(Zone.HAND, playerA, lightningBolt);
        addCard(Zone.BATTLEFIELD, playerA, mari);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, automation);
        addCard(Zone.BATTLEFIELD, playerB, sliver);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, sliver);
        attack(1, playerA, automation, playerB);
        setChoice(playerA, "Yes");
        setChoice(playerA, sliver);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertPermanentCount(playerA, "Treasure Token", 2);

        assertExileCount(playerB, sliver, 1);
        assertCounterOnExiledCardCount(sliver, CounterType.HIT, 0);
    }
}
