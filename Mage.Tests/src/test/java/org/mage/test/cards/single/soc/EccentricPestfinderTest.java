package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EccentricPestfinderTest extends CardTestPlayerBase {

    @Test
    public void testLifeGainPreparesTurnStones() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Eccentric Pestfinder");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah");
        addCard(Zone.BATTLEFIELD, playerA, "Bayou");
        addCard(Zone.HAND, playerA, "Revitalize");
        addCard(Zone.LIBRARY, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Revitalize");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Turn Stones");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 23);
        assertPermanentCount(playerA, "Pest Token", 1);
        assertGraveyardCount(playerA, "Turn Stones", 0);
    }
}
