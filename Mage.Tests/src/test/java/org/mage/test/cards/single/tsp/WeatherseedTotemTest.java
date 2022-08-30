package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class WeatherseedTotemTest extends CardTestPlayerBase {
    private static final String totem = "Weatherseed Totem";
    private static final String naturalize = "Naturalize";

    @Test
    public void testNonCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, totem);
        addCard(Zone.HAND, playerA, naturalize);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, naturalize, totem);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, totem, 0);
        assertGraveyardCount(playerA, totem, 1);
        assertGraveyardCount(playerA, naturalize, 1);
    }

    @Test
    public void testCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.BATTLEFIELD, playerA, totem);
        addCard(Zone.HAND, playerA, naturalize);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{G}{G}{G}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, naturalize, totem);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, totem, 1);
        assertGraveyardCount(playerA, totem, 0);
        assertGraveyardCount(playerA, naturalize, 1);
    }
}
