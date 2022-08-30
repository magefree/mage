package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GideonBlackbladeTest extends CardTestPlayerBase {

    // Gideon Blackblade L4
    // As long as it's your turn, Gideon Blackblade is a 4/4 Human Soldier creature with indestructible that's still a planeswalker.
    // Prevent all damage that would be dealt to Gideon Blackblade during your turn.

    @Test
    public void test_PreventDamageToGideonOnYourTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Gideon Blackblade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        checkPT("turn 1 before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gideon Blackblade", 4, 4);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Gideon Blackblade");
        checkPT("turn 1 after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Blackblade", 4, 4);
        checkPermanentCounters("turn 1 after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Blackblade", CounterType.LOYALTY, 4);

        checkPT("turn 2 before", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Gideon Blackblade", 0, 0);
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Gideon Blackblade");
        checkPT("turn 2 after", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Blackblade", 0, 0);
        checkPermanentCounters("turn 2 after", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gideon Blackblade", CounterType.LOYALTY, 4 - 3);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
