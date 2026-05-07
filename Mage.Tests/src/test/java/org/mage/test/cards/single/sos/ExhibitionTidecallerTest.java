package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExhibitionTidecallerTest extends CardTestPlayerBase {

    @Test
    public void testBig() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Darksteel Relic", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Exhibition Tidecaller");
        addCard(Zone.HAND, playerA, "Aether Snap");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Snap");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Darksteel Relic", 10);
    }

    @Test
    public void testSmall() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Darksteel Relic", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Exhibition Tidecaller");
        addCard(Zone.HAND, playerA, "Reach Through Mists");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reach Through Mists");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Darksteel Relic", 3);
    }

}
