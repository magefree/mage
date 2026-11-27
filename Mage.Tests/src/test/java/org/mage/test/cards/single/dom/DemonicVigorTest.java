package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DemonicVigorTest extends CardTestPlayerBase {

    private static final String vigor = "Demonic Vigor"; // {B} Aura
    // Enchanted creature gets +1/+1.
    // When enchanted creature dies, return that card to its owner's hand.

    /**
     * Exiled from the graveyard in response, the creature card stays in exile.
     */
    @Test
    public void test_CreatureExiledFromGraveyardInResponse() {
        addCard(Zone.HAND, playerA, vigor);
        addCard(Zone.HAND, playerA, "Coffin Purge"); // {B}: Exile target card from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vigor, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coffin Purge", "Grizzly Bears", "When enchanted creature dies");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Grizzly Bears", 0);
    }
}
