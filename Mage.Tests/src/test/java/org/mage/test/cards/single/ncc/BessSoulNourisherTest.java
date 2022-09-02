package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BessSoulNourisher Bess, Soul Nourisher}
 * <p>
 * Whenever one or more other creatures with base power and toughness 1/1 enter the battlefield under your control,
 * put a +1/+1 counter on Bess, Soul Nourisher.
 * <p>
 * Whenever Bess attacks, each other creature you control with base power and toughness 1/1 gets +X/+X until end of turn,
 * where X is the number of +1/+1 counters on Bess.
 *
 * @author Alex-Vasile
 */
public class BessSoulNourisherTest extends CardTestPlayerBase {

    // {1}{G}{W}
    private static final String bessSoulNourisher = "Bess, Soul Nourisher";
    // {3}{W}
    // Create three 1/1 white Soldier creature tokens.
    private static final String captainsCall = "Captain's Call";

    /**
     * Test that it only triggers once for a group entering
     */
    @Test
    public void testEntersGroup() {
        addCard(Zone.BATTLEFIELD, playerA, bessSoulNourisher);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, captainsCall);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, captainsCall);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Soldier Token", 3);
        assertCounterCount(playerA, bessSoulNourisher, CounterType.P1P1, 1); // Should only get one +1/+1 since all soldiers tokens enter at once
    }

    /**
     * Test that the boosting works properly
     */
    @Test
    public void testBoost() {
        // TODO
    }
}
