package org.mage.test.cards.single.jud;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.builder.TestBuilder;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SpoilsOfWar}
 *
 * Spoils of War {X}{B}
 * Sorcery
 * X is the number of artifact and/or creature cards in an opponent's graveyard as you cast this spell.
 * Distribute X +1/+1 counters among any number of target creatures.
 *
 * @author Vernon
 */
public class SpoilsOfWarTest extends CardTestPlayerBase {

    @Test
    public void testSpoilsOfWarWithArtifacts() {
        // Setup: Opponent has 2 artifacts in graveyard
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should have 2 +1/+1 counters
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }

    @Test
    public void testSpoilsOfWarWithCreatures() {
        // Setup: Opponent has 3 creatures in graveyard
        addCard(Zone.GRAVEYARD, playerB, "Goblin Token", 3);
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should have 3 +1/+1 counters
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    @Test
    public void testSpoilsOfWarWithMixed() {
        // Setup: Opponent has 2 artifacts and 3 creatures in graveyard (total 5)
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        addCard(Zone.GRAVEYARD, playerB, "Goblin Token", 3);
        // Setup: Player A has multiple creatures to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears^0");
        addTarget(playerA, "Grizzly Bears^1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Both Grizzly Bears should have counters distributed (total 5 counters distributed)
        assertCounterCount(playerA, "Grizzly Bears", "P1P1", 3); // First bear gets 3
        assertCounterCount(playerA, "Grizzly Bears", "P1P1", 2); // Second bear gets 2
    }

    @Test
    public void testSpoilsOfWarWithoutCardsInGraveyard() {
        // Setup: Opponent has no artifacts or creatures in graveyard
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should not gain any counters (X=0)
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void testSpoilsOfWarWithLands() {
        // Setup: Opponent has lands and artifacts in graveyard (lands don't count)
        addCard(Zone.GRAVEYARD, playerB, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should only have 2 +1/+1 counters (lands don't count)
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }

    @Test
    public void testSpoilsOfWarDistributesAmongMultipleCreatures() {
        // Setup: Opponent has 4 artifacts in graveyard
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 4);
        // Setup: Player A has 2 creatures to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 1);
        // Add the spell to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        // Player should be able to distribute 4 counters among the 2 creatures
        addTarget(playerA, "Grizzly Bears^0");
        addTarget(playerA, "Llanowar Elves^0");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Counters should be distributed (3 on bears, 1 on elves for example, or any distribution)
        assertCounterCount(playerA, "Grizzly Bears", "P1P1", 3);
        assertCounterCount(playerA, "Llanowar Elves", "P1P1", 1);
    }
}

