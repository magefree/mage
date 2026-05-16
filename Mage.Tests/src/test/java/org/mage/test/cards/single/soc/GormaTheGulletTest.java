package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GormaTheGulletTest extends CardTestPlayerBase {

    @Test
    public void testCountsCreaturesDiedUnderYourControlForCounters() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Gorma, the Gullet");
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature: Add {C}{C}");
        setChoice(playerA, "Memnite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Gorma, the Gullet", 2, 2);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }
}
