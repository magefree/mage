package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.o.OminousRoost Ominous Roost}
 * {2}{U}
 * Enchantment
 * When Ominous Roost enters the battlefield or whenever you cast a spell from your graveyard,
 * create a 1/1 blue Bird creature token with flying and “This creature can block only creatures with flying.”
 *
 * @author alexander-novo, Alex-Vasile
 */
public class OminousRoostTest extends CardTestPlayerBase {
	private static final String ominousRoost = "Ominous Roost";

	/**
	 * Reported bug: https://github.com/magefree/mage/issues/9078
	 * 		If Ominous Roost is in your library, it will trigger any time you cast a spell from your graveyard.
	 */
	@Test
	public void doesNotTriggerFromOtherZones() {
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

		addCard(Zone.EXILED, playerA, ominousRoost);
		addCard(Zone.LIBRARY, playerA, ominousRoost);
		addCard(Zone.GRAVEYARD, playerA, ominousRoost);
		addCard(Zone.HAND, playerA, ominousRoost);

		// Flashback {2}{R}
		// Create a treasure token
		addCard(Zone.GRAVEYARD, playerA, "Strike It Rich");

		setStrictChooseMode(true);

		activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {2}{R}");

		setStopAt(1, PhaseStep.END_TURN);
		execute();
		assertPermanentCount(playerA, ominousRoost, 0);
		assertPermanentCount(playerA, "Bird Token", 0); // None of the cards are on the field, so it should not have triggered
	}

	/**
	 * Test that it triggers on ETB
	 */
	@Test
	public void triggersOnOwnETB() {
		addCard(Zone.HAND, playerA, ominousRoost);
		addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ominousRoost);

		setStopAt(1, PhaseStep.END_TURN);
		execute();
		assertPermanentCount(playerA, ominousRoost, 1);
		assertPermanentCount(playerA, "Bird Token", 1);
	}
}
