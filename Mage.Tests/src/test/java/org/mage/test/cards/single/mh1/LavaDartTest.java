package org.mage.test.cards.single.mh1;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class LavaDartTest extends CardTestPlayerBase {

    @Test
    public void test_Play() {
        // Lava Dart deals 1 damage to any target.
        // Flashback-Sacrifice a Mountain.
        addCard(Zone.GRAVEYARD, playerA, "Lava Dart"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", playerB);
        setChoice(playerA, "Mountain"); // sacrifice cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
    }
}