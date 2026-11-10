package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class HeartShapedHerbTest extends CardTestPlayerBase {

    // Heart-Shaped Herb {4}
    // Artifact
    // If a source an opponent controls would deal damage to you, prevent 1 of that damage.
    // {2}, {T}, Sacrifice this artifact: You may sacrifice a creature. If you do, return that card to the battlefield
    // under its owner's control with three +1/+1 counters on it and you become the monarch.

    private static final String HERB = "Heart-Shaped Herb";
    private static final String HERB_ABILITY = "{2}, {T}, Sacrifice";
    private static final String BEARS = "Grizzly Bears";
    private static final String BOLT = "Lightning Bolt";
    private static final String HERON = "Dreamtail Heron";
    private static final String SAVANT = "Beastcaller Savant";
    private static final String SPROUT = "Sprout";
    private static final String SAPROLING = "Saproling Token";

    @Test
    public void testPreventsOneDamageFromOpponentSource() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, BOLT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, BOLT, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, BOLT, 1);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testDoesNotPreventDamageFromOwnSource() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, BOLT);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BOLT, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, BOLT, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testSacrificeCreatureReturnsItWithCountersAndMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, HERB_ABILITY);
        setChoice(playerA, true); // sacrifice a creature
        setChoice(playerA, BEARS);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, HERB, 1);
        assertPermanentCount(playerA, BEARS, 1);
        assertGraveyardCount(playerA, BEARS, 0);
        assertCounterCount(playerA, BEARS, CounterType.P1P1, 3);
        assertPowerToughness(playerA, BEARS, 5, 5);
        Assert.assertEquals("playerA must be the monarch", playerA.getId(), currentGame.getMonarchId());
    }

    @Test
    public void testNoCreatureToSacrificeDoesNothing() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // no creature, so there is no choice to make
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, HERB_ABILITY);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, HERB, 1);
        Assert.assertNull("nobody must be the monarch", currentGame.getMonarchId());
    }

    // Dreamtail Heron {4}{U}
    // Creature - Bird
    // Mutate {3}{U}
    // Flying
    // Whenever this creature mutates, draw a card.
    //
    // 730.3, 730.3c: a merged permanent leaves as one permanent, but every component card is affected,
    // so each card must return with its own three +1/+1 counters.
    @Test
    public void testSacrificeMutatedCreatureReturnsAllCards() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, SAVANT);
        addCard(Zone.HAND, playerA, HERON);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HERON + " using Mutate", SAVANT);
        setChoice(playerA, true); // mutate under
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, HERB_ABILITY);
        setChoice(playerA, true); // sacrifice a creature
        setChoice(playerA, SAVANT);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, HERB, 1);
        assertGraveyardCount(playerA, SAVANT, 0);
        assertGraveyardCount(playerA, HERON, 0);
        assertPermanentCount(playerA, SAVANT, 1);
        assertPermanentCount(playerA, HERON, 1);
        assertCounterCount(playerA, SAVANT, CounterType.P1P1, 3);
        assertCounterCount(playerA, HERON, CounterType.P1P1, 3);
        Assert.assertEquals("playerA must be the monarch", playerA.getId(), currentGame.getMonarchId());
    }

    // Sprout {G}
    // Instant
    // Create a 1/1 green Saproling creature token.
    //
    // 730.2d, 730.3e: if the top component is a token, the card component is still a card
    // and must return, while the token ceases to exist.
    @Test
    public void testSacrificeMutatedTokenReturnsOnlyCard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, HERB);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, SPROUT);
        addCard(Zone.HAND, playerA, HERON);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPROUT);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HERON + " using Mutate", SAPROLING);
        setChoice(playerA, true); // mutate under, so the token stays on top
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, HERB_ABILITY);
        setChoice(playerA, true); // sacrifice a creature
        setChoice(playerA, SAPROLING);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, HERB, 1);
        assertGraveyardCount(playerA, HERON, 0);
        assertPermanentCount(playerA, HERON, 1);
        assertCounterCount(playerA, HERON, CounterType.P1P1, 3);
        assertPermanentCount(playerA, SAPROLING, 0);
        Assert.assertEquals("playerA must be the monarch", playerA.getId(), currentGame.getMonarchId());
    }
}
