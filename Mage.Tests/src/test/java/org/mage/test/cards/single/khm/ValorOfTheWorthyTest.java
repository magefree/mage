package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ValorOfTheWorthyTest extends CardTestPlayerBase {

    @Test
    public void test_DieTarget() {
        addCustomEffect_TargetDestroy(playerA);

        // Enchant creature
        // Enchanted creature gets +1/+1.
        // When enchanted creature leaves the battlefield, create a 1/1 white Spirit creature token with flying.
        addCard(Zone.HAND, playerA, "Valor of the Worthy"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // attach
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Valor of the Worthy", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPT("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 1, 2 + 1);

        // destroy target - must trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve destroy
        checkStackObject("must trigger on destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "When enchanted creature leaves the battlefield", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTokenCount(playerA, "Spirit Token", 1);
    }

    @Test
    public void test_DieItself() {
        addCustomEffect_TargetDestroy(playerA, 2);

        // Enchant creature
        // Enchanted creature gets +1/+1.
        // When enchanted creature leaves the battlefield, create a 1/1 white Spirit creature token with flying.
        addCard(Zone.HAND, playerA, "Valor of the Worthy"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // attach
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Valor of the Worthy", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPT("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 1, 2 + 1);

        // destroy all - must trigger anyway
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy");
        addTarget(playerA, "Valor of the Worthy^Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // resolve destroy
        checkStackObject("must trigger on destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "When enchanted creature leaves the battlefield", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTokenCount(playerA, "Spirit Token", 1);
    }
}
