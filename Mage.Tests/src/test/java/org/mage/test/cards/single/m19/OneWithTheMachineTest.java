package org.mage.test.cards.single.m19;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class OneWithTheMachineTest extends CardTestPlayerBase {

    @Test
    public void testOnlyArtifactsAreConsidered() {
        // Draw cards equal to the highest converted mana cost among artifacts you control
        addCard(Zone.HAND, playerA, "One with the Machine");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // CMC = 2, artifact
        addCard(Zone.BATTLEFIELD, playerA, "Millstone");
        // CMC = 6, not artifact
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One with the Machine");

        execute();

        assertHandCount(playerA, 2);
    }

}
