package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class AwakenTest extends CardTestPlayerBase {

    @Test
    public void testCastWithAwaken() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Destroy target creature or planeswalker.
        // Awaken 4-{5}{B}{B}
        addCard(Zone.HAND, playerA, "Ruinous Path", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ruinous Path with awaken", "Silvercoat Lion");
        addTarget(playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Plains", 4, 4);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Used Awaken on Scatter to the Winds on an unanimated Shambling Vent.
     * Animated vent and it was still a 3/3 with lifelink when it should've been
     * 5/6.
     */
    @Test
    public void testShamblingVentAndAnimation() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Lantern", 1);
        // Shambling Vent enters the battlefield tapped.
        // {T}: Add {W} or {B}.
        // {1}{W}{B}: Shambling Vent becomes a 2/3 white and black Elemental creature with lifelink until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerA, "Shambling Vent", 1);
        // Counter target spell.
        // Awaken 3-{4}{U}{U}
        addCard(Zone.HAND, playerA, "Scatter to the Winds", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Scatter to the Winds with awaken", "Silvercoat Lion");
        addTarget(playerA, "Shambling Vent");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}{B}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Scatter to the Winds", 1);
        assertPowerToughness(playerA, "Shambling Vent", 5, 6);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testShamblingVent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Lantern", 1);
        // Shambling Vent enters the battlefield tapped.
        // {T}: Add {W} or {B}.
        // {1}{W}{B}: Shambling Vent becomes a 2/3 white and black Elemental creature with lifelink until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerA, "Shambling Vent", 1);
        // Counter target spell.
        // Awaken 3-{4}{U}{U}
        addCard(Zone.HAND, playerA, "Scatter to the Winds", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Scatter to the Winds with awaken", "Silvercoat Lion");
        addTarget(playerA, "Shambling Vent");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Scatter to the Winds", 1);
        assertPowerToughness(playerA, "Shambling Vent", 3, 3);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    /**
     * Awakened Clutch of Currents returned the targeted land (for awaken) to my
     * hand in addition to the targeted creature.
     */
    @Test
    public void testClutchOfCurrents() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Return target creature to its owner's hand.
        // Awaken 3—{4}{U}
        addCard(Zone.HAND, playerA, "Clutch of Currents", 1); // {U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clutch of Currents with awaken", "Silvercoat Lion");
        addTarget(playerA, "Island");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Clutch of Currents", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Island", 5);
        assertPowerToughness(playerA, "Island", 3, 3, Filter.ComparisonScope.Any);

    }

    /**
     * Select spell ability with different targets
     */
    @Test
    public void test_CastNormalSpell() {
        // Counter target spell.
        // Awaken 3—{4}{U}{U} (If you cast this spell for {4}{U}{U}, also put three +1/+1 counters on target land you control
        // and it becomes a 0/0 Elemental creature with haste. It’s still a land.)
        addCard(Zone.HAND, playerA, "Scatter to the Winds", 1);  // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        // counter by normal cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scatter to the Winds", "Lightning Bolt");

        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerB, 20);
        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CastAwakenSpell() {
        // Counter target spell.
        // Awaken 3—{4}{U}{U} (If you cast this spell for {4}{U}{U}, also put three +1/+1 counters on target land you control
        // and it becomes a 0/0 Elemental creature with haste. It’s still a land.)
        addCard(Zone.HAND, playerA, "Scatter to the Winds", 1);  // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        // counter by normal cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scatter to the Winds with awaken", "Lightning Bolt");
        addTarget(playerA, "Island");

        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerB, 20);
        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
