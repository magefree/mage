
package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FracturingGustTest extends CardTestPlayerBase {

    @Test
    public void testWithStaticAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // Players can't gain life.
        // At the beginning of your upkeep, Witch Hunt deals 4 damage to you.
        // At the beginning of your end step, target opponent chosen at random gains control of Witch Hunt.
        addCard(Zone.BATTLEFIELD, playerB, "Witch Hunt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerB, "Witch Hunt", 1);

        // + 2 from destroyed Witch Hunt
        assertLife(playerA, 22);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithTriggerdAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // When Guardian Automaton dies, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Automaton", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerA, "Guardian Automaton", 1);

        // + 2 from destroyed Guardian Automaton
        assertLife(playerA, 25);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        // Flying
        // Indestructible (Damage and effects that say "destroy" don't destroy this creature.)
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Gargoyle", 1); // Artifact Creature - Gargoyle

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertPermanentCount(playerB, "Darksteel Gargoyle", 1);

        // No life because Darksteel Gargoyle is Indestructible
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testWithRestInPeace() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        addCard(Zone.HAND, playerA, "Fracturing Gust", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1); // Artifact Creature 2/2

        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Rest in Peace"); // Artifact

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fracturing Gust");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fracturing Gust", 1);
        assertGraveyardCount(playerB, "Ornithopter", 0);
        assertExileCount(playerB, "Ornithopter", 1);

        assertLife(playerA, 24);
        assertLife(playerB, 20);

    }
}
