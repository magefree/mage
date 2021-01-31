package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */

public class KarfellHarbingerTest extends CardTestPlayerBase {

    @Test
    public void testForetellMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Augury Raven");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fore");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, "Augury Raven", 0);
    }

    @Test
    public void testSpellMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Obsessive Search");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Obsessive Search");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Obsessive Search", 1);
    }

    @Test
    public void testOtherMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Flying Men");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flying Men");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Flying Men", 1);
    }
}
