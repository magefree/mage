package org.mage.test.ai.bugs;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Reproduces bug when AI didn't attack with one of the creatures.
 *
 * @ayratn
 */
public class BugDoesntAttackWithZephyrSpriteTest extends CardTestBase {

	@Test
	public void testVersusInfectCreature() throws Exception {
		useRedDefault();
		addCard(Constants.Zone.HAND, playerA, "Zephyr Sprite");
		addCard(Constants.Zone.HAND, playerA, "Island");
		addCard(Constants.Zone.HAND, playerA, "Rupture Spire");
		setLife(playerB, 1);

		setStopOnTurn(4);
		execute();

		// life:ComputerB:0
		assertLife(playerB, 0);
		// turn:1
		assertTurn(3);
		// result:won
		assertResult(playerA, GameResult.WON);
		// life:ComputerA:20
		assertLife(playerA, 20);
	}
}
