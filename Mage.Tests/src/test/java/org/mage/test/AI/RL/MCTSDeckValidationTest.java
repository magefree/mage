package org.mage.test.AI.RL;

import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.cards.repository.RepositoryUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mage.magezero.Config;
import org.mage.magezero.ParallelDataGenerator;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MCTSDeckValidationTest {
    //config
    private static final int GAMES_PER_TEST = 16;
    private static final int MAX_TURNS = 50;
    private static final String OPPONENT_DECK = "decks/Standard-MonoB.dck";
    /*private static final String[] DECK_POOL = {
            "decks/Standard-MonoB.dck",
            "decks/Standard-MonoG.dck",
            "decks/Standard-MonoR.dck",
            "decks/Standard-MonoU.dck",
            "decks/Standard-MonoW.dck",
            "decks/MTGA_MonoB.dck",
            "decks/MTGA_MonoG.dck",
            "decks/MTGA_MonoR.dck",
            "decks/MTGA_MonoU.dck",
            "decks/MTGA_MonoW.dck",
            "decks/BGRoots.dck",
            "decks/BWBats.dck",
            "decks/EsperTempo.dck",
            "decks/GBLegends.dck",
            "decks/MonoUArtifacts.dck",
            "decks/RB Aggro.dck",
            "decks/UBArtifacts.dck",
            "decks/simic-eldrazi.dck",
            "decks/HighNoonControl.dck",
            "decks/EVG_Elves.dck",
            "decks/EVG_Goblins.dck",
            "decks/Mind(MindvsMight).dck",
            "decks/WillS_Tempo.dck",
            "decks/UW Control.dck"
    };*/
    private static final String[] DECK_POOL = {
            "decks/Standard16-UB.dck",
            "decks/Standard-MonoB.dck",
            "decks/Standard-MonoG.dck",
            "decks/Standard-MonoR.dck",
            "decks/Standard-MonoU.dck",
            "decks/Standard-MonoW.dck",
            "decks/Standard16-5C.dck",
            "decks/Standard16-BW.dck",
            "decks/Standard16-GB.dck",
            "decks/Standard16-GW.dck",
            "decks/Standard16-RB.dck",
            "decks/Standard16-RG.dck",
            "decks/Standard16-RW.dck",
            "decks/Standard16-UG.dck",
            "decks/Standard16-UR.dck",
            "decks/Standard16-UW.dck"
    };
    static Stream<String> deckPool() {
        return Arrays.stream(DECK_POOL);
    }

    @BeforeAll
    static void setup() {
        Config.loadDefault();
        // Initialize card database
        RepositoryUtil.bootstrapLocalDb();
        CardScanner.scan();
    }
    @BeforeEach
    void resetConfig() {
        Config.loadDefault();
    }

    @ParameterizedTest
    @MethodSource("deckPool")
    public void testDeckAgainstMCTS(String testDeck) {
        Config.INSTANCE.playerA.deckPath = testDeck;
        Config.INSTANCE.playerB.deckPath = OPPONENT_DECK;
        Config.INSTANCE.playerA.mcts.offlineMode = true;
        Config.INSTANCE.playerB.mcts.offlineMode = true;
        Config.INSTANCE.playerA.type = "mcts";
        Config.INSTANCE.playerB.type = "mcts";
        Config.INSTANCE.training.games = GAMES_PER_TEST;
        Config.INSTANCE.training.maxTurns = MAX_TURNS;
        Config.INSTANCE.training.threads = 4;
        //Config.INSTANCE.playerA.gameplay.manualTap = true;

        try {
            ParallelDataGenerator generator = new ParallelDataGenerator();
            generator.generateData();
            int gamesPlayed = generator.gameCount.get();
            assertEquals(GAMES_PER_TEST, gamesPlayed, "Should complete all games");
            assertTrue(gamesPlayed > 0, "Should play at least one game");
        } catch (Exception e) {
            fail("Deck " + testDeck + " caused crash: " + e.getMessage(), e);
        }
    }
    @ParameterizedTest
    @MethodSource("deckPool")
    public void testDeckAgainstMinimax(String testDeck) {
        Config.INSTANCE.playerA.deckPath = testDeck;
        Config.INSTANCE.playerB.deckPath = OPPONENT_DECK;
        Config.INSTANCE.playerA.mcts.offlineMode = true;
        Config.INSTANCE.playerA.type = "mcts";
        Config.INSTANCE.playerB.type = "minimax";
        Config.INSTANCE.training.games = GAMES_PER_TEST;
        Config.INSTANCE.training.maxTurns = MAX_TURNS;
        Config.INSTANCE.training.threads = 4;
        //Config.INSTANCE.playerA.gameplay.manualTap = false;
        try {
            ParallelDataGenerator generator = new ParallelDataGenerator();
            generator.generateData();
            int gamesPlayed = generator.gameCount.get();
            assertEquals(GAMES_PER_TEST, gamesPlayed, "Should complete all games");
            assertTrue(gamesPlayed > 0, "Should play at least one game");
        } catch (Exception e) {
            fail("Deck " + testDeck + " caused crash: " + e.getMessage(), e);
        }
    }
    @ParameterizedTest
    @MethodSource("deckPool")
    public void testMinimaxAgainstDeck(String testDeck) {
        Config.INSTANCE.playerA.deckPath = OPPONENT_DECK;
        Config.INSTANCE.playerB.deckPath = testDeck;
        Config.INSTANCE.playerA.mcts.offlineMode = true;
        Config.INSTANCE.playerA.type = "mcts";
        Config.INSTANCE.playerB.type = "minimax";
        Config.INSTANCE.training.games = GAMES_PER_TEST;
        Config.INSTANCE.training.maxTurns = MAX_TURNS;
        Config.INSTANCE.training.threads = 4;
        try {
            ParallelDataGenerator generator = new ParallelDataGenerator();
            generator.generateData();
            int gamesPlayed = generator.gameCount.get();
            assertEquals(GAMES_PER_TEST, gamesPlayed, "Should complete all games");
            assertTrue(gamesPlayed > 0, "Should play at least one game");
        } catch (Exception e) {
            fail("Deck " + testDeck + " caused crash: " + e.getMessage(), e);
        }
    }
    @Test
    public void runSeedTest() {
        long seed = 2592031767805947275L;
        seed = -609776460546106132L;
        Config.INSTANCE.playerA.deckPath = "decks/MTGA_MonoU.dck";
        Config.INSTANCE.playerB.deckPath = "decks/Standard-MonoB.dck";
        try {
            ParallelDataGenerator generator = new ParallelDataGenerator();
            generator.test_single_game(seed);
            int gamesPlayed = generator.gameCount.get();
            assertEquals(1, gamesPlayed, "Should complete all games");
        } catch (Exception e) {
            fail("crash: " + e.getMessage(), e);
        }
    }
    @AfterAll
    static void cleanup() {
        //CardRepository.instance.closeDB(true);
    }
}
