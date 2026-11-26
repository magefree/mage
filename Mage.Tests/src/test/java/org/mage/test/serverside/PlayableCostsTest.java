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

    @Test
    public void test_NoCost_FromActivatedAbility() {
        // Cycling {3}{B}{B} - an activated ability must not replace the cost to cast the card
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Decree of Pain", 1); // {6}{B}{B}

        runCode("cycling must keep the cast cost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream().findFirst().get();
            Assert.assertEquals("printed cost must be unchanged", "{6}{B}{B}", cardView.getManaCostStr());

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            Assert.assertTrue("cycling must be playable", stats.getPlayableAmount() > 0);
            Assert.assertEquals("must show the cast cost, not the cycling cost",
                    Arrays.asList("{6}{B}{B}"), stats.getPlayableCostsToPay());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_NoCost_FromAdventureSpell() {
        // adventure is another way to cast, but its cost belongs to the spell card, not to the main card
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Bonecrusher Giant", 1); // {2}{R}, adventure Stomp {1}{R}

        runCode("adventure must keep the main cost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the main cast cost only",
                    Arrays.asList("{2}{R}"), getCostsToPay(player, "Bonecrusher Giant"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_AppliesToFilteredSpellsOnly() {
        // Black creature spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Bontu's Monument", 1);
        addCard(Zone.HAND, playerA, "Ravenous Chupacabra", 1); // {2}{B}{B}, black creature
        addCard(Zone.HAND, playerA, "Bloodsoaked Champion", 1); // {B}, black creature without generic mana
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}, creature but not black
        addCard(Zone.HAND, playerA, "Doom Blade", 1); // {1}{B}, black but not a creature

        runCode("reduction must apply to black creatures only", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("black creature must be reduced",
                    Arrays.asList("{1}{B}{B}"), getCostsToPay(player, "Ravenous Chupacabra"));
            Assert.assertEquals("nothing to reduce, so the cost must stay the same",
                    Arrays.asList("{B}"), getCostsToPay(player, "Bloodsoaked Champion"));
            Assert.assertEquals("creature of another color must keep its cost",
                    Arrays.asList("{1}{G}"), getCostsToPay(player, "Grizzly Bears"));
            Assert.assertEquals("black spell that isn't a creature must keep its cost",
                    Arrays.asList("{1}{B}"), getCostsToPay(player, "Doom Blade"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_IncreasedCost_FromOpponentPermanent() {
        // Spells your opponents cast cost {2} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "God-Pharaoh's Statue", 1);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}
        addCard(Zone.HAND, playerB, "Divination", 1); // {2}{U}

        runCode("increased cost for the opponent only", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the increased cost",
                    Arrays.asList("{4}{U}"), getCostsToPay(player, "Divination"));
        });

        runCode("own spells are not increased", 1, PhaseStep.PRECOMBAT_MAIN, playerB, (info, player, game) -> {
            Assert.assertEquals("statue controller must keep the printed cost",
                    Arrays.asList("{2}{U}"), getCostsToPay(player, "Divination"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_IncreasedCost_ForMatchingSpellsOnly() {
        // Artifact and enchantment spells your opponents cast cost {2} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Aura of Silence", 1);
        addCard(Zone.HAND, playerA, "Sol Ring", 1); // {1}, artifact
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}, creature

        runCode("only artifacts and enchantments are increased", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("artifact must show the increased cost",
                    Arrays.asList("{3}"), getCostsToPay(player, "Sol Ring"));
            Assert.assertEquals("creature must keep the printed cost",
                    Arrays.asList("{1}{G}"), getCostsToPay(player, "Grizzly Bears"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedAndIncreasedCost_AtTheSameTime() {
        // White spells you cast cost {1} less to cast, blue spells you cast cost {1} less to cast,
        // spells your opponents cast cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Grand Arbiter Augustin IV", 1);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}
        addCard(Zone.HAND, playerB, "Wrath of God", 1); // {2}{W}{W}

        runCode("opponent of the arbiter pays more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the increased cost",
                    Arrays.asList("{3}{U}"), getCostsToPay(player, "Divination"));
        });

        runCode("arbiter controller pays less", 1, PhaseStep.PRECOMBAT_MAIN, playerB, (info, player, game) -> {
            Assert.assertEquals("must show the reduced cost",
                    Arrays.asList("{1}{W}{W}"), getCostsToPay(player, "Wrath of God"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_FromAffinity() {
        // Affinity for creatures (this spell costs {1} less to cast for each creature you control)
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Witherbloom, the Balancer", 1); // {6}{B}{G}
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}

        runCode("two creatures on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must count the creatures already there",
                    Arrays.asList("{4}{B}{G}"), getCostsToPay(player, "Witherbloom, the Balancer"));
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        runCode("one more creature on battlefield", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must drop by one more",
                    Arrays.asList("{3}{B}{G}"), getCostsToPay(player, "Witherbloom, the Balancer"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_FromChosenType() {
        // Spells of the chosen type you cast cost {W}{U}{B}{R}{G} less to cast.
        // This effect reduces only the amount of colored mana you pay.
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, "Morophon, the Boundless", 1); // {7}
        addCard(Zone.HAND, playerA, "Diregraf Colossus", 1); // {2}{B}, zombie
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}, not a zombie

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morophon, the Boundless");
        setChoice(playerA, "Zombie");

        runCode("only the colored mana is reduced", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("chosen type must lose its colored mana",
                    Arrays.asList("{2}"), getCostsToPay(player, "Diregraf Colossus"));
            Assert.assertEquals("another type must keep the printed cost",
                    Arrays.asList("{1}{G}"), getCostsToPay(player, "Grizzly Bears"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_DownToNothing() {
        // Creature spells of the chosen type cost {2} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.HAND, playerA, "Urza's Incubator", 1); // {3}
        addCard(Zone.HAND, playerA, "Eldrazi Mimic", 1); // {2}, fully reduced
        addCard(Zone.HAND, playerA, "Thought-Knot Seer", 1); // {3}{C}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Incubator");
        setChoice(playerA, "Eldrazi");

        runCode("reduction bigger than the generic cost", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("partial reduction must be shown",
                    Arrays.asList("{1}{C}"), getCostsToPay(player, "Thought-Knot Seer"));
            Assert.assertEquals("fully reduced spell must be shown as free",
                    Arrays.asList("{0}"), getCostsToPay(player, "Eldrazi Mimic"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_FromGrantedAffinity() {
        // Enchantment spells you cast have affinity for Auras.
        addCard(Zone.BATTLEFIELD, playerA, "Pearl-Ear, Imperial Advisor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Pacifism", 1); // {1}{W}, aura
        addCard(Zone.HAND, playerA, "Oblivion Ring", 1); // {2}{W}, enchantment

        runCode("no auras yet", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must keep the printed cost",
                    Arrays.asList("{2}{W}"), getCostsToPay(player, "Oblivion Ring"));
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pacifism", "Grizzly Bears");

        runCode("one aura on battlefield", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("affinity for auras must reduce it",
                    Arrays.asList("{1}{W}"), getCostsToPay(player, "Oblivion Ring"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_FromGrantedAffinityToInstants() {
        // Instant and sorcery spells you cast have affinity for creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Witherbloom, the Balancer", 1); // a creature itself
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 5); // six creatures in total
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}, nothing to reduce
        addCard(Zone.HAND, playerA, "Cancel", 1); // {1}{U}{U}
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}, a creature spell

        runCode("six creatures on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("colored only, nothing to reduce",
                    Arrays.asList("{R}"), getCostsToPay(player, "Shock"));
            Assert.assertEquals("generic mana must be gone",
                    Arrays.asList("{U}{U}"), getCostsToPay(player, "Cancel"));
            Assert.assertEquals("generic mana must be gone",
                    Arrays.asList("{U}"), getCostsToPay(player, "Divination"));
            Assert.assertEquals("creature spells don't get the affinity",
                    Arrays.asList("{1}{G}"), getCostsToPay(player, "Grizzly Bears"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_FromTransformedPermanent() {
        // Aang, Master of Elements (back side): spells you cast cost {W}{U}{B}{R}{G} less to cast.
        // this one converts the unused colored part into generic mana, so a cheap spell becomes free
        addCard(Zone.BATTLEFIELD, playerA, "Aang, Master of Elements", 1);
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        // "you may transform" trigger on each upkeep
        setChoice(playerA, "No");

        runCode("colored mana reduced by the back side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show nothing left to pay",
                    Arrays.asList("{0}"), getCostsToPay(player, "Divination"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedCost_SeveralReducers() {
        // Bontu's Monument: black creature spells cost {1} less
        // Urza's Incubator: creature spells of the chosen type cost {2} less
        addCard(Zone.BATTLEFIELD, playerA, "Bontu's Monument", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.HAND, playerA, "Urza's Incubator", 1); // {3}
        addCard(Zone.HAND, playerA, "Diregraf Colossus", 1); // {2}{B}, black zombie

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Incubator");
        setChoice(playerA, "Zombie");

        runCode("both reducers applied", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("reduction must stop at the colored mana",
                    Arrays.asList("{B}"), getCostsToPay(player, "Diregraf Colossus"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_IncreasedCost_SeveralIncreasers() {
        // God-Pharaoh's Statue: opponent spells cost {2} more
        // Aura of Silence: opponent artifact and enchantment spells cost {2} more
        addCard(Zone.BATTLEFIELD, playerB, "God-Pharaoh's Statue", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Aura of Silence", 1);
        addCard(Zone.HAND, playerA, "Sol Ring", 1); // {1}, both apply
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}, only the statue applies

        runCode("both increasers applied", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("artifact must pay both increases",
                    Arrays.asList("{5}"), getCostsToPay(player, "Sol Ring"));
            Assert.assertEquals("other spells must pay the statue only",
                    Arrays.asList("{4}{U}"), getCostsToPay(player, "Divination"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ReducedAndIncreasedCost_Chained() {
        // increase from the opponent and reduction from your own permanent on the same spell
        addCard(Zone.BATTLEFIELD, playerB, "God-Pharaoh's Statue", 1); // {2} more
        addCard(Zone.BATTLEFIELD, playerA, "Bontu's Monument", 1); // {1} less
        addCard(Zone.HAND, playerA, "Ravenous Chupacabra", 1); // {2}{B}{B}

        runCode("increase and reduction together", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the result of both",
                    Arrays.asList("{3}{B}{B}"), getCostsToPay(player, "Ravenous Chupacabra"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Cost_WithAlternativeCostSource() {
        // Fist of Suns offers "pay {W}{U}{B}{R}{G} rather than the mana cost", which is an option
        // chosen when casting, so the card keeps showing what the normal cast costs
        addCard(Zone.BATTLEFIELD, playerA, "Fist of Suns", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bontu's Monument", 1);
        addCard(Zone.HAND, playerA, "Diregraf Colossus", 1); // {2}{B}, black creature

        runCode("fist of suns with a reduction", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the reduced normal cost",
                    Arrays.asList("{1}{B}"), getCostsToPay(player, "Diregraf Colossus"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_IncreasedCost_UntilYourNextTurn() {
        // Tax -- Until your next turn, spells your opponents cast cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Tax Collector", 1); // {1}{W}
        addCard(Zone.HAND, playerA, "Divination", 1); // {2}{U}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Tax Collector");
        setModeChoice(playerB, "1"); // Tax

        runCode("tax is on", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the increased cost",
                    Arrays.asList("{3}{U}"), getCostsToPay(player, "Divination"));
        });

        runCode("tax is over", 4, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must be back to the printed cost",
                    Arrays.asList("{2}{U}"), getCostsToPay(player, "Divination"));
        });

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Cost_PlayableByAlternativeCostOnly() {
        // You may sacrifice two Mountains rather than pay Fireblast's mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2); // not enough mana for the printed cost
        addCard(Zone.HAND, playerA, "Fireblast", 1); // {4}{R}{R}

        runCode("playable by the alternative cost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(player);
            CardView cardView = gameView.getMyHand().values().stream()
                    .filter(c -> c.getName().equals("Fireblast")).findFirst().get();

            PlayableObjectStats stats = gameView.getCanPlayObjects().getStats(cardView.getId());
            List<String> costsToPay = stats.getPlayableCostsToPay();
            Assert.assertTrue("must be playable without the mana", stats.getPlayableAmount() > 0);
            Assert.assertTrue("must show the printed cost: " + costsToPay, costsToPay.contains("{4}{R}{R}"));
            Assert.assertTrue("must show the free alternative: " + costsToPay, costsToPay.contains("{0}"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
