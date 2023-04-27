package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ForcefieldTest extends CardTestPlayerBase {

    private static final String giant = "Bonebreaker Giant";
    private static final String lion = "Silvercoat Lion";

    @Test
    public void testForcefield() {
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forcefield");
        addCard(Zone.BATTLEFIELD, playerA, giant);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        attack(1, playerA, giant);
        attack(1, playerA, lion);

        setChoice(playerB, giant);
        setChoice(playerB, lion);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{1}");
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{1}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1 - 1);
    }
}
