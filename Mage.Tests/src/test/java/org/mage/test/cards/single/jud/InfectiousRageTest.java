package org.mage.test.cards.single.jud;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class InfectiousRageTest extends CardTestPlayerBase {

    private static final String rage = "Infectious Rage"; // {1}{R} Aura
    // Enchanted creature gets +2/-1.
    // When enchanted creature dies, choose a creature at random Infectious Rage can enchant.
    // Return Infectious Rage to the battlefield attached to that creature.

    /**
     * Destroyed alone, the creature dies while Infectious Rage is still on the battlefield.
     */
    @Test
    public void test_DestroyedByEffect() {
        addCard(Zone.HAND, playerA, rage);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rage, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerB, rage, "Silvercoat Lion", true);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 1);
    }
}
