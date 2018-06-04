
package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Deflecting Palm Instant {R}{W} The next time a source of your choice would
 * deal damage to you this turn, prevent that damage. If damage is prevented
 * this way, Deflecting Palm deals that much damage to that source's controller.
 *
 * @author LevelX2
 */
public class DeflectingPalmTest extends CardTestPlayerBase {

    /**
     * Tests if a damage spell is selected as source the damage is prevented and
     * is dealt to the controller of the damage spell
     */
    @Test
    public void testPreventDamageFromSpell() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerB, "Deflecting Palm");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Deflecting Palm", null, "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Deflecting Palm", 1);
    }

    /**
     * I tried to prevent damage dealt by Deflecting Palm using a Drokoma's
     * Command and it seems it's not working properly. According to this, it
     * should work.
     */
    @Test
    public void testPreventDamageWithDromokasCommand() {

        // Choose two -
        // - Prevent all damage target instant or sorcery spell would deal this turn;
        // - or Target player sacrifices an enchantment;
        // - Put a +1/+1 counter on target creature;
        // - or Target creature you control fights target creature you don't control.
        addCard(Zone.HAND, playerA, "Dromoka's Command");// Instant {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // Creature 2/2

        // The next time a source of your choice would deal damage to you this turn, prevent that damage.
        // If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        addCard(Zone.HAND, playerB, "Deflecting Palm"); // Instant {R}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Deflecting Palm");
        setChoice(playerB, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dromoka's Command", "Deflecting Palm");
        addTarget(playerA, "Silvercoat Lion");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "3");

        attack(1, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerB, "Deflecting Palm", 1);
        assertGraveyardCount(playerA, "Dromoka's Command", 1);

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

}
