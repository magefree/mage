package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class KezzerdrixTest extends CardTestPlayerBase {

    @Test
    public void test_OpponentsControlNoCreatures() {
        // At the beginning of your upkeep, if your opponents control no creatures, this creature deals 4 damage to you.
        addCard(Zone.BATTLEFIELD, playerA, "Kezzerdrix");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 4);
    }

    @Test
    public void test_OpponentControlsCreature() {
        // At the beginning of your upkeep, if your opponents control no creatures, this creature deals 4 damage to you.
        addCard(Zone.BATTLEFIELD, playerA, "Kezzerdrix");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
    }
}
