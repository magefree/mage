package org.mage.test.serverside.cards.abilities;

import mage.Constants;
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
		addCard(Constants.Zone.BATTLEFIELD, playerA, "Royal Assassin");

		// tapped White Knight with Protection from Black
		addCard(Constants.Zone.BATTLEFIELD, playerB, "White Knight", 1, true);
		addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, true);
		// one not tapped White Knight to prevent AI from attacking
		addCard(Constants.Zone.BATTLEFIELD, playerB, "White Knight", 1, false);

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(playerA, CardTestAPI.GameResult.DRAW);
		assertLife(playerA, 20);
		assertLife(playerB, 20);

		// no one should be destroyed
		assertPermanentCount(playerB, "White Knight", 2);
		assertPermanentCount(playerB, "Runeclaw Bear", 0);
	}

	@Test
	public void testAgainstAbilityInTheStackNoProtection() {
		useRedDefault();
		addCard(Constants.Zone.BATTLEFIELD, playerA, "Royal Assassin");

		addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, true);
		addCard(Constants.Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1, false);

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(playerA, CardTestAPI.GameResult.DRAW);
		assertLife(playerA, 20);
		assertLife(playerB, 20);

		// One should have beendestroyed by Royal Assassin
		assertPermanentCount(playerB, "Runeclaw Bear", 1);
	}
}
