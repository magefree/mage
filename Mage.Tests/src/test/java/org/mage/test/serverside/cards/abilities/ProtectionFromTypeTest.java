package org.mage.test.serverside.cards.abilities;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class ProtectionFromTypeTest extends CardTestBase {

	@Test
	public void testProtectionFromArtifacts() {
		useRedDefault();
		addCard(Constants.Zone.HAND, playerA, "Trigon of Corruption");

		addCard(Constants.Zone.BATTLEFIELD, playerB, "Tel-Jilad Fallen");

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(playerA, GameResult.DRAW);
		assertLife(playerA, 20);
		assertLife(playerB, 20);

		// no one should be destroyed
		assertPermanentCount(playerB, "Tel-Jilad Fallen", 1);
	}

	@Test
	public void testNoProtection() {
		useRedDefault();
		addCard(Constants.Zone.HAND, playerA, "Trigon of Corruption");

		addCard(Constants.Zone.BATTLEFIELD, playerB, "Coral Merfolk");

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(playerA, GameResult.DRAW);
		assertLife(playerA, 20);
		assertLife(playerB, 20);

		// no one should be destroyed
		assertPermanentCount(playerB, "Coral Merfolk", 0);
	}
}
