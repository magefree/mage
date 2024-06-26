package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GluttonousHellkiteTest extends CardTestPlayerBase {

    @Test
    public void test_CastWithoutSac_X0() {
        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield
        // with two +1/+1 counters on it for each creature sacrificed this way.
        addCard(Zone.HAND, playerA, "Gluttonous Hellkite", 1); // {X}{X}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite");
        setChoice(playerA, "X=0");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gluttonous Hellkite", 1);
        assertCounterCount(playerA, "Gluttonous Hellkite", CounterType.P1P1, 0);
    }

    @Test
    public void test_CastWithoutSac_NothingToSac() {
        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield
        // with two +1/+1 counters on it for each creature sacrificed this way.
        addCard(Zone.HAND, playerA, "Gluttonous Hellkite", 1); // {X}{X}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 * 2); // fox X

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite");
        setChoice(playerA, "X=1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gluttonous Hellkite", 1);
        assertCounterCount(playerA, "Gluttonous Hellkite", CounterType.P1P1, 0);
    }

    @Test
    public void test_CastWithoutSac_CounterTrigger() {
        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield
        // with two +1/+1 counters on it for each creature sacrificed this way.
        addCard(Zone.HAND, playerA, "Gluttonous Hellkite", 1); // {X}{X}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 * 2); // fox X
        //
        // Ultimate Sacrifice -- {1}{U}, Sacrifice Adric: Counter target activated or triggered ability.
        addCard(Zone.BATTLEFIELD, playerB, "Adric, Mathematical Genius", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1);

        // cast with sac, but B counter it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite");
        setChoice(playerA, "X=1");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "<i>Ultimate Sacrifice</i>");
        addTarget(playerB, "stack ability (When you cast this spell, each player sacrifices"); // counter sac

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // no sac due counter
        assertPermanentCount(playerA, "Gluttonous Hellkite", 1);
        assertCounterCount(playerA, "Gluttonous Hellkite", CounterType.P1P1, 0);
    }

    @Test
    public void test_CastWithSac_LackingToSac() {
        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield
        // with two +1/+1 counters on it for each creature sacrificed this way.
        addCard(Zone.HAND, playerA, "Gluttonous Hellkite", 1); // {X}{X}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 * 2); // fox X
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite");
        setChoice(playerA, "X=2");
        setChoice(playerA, "Balduvian Bears"); // sac
        setChoice(playerB, "Augmenting Automaton"); // sac

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gluttonous Hellkite", 1);
        assertCounterCount(playerA, "Gluttonous Hellkite", CounterType.P1P1, 2 + 2); // bear from A + aug from B
    }

    @Test
    public void test_CastWithSac_SacFullAndBlink() {
        addCustomEffect_BlinkTarget(playerA);

        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield
        // with two +1/+1 counters on it for each creature sacrificed this way.
        addCard(Zone.HAND, playerA, "Gluttonous Hellkite", 1); // {X}{X}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 * 3); // fox X
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 2);

        // first cast with counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite");
        setChoice(playerA, "X=3");
        setChoice(playerB, "Balduvian Bears", 2); // sac
        setChoice(playerB, "Augmenting Automaton", 1); // sac

        // blinked without counters
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("before blink", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite", 1);
        checkPermanentCounters("before blink", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Hellkite", CounterType.P1P1, 2 + 2 + 2); // 2x bears and 1x aug from B
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Gluttonous Hellkite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gluttonous Hellkite", 1);
        assertCounterCount(playerA, "Gluttonous Hellkite", CounterType.P1P1, 0); // 0 after blink
    }
}
