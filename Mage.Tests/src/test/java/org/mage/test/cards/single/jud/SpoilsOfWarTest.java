package org.mage.test.cards.single.jud;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
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

    /**
     * Test: Spoils of War counts artifacts in opponent graveyard as X value
     * Expected: X equals the number of artifacts (2)
     */
    @Test
    public void testXCountsArtifacts() {
        // Setup: Opponent has 2 artifacts in graveyard
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        // Add the spell and mana to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should have 2 +1/+1 counters (2/2 -> 4/4)
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    /**
     * Test: Spoils of War with empty opponent graveyard
     * Expected: X equals 0, no counters added
     */
    @Test
    public void testXEqualsZeroWithEmptyGraveyard() {
        // Setup: Opponent has no artifacts or creatures in graveyard
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        // Add the spell and mana to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should have no counters
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    /**
     * Test: Spoils of War filters out non-artifact/creature cards
     * Expected: X counts only artifacts (2), not lands (3)
     */
    @Test
    public void testXFiltersOutLands() {
        // Setup: Opponent has lands and artifacts in graveyard
        addCard(Zone.GRAVEYARD, playerB, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        // Setup: Player A has a creature to boost
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        // Add the spell and mana to player A's hand
        addCard(Zone.HAND, playerA, "Spoils of War");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spoils of War");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Grizzly Bears should only have 2 +1/+1 counters (from artifacts only)
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }
}






