package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class LegendRuleTest extends CardTestPlayerBase {

    private static final String isamaru = "Isamaru, Hound of Konda";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, isamaru, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, isamaru);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, isamaru);

        setChoice(playerA, isamaru);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, isamaru, 1);
        assertPermanentCount(playerA, isamaru, 1);
    }
}
