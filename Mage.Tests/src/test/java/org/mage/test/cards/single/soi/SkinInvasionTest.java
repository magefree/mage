package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SkinInvasionTest extends CardTestPlayerBase {

    private static final String invasion = "Skin Invasion"; // {R} Aura
    // Enchanted creature attacks each combat if able.
    // When enchanted creature dies, return Skin Invasion to the battlefield transformed under your control.

    /**
     * Exiled from the graveyard in response, Skin Invasion stays in exile.
     */
    @Test
    public void test_ExiledFromGraveyardInResponse() {
        addCard(Zone.HAND, playerA, invasion);
        addCard(Zone.HAND, playerA, "Coffin Purge"); // {B}: Exile target card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, invasion, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coffin Purge", invasion, "When enchanted creature dies");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, invasion, 1);
        assertPermanentCount(playerA, "Skin Shedder", 0);
    }
}
