package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Make sure AI can simulate priority with triggers resolve
 *
 * @author JayDi85
 */
public class SimulationTriggersAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    @Ignore
    // TODO: trigger's target options supported on priority sim, but do not used for some reason
    //   see addTargetOptions, node.children, ComputerPlayer6->targets, etc
    public void test_DeepglowSkate_MustBeSimulated() {
        // make sure targets choosing on trigger use same game sims and best results

        // When Deepglow Skate enters the battlefield, double the number of each kind of counter on any number
        // of target permanents.
        addCard(Zone.HAND, playerA, "Deepglow Skate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Adversary of Tyrants", 1); // x4 loyalty
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Caller of the Pride", 1); // x4 loyalty
        //
        // This creature enters with a -1/-1 counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodied Ghost", 1); // 3/3
        //
        // Players can't activate planeswalkers' loyalty abilities.
        addCard(Zone.BATTLEFIELD, playerA, "The Immortal Sun", 1); // disable planeswalkers usage by AI

        // AI must cast boost and ignore doubling of -1/-1 counters on own creatures due bad score
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Deepglow Skate", 1);
        assertCounterCount(playerA, "Ajani, Adversary of Tyrants", CounterType.LOYALTY, 4 * 2);
        assertCounterCount(playerA, "Ajani, Caller of the Pride", CounterType.LOYALTY, 4 * 2);
        assertCounterCount(playerA, "Bloodied Ghost", CounterType.M1M1, 1); // make sure AI will not double bad counters
    }

    @Test
    public void test_DeepglowSkate_PerformanceOnTooManyChoices() {
        // https://github.com/magefree/mage/issues/9438
        int quantity = 1;
        String[] cardNames = {
                "Island", "Plains", "Swamp", "Mountain",
                "Runeclaw Bear", "Absolute Law", "Gilded Lotus", "Alpha Myr"
        };

        // When Deepglow Skate enters the battlefield, double the number of each kind of counter on any number
        // of target permanents.
        addCard(Zone.HAND, playerA, "Deepglow Skate", 1); // {4}{U}
        // Bloat the battlefield with permanents (possible targets)
        for (String card : cardNames) {
            addCard(Zone.BATTLEFIELD, playerA, card, quantity);
            addCard(Zone.BATTLEFIELD, playerB, card, quantity);
            addCard(Zone.BATTLEFIELD, playerC, card, quantity);
            addCard(Zone.BATTLEFIELD, playerD, card, quantity);
        }

        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Adversary of Tyrants", 1); // x4 loyalty
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Caller of the Pride", 1); // x4 loyalty
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Goldmane", 1); // x4 loyalty
        addCard(Zone.BATTLEFIELD, playerB, "Ajani, Inspiring Leader", 1); // x5 loyalty

        // Players can't activate planeswalkers' loyalty abilities.
        addCard(Zone.BATTLEFIELD, playerA, "The Immortal Sun", 1); // disable planeswalkers usage by AI

        // AI must cast multiple booster spells and double only own counters and only good
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Deepglow Skate", 1);
        assertCounterCount(playerA, "Ajani, Adversary of Tyrants", CounterType.LOYALTY, 4 * 2);
        assertCounterCount(playerA, "Ajani, Caller of the Pride", CounterType.LOYALTY, 4 * 2);
        assertCounterCount(playerB, "Ajani Goldmane", CounterType.LOYALTY, 4);
        assertCounterCount(playerB, "Ajani, Inspiring Leader", CounterType.LOYALTY, 5);
    }
}
