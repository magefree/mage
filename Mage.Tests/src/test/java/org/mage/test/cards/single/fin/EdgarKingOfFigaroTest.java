package org.mage.test.cards.single.fin;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class EdgarKingOfFigaroTest extends CardTestPlayerBase {

    private static final String edgar = "Edgar, King of Figaro";
    private static final String traprunner = "Goblin Traprunner";
    private static final String swindler = "Tavern Swindler";

    @Test
    public void testTraprunnerThenSwindler() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, traprunner);
        addCard(Zone.BATTLEFIELD, playerA, swindler);

        attack(1, playerA, traprunner);

        setFlipCoinResult(playerA, false);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 3);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 4 - 1 - 1 - 1);
    }

    @Test
    public void testSwindlerThenTraprunner() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, traprunner);
        addCard(Zone.BATTLEFIELD, playerA, swindler);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, false);
        attack(1, playerA, traprunner);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 0);
        assertLife(playerA, 20 - 3 + 6);
        assertLife(playerB, 20 - 4);
    }

    private static final String giant = "Two-Headed Giant";

    @Test
    public void testTwoHeadedGiant() {
        addCard(Zone.BATTLEFIELD, playerA, edgar);
        addCard(Zone.BATTLEFIELD, playerA, giant);

        attack(1, playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(giant, true);
        assertAbility(playerA, giant, DoubleStrikeAbility.getInstance(), true);
        assertLife(playerB, 20 - 4 - 4);
    }
}
