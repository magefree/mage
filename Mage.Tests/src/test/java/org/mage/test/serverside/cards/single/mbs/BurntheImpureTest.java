package org.mage.test.serverside.cards.single.mbs;

import mage.Constants;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.CardTestBase;

/**
 * First JUnit tests for Mage card.
 *
 * @ayratn
 */
public class BurntheImpureTest extends CardTestBase {

	/**
	 * Reproduces the test written in MBS/Burn the Impure.test
	 *
	 * Actually it can be tested with one java line that loads all test metadata from text file:
	 *   load("MBS/Burn the Impure.test");
	 *
	 * But it was decided to use java code only.
	 *
	 * @throws Exception
	 */
	@Test
	public void testVersusInfectCreature() throws Exception {
		// $include red.default
		useRedDefault();
		// hand:ComputerA:Burn the Impure:1
		addCard(Constants.Zone.HAND, computerA, "Burn the Impure");
		// battlefield:ComputerB:Tine Shrike:1
		addCard(Constants.Zone.BATTLEFIELD, computerB, "Tine Shrike");
		// player:ComputerB:life:3
		setLife(computerB, 3);

		setStopOnTurn(2);
		execute();

		// turn:1
		assertTurn(1);
		// result:won
		assertResult(computerA, CardTestAPI.GameResult.WON);
		// life:ComputerA:20
		assertLife(computerA, 20);
		// life:ComputerB:0
		assertLife(computerB, 0);
		// assert Tine Shrike has been killed
		assertPermanentCount(computerB, "Tine Shrike", 0);
	}

	/**
	 * load("MBS/Burn the Impure - no infect.test");
	 * @throws Exception
	 */
	@Test
	public void testVersusNonInfectCreature() throws Exception {
		useRedDefault();
		addCard(Constants.Zone.HAND, computerA, "Burn the Impure");
		addCard(Constants.Zone.BATTLEFIELD, computerB, "Runeclaw Bear", 3);
		setLife(computerB, 3);

		setStopOnTurn(2);
		execute();

		assertTurn(2);
		assertResult(computerA, CardTestAPI.GameResult.DRAW);
		assertLife(computerA, 20);
		assertLife(computerB, 3);
		assertPermanentCount(computerB, "Runeclaw Bear", 2);
		assertPowerToughness(computerB, "Runeclaw Bear", 2, 2, Filter.ComparisonScope.Any);
		assertPowerToughness(computerB, "Runeclaw Bear", 2, 2, Filter.ComparisonScope.All);
	}
}
