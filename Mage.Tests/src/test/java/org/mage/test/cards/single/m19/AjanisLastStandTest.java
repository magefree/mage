package org.mage.test.cards.single.m19;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AjanisLastStandTest extends CardTestPlayerBase {

    @Test
    public void test_TriggerOnAlive() {
        addCustomEffect_TargetDestroy(playerA);

        // Whenever a creature or planeswalker you control dies, you may sacrifice Ajani's Last Stand.
        // If you do, create a 4/4 white Avatar creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Ajani's Last Stand");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Grizzly Bears"); // to destroy
        setChoice(playerA, true); // yes to sacrifice

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Ajani's Last Stand", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Avatar Token", 1);
    }

    @Test
    public void test_NoTriggerOnSelfDies() {
        addCustomEffect_AllDestroy(playerA);

        // Whenever a creature or planeswalker you control dies, you may sacrifice Ajani's Last Stand.
        // If you do, create a 4/4 white Avatar creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Ajani's Last Stand");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        // destroy all without triggers (cause no sacrifice cost can be pay here)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "all destroy");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Ajani's Last Stand", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Avatar Token", 0);
    }
}
