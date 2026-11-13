package org.mage.test.cards.single.fra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 704.5i
 * If a planeswalker has loyalty 0, it's put into its owner's graveyard.
 * <p>
 * 704.3
 * Whenever a player would get priority, the game checks for any of the listed conditions for state-based actions,
 * then performs all applicable state-based actions simultaneously as a single event.
 *
 * @author DarioBarrabinoDev
 */
public class SanctumLurkerTest extends CardTestPlayerBase {

    @Test
    public void test_ZeroLoyalty_YourPlaneswalkerStays() {
        // Planeswalkers you control aren't put into their owners' graveyards for having 0 loyalty.
        addCard(Zone.BATTLEFIELD, playerA, "Sanctum Lurker"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Liliana of the Veil"); // starting loyalty 3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Liliana of the Veil"); // removes all 3 loyalty

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Liliana of the Veil", 1);
        assertCounterCount(playerA, "Liliana of the Veil", CounterType.LOYALTY, 0);
        assertGraveyardCount(playerA, "Liliana of the Veil", 0);
    }

    @Test
    public void test_ZeroLoyalty_OpponentPlaneswalkerIsPutIntoGraveyard() {
        // Planeswalkers you control aren't put into their owners' graveyards for having 0 loyalty.
        addCard(Zone.BATTLEFIELD, playerA, "Sanctum Lurker"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        //
        addCard(Zone.BATTLEFIELD, playerB, "Liliana of the Veil"); // starting loyalty 3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Liliana of the Veil"); // removes all 3 loyalty

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Liliana of the Veil", 0);
        assertGraveyardCount(playerB, "Liliana of the Veil", 1);
    }

    @Test
    public void test_ZeroLoyalty_PlaneswalkerDiesAfterLurkerLeaves() {
        // Planeswalkers you control aren't put into their owners' graveyards for having 0 loyalty.
        addCard(Zone.BATTLEFIELD, playerA, "Sanctum Lurker"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Liliana of the Veil"); // starting loyalty 3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Liliana of the Veil"); // removes all 3 loyalty
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("still on battlefield at 0 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana of the Veil", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Sanctum Lurker"); // kills the 3/2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Sanctum Lurker", 1);
        assertPermanentCount(playerA, "Liliana of the Veil", 0);
        assertGraveyardCount(playerA, "Liliana of the Veil", 1);
    }

    @Test
    public void test_GrantedAbility_DamageEachOpponentAndGainLife() {
        // Planeswalkers you control have "[+2]: This planeswalker deals 1 damage to each opponent and you gain 1 life."
        addCard(Zone.BATTLEFIELD, playerA, "Sanctum Lurker"); // 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Liliana of the Veil"); // starting loyalty 3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: This planeswalker deals 1 damage to each opponent");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Liliana of the Veil", CounterType.LOYALTY, 3 + 2);
        assertLife(playerB, 20 - 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void test_Enters_EmpowerJace() {
        // When this creature enters, empower Jace 1.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Sanctum Lurker"); // {2}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sanctum Lurker");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sanctum Lurker", 1);
        assertPermanentCount(playerA, "Jace", 1); // created by empower Jace
        assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 1);
    }
}
