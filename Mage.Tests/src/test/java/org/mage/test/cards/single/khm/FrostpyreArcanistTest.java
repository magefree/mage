package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class FrostpyreArcanistTest extends CardTestPlayerBase {

    private static final String arcanist = "Frostpyre Arcanist";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testCanFind() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, bolt);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.HAND, playerA, arcanist);

        addTarget(playerA, bolt);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcanist);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLibraryCount(playerA, bolt, 0);
        assertHandCount(playerA, bolt, 1);
    }

    @Test
    public void testCantFind() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, bolt);
        addCard(Zone.HAND, playerA, arcanist);

        addTarget(playerA, bolt);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcanist);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerA, bolt, 1);
        assertHandCount(playerA, bolt, 0);
    }
}
