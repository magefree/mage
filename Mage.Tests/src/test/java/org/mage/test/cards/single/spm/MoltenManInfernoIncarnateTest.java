package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Molten Man, Inferno Incarnate
 * {2}{R}
 * Legendary Creature - Elemental Villain
 * When this creature enters, search your library for a basic Mountain card, put it onto the
 * battlefield tapped, then shuffle.
 * This creature gets +1/+1 for each Mountain you control.
 * When this creature leaves the battlefield, sacrifice a land.
 * <p>
 * The leaves-the-battlefield trigger makes its controller sacrifice a land (CR 603.10a).
 */
public class MoltenManInfernoIncarnateTest extends CardTestPlayerBase {

    private static final String molten = "Molten Man, Inferno Incarnate";

    @Test
    public void test_LeavesBattlefieldControllerSacrificesALand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, molten);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", molten);
        setChoice(playerA, "Mountain"); // the controller sacrifices its only land

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, molten, 1);
        assertPermanentCount(playerA, "Mountain", 0);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertPermanentCount(playerB, "Swamp", 3);
    }
}
