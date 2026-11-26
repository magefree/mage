package org.mage.test.serverside;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import mage.players.PlayableObjectStats;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.List;

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
            Assert.assertEquals("real cost to pay must be same as printed", Arrays.asList("{2}{U}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AlternativeCost_InHand() {
        // Spectacle {R} (You may cast this spell for its spectacle cost rather than its mana cost
        // if an opponent lost life this turn.)
        addCard(Zone.HAND, playerA, "Light Up the Stage", 1); // {2}{R}
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // no opponent lost life yet, so only the normal cast is playable
        runCode("no spectacle yet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Light Up the Stage")).findFirst().get();
            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must have normal cost only", Arrays.asList("{2}{R}"), stats.getPlayableCostsToPay());
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);

        // opponent lost life, so spectacle cost is playable too
        runCode("spectacle enabled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Light Up the Stage")).findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{2}{R}", cardView.getManaCostStr());

            List<String> costsToPay = gameView.getCanPlayObjects().getStats(cardView.getId()).getPlayableCostsToPay();
            Assert.assertTrue("must have normal cost: " + costsToPay, costsToPay.contains("{2}{R}"));
            Assert.assertTrue("must have spectacle cost: " + costsToPay, costsToPay.contains("{R}"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_InHand_NotEnoughMana() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer", 1);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}
        // no lands, so it can't be cast at all

        runCode("real cost without mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{2}{U}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must not be playable", 0, stats.getPlayableAmount());
            Assert.assertEquals("must have real cost to pay", Arrays.asList("{1}{U}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Cost_OnOpponentTurn() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}, sorcery

        // sorcery can't be cast on opponent's turn, but it must show the real cost anyway
        runCode("real cost on opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must not be playable on opponent's turn", 0, stats.getPlayableAmount());
            Assert.assertEquals("must have real cost to pay", Arrays.asList("{1}{U}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CostChanges_WhenCostModifierEnters() {
        // Black creature spells you cast cost {1} less to cast.
        addCard(Zone.HAND, playerA, "Bontu's Monument", 1); // {3}
        addCard(Zone.HAND, playerA, "Ravenous Chupacabra", 1); // {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        runCode("before cost modifier", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Ravenous Chupacabra")).findFirst().get();

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must have printed cost", Arrays.asList("{2}{B}{B}"), stats.getPlayableCostsToPay());
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bontu's Monument");

        runCode("after cost modifier", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Ravenous Chupacabra")).findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{2}{B}{B}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertEquals("must not be playable with one swamp left", 0, stats.getPlayableAmount());
            Assert.assertEquals("must have reduced cost", Arrays.asList("{1}{B}{B}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_AlternativeCost_RevertsOnNextTurn() {
        // Spectacle {R} (You may cast this spell for its spectacle cost rather than its mana cost
        // if an opponent lost life this turn.)
        addCard(Zone.HAND, playerA, "Light Up the Stage", 1); // {2}{R}
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);

        runCode("spectacle enabled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            List<String> costsToPay = getCostsToPay(player, "Light Up the Stage");
            Assert.assertTrue("must have spectacle cost: " + costsToPay, costsToPay.contains("{R}"));
        });

        // no opponent lost life on the new turn, so spectacle cost is gone again
        runCode("spectacle gone", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<String> costsToPay = getCostsToPay(player, "Light Up the Stage");
            Assert.assertEquals("must have normal cost only", Arrays.asList("{2}{R}"), costsToPay);
        });

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_NoCost_ForOpponentHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        runCode("no costs from opponent's hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            Card opponentCard = game.getPlayer(playerB.getId()).getHand().getCards(game).stream().findFirst().get();
            Assert.assertEquals("test must use opponent's card", "Lightning Bolt", opponentCard.getName());
            Assert.assertFalse("opponent's hand card must not be in GUI data at all",
                    gameView.getCanPlayObjects().hasObject(opponentCard.getId()));

            // direct hand zone request must be safe too (Sen Triplets like code)
            Assert.assertFalse("opponent's hand card must not be in hand zone data",
                    player.getPlayableObjects(game, Zone.HAND).hasObject(opponentCard.getId()));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    private List<String> getCostsToPay(Player player, String cardName) {
        GameView gameView = getGameView(player);
        CardView cardView = gameView.getMyHand().values().stream()
                .filter(c -> c.getName().equals(cardName)).findFirst().get();
        return gameView.getCanPlayObjects().getStats(cardView.getId()).getPlayableCostsToPay();
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
