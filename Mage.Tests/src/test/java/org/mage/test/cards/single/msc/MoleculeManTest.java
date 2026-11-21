package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MoleculeManTest extends CardTestPlayerBase {

    @Test
    public void testFirstCardDrawnEachTurnCanBeCastForFree() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Molecule Man");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
