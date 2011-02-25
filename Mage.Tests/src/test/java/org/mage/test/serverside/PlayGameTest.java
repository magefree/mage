package org.mage.test.serverside;

import mage.Constants;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.decks.Deck;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.players.Player;
import mage.server.game.PlayerFactory;
import mage.sets.Sets;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author ayratn
 */
public class PlayGameTest extends MageTestBase {

	private List<Card> handCardsA = new ArrayList<Card>();
	private List<Card> handCardsB = new ArrayList<Card>();
	private List<Card> battlefieldCardsA = new ArrayList<Card>();
	private List<Card> battlefieldCardsB = new ArrayList<Card>();
	private List<Card> graveyardCardsA = new ArrayList<Card>();
	private List<Card> graveyardCardsB = new ArrayList<Card>();
	private List<Card> libraryCardsA = new ArrayList<Card>();
	private List<Card> libraryCardsB = new ArrayList<Card>();

	private Map<Constants.Zone, String> commandsA = new HashMap<Constants.Zone, String>();
	private Map<Constants.Zone, String> commandsB = new HashMap<Constants.Zone, String>();

	@Test
	public void playOneGame() throws GameException, FileNotFoundException, IllegalArgumentException {
		Game game = new TwoPlayerDuel(Constants.MultiplayerAttackOption.LEFT, Constants.RangeOfInfluence.ALL);

		Player computerA = createPlayer("ComputerA", "Computer - mad");
		Deck deck = Deck.load(Sets.loadDeck("RB Aggro.dck"));

		if (deck.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
		}
		game.addPlayer(computerA, deck);
		game.loadCards(deck.getCards(), computerA.getId());

		Player computerB = createPlayer("ComputerB", "Computer - minimax hybrid");
		Deck deck2 = Deck.load(Sets.loadDeck("RB Aggro.dck"));
		if (deck2.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size());
		}
		game.addPlayer(computerB, deck2);
		game.loadCards(deck2.getCards(), computerB.getId());

		/*parseScenario("scenario7.txt");
		game.cheat(computerA.getId(), commandsA);
		game.cheat(computerA.getId(), libraryCardsA, handCardsA, battlefieldCardsA, graveyardCardsA);
		game.cheat(computerB.getId(), commandsB);
		game.cheat(computerB.getId(), libraryCardsB, handCardsB, battlefieldCardsB, graveyardCardsB);
		*/

		long t1 = System.nanoTime();
		game.start(computerA.getId(), false);
		long t2 = System.nanoTime();

		logger.info("Winner: " + game.getWinner());
		logger.info("Time: " + (t2 - t1) / 1000000 + " ms");
		/*if (!game.getWinner().equals("Player ComputerA is the winner")) {
			throw new RuntimeException("Lost :(");
		}*/
	}

	private void addCard(List<Card> cards, String name, int count) {
		for (int i = 0; i < count; i++) {
			Card card = Sets.findCard(name, true);
			if (card == null) {
				throw new IllegalArgumentException("Couldn't find a card for test: " + name);
			}
			cards.add(card);
		}
	}

	private void parseScenario(String filename) throws FileNotFoundException {
		File f = new File(filename);
		Scanner scanner = new Scanner(f);
		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.startsWith("#")) continue;
				Matcher m = pattern.matcher(line);
				if (m.matches()) {

					String zone = m.group(1);
					String nickname = m.group(2);

					if (nickname.equals("ComputerA") || nickname.equals("ComputerB")) {
						List<Card> cards;
						Constants.Zone gameZone;
						if ("hand".equalsIgnoreCase(zone)) {
							gameZone = Constants.Zone.HAND;
							cards = nickname.equals("ComputerA") ? handCardsA : handCardsB;
						} else if ("battlefield".equalsIgnoreCase(zone)) {
							gameZone = Constants.Zone.BATTLEFIELD;
							cards = nickname.equals("ComputerA") ? battlefieldCardsA : battlefieldCardsB;
						} else if ("graveyard".equalsIgnoreCase(zone)) {
							gameZone = Constants.Zone.GRAVEYARD;
							cards = nickname.equals("ComputerA") ? graveyardCardsA : graveyardCardsB;
						} else if ("library".equalsIgnoreCase(zone)) {
							gameZone = Constants.Zone.LIBRARY;
							cards = nickname.equals("ComputerA") ? libraryCardsA : libraryCardsB;
						} else if ("player".equalsIgnoreCase(zone)) {
							String command = m.group(3);
							if ("life".equals(command)) {
								if (nickname.equals("ComputerA")) {
									commandsA.put(Constants.Zone.OUTSIDE, "life:" + m.group(4));
								} else {
									commandsB.put(Constants.Zone.OUTSIDE, "life:" + m.group(4));
								}
							}
							continue;
						} else {
							continue; // go parse next line
						}

						String cardName = m.group(3);
						Integer amount = Integer.parseInt(m.group(4));

						if (cardName.equals("clear")) {
							if (nickname.equals("ComputerA")) {
								commandsA.put(gameZone, "clear");
							} else {
								commandsB.put(gameZone, "clear");
							}
						} else {
							for (int i = 0; i < amount; i++) {
								Card card = Sets.findCard(cardName, true);
								if (card != null) {
									cards.add(card);
								} else {
									logger.fatal("Couldn't find a card: " + cardName);
									logger.fatal("line: " + line);
								}
							}
						}
					} else {
						logger.warn("Unknown player: " + nickname);
					}
				} else {
					logger.warn("Init string wasn't parsed: " + line);
				}
			}
		} finally {
			scanner.close();
		}
	}

	private Player createPlayer(String name, String playerType) {
		return PlayerFactory.getInstance().createPlayer(playerType, name, Constants.RangeOfInfluence.ALL);
	}
}
