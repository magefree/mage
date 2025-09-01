package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DivertDisasterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DivertDisaster Divert Disaster} {1}{U}
     * Instant
     * Counter target spell unless its controller pays {2}. If they do, you create a Lander token.
     */
    private static final String disaster = "Divert Disaster";

    @Test
    public void test_Pay() {
        addCard(Zone.HAND, playerA, disaster);
        addCard(Zone.HAND, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, disaster, "Elite Vanguard", "Elite Vanguard");
        setChoice(playerB, true); // yes to "pays {2}"

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Plains", true, 3);
        assertPermanentCount(playerA, "Lander Token", 1);
        assertPermanentCount(playerB, "Elite Vanguard", 1);
    }

    @Test
    public void test_NoPay() {
        addCard(Zone.HAND, playerA, disaster);
        addCard(Zone.HAND, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, disaster, "Elite Vanguard", "Elite Vanguard");
        setChoice(playerB, false); // yes to "pays {2}"

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Plains", true, 1);
        assertPermanentCount(playerA, "Lander Token", 0);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
    }

    @Test
    public void test_CantPay() {
        addCard(Zone.HAND, playerA, disaster);
        addCard(Zone.HAND, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elite Vanguard");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, disaster, "Elite Vanguard", "Elite Vanguard");
        setChoice(playerB, false); // yes to "pays {2}"

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Plains", true, 1);
        assertPermanentCount(playerA, "Lander Token", 0);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
    }
}
