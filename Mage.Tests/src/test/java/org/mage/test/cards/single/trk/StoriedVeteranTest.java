package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class StoriedVeteranTest extends CardTestPlayerBase {

    @Test
    public void test_CastFromGraveyard() {
        // You may cast this card from your graveyard. If you do, it enters with a finality counter on it.
        addCard(Zone.GRAVEYARD, playerA, "Storied Veteran"); // {B}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storied Veteran");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("finality", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storied Veteran", CounterType.FINALITY, 1);

        // If a permanent with a finality counter on it would be put into a graveyard from the battlefield, exile it
        // instead.
        // (Hundred-Battle Veteran, 2025-04-04)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Storied Veteran");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Storied Veteran", 1);
        assertGraveyardCount(playerA, "Storied Veteran", 0);
    }

    @Test
    public void test_CastFromHand() {
        // You may cast this card from your graveyard. If you do, it enters with a finality counter on it.
        addCard(Zone.HAND, playerA, "Storied Veteran"); // {B}, 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storied Veteran");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("no finality", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storied Veteran", CounterType.FINALITY, 0);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Storied Veteran");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Storied Veteran", 0);
        assertGraveyardCount(playerA, "Storied Veteran", 1);
    }
}
