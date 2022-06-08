package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author ciaccona007
 */
public class OminousRoostTest extends CardTestPlayerBase {

	@Test
	public void testTriggerFromOtherZones() {
		skipInitShuffling();
		addCard(Zone.BATTLEFIELD, playerA, "Steam Vents", 6);

		addCard(Zone.EXILED, playerA, "Ominous Roost");
		addCard(Zone.LIBRARY, playerA, "Ominous Roost");
		addCard(Zone.GRAVEYARD, playerA, "Ominous Roost");
		addCard(Zone.HAND, playerA, "Ominous Roost", 2);

		addCard(Zone.LIBRARY, playerA, "Mountain", 10);

		addCard(Zone.GRAVEYARD, playerA, "Strike It Rich");

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ominous Roost");
		waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
		activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {2}{R}");
		checkStackSize("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

		setStopAt(1, PhaseStep.BEGIN_COMBAT);

		// Can't use strict choice checking since there are a variable number of choices
		// depending on how correctly the ability is implemented
		execute();

		assertAllCommandsUsed();
	}
}
