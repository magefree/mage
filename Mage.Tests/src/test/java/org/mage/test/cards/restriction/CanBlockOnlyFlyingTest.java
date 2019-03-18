

package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

/*
 *  Vaporkin
 *  Creature — Elemental 2/1, 1U
 *  Flying
 *  Vaporkin can block only creatures with flying.
 */

public class CanBlockOnlyFlyingTest extends CardTestPlayerBase {

    /**
     * Tests if Vaporkin can't block a creature without flying
     */

    @Test
    public void testCannotBlockCreatureWithoutFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Vaporkin", "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Vaporkin", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

    /**
     * Tests if Vaporkin can block a creature with flying
     */

    @Test
    public void testCanBlockCreatureWithFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Wingsteed Rider");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Wingsteed Rider");
        block(3, playerB, "Vaporkin", "Wingsteed Rider");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Wingsteed Rider", 0);
        assertPermanentCount(playerB, "Vaporkin", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Tests if Vaporkin can't block a flying creature after loosing Flying
     */

    @Test
    public void testCantBlockFlyerAfterLosingFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Archetype of Imagination");

        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");

        attack(3, playerA, "Archetype of Imagination");
        block(3, playerB, "Vaporkin", "Archetype of Imagination");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Archetype of Imagination", 1);
        assertPermanentCount(playerB, "Vaporkin", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

/**
     * Tests if Vaporkin can block a creature whicj gained flying
     */

    @Test
    public void testCanBlockCreatureWhichGainedFlying() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Jump");
        addCard(Zone.BATTLEFIELD, playerB, "Vaporkin");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Jump", "Silvercoat Lion");
        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Vaporkin", "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Vaporkin", 0);


    }



}
