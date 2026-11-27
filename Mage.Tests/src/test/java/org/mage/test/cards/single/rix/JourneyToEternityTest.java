package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class JourneyToEternityTest extends CardTestPlayerBase {

    private static final String journey = "Journey to Eternity"; // {1}{B}{G} Aura
    // Enchant creature you control
    // When enchanted creature dies, return it to the battlefield under your control,
    // then return Journey to Eternity to the battlefield transformed under your control.

    @Test
    public void test_ReturnsTransformed() {
        addCard(Zone.HAND, playerA, journey);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, journey, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Atzal, Cave of Eternity", 1);
    }
}
