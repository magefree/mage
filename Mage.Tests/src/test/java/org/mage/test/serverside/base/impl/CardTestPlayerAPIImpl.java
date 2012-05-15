package org.mage.test.serverside.base.impl;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.PhaseStep;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.game.ExileZone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.sets.Sets;
import org.junit.Assert;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.util.List;
import java.util.UUID;

/**
 * API for test initialization and asserting the test results.
 *
 * @author ayratn
 */
public abstract class CardTestPlayerAPIImpl extends MageTestPlayerBase implements CardTestAPI {

	/**
	 * Default game initialization params for red player (that plays with Mountains)
	 */
	public void useRedDefault() {
		// *** ComputerA ***
		// battlefield:ComputerA:Mountain:5
		addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
		// hand:ComputerA:Mountain:4
		addCard(Constants.Zone.HAND, playerA, "Mountain", 5);
		// library:ComputerA:clear:0
		removeAllCardsFromLibrary(playerA);
		// library:ComputerA:Mountain:10
		addCard(Constants.Zone.LIBRARY, playerA, "Mountain", 10);

		// *** ComputerB ***
		// battlefield:ComputerB:Plains:2
		addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 2);
		// hand:ComputerB:Plains:2
		addCard(Constants.Zone.HAND, playerB, "Plains", 2);
		// library:ComputerB:clear:0
		removeAllCardsFromLibrary(playerB);
		// library:ComputerB:Plains:10
		addCard(Constants.Zone.LIBRARY, playerB, "Plains", 10);
	}
	
	/**
	 * Default game initialization params for white player (that plays with Plains)
	 */
	public void useWhiteDefault() {
		// *** ComputerA ***
		addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 5);
		addCard(Constants.Zone.HAND, playerA, "Plains", 5);
		removeAllCardsFromLibrary(playerA);
		addCard(Constants.Zone.LIBRARY, playerA, "Plains", 10);

		// *** ComputerB ***
		addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 2);
		addCard(Constants.Zone.HAND, playerB, "Plains", 2);
		removeAllCardsFromLibrary(playerB);
		addCard(Constants.Zone.LIBRARY, playerB, "Plains", 10);
	}

	/**
	 * Removes all cards from player's library from the game.
	 * Usually this should be used once before initialization to form the library in certain order.
	 *
	 * @param player {@link Player} to remove all library cards from.
	 */
	public void removeAllCardsFromLibrary(Player player) {
		if (player.equals(playerA)) {
			commandsA.put(Constants.Zone.LIBRARY, "clear");
		} else if (player.equals(playerB)) {
			commandsB.put(Constants.Zone.LIBRARY, "clear");
		}
	}

	/**
	 * Removes all cards from player's hand from the game.
	 * Usually this should be used once before initialization to set the players hand.
	 *
	 * @param player {@link Player} to remove all cards from hand.
	 */
	public void removeAllCardsFromHand(Player player) {
		if (player.equals(playerA)) {
			commandsA.put(Constants.Zone.HAND, "clear");
		} else if (player.equals(playerB)) {
			commandsB.put(Constants.Zone.HAND, "clear");
		}
	}

    /**
	 * Add a card to specified zone of specified player.
	 *
	 * @param gameZone {@link Constants.Zone} to add cards to.
	 * @param player   {@link Player} to add cards for. Use either playerA or playerB.
	 * @param cardName Card name in string format.
	 */
	public void addCard(Constants.Zone gameZone, Player player, String cardName) {
		addCard(gameZone, player, cardName, 1, false);
	}

	/**
	 * Add any amount of cards to specified zone of specified player.
	 *
	 * @param gameZone {@link Constants.Zone} to add cards to.
	 * @param player   {@link Player} to add cards for. Use either playerA or playerB.
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
	 * @param player   {@link Player} to add cards for. Use either playerA or playerB.
	 * @param cardName Card name in string format.
	 * @param count    Amount of cards to be added.
	 * @param tapped   In case gameZone is Battlefield, determines whether permanent should be tapped.
	 *                 In case gameZone is other than Battlefield, {@link IllegalArgumentException} is thrown
	 */
	public void addCard(Constants.Zone gameZone, Player player, String cardName, int count, boolean tapped) {


		if (gameZone.equals(Constants.Zone.BATTLEFIELD)) {
			for (int i = 0; i < count; i++) {
				Card card = Sets.findCard(cardName, true);
                if (card == null) {
                    throw new IllegalArgumentException("[TEST] Couldn't find a card: " + cardName);
                }
				PermanentCard p = new PermanentCard(card, null);
				p.setTapped(tapped);
				if (player.equals(playerA)) {
					battlefieldCardsA.add(p);
				} else if (player.equals(playerB)) {
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
		if (player.equals(playerA)) {
			if (gameZone.equals(Constants.Zone.HAND)) {
				return handCardsA;
			} else if (gameZone.equals(Constants.Zone.GRAVEYARD)) {
				return graveyardCardsA;
			} else if (gameZone.equals(Constants.Zone.LIBRARY)) {
				return libraryCardsA;
			}
		} else if (player.equals(playerB)) {
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
		if (player.equals(playerA)) {
			commandsA.put(Constants.Zone.OUTSIDE, "life:" + String.valueOf(life));
		} else if (player.equals(playerB)) {
			commandsB.put(Constants.Zone.OUTSIDE, "life:" + String.valueOf(life));
		}
	}

	/**
	 * Define turn number to stop the game on.
	 */
	public void setStopOnTurn(int turn) {
		stopOnTurn = turn == -1 ? null : Integer.valueOf(turn);
        stopAtStep = PhaseStep.UNTAP;
	}

    /**
	 * Define turn number and step to stop the game on.
	 */
	public void setStopAt(int turn, PhaseStep step) {
		stopOnTurn = turn == -1 ? null : Integer.valueOf(turn);
        stopAtStep = step;
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
		if (player.equals(playerA)) {
			GameResult actual = CardTestAPI.GameResult.DRAW;
			if (currentGame.getWinner().equals("Player PlayerA is the winner")) {
				actual = CardTestAPI.GameResult.WON;
			} else if (currentGame.getWinner().equals("Player PlayerB is the winner")) {
				actual = CardTestAPI.GameResult.LOST;
			}
			Assert.assertEquals("Game results are not equal", result, actual);
		} else if (player.equals(playerB)) {
			GameResult actual = CardTestAPI.GameResult.DRAW;
			if (currentGame.getWinner().equals("Player PlayerB is the winner")) {
				actual = CardTestAPI.GameResult.WON;
			} else if (currentGame.getWinner().equals("Player PlayerA is the winner")) {
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
		Assert.assertEquals("Life amounts are not equal for player " + player.getName(), life, actual);
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
        int foundPower = 0;
        int foundToughness = 0;
        int found = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
			if (permanent.getName().equals(cardName) && permanent.getControllerId().equals(player.getId())) {
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
                    found++;
                    foundPower = permanent.getPower().getValue();
                    foundToughness = permanent.getToughness().getValue();
				}
			}
		}

		Assert.assertTrue("There is no such permanent under player's control, player=" + player.getName() +
				", cardName=" + cardName, count > 0);

		if (scope.equals(Filter.ComparisonScope.Any)) {
			Assert.assertTrue("There is no such creature under player's control with specified power&toughness, player=" + player.getName() +
					", cardName=" + cardName + " (found similar: " + found + ", one of them: power=" + foundPower + " toughness=" + foundToughness + ")", fit > 0);
		}
	}

    /**
     * See {@link #assertPowerToughness(mage.players.Player, String, int, int, mage.filter.Filter.ComparisonScope)}
     * 
     * @param player
     * @param cardName
     * @param power
     * @param toughness
     */
    public void assertPowerToughness(Player player, String cardName, int power, int toughness) {
        assertPowerToughness(player, cardName, power, toughness, Filter.ComparisonScope.Any);
    }

    /**
     * {@inheritDoc}
     */
    public void assertAbilities(Player player, String cardName, List<Ability> abilities)
            throws AssertionError {
        int count = 0;
        Permanent found = null;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
			if (permanent.getName().equals(cardName)) {
				found = permanent;
                count++;
			}
		}

		Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName() +
				", cardName=" + cardName, found);

        Assert.assertTrue("There is more than one such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, count == 1);

        for (Ability ability : abilities) {
            Assert.assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName() +
                    ", cardName" + cardName, found.getAbilities().contains(ability));
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

	/**
	 * Assert counter count on a permanent
	 *
	 * @param cardName  Name of the cards that should be counted.
	 * @param type      Type of the counter that should be counted.
     * @param count     Expected count.
	 */
	public void assertCounterCount(String cardName, CounterType type, int count) throws AssertionError {
        Permanent found = null;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
			if (permanent.getName().equals(cardName)) {
				found = permanent;
                break;
			}
		}

		Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);

        Assert.assertEquals("(Battlefield) Counter counts are not equal (" + cardName + ":" + type + ")", count, found.getCounters().getCount(type));
	}

    /**
	 * Assert counter count on a player
	 *
	 * @param player    The player whos counters should be counted.
	 * @param type      Type of the counter that should be counted.
     * @param count     Expected count.
	 */
	public void assertCounterCount(Player player, CounterType type, int count) throws AssertionError {
        Assert.assertEquals("(Battlefield) Counter counts are not equal (" + player.getName() + ":" + type + ")", count, player.getCounters().getCount(type));
	}
    
	/**
	 * Assert whether a permanent is a specified type or not
	 *
	 * @param cardName  Name of the permanent that should be checked.
	 * @param type      A type to test for
     * @param subType   a subtype to test for
	 */
	public void assertType(String cardName, CardType type, String subType) throws AssertionError {
        Permanent found = null;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
			if (permanent.getName().equals(cardName)) {
				found = permanent;
                break;
			}
		}

		Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);
        
		Assert.assertTrue("(Battlefield) card type not found (" + cardName + ":" + type + ")", found.getCardType().contains(type));

        Assert.assertTrue("(Battlefield) card sub-type not equal (" + cardName + ":" + subType + ")", found.getSubtype().contains(subType));
	}

    /**
	 * Assert whether a permanent is tapped or not
	 *
	 * @param cardName  Name of the permanent that should be checked.
	 * @param tapped    Whether the permanent is tapped or not
	 */
	public void assertTapped(String cardName, boolean tapped) throws AssertionError {
        Permanent found = null;
		for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
			if (permanent.getName().equals(cardName)) {
				found = permanent;
			}
		}

		Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);
        
		Assert.assertEquals("(Battlefield) Tapped state is not equal (" + cardName + ")", tapped, found.isTapped());
	}

    /**
	 * Assert card count in player's hand.
	 *
	 * @param player   {@link Player} who's hand should be counted.
	 * @param count    Expected count.
	 */
	public void assertHandCount(Player player, int count) throws AssertionError {
		int actual = currentGame.getPlayer(player.getId()).getHand().size();
		Assert.assertEquals("(Hand) Card counts are not equal ", count, actual);
	}

	/**
	 * Assert card count in player's graveyard.
	 *
	 * @param player   {@link Player} who's graveyard should be counted.
	 * @param count    Expected count.
	 */
	public void assertGraveyardCount(Player player, int count) throws AssertionError {
		int actual = currentGame.getPlayer(player.getId()).getGraveyard().size();
		Assert.assertEquals("(Graveyard) Card counts are not equal ", count, actual);
	}

    /**
	 * Assert card count in exile.
	 *
	 * @param cardName   Name of the cards that should be counted.
	 * @param count    Expected count.
	 */
	public void assertExileCount(String cardName, int count) throws AssertionError {
		int actualCount = 0;
        for (ExileZone exile: currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (card.getName().equals(cardName)) {
                    actualCount++;
                }
            }
        }

		Assert.assertEquals("(Exile) Card counts are not equal (" + cardName + ")", count, actualCount);
	}

	/**
	 * Assert card count in player's graveyard.
	 *
	 * @param player   {@link Player} who's graveyard should be counted.
	 * @param cardName Name of the cards that should be counted.
	 * @param count    Expected count.
	 */
	public void assertGraveyardCount(Player player, String cardName, int count) throws AssertionError {
		int actualCount = 0;
		for (Card card : player.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals(cardName)) {
                actualCount++;
            }
        }

		Assert.assertEquals("(Graveyard) Card counts are not equal (" + cardName + ")", count, actualCount);
	}

    public Permanent getPermanent(String cardName, UUID controller) {
		Permanent permanent0 = null;
		int count = 0;
		for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
			if (permanent.getControllerId().equals(controller)) {
				if (permanent.getName().equals(cardName)) {
					permanent0 = permanent;
					count++;
				}
			}
		}
		Assert.assertNotNull("Couldn't find a card with specified name: " + cardName, permanent0);
		Assert.assertEquals("More than one permanent was found: " + cardName + "(" + count + ")", 1, count);
		return permanent0;
	}

    public void playLand(int turnNum, PhaseStep step, TestPlayer player, String cardName) {
        player.addAction(turnNum, step, "activate:Play " + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName) {
        player.addAction(turnNum, step, "activate:Cast " + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, Player target) {
        player.addAction(turnNum, step, "activate:Cast " + cardName + ";targetPlayer=" + target.getName());
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName) {
        player.addAction(turnNum, step, "activate:Cast " + cardName + ";target=" + targetName);
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability) {
        player.addAction(turnNum, step, "activate:" + ability);
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, Player target) {
        player.addAction(turnNum, step, "activate:" + ability + ";targetPlayer=" + target.getName());
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String targetName) {
        player.addAction(turnNum, step, "activate:" + ability + ";target=" + targetName);
    }

    public void addCounters(int turnNum, PhaseStep step, TestPlayer player, String cardName, CounterType type, int count) {
        player.addAction(turnNum, step, "addCounters:" + cardName + ";" + type.getName() + ";" + count);
    }

    public void attack(int turnNum, TestPlayer player, String attacker) {
        player.addAction(turnNum, PhaseStep.DECLARE_ATTACKERS, "attack:"+attacker);
    }

    public void block(int turnNum, TestPlayer player, String blocker, String attacker) {
        player.addAction(turnNum, PhaseStep.DECLARE_BLOCKERS, "block:"+blocker+";"+attacker);
    }
    
    public void setChoice(TestPlayer player, String choice) {
        player.addChoice(choice);
    }
}
