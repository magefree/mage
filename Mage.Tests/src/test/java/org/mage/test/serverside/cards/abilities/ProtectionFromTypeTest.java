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
		addCard(Constants.Zone.HAND, computerA, "Trigon of Corruption");

		addCard(Constants.Zone.BATTLEFIELD, computerB, "Tel-Jilad Fallen");

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(computerA, GameResult.DRAW);
		assertLife(computerA, 20);
		assertLife(computerB, 20);

		// no one should be destroyed
		assertPermanentCount(computerB, "Tel-Jilad Fallen", 1);
	}

	@Test
	public void testNoProtection() {
		useRedDefault();
		addCard(Constants.Zone.HAND, computerA, "Trigon of Corruption");

		addCard(Constants.Zone.BATTLEFIELD, computerB, "Coral Merfolk");

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(computerA, GameResult.DRAW);
		assertLife(computerA, 20);
		assertLife(computerB, 20);

		// no one should be destroyed
		assertPermanentCount(computerB, "Coral Merfolk", 0);
	}
}
