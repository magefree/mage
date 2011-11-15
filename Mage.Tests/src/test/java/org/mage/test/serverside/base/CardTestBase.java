package org.mage.test.serverside.base;

import mage.Constants;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.mage.test.serverside.base.impl.CardTestAPIImpl;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Base class for testing single cards and effects.
 *
 * @author ayratn
 */
public abstract class CardTestBase extends CardTestAPIImpl {

	protected enum AIType {
		MinimaxHybrid,
		MAD
	}

	protected enum ExpectedType {
		TURN_NUMBER,
		RESULT,
		LIFE,
		BATTLEFIELD,
		GRAVEYARD,
		UNKNOWN
	}

	/**
	 * Computer types used to test cards.
	 * By default: MAD.
	 */
	private AIType aiTypeA, aiTypeB;

	public CardTestBase() {
		aiTypeA = CardTestBase.AIType.MAD;
		aiTypeB = CardTestBase.AIType.MAD;
	}

	public CardTestBase(AIType aiTypeA, AIType aiTypeB) {
		this.aiTypeA = aiTypeA;
		this.aiTypeB = aiTypeB;
	}

	@Before
	public void reset() throws GameException, FileNotFoundException {
		if (currentGame != null) {
			logger.info("Resetting previous game and creating new one!");
			currentGame = null;
			System.gc();
		}

		Game game = new TwoPlayerDuel(Constants.MultiplayerAttackOption.LEFT, Constants.RangeOfInfluence.ALL);

		playerA = aiTypeA.equals(CardTestBase.AIType.MinimaxHybrid) ?
				createPlayer("PlayerA", "Computer - minimax hybrid") :
				createPlayer("PlayerA", "Computer - mad");
		playerA.setTestMode(true);
        logger.info("Loading deck...");
		Deck deck = Deck.load(Sets.loadDeck("RB Aggro.dck"));
        logger.info("Done!");
		if (deck.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
		}
		game.addPlayer(playerA, deck);
		game.loadCards(deck.getCards(), playerA.getId());

		playerB = aiTypeB.equals(CardTestBase.AIType.MinimaxHybrid) ?
				createPlayer("PlayerB", "Computer - minimax hybrid") :
				createPlayer("PlayerB", "Computer - mad");
		playerB.setTestMode(true);
		Deck deck2 = Deck.load(Sets.loadDeck("RB Aggro.dck"));
		if (deck2.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size());
		}
		game.addPlayer(playerB, deck2);
		game.loadCards(deck2.getCards(), playerB.getId());
		activePlayer = playerA;
		currentGame = game;

        stopOnTurn = 2;
		handCardsA.clear();
		handCardsB.clear();
		battlefieldCardsA.clear();
		battlefieldCardsB.clear();
		graveyardCardsA.clear();
		graveyardCardsB.clear();
		libraryCardsA.clear();
		libraryCardsB.clear();
		commandsA.clear();
		commandsB.clear();
	}

	public void load(String path) throws FileNotFoundException, GameException {
		load(path, AIType.MAD, AIType.MAD);
	}

	public void load(String path, AIType aiTypeA, AIType aiTypeB) throws FileNotFoundException, GameException {
		String cardPath = TESTS_PATH + path;
		File checkFile = new File(cardPath);
		if (!checkFile.exists()) {
			throw new FileNotFoundException("Couldn't find test file: " + cardPath);
		}
		if (checkFile.isDirectory()) {
			throw new FileNotFoundException("Couldn't find test file: " + cardPath + ". It is directory.");
		}

		if (currentGame != null) {
			logger.info("Resetting previous game and creating new one!");
			currentGame = null;
			System.gc();
		}

		Game game = new TwoPlayerDuel(Constants.MultiplayerAttackOption.LEFT, Constants.RangeOfInfluence.ALL);

		playerA = aiTypeA.equals(CardTestBase.AIType.MinimaxHybrid) ?
				createPlayer("ComputerA", "Computer - minimax hybrid") :
				createPlayer("ComputerA", "Computer - mad");
		playerA.setTestMode(true);

		Deck deck = Deck.load(Sets.loadDeck("RB Aggro.dck"));

		if (deck.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
		}
		game.addPlayer(playerA, deck);
		game.loadCards(deck.getCards(), playerA.getId());

		playerB = aiTypeB.equals(CardTestBase.AIType.MinimaxHybrid) ?
				createPlayer("ComputerB", "Computer - minimax hybrid") :
				createPlayer("ComputerB", "Computer - mad");
		playerB.setTestMode(true);
		Deck deck2 = Deck.load(Sets.loadDeck("RB Aggro.dck"));
		if (deck2.getCards().size() < 40) {
			throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck2.getCards().size());
		}
		game.addPlayer(playerB, deck2);
		game.loadCards(deck2.getCards(), playerB.getId());

		parseScenario(cardPath);

		activePlayer = playerA;
		currentGame = game;
	}

	/**
	 * Starts testing card by starting current game.
	 *
	 * @throws IllegalStateException In case game wasn't created previously. Use {@link #load} method to initialize the game.
	 */
	public void execute() throws IllegalStateException {
		if (currentGame == null || activePlayer == null) {
			throw new IllegalStateException("Game is not initialized. Use load method to load a test case and initialize a game.");
		}

		currentGame.cheat(playerA.getId(), commandsA);
		currentGame.cheat(playerA.getId(), libraryCardsA, handCardsA, battlefieldCardsA, graveyardCardsA);
		currentGame.cheat(playerB.getId(), commandsB);
		currentGame.cheat(playerB.getId(), libraryCardsB, handCardsB, battlefieldCardsB, graveyardCardsB);

		boolean testMode = true;
		long t1 = System.nanoTime();
		GameOptions gameOptions = new GameOptions();
		gameOptions.testMode = true;
		gameOptions.stopOnTurn = stopOnTurn;
		currentGame.start(activePlayer.getId(), gameOptions);
		long t2 = System.nanoTime();
		logger.info("Winner: " + currentGame.getWinner());
		logger.info("Time: " + (t2 - t1) / 1000000 + " ms");

		assertTheResults();
	}

	/**
	 * Assert expected and actual results.
	 */
	private void assertTheResults() {
		logger.info("Matching expected results:");
		for (String line : expectedResults) {
			boolean ok = false;
			try {
				ExpectedType type = getExpectedType(line);
				if (type.equals(CardTestBase.ExpectedType.UNKNOWN)) {
					throw new AssertionError("Unknown expected type, check the line in $expected section=" + line);
				}
				parseType(type, line);
				ok = true;
			} finally {
				logger.info("  " + line + " - " + (ok ? "OK" : "ERROR"));
			}
		}
	}

	private ExpectedType getExpectedType(String line) {
		if (line.startsWith("turn:")) {
			return CardTestBase.ExpectedType.TURN_NUMBER;
		}
		if (line.startsWith("result:")) {
			return CardTestBase.ExpectedType.RESULT;
		}
		if (line.startsWith("life:")) {
			return CardTestBase.ExpectedType.LIFE;
		}
		if (line.startsWith("battlefield:")) {
			return CardTestBase.ExpectedType.BATTLEFIELD;
		}
		if (line.startsWith("graveyard:")) {
			return CardTestBase.ExpectedType.GRAVEYARD;
		}
		return CardTestBase.ExpectedType.UNKNOWN;
	}

	private void parseType(ExpectedType type, String line) {
		if (type.equals(CardTestBase.ExpectedType.TURN_NUMBER)) {
			int turn = getIntParam(line, 1);
			Assert.assertEquals("Turn numbers are not equal", turn, currentGame.getTurnNum());
			return;
		}
		if (type.equals(CardTestBase.ExpectedType.RESULT)) {
			String expected = getStringParam(line, 1);
			String actual = "draw";
			if (currentGame.getWinner().equals("Player ComputerA is the winner")) {
				actual = "won";
			} else if (currentGame.getWinner().equals("Player ComputerB is the winner")) {
				actual = "lost";
			}
			Assert.assertEquals("Game results are not equal", expected, actual);
			return;
		}
		if (type.equals(CardTestBase.ExpectedType.LIFE)) {
			String player = getStringParam(line, 1);
			int expected = getIntParam(line, 2);
			if (player.equals("ComputerA")) {
				int actual = currentGame.getPlayer(playerA.getId()).getLife();
				Assert.assertEquals("Life amounts are not equal", expected, actual);
			} else if (player.equals("ComputerB")) {
				int actual = currentGame.getPlayer(playerB.getId()).getLife();
				Assert.assertEquals("Life amounts are not equal", expected, actual);
			} else {
				throw new IllegalArgumentException("Wrong player in 'life' line, player=" + player + ", line=" + line);
			}
			return;
		}
		if (type.equals(CardTestBase.ExpectedType.BATTLEFIELD)) {
			String playerName = getStringParam(line, 1);
			String cardName = getStringParam(line, 2);
			int expectedCount = getIntParam(line, 3);
			Player player = null;
			if (playerName.equals("ComputerA")) {
				player = currentGame.getPlayer(playerA.getId());
			} else if (playerName.equals("ComputerB")) {
				player = currentGame.getPlayer(playerB.getId());
			} else {
				throw new IllegalArgumentException("Wrong player in 'battlefield' line, player=" + player + ", line=" + line);
			}
			int actualCount = 0;
			for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
				if (permanent.getControllerId().equals(player.getId())) {
					if (permanent.getName().equals(cardName)) {
						actualCount++;
					}
				}
			}
			Assert.assertEquals("(Battlefield) Card counts are not equal (" + cardName + ")", expectedCount, actualCount);
			return;
		}
		if (type.equals(CardTestBase.ExpectedType.GRAVEYARD)) {
			String playerName = getStringParam(line, 1);
			String cardName = getStringParam(line, 2);
			int expectedCount = getIntParam(line, 3);
			Player player = null;
			if (playerName.equals("ComputerA")) {
				player = currentGame.getPlayer(playerA.getId());
			} else if (playerName.equals("ComputerB")) {
				player = currentGame.getPlayer(playerB.getId());
			} else {
				throw new IllegalArgumentException("Wrong player in 'graveyard' line, player=" + player + ", line=" + line);
			}
			int actualCount = 0;
			for (Card card : player.getGraveyard().getCards(currentGame)) {
				if (card.getName().equals(cardName)) {
					actualCount++;
				}
			}
			Assert.assertEquals("(Graveyard) Card counts are not equal (" + cardName + ")", expectedCount, actualCount);
			return;
		}
	}

	private int getIntParam(String line, int index) {
		String[] params = line.split(":");
		if (index > params.length - 1) {
			throw new IllegalArgumentException("Not correct line: " + line);
		}
		return Integer.parseInt(params[index]);
	}

	private String getStringParam(String line, int index) {
		String[] params = line.split(":");
		if (index > params.length - 1) {
			throw new IllegalArgumentException("Not correct line: " + line);
		}
		return params[index];
	}

	protected void checkPermanentPT(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope) {
		if (currentGame == null) {
			throw new IllegalStateException("Current game is null");
		}
		if (scope.equals(Filter.ComparisonScope.All)) {
			throw new UnsupportedOperationException("ComparisonScope.All is not implemented.");
		}
		int count = 0;
		int fit = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
			if (permanent.getName().equals(cardName)) {
				Assert.assertEquals("Power is not the same", power, permanent.getPower().getValue());
				Assert.assertEquals("Toughness is not the same", toughness, permanent.getToughness().getValue());
				break;
			}
		}
	}

}
