package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class FlyingDroneTest extends CardTestPlayerBase {

    /**
     * Flying Drone {1}{U}
     * Artifact Creature - Robot Scout 1/2
     * Flying, vigilance
     * {1}{U}, {T}: Draw a card, then discard a card. This ability costs {1}{U} less to activate
     * if another creature with flying entered the battlefield under your control this turn.
     */
    private static final String drone = "Flying Drone";
    private static final String loot = "{1}{U}, {T}: Draw a card, then discard a card.";

    private static final String ornithopter = "Ornithopter"; // {0} 0/2 flying
    private static final String memnite = "Memnite"; // {0} 1/1
    private static final String fervor = "Fervor"; // creatures you control have haste
    private static final String bolt = "Lightning Bolt";

    @Test
    public void test_FullCost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, drone);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bolt);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, loot);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Island", true, 2);
        assertTapped(drone, true);
        assertGraveyardCount(playerA, bolt, 1);
    }

    @Test
    public void test_ReducedAfterAnotherFlyingCreatureEntered() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, drone);
        addCard(Zone.HAND, playerA, ornithopter);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bolt);

        checkPlayableAbility("no mana before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, loot, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ornithopter);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("only tap after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, loot, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, loot);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(drone, true);
        assertGraveyardCount(playerA, bolt, 1);
    }

    @Test
    public void test_SelfEnteringDoesNotReduce() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, fervor);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, drone);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, drone);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, loot);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // {1}{U} for the drone and {1}{U} for the full cost of the ability
        assertTappedCount("Island", true, 4);
        assertTapped(drone, true);
        assertGraveyardCount(playerA, bolt, 1);
    }

    @Test
    public void test_SelfEnteringDoesNotReduce_NoMana() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, fervor);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, drone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, drone);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("not reduced", 1, PhaseStep.PRECOMBAT_MAIN, playerA, loot, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(drone, false);
    }

    @Test
    public void test_NonFlyingCreatureDoesNotReduce() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, drone);
        addCard(Zone.HAND, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("not reduced", 1, PhaseStep.PRECOMBAT_MAIN, playerA, loot, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, memnite, 1);
        assertTapped(drone, false);
    }
}
