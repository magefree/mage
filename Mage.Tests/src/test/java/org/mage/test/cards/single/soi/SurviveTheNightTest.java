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
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3
        
        attack(1, playerA, "Bronze Sable");
        block(1, playerB, "Hill Giant", "Bronze Sable");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Survive the Night", "Bronze Sable");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Survive the Night", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerA, "Clue", 1);
        assertPermanentCount(playerA, "Bronze Sable", 1);
        assertPowerToughness(playerA, "Bronze Sable", 3, 1);
        assertAbility(playerA, "Bronze Sable", IndestructibleAbility.getInstance(), true);
    }
}
