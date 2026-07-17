package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EstridsInvocationTest extends CardTestPlayerBase {

    @Test
    public void testCopyAura() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Bear Cub");
        addCard(Zone.HAND, playerA, "Estrid's Invocation");
        addCard(Zone.HAND, playerA, "Feather of Flight");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Impact Tremors");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Feather of Flight");
        addTarget(playerA, "Balduvian Bears");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Estrid's Invocation");
        setChoice(playerA, true);
        setChoice(playerA, "Feather of Flight");
        setChoice(playerA, "Bear Cub");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, "Feather of Flight");
        setChoice(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 3, 2);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 2);
        assertPowerToughness(playerA, "Bear Cub", 2, 2);
    }

}
