package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class WheelOfMisfortuneTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AtOneLife_AIAvoidsLethalDamage() {
        // Wheel of Misfortune deals damage equal to the highest number to each player who chose that number.
        addCard(Zone.HAND, playerA, "Wheel of Misfortune"); // {2}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        setLife(playerB, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wheel of Misfortune");
        setChoice(playerA, "X=1"); // only 0 is safe for playerB
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 1);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void test_AtTwoLife_AIAvoidsLethalDamage() {
        // Wheel of Misfortune deals damage equal to the highest number to each player who chose that number.
        addCard(Zone.HAND, playerA, "Wheel of Misfortune"); // {2}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        setLife(playerB, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wheel of Misfortune");
        setChoice(playerA, "X=2"); // both 0 and 1 are safe for playerB
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 2);
        assertLife(playerA, 20 - 2);
    }
}
