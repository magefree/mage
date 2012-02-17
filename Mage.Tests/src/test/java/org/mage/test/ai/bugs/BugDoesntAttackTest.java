package org.mage.test.ai.bugs;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * Reproduces bug when AI didn't attack with one of the creatures.
 *
 * @author ayratn
 */
public class BugDoesntAttackTest extends CardTestBase {

	@Test
    @Ignore
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
    @Ignore
	public void testAttackWithGoblinGuide() throws Exception {
		addCard(Constants.Zone.HAND, playerA, "Goblin Guide");
		addCard(Constants.Zone.HAND, playerA, "Mountain");

        playLand(playerA, "Mountain");
        castSpell(playerA, "Goblin Guide");

		execute();

		assertLife(playerB, 18);
	}
}
