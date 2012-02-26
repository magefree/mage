package org.mage.test.serverside;

import mage.Constants;
import mage.Constants.ColoredManaSymbol;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.sets.Sets;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author ayratn
 */
public class TestPlayRandomGame extends MageTestBase {

	private static List<String> colorChoices = Arrays.asList("bu", "bg", "br", "bw", "ug", "ur", "uw", "gr", "gw", "rw", "bur", "buw", "bug", "brg", "brw", "bgw", "wur", "wug", "wrg", "rgu");

    @Test
    @Ignore
    public void playGames() throws GameException, FileNotFoundException {
        for (int i = 1; i < 100; i++) {
            logger.info("Playing game: " + i);
            playOneGame();
        }
    }
    
	private void playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
		Game game = new TwoPlayerDuel(Constants.MultiplayerAttackOption.LEFT, Constants.RangeOfInfluence.ALL);

		Player computerA = createRandomPlayer("ComputerA");
		Deck deck = generateRandomDeck();

		if (deck.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
		}
		game.addPlayer(computerA, deck);
		game.loadCards(deck.getCards(), computerA.getId());

		Player computerB = createRandomPlayer("ComputerB");
		Deck deck2 = generateRandomDeck();
		if (deck2.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size());
		}
		game.addPlayer(computerB, deck2);
		game.loadCards(deck2.getCards(), computerB.getId());

		boolean testMode = true;

		long t1 = System.nanoTime();
		GameOptions options = new GameOptions();
		options.testMode = true;
		game.start(computerA.getId(), options);
		long t2 = System.nanoTime();

		logger.info("Winner: " + game.getWinner());
		logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
	}

	private Deck generateRandomDeck() {
		String selectedColors = colorChoices.get(new Random().nextInt(colorChoices.size())).toUpperCase();
		List<ColoredManaSymbol> allowedColors = new ArrayList<ColoredManaSymbol>();
		logger.info("Building deck with colors: " + selectedColors);
		for (int i = 0; i < selectedColors.length(); i++) {
			char c = selectedColors.charAt(i);
			allowedColors.add(ColoredManaSymbol.lookup(c));
		}
		List<Card> cardPool = Sets.generateRandomCardPool(45, allowedColors);
		return ComputerPlayer.buildDeck(cardPool, allowedColors);
	}
}
