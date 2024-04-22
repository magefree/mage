package org.mage.test.cards.single.bng;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ThunderousMightTest extends CardTestPlayerBase {

    // See bug #3777
    private static final String aura = "Thunderous Might"; // {1}{R} Aura
    // Whenever enchanted creature attacks, it gets +X/+0 until end of turn, where X is your devotion to red.

    @Test
    public void testLockedIn() {
        String bear = "Runeclaw Bear"; // 2/2 {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, "Duergar Assailant"); // 1/1 {R/W} Sac: 1 dmg to target attacking/blocking
        addCard(Zone.HAND, playerA, aura);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aura, bear);
        attack(1, playerA, bear, playerB);
        waitStackResolved(1, PhaseStep.DECLARE_ATTACKERS);
        checkPT("2 devotion", 1, PhaseStep.DECLARE_ATTACKERS, playerA, bear, 4, 2);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Sacrifice", bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, bear, 4, 2);
        assertDamageReceived(playerA, bear, 1);
        assertLife(playerB, 16);
        assertGraveyardCount(playerA, 1);

    }
}
