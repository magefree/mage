package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Reassembling Skeleton: {1}{B}: Return Reassembling Skeleton from your graveyard to the battlefield tapped.
 * Hungry Ghoul: {1}, Sacrifice another creature: Put a +1/+1 counter on Hungry Ghoul.
 * <p>
 * CR 400.7: an object that moves from one zone to another becomes a new object with no memory of,
 * or relation to, its previous existence.
 *
 * @author adams-shaun
 */
public class ReassemblingSkeletonStaleReturnTest extends CardTestPlayerBase {

    private static final String skeleton = "Reassembling Skeleton";
    private static final String ghoul = "Hungry Ghoul";

    /**
     * Two return abilities (A1, A2) are put on the stack for the graveyard card. A2 resolves and the Skeleton
     * returns; it is sacrificed to the Ghoul and is a new object in the graveyard. A1 was put on the stack for the
     * old object, so it must do nothing.
     */
    @Test
    public void testStaleReturnAbilityDoesNothing() {
        addCard(Zone.BATTLEFIELD, playerA, ghoul);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.GRAVEYARD, playerA, skeleton);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Return");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Return");
        checkStackSize("two return abilities", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // A2 resolves, the Skeleton is back (tapped)
        checkPermanentCount("skeleton returned", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skeleton, 1);

        setChoice(playerA, skeleton); // the creature sacrificed to Hungry Ghoul
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice another creature");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // the Ghoul ability resolves, A1 still on the stack
        checkStackSize("stale return ability left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkGraveyardCount("skeleton sacrificed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skeleton, 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, skeleton, 0);
        assertGraveyardCount(playerA, skeleton, 1);
        assertCounterCount(playerA, ghoul, CounterType.P1P1, 1);
    }

    /**
     * A single return ability still returns the card: the check must not break the ordinary case.
     */
    @Test
    public void testReturnFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, skeleton);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}: Return");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, skeleton, 1);
        assertTapped(skeleton, true);
        assertGraveyardCount(playerA, skeleton, 0);
    }
}
