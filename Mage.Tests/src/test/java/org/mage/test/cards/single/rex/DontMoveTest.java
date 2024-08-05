package org.mage.test.cards.single.rex;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DontMoveTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DontMove Don't Move} {3}{W}{W}
     * Sorcery
     * Destroy all tapped creatures. Until your next turn, whenever a creature becomes tapped, destroy it.
     */
    private static final String dont = "Don't Move";

    /**
     * {@link mage.cards.s.Soulmender Soulmender} {W}
     * Creature — Human Cleric
     * {T}: You gain 1 life.
     * 1/1
     */
    private static final String mender = "Soulmender";

    @Test
    public void test_TappedThisTurn() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, dont, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, mender, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dont);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: You gain 1 life");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertGraveyardCount(playerA, mender, 1);
    }

    @Test
    public void test_TappedOppTurn() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, dont, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, mender, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dont);
        activateAbility(2, PhaseStep.UPKEEP, playerA, "{T}: You gain 1 life");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertGraveyardCount(playerA, mender, 1);
    }

    @Test
    public void test_TappedYourNextTurn_EffectExpired() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, dont, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, mender, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dont);
        activateAbility(3, PhaseStep.UPKEEP, playerA, "{T}: You gain 1 life");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
        assertGraveyardCount(playerA, mender, 0);
        assertTappedCount(mender, true, 1);
    }
}
