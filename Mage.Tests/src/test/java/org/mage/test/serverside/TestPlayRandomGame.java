package org.mage.test.serverside;

import mage.cards.decks.Deck;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import mage.players.Player;
import mage.util.RandomUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;
import org.mage.test.utils.DeckTestUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author ayratn
 */
public class TestPlayRandomGame extends MageTestBase {

    private static final List<String> colorChoices = new ArrayList<>(Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu"));

    @Test
    @Ignore
    public void playGames() throws GameException, FileNotFoundException {
        for (int i = 1; i < 100; i++) {
            logger.info("Playing game: " + i);
            playOneGame();
        }
    }

    private void playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);

        Player computerA = createRandomPlayer("ComputerA");
        String deckAColors = colorChoices.get(RandomUtil.nextInt(colorChoices.size())).toUpperCase(Locale.ENGLISH);
        Deck deckA = DeckTestUtils.buildRandomDeck(deckAColors, false);

        game.addPlayer(computerA, deckA);
        game.loadCards(deckA.getCards(), computerA.getId());

        Player computerB = createRandomPlayer("ComputerB");
        String deckBColors = colorChoices.get(RandomUtil.nextInt(colorChoices.size())).toUpperCase(Locale.ENGLISH);
        Deck deckB = DeckTestUtils.buildRandomDeck(deckBColors, false);
        game.addPlayer(computerB, deckB);
        game.loadCards(deckB.getCards(), computerB.getId());

        long t1 = System.nanoTime();
        GameOptions options = new GameOptions();
        options.testMode = true;
        game.setGameOptions(options);
        game.start(computerA.getId());
        long t2 = System.nanoTime();

        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
    }
}
