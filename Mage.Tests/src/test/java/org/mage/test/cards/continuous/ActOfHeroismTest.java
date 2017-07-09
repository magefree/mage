package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author mzulch
 */
public class ActOfHeroismTest extends CardTestPlayerBase {

    // Test that loss of abilities doesn't remove Act of Heroism's "block an additional creature" effect
    @Test
    public void testCanBlockMultiple() {
        // 0/4 Creature - Camel
        addCard(Zone.BATTLEFIELD, playerA, "Tasseled Dromedary");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Act of Heroism");

        addCard(Zone.BATTLEFIELD, playerB, "Unwavering Initiate");
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Cat");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 8);
        addCard(Zone.HAND, playerB, "Overwhelming Splendor");

        // Untap target creature. It gets +2/+2 until end of turn and can block an additional creature this turn
        castSpell(2, PhaseStep.UPKEEP, playerA, "Act of Heroism", "Tasseled Dromedary");

        // Creatures enchanted player controls loses all abilities and have base power and toughness 1/1
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Overwhelming Splendor", playerA);

        attack(2, playerB, "Unwavering Initiate");
        attack(2, playerB, "Sacred Cat");

        block(2, playerA, "Tasseled Dromedary", "Unwavering Initiate");
        block(2, playerA, "Tasseled Dromedary", "Sacred Cat");

        setStopAt(2, PhaseStep.COMBAT_DAMAGE);

        // Fails if A's creature is unable to block both attackers
        execute();
    }
}
