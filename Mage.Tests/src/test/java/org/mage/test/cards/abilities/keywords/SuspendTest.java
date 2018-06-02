
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SuspendTest extends CardTestPlayerBase {

    /**
     * Tests Epochrasite works (give suspend to a exiled card) When Epochrasite
     * dies, exile it with three time counters on it and it gains suspend.
     *
     */
    @Test
    public void testEpochrasite() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Epochrasite enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand.
        // When Epochrasite dies, exile it with three time counters on it and it gains suspend.
        addCard(Zone.HAND, playerA, "Epochrasite", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Epochrasite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Epochrasite");

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Epochrasite", 1); // returned on turn 7 with 3 +1/+1 Counter
        assertPowerToughness(playerA, "Epochrasite", 4, 4);
        assertAbility(playerA, "Epochrasite", HasteAbility.getInstance(), true);

    }

    /**
     * Tests Jhoira of the Ghitu works (give suspend to a exiled card) {2},
     * Exile a nonland card from your hand: Put four time counters on the exiled
     * card. If it doesn't have suspend, it gains suspend.
     *
     */
    @Test
    public void testJhoiraOfTheGhitu() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Jhoira of the Ghitu", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Exile a nonland card from your hand: Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(11, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Jhoira of the Ghitu", 1);
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    /**
     * Tests that a spell countered with delay goes to exile with 3 time
     * counters and can be cast after the 3 counters are removed
     *
     */
    @Test
    public void testDelay() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // Instant {1}{U}
        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend. (At the beginning of its owner's upkeep, remove a counter from that card. When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Delay", "Silvercoat Lion");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Delay", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    @Test
    public void testDeepSeaKraken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Suspend 9-{2}{U}
        // Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.
        addCard(Zone.HAND, playerA, "Deep-Sea Kraken", 1);

        // Instant {1}{U}
        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend. (At the beginning of its owner's upkeep, remove a counter from that card. When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Deep-Sea Kraken", 1);

        assertCounterOnExiledCardCount("Deep-Sea Kraken", CounterType.TIME, 8); // -1 from spell of player B

    }

    @Test
    public void testAncestralVisionCantBeCastDirectly() {
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.HAND, playerA, "Ancestral Vision", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Vision", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Ancestral Vision", 1);

    }

    /**
     * Suppression Field incorrectly makes suspend cards cost 2 more to suspend.
     * It made my Rift Bolt cost 2R to suspend instead of R
     *
     */
    @Test
    public void testCostManipulation() {
        // Rift Bolt deals 3 damage to any target.
        // Suspend 1-{R}
        addCard(Zone.HAND, playerA, "Rift Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Suppression Field", 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Rift Bolt", 0);

    }

    /**
     * Cards cast from other zones that aren't the hand should not trigger
     * Knowledge Pool, as it states that only cards cast from the hand should be
     * exiled afterwards.
     *
     * Example: cards coming off suspend shouldn't trigger Knowledge Pool.
     *
     */
    @Test
    public void testThatNotCastFromHand() {

        // Rift Bolt deals 3 damage to any target.
        // Suspend 1-{R}
        addCard(Zone.HAND, playerA, "Rift Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        // Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of their library
        // Whenever a player casts a spell from their hand, that player exiles it. If the player does, he or she may cast another nonland card
        // exiled with Knowledge Pool without paying that card's mana cost.
        addCard(Zone.HAND, playerB, "Knowledge Pool", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Knowledge Pool");

        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Knowledge Pool", 1);
        assertHandCount(playerA, "Rift Bolt", 0);
        assertGraveyardCount(playerA, "Rift Bolt", 1);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);

    }
}
