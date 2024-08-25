package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AddManaAnyCombinationTest extends CardTestPlayerBase {

    @Test
    public void testOrcishLumberjack() {
        String ability = "{T}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.";

        addCard(Zone.BATTLEFIELD, playerA, "Orcish Lumberjack");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Living Twister");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability);
        setChoice(playerA, "Forest"); // to sac
        setChoiceAmount(playerA, 2, 1); // RRG
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Twister");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Living Twister", 2, 5);
        assertGraveyardCount(playerA, "Forest", 1);
    }
}
