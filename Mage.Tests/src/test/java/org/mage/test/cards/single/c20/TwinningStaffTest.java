package org.mage.test.cards.single.c20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TwinningStaffTest extends CardTestPlayerBase {
    private static final String staff = "Twinning Staff";
    private static final String zada = "Zada, Hedron Grinder";
    private static final String bear = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";
    private static final String growth = "Giant Growth";
    private static final String disfigure = "Disfigure";
    private static final String fork = "Fork";
    private static final String elite = "Scalebane's Elite";

    @Test
    public void testWithZada() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.BATTLEFIELD, playerA, zada);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, growth);

        setChoice(playerA, TestPlayer.CHOICE_SKIP); // skip stack order
        setChoice(playerA, true);
        addTarget(playerA, bear);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, zada);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, zada, 3 + 3, 3 + 3);
        assertPowerToughness(playerA, bear, 2 + 3 + 3, 2 + 3 + 3);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 3);
    }

    @Test
    public void testWithZadaTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, staff, 2);
        addCard(Zone.BATTLEFIELD, playerA, zada);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, growth);

        addTarget(playerA, bear, 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, zada);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, zada, 3 + 3, 3 + 3);
        assertPowerToughness(playerA, bear, 2 + 3 + 3 + 3, 2 + 3 + 3 + 3);
        assertPowerToughness(playerA, lion, 2 + 3, 2 + 3);
    }

    @Test
    public void testFork() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3);
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, elite); // protection from black
        addCard(Zone.HAND, playerA, disfigure);
        addCard(Zone.HAND, playerA, fork);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, disfigure, bear);
        setChoice(playerA, true, 2);
        addTarget(playerA, elite, 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fork, disfigure, disfigure);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, disfigure, 1);
        assertGraveyardCount(playerA, fork, 1);
        assertGraveyardCount(playerA, bear, 1);
        assertGraveyardCount(playerA, elite, 1);
    }

    @Test
    public void testThousandYearStormZeroCopies() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Thousand-Year Storm");
        addCard(Zone.BATTLEFIELD, playerA, staff);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", playerB);
        checkLife("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStrictChooseMode(false);
        checkLife("copy", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 3);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
