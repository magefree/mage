package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class EnergyRefractorManaCalculationsTest extends CardTestPlayerBase {

    @Test
    public void test_Single() {
        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // make sure it works
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ");
        setChoice(playerA, "Red");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    @Ignore // TODO: must fix infinite mana calculation
    public void test_Multiple() {
        int cardsAmount = 2; // after fix make it 10+ for testing

        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", cardsAmount);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * cardsAmount);

        runCode("simple way to cause freeze", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            player.getManaAvailable(game);
        });

        // make sure it works
        //activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ");
        //setChoice(playerA, "Red");
        //checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
