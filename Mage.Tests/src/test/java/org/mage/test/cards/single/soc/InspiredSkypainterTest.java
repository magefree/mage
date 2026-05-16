package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class InspiredSkypainterTest extends CardTestPlayerBase {

    @Test
    public void testPreparedMaestrosGiftCreatesHastyTokenCopy() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Inspired Skypainter");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Inspired Skypainter");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Maestro's Gift");
        addTarget(playerA, "Grizzly Bears");

        attack(3, playerA, "Grizzly Bears", playerB);
        attack(3, playerA, "Grizzly Bears", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertLife(playerB, 16);
        assertGraveyardCount(playerA, "Maestro's Gift", 0);
    }
}
