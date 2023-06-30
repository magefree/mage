package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CadricSoulKindlerTest extends CardTestPlayerBase {

    private static final String cadric = "Cadric, Soul Kindler";
    private static final String isamaru = "Isamaru, Hound of Konda";

    @Test
    public void testOneCopy() {
        addCard(Zone.BATTLEFIELD, playerA, cadric);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, isamaru);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, isamaru);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, isamaru, 2);
        assertGraveyardCount(playerA, isamaru, 0);
    }

    @Test
    public void testTwoCopies() {
        addCard(Zone.BATTLEFIELD, playerA, cadric);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, isamaru, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, isamaru);
        setChoice(playerA, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, isamaru);
        setChoice(playerA, true);
        setChoice(playerA, isamaru);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, isamaru, 3);
        assertGraveyardCount(playerA, isamaru, 1);
    }
}
