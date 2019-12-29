
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TouchOfMoongloveTest extends CardTestPlayerBase {

    /**
     * I blocked my opponent's Pharika's Disciple with a Cleric of the Forward
     * Order and Guardian Automaton. They cast Touch of Moonglove on their
     * Pharika's Disciple and both of my creatures were killed, but I only lost
     * 2 life instead of 4.(and gained 3 from Guardian Automaton dying).
     *
     */
    @Test
    public void testDiesAndControllerDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // When Cleric of the Forward Order enters the battlefield, you gain 2 life for each creature you control named Cleric of the Forward Order.
        addCard(Zone.HAND, playerA, "Cleric of the Forward Order", 1);
        // When Guardian Automaton dies, you gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Guardian Automaton", 1); // 3/3

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        // Target creature you control gets +1/+0 and gains deathtouch until end of turn.
        // Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.
        addCard(Zone.HAND, playerB, "Touch of Moonglove"); // {B}
        addCard(Zone.BATTLEFIELD, playerB, "Pharika's Disciple", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cleric of the Forward Order");
        attack(2, playerB, "Pharika's Disciple");
        block(2, playerA, "Cleric of the Forward Order", "Pharika's Disciple");
        block(2, playerA, "Guardian Automaton", "Pharika's Disciple");

        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerB, "Touch of Moonglove", "Pharika's Disciple");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Touch of Moonglove", 1);
        assertGraveyardCount(playerA, "Cleric of the Forward Order", 1);
        assertGraveyardCount(playerB, "Pharika's Disciple", 1);

        assertGraveyardCount(playerA, "Guardian Automaton", 1);

        assertLife(playerA, 21); // +2 by Cleric + 2x -2 by Touch +3 by Guardian Automation
        assertLife(playerB, 20);

    }

}
