
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OnduRisingTest extends CardTestPlayerBase {

    @Test
    public void testLiflinkGained() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // Whenever a creature attacks this turn, it gains lifelink until end of turn.
        // Awaken 4—{4}{W}
        addCard(Zone.HAND, playerB, "Ondu Rising", 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}");
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}");
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}");
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}");
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ondu Rising with awaken");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Mountain");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Ondu Rising", 1);
        assertPowerToughness(playerB, "Mountain", 4, 4);

        assertLife(playerA, 14);
        assertLife(playerB, 26);
    }

}
