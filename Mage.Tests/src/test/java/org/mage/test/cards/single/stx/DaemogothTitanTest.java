package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class DaemogothTitanTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Attack_Manual() {
        // Whenever Daemogoth Titan attacks or blocks, sacrifice a creature.
        addCard(Zone.BATTLEFIELD, playerA, "Daemogoth Titan"); // 11/10
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        attack(1, playerA, "Daemogoth Titan");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 20 - 11);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Attack_AI() {
        // Whenever Daemogoth Titan attacks or blocks, sacrifice a creature.
        addCard(Zone.BATTLEFIELD, playerA, "Daemogoth Titan"); // 11/10
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 20 - 11);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
