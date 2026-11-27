package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * A dies trigger that returns its own source with "return this card from your graveyard to the battlefield".
 *
 * @author notgreat, Claude Fable 5.2
 */
public class DiesTriggerReturnsSourceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.UnstoppableSlasher Unstoppable Slasher} {2}{B}, 2/3 deathtouch
     * When Unstoppable Slasher dies, if it had no counters on it, return it to the battlefield tapped under its
     * owner's control with two stun counters on it.
     * <p>
     * The dies trigger finds the card the Slasher became in the graveyard (400.7e) and returns it. The second
     * death has counters, so it stays dead.
     */
    @Test
    public void testUnstoppableSlasherReturnsOnceWithStunCounters() {
        String slasher = "Unstoppable Slasher";
        addCard(Zone.BATTLEFIELD, playerA, slasher);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", slasher);
        checkPermanentCount("returned", 1, PhaseStep.BEGIN_COMBAT, playerA, slasher, 1);
        checkPermanentCounters("stunned", 1, PhaseStep.BEGIN_COMBAT, playerA, slasher, CounterType.STUN, 2);
        checkPermanentTapped("tapped", 1, PhaseStep.BEGIN_COMBAT, playerA, slasher, true, 1);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", slasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, slasher, 0);
        assertGraveyardCount(playerA, slasher, 1);
    }
}
