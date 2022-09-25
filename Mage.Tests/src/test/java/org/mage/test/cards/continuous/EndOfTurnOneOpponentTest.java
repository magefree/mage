package org.mage.test.cards.continuous;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 * @author JayDi85
 */
public class EndOfTurnOneOpponentTest extends CardTestPlayerBase {

    public static String cardBear2 = "Balduvian Bears"; // 2/2

    public static void prepareStepChecks(CardTestPlayerAPIImpl testEngine, String testName, int turnNum, TestPlayer player,
                                         boolean willBeBattle, PhaseStep mustExistUntilStep) {
        for (PhaseStep step : PhaseStep.values()) {
            // skip auto-steps without priority/checks
            switch (step) {
                case UNTAP:
                case DRAW:
                case UPKEEP:
                case FIRST_COMBAT_DAMAGE:
                case CLEANUP:
                    continue; // auto-skip steps without priority
                case PRECOMBAT_MAIN:
                case POSTCOMBAT_MAIN:
                case END_TURN:
                    break; // always use
                case BEGIN_COMBAT:
                case DECLARE_ATTACKERS:
                case DECLARE_BLOCKERS:
                case COMBAT_DAMAGE:
                case END_COMBAT:
                    if (!willBeBattle) continue; // combat skip
                    break;
                default:
                    throw new IllegalStateException("Unknown phase step " + step);
            }

            int permP = 2;
            int permT = 2;
            String existsStr = "must NOT EXISTS";
            if (mustExistUntilStep != null && step.getIndex() <= mustExistUntilStep.getIndex()) {
                permP++;
                permT++;
                existsStr = "must EXISTS";
            }

            testEngine.checkPT(testName + " " + existsStr + " on turn " + turnNum + " - " + step.toString() + " for " + player.getName(),
                    turnNum, step, player, cardBear2, permP, permT);
        }
    }

    @Test
    public void test_EndOfTurnSingle() {
        addCustomCardWithAbility("boost1", playerA, new SimpleStaticAbility(Zone.ALL, new BoostAllEffect(1, 1, Duration.EndOfTurn)));
        prepareStepChecks(this, "Duration.EndOfTurn effect", 1, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.EndOfTurn effect", 2, playerA, true, null);
        prepareStepChecks(this, "Duration.EndOfTurn effect", 3, playerA, true, null);
        prepareStepChecks(this, "Duration.EndOfTurn effect", 4, playerA, true, null);

        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerB, cardBear2, 1);

        attack(1, playerA, cardBear2);
        attack(2, playerB, cardBear2);
        attack(3, playerA, cardBear2);
        attack(4, playerB, cardBear2);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_UntilYourNextTurnSingle() {
        addCustomCardWithAbility("boost1", playerA, new SimpleStaticAbility(Zone.ALL, new BoostAllEffect(1, 1, Duration.UntilYourNextTurn)));
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 1, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 2, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 3, playerA, true, null);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 4, playerA, true, null);

        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerB, cardBear2, 1);

        attack(1, playerA, cardBear2);
        attack(2, playerB, cardBear2);
        attack(3, playerA, cardBear2);
        attack(4, playerB, cardBear2);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_UntilEndOfYourNextTurnSingle() {
        addCustomCardWithAbility("boost1", playerA, new SimpleStaticAbility(Zone.ALL, new BoostAllEffect(1, 1, Duration.UntilEndOfYourNextTurn)));
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 1, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 2, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 3, playerA, true, PhaseStep.END_TURN);
        prepareStepChecks(this, "Duration.UntilYourNextTurn effect", 4, playerA, true, null);

        addCard(Zone.BATTLEFIELD, playerA, cardBear2, 1);
        addCard(Zone.BATTLEFIELD, playerB, cardBear2, 1);

        attack(1, playerA, cardBear2);
        attack(2, playerB, cardBear2);
        attack(3, playerA, cardBear2);
        attack(4, playerB, cardBear2);

        setStopAt(4, PhaseStep.CLEANUP);
        setStrictChooseMode(true);
        execute();
    }
}