package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PuppetMasterTest extends CardTestPlayerBase {

    private static final String master = "Puppet Master"; // {U}{U}{U} Aura
    // When enchanted creature dies, return that card to its owner's hand. If that card is returned to its owner's
    // hand this way, you may pay {U}{U}{U}. If you do, return Puppet Master to its owner's hand.

    /**
     * Destroyed together with the creature, Puppet Master is already in the graveyard when the creature's death is
     * handled.
     */
    @Test
    public void test_DestroyedWithCreature() {
        addCard(Zone.HAND, playerA, master);
        addCard(Zone.HAND, playerA, "Akroma's Vengeance"); // {4}{W}{W}: Destroy all artifacts, creatures, and enchantments.
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, master, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma's Vengeance");
        setChoice(playerA, true); // pay {U}{U}{U}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, master, 1);
    }
}
