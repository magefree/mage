package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class WarpTest extends CardTestPlayerBase {

    private static final String colossus = "Bygone Colossus";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
    }

    @Test
    public void testWarpExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, colossus, 1);
    }

    @Test
    public void testWarpExileCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
    }
}
