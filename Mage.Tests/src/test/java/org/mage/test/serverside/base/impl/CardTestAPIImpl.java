package org.mage.test.serverside.base.impl;

import mage.Constants;
import mage.cards.Card;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.sets.Sets;
import org.junit.Assert;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.MageTestBase;

import java.util.List;

/**
 * API for test initialization and asserting the test results.
 *
 * @author ayratn
 */
public abstract class CardTestAPIImpl extends MageTestBase implements CardTestAPI {

	/**
	 * Default game initialization params for red player (that plays with Mountains)
	 */
	public void useRedDefault() {
		// *** ComputerA ***
		// battlefield:ComputerA:Mountain:5
		addCard(Constants.Zone.BATTLEFIELD, computerA, "Mountain", 5);
		// hand:ComputerA:Mountain:4
		addCard(Constants.Zone.HAND, computerA, "Mountain", 5);
		// library:ComputerA:clear:0
		removeAllCardsFromLibrary(computerA);
		// library:ComputerA:Mountain:10
		addCard(Constants.Zone.LIBRARY, computerA, "Mountain", 10);

		// *** ComputerB ***
		// battlefield:ComputerB:Plains:2
		addCard(Constants.Zone.BATTLEFIELD, computerB, "Plains", 2);
		// hand:ComputerB:Plains:2
		addCard(Constants.Zone.HAND, computerB, "Plains", 2);
		// library:ComputerB:clear:0
		removeAllCardsFromLibrary(computerB);
		// library:ComputerB:Plains:10
		addCard(Constants.Zone.LIBRARY, computerB, "Plains", 10);
	}

	/**
	 * Removes all cards from player's library from the game.
	 * Usually this should be used once before initialization to form the library in certain order.
	 *
	 * @param player {@link Player} to remove all library cards from.
	 */
	public void removeAllCardsFromLibrary(Player player) {
		if (player.equals(computerA)) {
			commandsA.put(Constants.Zone.LIBRARY, "clear");
		} else if (player.equals(computerB)) {
			commandsB.put(Constants.Zone.LIBRARY, "clear");
		}
	}

	/**
	 * Add a card to specified zone of specified player.
	 *
	 * @param gameZone {@link Constants.Zone} to add cards to.
	 * @param player   {@link Player} to add cards for. Use either computerA or computerB.
	 * @param cardName Card name in string format.
	 */
	public void addCard(Constants.Zone gameZone, Player player, String cardName) {
		addCard(gameZone, player, cardName, 1, false);
	}

	/**
	 * Add any amount of cards to specified zone of specified player.
	 *
	 * @param gameZone {@link Constants.Zone} to add cards to.
	 * @param player   {@link Player} to add cards for. Use either computerA or computerB.
	 * @param cardName Card name in string format.
	 * @param count    Amount of cards to be added.
	 */
	public void addCard(Constants.Zone gameZone, Player player, String cardName, int count) {
		addCard(gameZone, player, cardName, count, false);
	}

	/**
	 * Add any amount of cards to specified zone of specified player.
	 *
	 * @param gameZone {@link Constants.Zone} to add cards to.
	 * @param player   {@link Player} to add cards for. Use either computerA or computerB.
	 * @param cardName Card name in string format.
	 * @param count    Amount of cards to be added.
	 * @param tapped   In case gameZone is Battlefield, determines whether permanent should be tapped.
	 *                 In case gameZone is other than Battlefield, {@link IllegalArgumentException} is thrown
	 */
	public void addCard(Constants.Zone gameZone, Player player, String cardName, int count, boolean tapped) {


		if (gameZone.equals(Constants.Zone.BATTLEFIELD)) {
			for (int i = 0; i < count; i++) {
				Card card = Sets.findCard(cardName, true);
				PermanentCard p = new PermanentCard(card, null);
				p.setTapped(tapped);
				if (player.equals(computerA)) {
					battlefieldCardsA.add(p);
				} else if (player.equals(computerB)) {
					battlefieldCardsB.add(p);
				}
			}
		} else {
			if (tapped) {
				throw new IllegalArgumentException("Parameter tapped=true can be used only for Zone.BATTLEFIELD.");
			}
			List<Card> cards = getCardList(gameZone, player);
			for (int i = 0; i < count; i++) {
				Card card = Sets.findCard(cardName, true);
				cards.add(card);
			}
		}
	}

	/**
	 * Returns card list containter for specified game zone and player.
	 *
	 * @param gameZone
	 * @param player
	 * @return
	 */
	private List<Card> getCardList(Constants.Zone gameZone, Player player) {
		if (player.equals(computerA)) {
			if (gameZone.equals(Constants.Zone.HAND)) {
				return handCardsA;
			} else if (gameZone.equals(Constants.Zone.GRAVEYARD)) {
				return graveyardCardsA;
			} else if (gameZone.equals(Constants.Zone.LIBRARY)) {
				return libraryCardsA;
			}
		} else if (player.equals(computerB)) {
			if (gameZone.equals(Constants.Zone.HAND)) {
				return handCardsB;
			} else if (gameZone.equals(Constants.Zone.GRAVEYARD)) {
				return graveyardCardsB;
			} else if (gameZone.equals(Constants.Zone.LIBRARY)) {
				return libraryCardsB;
			}
		}
		return null;
	}

	/**
	 * Set player's initial life count.
	 *
	 * @param player {@link Player} to set life count for.
	 * @param life   Life count to set.
	 */
	public void setLife(Player player, int life) {
		if (player.equals(computerA)) {
			commandsA.put(Constants.Zone.OUTSIDE, "life:" + String.valueOf(life));
		} else if (player.equals(computerB)) {
			commandsB.put(Constants.Zone.OUTSIDE, "life:" + String.valueOf(life));
		}
	}

	/**
	 * Define turn number to stop the game on.
	 */
	public void setStopOnTurn(int turn) {
		stopOnTurn = turn == -1 ? null : Integer.valueOf(turn);
	}

	/**
	 * Assert turn number after test execution.
	 *
	 * @param turn Expected turn number to compare with. 1-based.
	 */
	public void assertTurn(int turn) throws AssertionError {
		Assert.assertEquals("Turn numbers are not equal", turn, currentGame.getTurnNum());
	}

	/**
	 * Assert game result after test execution.
	 *
	 * @param result Expected {@link GameResult} to compare with.
	 */
	public void assertResult(Player player, GameResult result) throws AssertionError {
		if (player.equals(computerA)) {
			GameResult actual = CardTestAPI.GameResult.DRAW;
			if (currentGame.getWinner().equals("Player ComputerA is the winner")) {
				actual = CardTestAPI.GameResult.WON;
			} else if (currentGame.getWinner().equals("Player ComputerB is the winner")) {
				actual = CardTestAPI.GameResult.LOST;
			}
			Assert.assertEquals("Game results are not equal", result, actual);
		} else if (player.equals(computerB)) {
			GameResult actual = CardTestAPI.GameResult.DRAW;
			if (currentGame.getWinner().equals("Player ComputerB is the winner")) {
				actual = CardTestAPI.GameResult.WON;
			} else if (currentGame.getWinner().equals("Player ComputerA is the winner")) {
				actual = CardTestAPI.GameResult.LOST;
			}
			Assert.assertEquals("Game results are not equal", result, actual);
		}
	}

	/**
	 * Assert player's life count after test execution.
	 *
	 * @param player {@link Player} to get life for comparison.
	 * @param life   Expected player's life to compare with.
	 */
	public void assertLife(Player player, int life) throws AssertionError {
		int actual = currentGame.getPlayer(player.getId()).getLife();
		Assert.assertEquals("Life amounts are not equal", life, actual);
	}

	/**
	 * Assert creature's power and toughness by card name.
	 * <p/>
	 * Throws {@link AssertionError} in the following cases:
	 * 1. no such player
	 * 2. no such creature under player's control
	 * 3. depending on comparison scope:
	 * 3a. any: no creature under player's control with the specified p\t params
	 * 3b. all: there is at least one creature with the cardName with the different p\t params
	 *
	 * @param player    {@link Player} to get creatures for comparison.
	 * @param cardName  Card name to compare with.
	 * @param power     Expected power to compare with.
	 * @param toughness Expected toughness to compare with.
	 * @param scope     {@link mage.filter.Filter.ComparisonScope} Use ANY, if you want "at least one creature with given name should have specified p\t"
	 *                  Use ALL, if you want "all creature with gived name should have specified p\t"
	 */
	public void assertPowerToughness(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope)
			throws AssertionError {
		int count = 0;
		int fit = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
			if (permanent.getName().equals(cardName)) {
				count++;
				if (scope.equals(Filter.ComparisonScope.All)) {
					Assert.assertEquals("Power is not the same (" + power + " vs. " + permanent.getPower().getValue() + ")",
							power, permanent.getPower().getValue());
					Assert.assertEquals("Toughness is not the same (" + toughness + " vs. " + permanent.getToughness().getValue() + ")",
							toughness, permanent.getToughness().getValue());
				} else if (scope.equals(Filter.ComparisonScope.Any)) {
					if (power == permanent.getPower().getValue() && toughness == permanent.getToughness().getValue()) {
						fit++;
						break;
					}
				}
			}
		}

		Assert.assertTrue("There is no such permanent under player's control, player=" + player.getName() +
				", cardName=" + cardName, count > 0);

		if (scope.equals(Filter.ComparisonScope.Any)) {
			Assert.assertTrue("There is no such creature under player's control with specified power&toughness, player=" + player.getName() +
					", cardName=" + cardName, fit > 0);
		}
	}

	/**
	 * Assert permanent count under player's control.
	 *
	 * @param player {@link Player} which permanents should be counted.
	 * @param count  Expected count.
	 */
	public void assertPermanentCount(Player player, int count) throws AssertionError {
		int actualCount = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
			if (permanent.getControllerId().equals(player.getId())) {
				actualCount++;
			}
		}
		Assert.assertEquals("(Battlefield) Card counts are not equal ", count, actualCount);
	}

	/**
	 * Assert permanent count under player's control.
	 *
	 * @param player   {@link Player} which permanents should be counted.
	 * @param cardName Name of the cards that should be counted.
	 * @param count    Expected count.
	 */
	public void assertPermanentCount(Player player, String cardName, int count) throws AssertionError {
		int actualCount = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
			if (permanent.getControllerId().equals(player.getId())) {
				if (permanent.getName().equals(cardName)) {
					actualCount++;
				}
			}
		}
		Assert.assertEquals("(Battlefield) Card counts are not equal (" + cardName + ")", count, actualCount);
	}
}
