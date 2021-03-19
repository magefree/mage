package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class KinnanBonderProdigyTest extends CardTestPlayerBase {

    private static final String kinnan = "Kinnan, Bonder Prodigy";
    private static final String egg = "Golden Egg";
    private static final String hovermyr = "Hovermyr";

    @Test
    public void testSacrificedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, kinnan);
        addCard(Zone.BATTLEFIELD, playerA, egg);
        addCard(Zone.HAND, playerA, hovermyr);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hovermyr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, hovermyr, 1);
        assertPermanentCount(playerA, egg, 0);
        assertGraveyardCount(playerA, egg, 1);
    }
}
