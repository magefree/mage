package org.mage.test.ai.bugs;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Reproduces bug when AI didn't attack with one of the creatures.
 *
 * @ayratn
 */
public class BugDoesntAttackTest extends CardTestBase {

	@Test
	public void testAttackWithZephyrSprite() throws Exception {
		addCard(Constants.Zone.HAND, playerA, "Zephyr Sprite");
		addCard(Constants.Zone.HAND, playerA, "Island");
		addCard(Constants.Zone.HAND, playerA, "Rupture Spire");
		setLife(playerB, 1);
        setStopOnTurn(4);

		execute();

		assertLife(playerB, 0);
	}

    @Test
	public void testAttackWithGoblinGuide() throws Exception {
		addCard(Constants.Zone.HAND, playerA, "Goblin Guide");
		addCard(Constants.Zone.HAND, playerA, "Mountain");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Goblin Guide");

		execute();

		assertLife(playerB, 18);
	}
}
