package org.mage.test.serverside.ai;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Should reproduce the bug with AI tapping itself with Blinding Mage.
 * But it doesn't ^(
 *
 * @ayratn
 */
public class BugTapsItselfTest extends CardTestBase {

	@Test
	public void testVersusInfectCreature() throws Exception {
		useWhiteDefault();
		addCard(Constants.Zone.BATTLEFIELD, playerA, "Blinding Mage");
		addCard(Constants.Zone.BATTLEFIELD, playerB, "Myr Sire");

		setStopOnTurn(4);
		execute();

		assertResult(playerA, GameResult.DRAW);
		Permanent permanent = getPermanent("Blinding Mage", playerA.getId());
		Assert.assertFalse("Should have been untapped", permanent.isTapped());
	}
}
