package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ZabazTheGlimmerwaspTest extends CardTestPlayerBase {

    private static final String zabaz = "Zabaz, the Glimmerwasp";
    private static final String worker = "Arcbound Worker";
    private static final String atog = "Atog";

    @Test
    public void testModularETB() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, zabaz);
        addCard(Zone.HAND, playerA, worker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, worker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, worker, 1, 1);
        assertPowerToughness(playerA, zabaz, 1, 1);
    }

    @Test
    public void testModularDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, zabaz);
        addCard(Zone.BATTLEFIELD, playerA, atog);
        addCard(Zone.HAND, playerA, worker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, worker);

        setChoice(playerA, worker);
        addTarget(playerA, zabaz);
        setChoice(playerA, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, worker, 1);
        assertPowerToughness(playerA, zabaz, 1 + 1 + 1, 1 + 1 + 1);
        assertPowerToughness(playerA, atog, 1 + 2, 2 + 2);
    }
}
