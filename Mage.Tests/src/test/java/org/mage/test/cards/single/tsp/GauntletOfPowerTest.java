package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GauntletOfPowerTest extends CardTestPlayerBase {

    private static final String gauntlet = "Gauntlet of Power";
    private static final String red = "Red";
    private static final String maaka = "Feral Maaka";
    private static final String myr = "Hovermyr";

    @Test
    public void testBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, maaka);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.HAND, playerA, gauntlet);

        setChoice(playerA, red);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gauntlet);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, maaka, 2 + 1, 2 + 1);
        assertPowerToughness(playerA, myr, 1, 2);
    }

    @Test
    public void testControllerMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, gauntlet);
        addCard(Zone.HAND, playerA, maaka);

        setChoice(playerA, red);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gauntlet);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, maaka);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, maaka, 2 + 1, 2 + 1);
    }

    @Test
    public void testNotControllerMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, gauntlet);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, maaka);

        setChoice(playerA, red);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gauntlet);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, maaka);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, maaka, 2 + 1, 2 + 1);
    }
}
