package org.mage.test.cards.single.sws;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ForceLightningTest extends CardTestPlayerBase {

    @Test
    public void test_DamageAndScryX() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // draw on turn 3
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Mountain");

        // Force Lightning deals X damage to any target.
        // Scry X.
        addCard(Zone.HAND, playerA, "Force Lightning"); // {X}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Force Lightning", playerB);
        setChoice(playerA, "X=2");
        addTarget(playerA, "Mountain^Island"); // scry 2 - put both on bottom
        setChoice(playerA, "Mountain"); // order on bottom

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
