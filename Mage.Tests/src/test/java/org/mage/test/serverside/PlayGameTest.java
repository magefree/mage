package org.mage.test.serverside;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.constants.ColoredManaSymbol;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.players.PlayerType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

/**
 * @author ayratn
 */
public class PlayGameTest extends MageTestBase {

    private final static List<String> colorChoices = Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu");

    @Ignore
    @Test
    public void playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ALL, 0, 20);

        Player computerA = createPlayer("ComputerA", PlayerType.COMPUTER_MINIMAX_HYBRID);
//        Player playerA = createPlayer("ComputerA", "Computer - mad");
//        Deck deck = Deck.load(Sets.loadDeck("RB Aggro.dck"));
        Deck deck = generateRandomDeck();

        if (deck.getCards().size() < 40) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
        }
        game.addPlayer(computerA, deck);
        game.loadCards(deck.getCards(), computerA.getId());

        Player computerB = createPlayer("ComputerB", PlayerType.COMPUTER_MINIMAX_HYBRID);
//        Player playerB = createPlayer("ComputerB", "Computer - mad");
//        Deck deck2 = Deck.load(Sets.loadDeck("RB Aggro.dck"));
        Deck deck2 = generateRandomDeck();
        if (deck2.getCards().size() < 40) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size());
        }
        game.addPlayer(computerB, deck2);
        game.loadCards(deck2.getCards(), computerB.getId());

//        parseScenario("scenario1.txt");
//        game.cheat(playerA.getId(), commandsA);
//        game.cheat(playerA.getId(), libraryCardsA, handCardsA, battlefieldCardsA, graveyardCardsA);
//        game.cheat(playerB.getId(), commandsB);
//        game.cheat(playerB.getId(), libraryCardsB, handCardsB, battlefieldCardsB, graveyardCardsB);
        //boolean testMode = false;
        boolean testMode = true;

        long t1 = System.nanoTime();
        GameOptions options = new GameOptions();
        options.testMode = true;
        game.setGameOptions(options);
        game.start(computerA.getId());
        long t2 = System.nanoTime();

        logger.info("Winner: " + game.getWinner());
        logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
        /*if (!game.getWinner().equals("Player ComputerA is the winner")) {
            throw new RuntimeException("Lost :(");
        }*/
    }

    private Deck generateRandomDeck() {
        String selectedColors = colorChoices.get(new Random().nextInt(colorChoices.size())).toUpperCase(Locale.ENGLISH);
        List<ColoredManaSymbol> allowedColors = new ArrayList<>();
        logger.info("Building deck with colors: " + selectedColors);
        for (int i = 0; i < selectedColors.length(); i++) {
            char c = selectedColors.charAt(i);
            allowedColors.add(ColoredManaSymbol.lookup(c));
        }
        List<Card> cardPool = Sets.generateRandomCardPool(45, allowedColors);
        return ComputerPlayer.buildDeck(cardPool, allowedColors);
    }
}
