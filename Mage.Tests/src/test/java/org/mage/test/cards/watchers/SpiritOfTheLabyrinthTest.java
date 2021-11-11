package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SpiritOfTheLabyrinthTest extends CardTestPlayerBase {

    /*
     * Spirit of the Labyrinth
     * Enchantment Creature — Spirit 3/1, 1W (2)
     * Each player can't draw more than one card each turn.
     *
     */

    // test that only 1 card is drawn
    @Test
    public void testDrawCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Spirit of the Labyrinth");
        addCard(Zone.HAND, playerA, "Brilliant Plan");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brilliant Plan");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        this.assertHandCount(playerA, 1);

    }

    // test that only 1 card is drawn
    @Test
    public void testDrawCardHondenOfSeeingWinds() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Spirit of the Labyrinth");
        addCard(Zone.HAND, playerA, "Brilliant Plan");

        // At the beginning of your upkeep, draw a card for each Shrine you control.
        addCard(Zone.BATTLEFIELD, playerB, "Honden of Seeing Winds");
        // At the beginning of your upkeep, put a 1/1 colorless Spirit creature token onto the battlefield for each Shrine you control.
        addCard(Zone.BATTLEFIELD, playerB, "Honden of Life's Web");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brilliant Plan");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Spirit Token", 2);
        this.assertHandCount(playerA, 1);
        this.assertHandCount(playerB, 1);

    }
}
