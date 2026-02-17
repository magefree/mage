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

    private static final String SPOILS_OF_WAR = "Spoils of War";

    /**
     * Test: Spoils of War with 2 artifacts in opponent graveyard
     * Expected: Grizzly Bears gains 2 +1/+1 counters
     */
    @Test
    public void testWithArtifacts() {
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, SPOILS_OF_WAR);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPOILS_OF_WAR);
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }

    /**
     * Test: Spoils of War with empty graveyard
     * Expected: Grizzly Bears gains no counters
     */
    @Test
    public void testWithEmptyGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, SPOILS_OF_WAR);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPOILS_OF_WAR);
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    /**
     * Test: Spoils of War filters land cards correctly
     * Expected: Only counts artifacts and creatures (2), not lands (3)
     */
    @Test
    public void testFiltersLands() {
        addCard(Zone.GRAVEYARD, playerB, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerB, "Ornithopter", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, SPOILS_OF_WAR);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPOILS_OF_WAR);
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
    }
}




