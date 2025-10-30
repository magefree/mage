package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DayOfTheMoonTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DayOfTheMoon Day of the Moon} {2}{R}
     * Enchantment — Saga
     * I, II, III — Choose a creature card name, then goad all creatures with a name chosen for this enchantment. (Until your next turn, they attack each combat if able and attack a player other than you if able.)
     */
    private static final String day = "Day of the Moon";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, day, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, day);
        setChoice(playerA, "Memnite");

        // turn 2, Memnite has to attack.

        // turn 3
        setChoice(playerA, "Centaur Courser");

        // turn 4, Memnite and Centaur Courser have to attack.

        // turn 5
        setChoice(playerA, "Elite Vanguard");

        // turn 6, Memnite, Centaur Courser and Elite Vanguard have to attack.

        // turn 8, Nothing goaded anymore

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1 * 3 - 3 * 2 - 2);
        assertGraveyardCount(playerA, day, 1);
    }
}
