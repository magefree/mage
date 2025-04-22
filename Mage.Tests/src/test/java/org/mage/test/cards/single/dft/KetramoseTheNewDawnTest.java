package org.mage.test.cards.single.dft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.jupiter.api.Assertions.*;

public class KetramoseTheNewDawnTest extends CardTestPlayerBase {

    private final String ketramose = "Ketramose, the New Dawn";
    private final String relic = "Relic of Progenitus";
    private final String ephemerate = "Ephemerate";

    @Test
    public void testExile() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, ketramose);
        addCard(Zone.BATTLEFIELD, playerA, relic);
        addCard(Zone.HAND, playerA, ephemerate);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 10);
        addCard(Zone.GRAVEYARD, playerB, "Forest", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        addTarget(playerA, playerB);
        addTarget(playerB, "Forest");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}, Exile {this}");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, ephemerate, ketramose, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 4);
        assertLife(playerA, 20 - 3);
    }
}