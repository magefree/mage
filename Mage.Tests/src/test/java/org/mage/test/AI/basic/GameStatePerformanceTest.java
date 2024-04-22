package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class GameStatePerformanceTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_StackObjects_BookmarkMustCleanDataAfterTriggerResolve() {
        // memory overflow problem on too much stack objects
        // related bug: https://github.com/magefree/mage/issues/9302
        // go to "save(GameState gameState)" and enable size logs to test real usage
        final int MAX_STACK_OBJECTS = 300;

        // Whenever an artifact creature you control deals combat damage to a player,
        // you may create a 1/1 blue Thopter artifact creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Sharding Sphinx", MAX_STACK_OBJECTS);
        //
        // You can’t lose the game and your opponents can’t win the game.
        addCard(Zone.BATTLEFIELD, playerB, "Platinum Angel", 1);
        //
        // Creatures can’t block this turn.
        addCard(Zone.HAND, playerA, "Order // Chaos", 1); // instant, {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // prepare not loose
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chaos");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // attack and use triggers
        attack(1, playerA, "Sharding Sphinx");
        setChoice(playerA, "Whenever an artifact creature you control", MAX_STACK_OBJECTS - 1); // triggers order
        setChoice(playerA, true, MAX_STACK_OBJECTS); // triggers use

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thopter Token", MAX_STACK_OBJECTS);
    }
}
