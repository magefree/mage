package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author GregorStocks
 */
public class SouthPoleVoyagerTest extends CardTestPlayerBase {

    @Test
    public void testSecondResolutionDrawsCard() {
        addCard(Zone.BATTLEFIELD, playerA, "South Pole Voyager");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.HAND, playerA, "Universal Automaton", 2);

        // First Ally (changeling) enters: gain 1 life, no draw
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Universal Automaton");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Second Ally enters: gain 1 life, draw a card (second resolution this turn)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Universal Automaton");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
        assertHandCount(playerA, 1);
    }
}
