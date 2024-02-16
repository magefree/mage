package org.mage.test.cards.single.stx;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MageDuel Mage Duel}
 * {2}{G}
 * Instant
 * <p>
 * This spell costs {2} less to cast if you’ve cast another instant or sorcery spell this turn.
 * <p>
 * Target creature you control gets +1/+2 until end of turn.
 * Then it fights target creature you don’t control.
 * (Each deals damage equal to its power to the other.)
 *
 * @author Alex-Vasile
 */
public class MageDuelTest extends CardTestPlayerBase {

    private static final String mageDuel = "Mage Duel";
    // Instant
    // {W}
    // You gain hexproof until your next turn. You gain 2 life.
    private static final String blossomingCalm = "Blossoming Calm";
    // Sorcery
    // {W}
    // You gain 2 life for each creature you control.
    private static final String festivalOfTrokin = "Festival of Trokin";

    // Creature you control
    // 1/1
    private static final String akroanJailer = "Akroan Jailer";
    // Creature you don't control
    // 2/2
    private static final String lion = "Silvercoat Lion";

    /**
     * Test that the spell costs {2}{G} if no other spell was cast
     */
    @Test
    public void testCostReductionNotAlwaysOn() {
        addCard(Zone.HAND, playerA, mageDuel);
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, akroanJailer);

        addCard(Zone.BATTLEFIELD, playerB, lion);

        checkPlayableAbility("Can't cast for {1}{G}", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast" + mageDuel, false);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mageDuel);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, akroanJailer, 2, 3);
        assertPermanentCount(playerA, akroanJailer, 1);
        assertGraveyardCount(playerB, lion, 1);
    }

    /**
     * Test cost reduction does not apply to itself.
     * If you have {2}{G} to pay for it, it should still cost {2}{G} rather than {G}
     */
    @Test
    public void testDoesntDiscountItself() {
        addCard(Zone.HAND, playerA, mageDuel);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, akroanJailer);

        addCard(Zone.BATTLEFIELD, playerB, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mageDuel);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, akroanJailer, 2, 3);
        assertPermanentCount(playerA, akroanJailer, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertManaPool(playerA, ManaType.GREEN, 0);
        assertTappedCount("Forest", true, 3);
    }

    /**
     * Test that the cost reduction works with instant spells.
     */
    @Test
    public void testInstantCostReduction() {
        addCard(Zone.HAND, playerA, mageDuel);
        addCard(Zone.HAND, playerA, blossomingCalm);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, akroanJailer);

        addCard(Zone.BATTLEFIELD, playerB, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blossomingCalm);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mageDuel);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, akroanJailer, 2, 3);
        assertPermanentCount(playerA, akroanJailer, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertLife(playerA, 20 + 2);
    }

    /**
     * Test that the cost reduction works with sorcery spells.
     */
    @Test
    public void testSorceryCostReduction() {
        addCard(Zone.HAND, playerA, mageDuel);
        addCard(Zone.HAND, playerA, festivalOfTrokin);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, akroanJailer);

        addCard(Zone.BATTLEFIELD, playerB, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, festivalOfTrokin);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mageDuel);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, akroanJailer, 2, 3);
        assertPermanentCount(playerA, akroanJailer, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertLife(playerA, 20 + 2);
    }

    /**
     * Test that it works if a instant/sorcery card is copied and then cast
     */
    @Test
    public void testCopiedCardCast() {
        String isochronScpeter = "Isochron Scepter";
        addCard(Zone.HAND, playerA, isochronScpeter);
        addCard(Zone.HAND, playerA, mageDuel);
        addCard(Zone.HAND, playerA, blossomingCalm);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2 + 2);
        addCard(Zone.BATTLEFIELD, playerA, akroanJailer);

        addCard(Zone.BATTLEFIELD, playerB, lion);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, isochronScpeter);
        setChoice(playerA, "Yes");
        setChoice(playerA, blossomingCalm);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}:");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mageDuel);
        addTarget(playerA, akroanJailer);
        addTarget(playerA, lion);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, akroanJailer, 2, 3);
        assertPermanentCount(playerA, akroanJailer, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertLife(playerA, 20 + 2);
    }
}
