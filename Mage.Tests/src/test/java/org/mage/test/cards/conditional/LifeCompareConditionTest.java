package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class LifeCompareConditionTest extends CardTestPlayerBase {

    @Test
    public void test10OrLess() {
        String lacerator = "Vampire Lacerator"; // B 2/2
        // At the beginning of your upkeep, you lose 1 life unless an opponent has 10 or less life.
        String cullblade = "Ruthless Cullblade"; // 1B 2/1
        // Ruthless Cullblade gets +2/+1 as long as an opponent has 10 or less life.
        String vengeance = "Sorin's Vengeance"; // 4BBB Sorcery
        // Sorin’s Vengeance deals 10 damage to target player or planeswalker and you gain 10 life.

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.HAND, playerA, lacerator, 2);
        addCard(Zone.BATTLEFIELD, playerA, cullblade, 1);
        addCard(Zone.HAND, playerA, vengeance);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lacerator);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lacerator);

        checkPT("opp at 20", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, cullblade, 2, 1);

        setChoice(playerA, "At the beginning"); // turn 3 upkeep, order triggers

        checkPT("opp at 20", 3, PhaseStep.PRECOMBAT_MAIN, playerA, cullblade, 2, 1);
        checkLife("2 life lost", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 18);
        checkLife("no damage dealt", 3, PhaseStep.PRECOMBAT_MAIN, playerB, 20);

        setChoice(playerA, "At the beginning"); // turn 5 upkeep, order triggers

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, vengeance, playerB);

        checkPT("opp at 10", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, cullblade, 4, 2);
        checkLife("4 life lost, 10 gained", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, 26);
        checkLife("10 damage dealt", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, 10);

        setChoice(playerA, "At the beginning"); // turn 7 upkeep, order triggers but no life lost

        setStopAt(7, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 26);
        assertLife(playerB, 10);
    }

    @Test
    public void test25orMore() {
        String angel = "Angel of Vitality"; // 2W 2/2 flying
        // If you would gain life, you gain that much life plus 1 instead.
        // Angel of Vitality gets +2/+2 as long as you have 25 or more life.

        String missionary = "Lone Missionary"; // 1W 2/1
        // When Lone Missionary enters the battlefield, you gain 4 life.

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, missionary);
        addCard(Zone.BATTLEFIELD, playerA, angel);

        checkPT("20 life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, angel, 2, 2);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, missionary);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 25);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, angel, 4, 4);
        assertPowerToughness(playerA, missionary, 2, 1);
    }
}
