package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class NextOfKinTest extends CardTestPlayerBase {

    private static final String kin = "Next of Kin"; // {2}{G} Aura
    // Enchant creature you control
    // When enchanted creature dies, you may put a creature card you own with lesser mana value from your hand or
    // from the command zone onto the battlefield. If you do, return Next of Kin to the battlefield attached to that
    // creature at the beginning of the next end step.

    @Test
    public void test_DestroyedByEffect() {
        addCard(Zone.HAND, playerA, kin);
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1, mana value 0
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kin, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        setChoice(playerA, true); // use the trigger
        setChoice(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertAttachedTo(playerA, kin, "Memnite", true);
    }
}
