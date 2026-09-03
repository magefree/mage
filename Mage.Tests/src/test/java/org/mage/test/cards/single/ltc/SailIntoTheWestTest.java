package org.mage.test.cards.single.ltc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SailIntoTheWestTest extends CardTestPlayerBase {

    @Test
    public void testExileSelfReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.HAND, playerA, "Sail into the West");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sail into the West");
        setChoice(playerA, true);
        setChoice(playerB, true);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerB, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertExileCount(playerA, "Sail into the West", 1);
        assertGraveyardCount(playerA, "Sail into the West", 0);
    }

    @Test
    public void testNoExileSelfWheel() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.HAND, playerA, "Sail into the West");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sail into the West");
        setChoice(playerA, false);
        setChoice(playerB, false);
        setChoice(playerA, false);
        setChoice(playerB, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertExileCount(playerA, "Sail into the West", 0);
        assertGraveyardCount(playerA, "Sail into the West", 1);
    }

}
