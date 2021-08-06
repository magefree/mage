package org.mage.test.cards.digital;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ConjureTest extends CardTestPlayerBase {

    private static final String trainer = "Wingsteed Trainer";
    private static final String pegasus = "Stormfront Pegasus";

    @Test
    public void testConjureToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, trainer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trainer);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, trainer, 1);
        assertHandCount(playerA, pegasus, 1);
    }

    @Test
    public void testConjureToHandAndCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, trainer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trainer);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pegasus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, trainer, 1);
        assertPermanentCount(playerA, pegasus, 1);
    }
}
