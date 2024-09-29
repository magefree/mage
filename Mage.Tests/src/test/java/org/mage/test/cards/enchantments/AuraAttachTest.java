package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AuraAttachTest extends CardTestPlayerBase {

    @Test
    public void testInfectiousRage() {
        /*
         * Infectious Rage {1}{R}
         * Enchant creature
         * Enchanted creature gets +2/-1.
         * When enchanted creature dies, choose a creature at random Infectious Rage can enchant.
         * Return Infectious Rage to the battlefield attached to that creature.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Pensive Minotaur", 1); // 2/3 (vanilla)
        addCard(Zone.BATTLEFIELD, playerB, "Slippery Bogle", 1); // 1/1 hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Thermal Glider", 1); // 2/1 flying, protection from red
        addCard(Zone.HAND, playerA, "Infectious Rage", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infectious Rage", "Pensive Minotaur");
        // Minotaur is now a 4/2
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Pensive Minotaur");
        // Minotaur dies. Infectious Rage triggers, only legal creature to enchant is Slippery Bogle
        // Bogle dies. // Infectious Rage triggers, Thermal Glider can't be enchanted by it, so it remains in graveyard
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pensive Minotaur", 1);
        assertGraveyardCount(playerA, "Infectious Rage", 1);
        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerB, "Slippery Bogle", 1);
        assertPermanentCount(playerB, "Thermal Glider", 1);

    }

}
