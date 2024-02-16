package org.mage.test.cards.single.soi;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SurviveTheNightTest extends CardTestPlayerBase {

    // Reported bug: Survive the Night did not grant indestructibility
    @Test
    public void testIndestructibilityGranted() {

        // {2}{W} instant
        // Target creature gets +1/+0 and gains indestructible until end of turn
        // Investigate
        addCard(Zone.HAND, playerA, "Survive the Night");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Hinterland Logger"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Bloodbriar"); // 2/3

        attack(1, playerA, "Hinterland Logger");
        block(1, playerB, "Bloodbriar", "Hinterland Logger");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Survive the Night", "Hinterland Logger");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Survive the Night", 1);
        assertGraveyardCount(playerB, "Bloodbriar", 1);
        assertPermanentCount(playerA, "Clue Token", 1);
        assertPermanentCount(playerA, "Hinterland Logger", 1);
        assertPowerToughness(playerA, "Hinterland Logger", 3, 1);
        assertAbility(playerA, "Hinterland Logger", IndestructibleAbility.getInstance(), true);
    }
}
