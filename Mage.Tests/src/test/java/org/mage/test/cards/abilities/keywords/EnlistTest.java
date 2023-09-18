package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class EnlistTest extends CardTestPlayerBase {

    private static final String crusher = "Barkweave Crusher";
    private static final String lion = "Silvercoat Lion";
    private static final String goblin = "Raging Goblin";
    private static final String angel = "Serra Angel";

    @Test
    public void testRegularChooseYes() {
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        attack(1, playerA, crusher);
        setChoice(playerA, true);
        setChoice(playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2 + 2, 5);
        assertTapped(crusher, true);
        assertTapped(lion, true);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void testRegularChooseNo() {
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        attack(1, playerA, crusher);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2, 5);
        assertTapped(crusher, true);
        assertTapped(lion, false);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testSummoningSick() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.HAND, playerA, lion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);

        attack(1, playerA, crusher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2, 5);
        assertTapped(crusher, true);
        assertTapped(lion, false);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void testAttackWithBoth() {
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        attack(1, playerA, crusher);
        attack(1, playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2, 5);
        assertTapped(crusher, true);
        assertTapped(lion, true);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void testHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.HAND, playerA, goblin);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblin);

        attack(1, playerA, crusher);
        setChoice(playerA, true);
        setChoice(playerA, goblin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2 + 1, 5);
        assertTapped(crusher, true);
        assertTapped(goblin, true);
        assertLife(playerB, 20 - 2 - 1);
    }

    @Test
    public void testVigilance() {
        addCard(Zone.BATTLEFIELD, playerA, crusher);
        addCard(Zone.BATTLEFIELD, playerA, angel);

        attack(1, playerA, crusher);
        attack(1, playerA, angel);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, crusher, 2, 5);
        assertTapped(crusher, true);
        assertTapped(angel, false);
        assertLife(playerB, 20 - 2 - 4);
    }
}
