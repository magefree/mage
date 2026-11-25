package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.PlayableObjectStats;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.Collections;

/**
 * GUI tests: real costs to pay instead printed costs
 *
 * @author DarioBarrabinoDev
 */
public class PlayableCostsTest extends CardTestPlayerBase {

    @Test
    public void test_ReducedCost_InHand() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        runCode("real cost in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in hand", 1, gameView.getMyHand().values().size());
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{2}{U}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must have real cost to pay", Arrays.asList("{1}{U}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_IncreasedCost_InHand() {
        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        runCode("real cost in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{2}{U}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must have real cost to pay", Arrays.asList("{3}{U}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_NonModifiedCost_InHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        runCode("no real cost in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assert.assertEquals("printed cost", "{2}{U}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertTrue("must be playable", stats.getPlayableAmount() > 0);
            Assert.assertEquals("must not have real cost to pay", Collections.emptyList(), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_OnStack() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");

        runCode("real cost on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Assert.assertEquals("must have 1 card in stack", 1, gameView.getStack().values().size());
            CardView cardView = gameView.getStack().values().stream().findFirst().get();
            Assert.assertEquals("must show real cost to pay", "{1}{U}", cardView.getManaCostStr());
        });

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
