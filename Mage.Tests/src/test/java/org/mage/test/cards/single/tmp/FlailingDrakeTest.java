package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Flailing Drake
 * {3}{G}
 * Creature — Drake
 * Flying
 * Whenever Flailing Drake blocks or becomes blocked by a creature, that creature gets +1/+1 until end of turn.
 * 2/3
 *
 * @author anonymous
 */
public class FlailingDrakeTest extends CardTestPlayerBase {

    @Test
    public void testIncreaseBlocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Flailing Drake", 1); // Creature {3}{G} 2/3

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Snapping Drake", 1); // Creature {3}{U} 3/2

        attack(3, playerA, "Flailing Drake");
        block(3, playerB, "Snapping Drake", "Flailing Drake");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Flailing Drake", 1);
        //Snapping Drake 4/3
        assertPowerToughness(playerB, "Snapping Drake", 4, 3);
    }

    @Test
    public void testIncreaseBlocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Flailing Drake", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Snapping Drake", 1);

        attack(4, playerB, "Snapping Drake");
        block(4, playerA, "Flailing Drake", "Snapping Drake");

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Flailing Drake", 1);
        //Snapping Drake 4/3
        assertPowerToughness(playerB, "Snapping Drake", 4, 3);
    }
}
