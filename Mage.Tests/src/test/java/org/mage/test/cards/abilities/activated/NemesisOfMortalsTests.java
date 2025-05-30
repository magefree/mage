package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class NemesisOfMortalsTests extends CardTestPlayerBase {

    @Test
    public void testNoCostReduction() {

        addCard(Zone.BATTLEFIELD, playerA, "Nemesis of Mortals");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G}{G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Forest", true, 9);
    }

    @Test
    public void testWithCostReduction() {

        addCard(Zone.BATTLEFIELD, playerA, "Nemesis of Mortals");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);

        addCard(Zone.GRAVEYARD, playerA, "Memnite", 4);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G}{G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Forest", true, 5);
    }
}
