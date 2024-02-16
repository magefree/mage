package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HoodedBlightfangTest extends CardTestPlayerBase {

    private static final String blightfang = "Hooded Blightfang";
    private static final String goblin = "Raging Goblin";
    private static final String bow = "Bow of Nylea";
    private static final String jace = "Jace Beleren";

    @Test
    public void testBowOfNylea() {
        addCard(Zone.BATTLEFIELD, playerA, blightfang);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.BATTLEFIELD, playerA, bow);

        attack(1, playerA, goblin);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20 - 1 - 1);
    }

    @Test
    public void testDeathtouchPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, blightfang);
        addCard(Zone.BATTLEFIELD, playerA, goblin);
        addCard(Zone.BATTLEFIELD, playerA, bow);
        addCard(Zone.BATTLEFIELD, playerB, jace);

        attack(1, playerA, goblin, jace);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, jace, 0);
        assertGraveyardCount(playerB, jace, 1);
    }
}
