package org.mage.test.cards.single.m13;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OmniscienceTest extends CardTestPlayerBase {

    // Omniscience should only provide an alternative cost when casting from
    // your hand, not when casting from other players' hands
    @Test
    public void testNoCastFromOpponentHand() {
        Assertions.assertThrows(AssertionError.class, () -> {

            addCard(Zone.BATTLEFIELD, playerB, "Omniscience");
            addCard(Zone.BATTLEFIELD, playerB, "Sen Triplets");
            addCard(Zone.HAND, playerA, "Balduvian Bears");

            addTarget(playerB, playerA);
            castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears");

            setStrictChooseMode(true);
            setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
            execute();
        });
    }

    @Test
    public void testYesCastFromOpponentHand() {
        addCard(Zone.BATTLEFIELD, playerB, "Omniscience");
        addCard(Zone.BATTLEFIELD, playerB, "Sen Triplets");
        addCard(Zone.HAND, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        addTarget(playerB, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Balduvian Bears", 1);
    }
}
