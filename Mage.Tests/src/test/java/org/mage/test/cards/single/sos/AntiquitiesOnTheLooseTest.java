package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AntiquitiesOnTheLooseTest extends CardTestPlayerBase {

    private static final String antiquities = "Antiquities on the Loose";

    /**
     * Antiquities on the Loose should only add +1/+1 counters when it was cast from outside the hand.
     *
     * This covers the normal cast path:
     * - Cast it from hand.
     * - Create exactly two 2/2 red and white Spirit tokens.
     * - Leave those Spirits without +1/+1 counters.
     * - Put the resolved spell into its owner's graveyard.
     */
    @Test
    public void testCastFromHandCreatesTwoSpiritsWithoutCounters() {
        setStrictChooseMode(true);

        // Setup: a normal hand cast with exactly enough mana and no existing Spirits.
        addCard(Zone.HAND, playerA, antiquities);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        // Action: cast the spell from hand, so the non-hand bonus condition should be false.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, antiquities);

        // In-resolution expectation: the two created Spirits should not receive +1/+1 counters.
        assertSpiritCounters(1, 2, 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Final state: two Spirits were created and the spell resolved to the graveyard.
        assertPermanentCount(playerA, "Spirit Token", 2);
        assertGraveyardCount(playerA, antiquities, 1);
    }

    /**
     * Antiquities on the Loose should count as cast from outside the hand when cast with flashback.
     *
     * This covers the flashback path:
     * - Create one existing Spirit first.
     * - Flash back Antiquities on the Loose from the graveyard.
     * - Create two additional Spirits.
     * - Put one +1/+1 counter on each Spirit controlled by the caster, including the existing Spirit.
     * - Exile the spell through flashback's replacement effect.
     */
    @Test
    public void testFlashbackPutsCountersOnEachSpiritYouControl() {
        setStrictChooseMode(true);

        // Setup: Group Project creates the existing Spirit, and Antiquities is ready to be flashed back.
        addCard(Zone.HAND, playerA, "Group Project");
        addCard(Zone.GRAVEYARD, playerA, antiquities);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        // Action step 1: make one Spirit before Antiquities resolves.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Group Project");

        // Action step 2: cast Antiquities from the graveyard using flashback.
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {4}{W}{W}");

        // In-resolution expectation: all three Spirits controlled by playerA get one +1/+1 counter.
        assertSpiritCounters(3, 3, 1);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Final state: the three Spirits remain on the battlefield and Antiquities is exiled by flashback.
        assertPermanentCount(playerA, "Spirit Token", 3);
        assertExileCount(playerA, antiquities, 1);
    }

    private void assertSpiritCounters(int turn, int expectedCount, int expectedCounters) {
        runCode("check Spirit counters", turn, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            int count = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                if (permanent.isControlledBy(playerA.getId()) && permanent.getName().equals("Spirit Token")) {
                    count++;
                    Assert.assertEquals(
                            "Spirit Token should have expected +1/+1 counters",
                            expectedCounters,
                            permanent.getCounters(game).getCount(CounterType.P1P1)
                    );
                }
            }
            Assert.assertEquals("Unexpected Spirit Token count", expectedCount, count);
        });
    }
}
