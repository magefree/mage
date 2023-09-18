package org.mage.test.cards.single.rna;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PteramanderTest extends CardTestPlayerBase {

    @Test
    public void testNoReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Pteramander");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Pteramander", 5, 5);
    }

    @Test
    public void testReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pteramander");
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Recall", 7);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Pteramander", 5, 5);
    }
}
