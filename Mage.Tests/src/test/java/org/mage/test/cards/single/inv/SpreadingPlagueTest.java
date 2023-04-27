package org.mage.test.cards.single.inv;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class SpreadingPlagueTest extends CardTestPlayerBase {

    @Test
    public void test_ColorlessCreatureMustNotShareColorWithColoredCreatures() {
        // bug: https://github.com/magefree/mage/issues/6984
        // Golos got destroyed by spreading plague when a player played a red creature, and all colored creatures got destroyed when golos got played.

        addCard(Zone.HAND, playerA, "Golos, Tireless Pilgrim", 1); // {5}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // Whenever a creature enters the battlefield, destroy all other creatures that share a color with it. They can’t be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "Spreading Plague", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Archaeologist", 1); // red creature
        addCard(Zone.BATTLEFIELD, playerA, "Blisterpod", 1); // devoided creature (colorless)
        //
        addCard(Zone.HAND, playerA, "Goblin Arsonist", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Archaeologist", 1);
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blisterpod", 1);

        // play colorless creature and do not destroy anything
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golos, Tireless Pilgrim");
        setChoice(playerA, "When {this} enters the battlefield"); // two replacement effects
        setChoice(playerA, false); // no search
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after colorless play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golos, Tireless Pilgrim", 1);
        checkPermanentCount("after colorless play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Archaeologist", 1);
        checkPermanentCount("after colorless play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blisterpod", 1);
        checkPermanentCount("after colorless play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Arsonist", 0);

        // play red creature and destroy other red
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Arsonist");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after red play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Golos, Tireless Pilgrim", 1);
        checkPermanentCount("after red play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Archaeologist", 0);
        checkPermanentCount("after red play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blisterpod", 1);
        checkPermanentCount("after red play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Arsonist", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
