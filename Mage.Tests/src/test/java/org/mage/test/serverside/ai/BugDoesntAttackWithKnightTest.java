package org.mage.test.serverside.ai;

import mage.Constants;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Reproduces bug when AI didn't attack with one of the creatures.
 *
 * @ayratn
 */
public class BugDoesntAttackWithKnightTest extends CardTestBase {

	@Test
	public void testVersusInfectCreature() throws Exception {
		useRedDefault();
		addCard(Constants.Zone.HAND, computerA, "Zephyr Sprite");
		addCard(Constants.Zone.HAND, computerA, "Island");
		addCard(Constants.Zone.HAND, computerA, "Rupture Spire");
		setLife(computerB, 1);

		setStopOnTurn(4);
		execute();

		// life:ComputerB:0
		assertLife(computerB, 0);
		// turn:1
		assertTurn(3);
		// result:won
		assertResult(computerA, GameResult.WON);
		// life:ComputerA:20
		assertLife(computerA, 20);

	}
}
