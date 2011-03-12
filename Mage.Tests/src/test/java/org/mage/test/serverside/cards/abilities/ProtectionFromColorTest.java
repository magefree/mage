package org.mage.test.serverside.cards.abilities;

import mage.Constants;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class ProtectionFromColorTest extends CardTestBase {

	@Test
	public void testAgainstAbilityInTheStack() {
		useRedDefault();
		addCard(Constants.Zone.BATTLEFIELD, computerA, "Royal Assassin");

		// tapped White Knight with Protection from Black
		addCard(Constants.Zone.BATTLEFIELD, computerB, "White Knight", 1, true);
		// one not tapped White Knight to prevent AI from attacking
		addCard(Constants.Zone.BATTLEFIELD, computerB, "White Knight", 1, false);

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(computerA, CardTestAPI.GameResult.DRAW);
		assertLife(computerA, 20);
		assertLife(computerB, 20);

		// no one should be destroyed
		assertPermanentCount(computerB, "White Knight", 2);
	}

	@Test
	public void testAgainstAbilityInTheStackNoProtection() {
		useRedDefault();
		addCard(Constants.Zone.BATTLEFIELD, computerA, "Royal Assassin");

		addCard(Constants.Zone.BATTLEFIELD, computerB, "Runeclaw Bear", 1, true);
		addCard(Constants.Zone.BATTLEFIELD, computerB, "Runeclaw Bear", 1, false);

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(computerA, CardTestAPI.GameResult.DRAW);
		assertLife(computerA, 20);
		assertLife(computerB, 20);

		// One should have beendestroyed by Royal Assassin
		assertPermanentCount(computerB, "Runeclaw Bear", 1);
	}
}
