package org.mage.test.serverside;

import mage.Constants;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.players.Player;
import mage.server.game.PlayerFactory;
import mage.sets.Sets;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.io.FileNotFoundException;

/**
 * @author ayratn
 */
public class PlayGameTest extends MageTestBase {

	@Test
	public void playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
		Game game = new TwoPlayerDuel(Constants.MultiplayerAttackOption.LEFT, Constants.RangeOfInfluence.ALL);

		Player player = createPlayer("computer1", "Computer - mad");
		Deck deck = Deck.load(Sets.loadDeck("RB Aggro.dck"));

		if (deck.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck side=" + deck.getCards().size());
		}
		game.addPlayer(player, deck);
		game.loadCards(deck.getCards(), player.getId());

		Player player2 = createPlayer("computer2", "Computer - mad");
		Deck deck2 = Deck.load(Sets.loadDeck("UW Control.dck"));
		if (deck2.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck side=" + deck2.getCards().size());
		}
		game.addPlayer(player2, deck2);
		game.loadCards(deck2.getCards(), player2.getId());

		long t1 = System.nanoTime();
		game.start(player.getId());
		long t2 = System.nanoTime();

		logger.info("Winner: " + game.getWinner());
		logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
	}

	private Player createPlayer(String name, String playerType) {
		return PlayerFactory.getInstance().createPlayer(playerType, name, Constants.RangeOfInfluence.ALL);
	}
}
