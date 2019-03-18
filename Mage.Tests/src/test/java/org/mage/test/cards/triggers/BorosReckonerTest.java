

package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class BorosReckonerTest extends CardTestPlayerBase {
    /**
     * Boros Reckoner
     * {R/W}{R/W}{R/W}
     * Creature — Minotaur Wizard
     * Whenever Boros Reckoner is dealt damage, it deals that much damage to any target.
     * {R/W}: Boros Reckoner gains first strike until end of turn..
     */

    /**
     * If damage is dealt to Boros Reckoner - Exactly the same amount of damage
     * can be dealt to any target.
     *
     */
    @Test
    public void testDamageAmountLikeDamageDealt() {
        // When Phytotitan dies, return it to the battlefield tapped under its owner's control at the beginning of their next upkeep.
        addCard(Zone.BATTLEFIELD, playerA, "Phytotitan");
        addCard(Zone.BATTLEFIELD, playerB, "Boros Reckoner");

        attack(2, playerB, "Boros Reckoner");
        block(2,playerA, "Phytotitan", "Boros Reckoner");
        addTarget(playerB, playerA);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 13); // got 7 damage from Boros Reckoner
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Phytotitan", 1); // returned at the next upkeep
        assertTapped("Phytotitan", true);

    }

}
