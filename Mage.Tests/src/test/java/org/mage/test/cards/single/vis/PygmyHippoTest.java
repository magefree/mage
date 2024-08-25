package org.mage.test.cards.single.vis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class PygmyHippoTest extends CardTestPlayerBase {

    /*
     * Whenever Pygmy Hippo attacks and isn’t blocked, you may have defending player activate a mana ability of
     * each land they control and lose all unspent mana. If you do, Pygmy Hippo assigns no combat damage this turn
     * and at the beginning of your next main phase this turn, you add an amount of {C} equal to the amount of mana
     * that player lost this way.
     */
    private static final String hippo = "Pygmy Hippo"; // {G}{U} 2/2
    private static final String sentinel = "Gilded Sentinel"; // {4} 3/3

    @Test
    public void testPygmyHippo() {

        addCard(Zone.BATTLEFIELD, playerA, hippo);
        addCard(Zone.HAND, playerA, sentinel);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        attack(1, playerA, hippo, playerB);
        setChoice(playerA, true); // yes to ability
        setChoice(playerB, "Swamp"); // which land to tap
        setChoice(playerB, "Swamp"); // which land to tap
        setChoice(playerB, "Swamp"); // which land to tap
        setChoice(playerB, "Swamp"); // which land to tap

        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sentinel); // with four gained mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20); // no combat damage assigned
        assertPowerToughness(playerA, hippo, 2, 2);
        assertPowerToughness(playerA, sentinel, 3, 3);
        assertTappedCount("Swamp", true, 4);
    }
}
