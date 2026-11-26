package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AttentiveSunscribe Attentive Sunscribe} {1}{W}
 * Whenever Attentive Sunscribe becomes tapped, scry 1.
 * <p>
 * Entering the battlefield tapped (CR 614.12) is not becoming tapped (CR 701.26a),
 * so it must not trigger, even when a replacement effect such as
 * {@link mage.cards.d.DauntlessDismantler Dauntless Dismantler} makes it enter tapped.
 */
public class AttentiveSunscribeTest extends CardTestPlayerBase {

    private static final String sunscribe = "Attentive Sunscribe";
    private static final String dismantler = "Dauntless Dismantler";

    @Test
    public void test_EntersTappedDoesNotTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dismantler);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, sunscribe);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, sunscribe);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, playerB);
        checkStackSize("no scry trigger", 2, PhaseStep.PRECOMBAT_MAIN, playerB, 0);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, sunscribe, 1);
        assertTapped(sunscribe, true);
    }
}
