package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CAMPTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CAMP C.A.M.P.}  {3}
     * Artifact — Fortification
     * Whenever fortified land is tapped for mana, put a +1/+1 counter on target creature you control. If that creature shares a color with the mana that land produced, create a Junk token. (It’s an artifact with “{T}, Sacrifice this artifact: Exile the top card of your library. You may play that card this turn. Activate only as a sorcery.”)
     * Fortify {3} ({3}: Attach to target land you control. Fortify only as a sorcery.)
     */
    private static final String camp = "C.A.M.P.";

    @Test
    public void test_ShareColor() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, camp);
        addCard(Zone.BATTLEFIELD, playerA, "Coiling Oracle"); // UG creature
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 1);

        // Make sure mana from Wastes is used first.
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}", 3);

        // Fortify the Volcanic Island
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fortify {3}", "Volcanic Island");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Tap the fortified Volcanic Island for {U}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        addTarget(playerA, "Coiling Oracle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Coiling Oracle", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Junk Token", 1);
    }

    @Test
    public void test_DontShareColor() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, camp);
        addCard(Zone.BATTLEFIELD, playerA, "Coiling Oracle"); // UG creature
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 1);

        // Make sure mana from Wastes is used first.
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}", 3);

        // Fortify the Volcanic Island
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fortify {3}", "Volcanic Island");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Tap the fortified Volcanic Island for {R}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        addTarget(playerA, "Coiling Oracle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Coiling Oracle", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Junk Token", 0);
    }
}
