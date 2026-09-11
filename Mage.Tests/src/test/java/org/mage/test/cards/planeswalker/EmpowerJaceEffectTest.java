package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;
import java.util.stream.Collectors;

public class EmpowerJaceEffectTest extends CardTestPlayerBase {

	@Test
	public void empowerJaceCreatesTokenAndAddsLoyaltyCounters() {
		// No Admittance deals 3 damage to any target. Empower Jace 1.
		// (Put a loyalty counter on a Jace token you control. If you don't control one,
		// first create a blue Jace planeswalker token with "-1: Surveil 1" and "-3: Draw a card.")
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
		addCard(Zone.HAND, playerA, "No Admittance");

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);

		setStopAt(1, PhaseStep.END_TURN);
		setStrictChooseMode(true);
		execute();

		assertPermanentCount(playerA, "Jace", 1);
		assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 1);
	}

	@Test
	public void empowerJaceEmpowersExistingJaceWhenCastingTwoNoAdmittances() {
		// The first No Admittance creates and empowers a Jace token. The second one
		// sees the existing Jace token and puts another loyalty counter on it.
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
		addCard(Zone.HAND, playerA, "No Admittance", 2);

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);
		waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);

		setStopAt(1, PhaseStep.END_TURN);
		setStrictChooseMode(true);
		execute();

		assertPermanentCount(playerA, "Jace", 1);
		assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 2);
	}

	@Test
	public void empowerJaceWithDoublingSeasonCreatesTwoTokensAndOnlyOneSurvives() {
		// Doubling Season: If an effect would create one or more tokens under your control,
		// it creates twice that many instead. If an effect would put one or more counters
		// on a permanent you control, it puts twice that many counters on that permanent instead.
		// The two tokens enter at 0 loyalty; only the one empowered before SBAs survives.
		addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
		addCard(Zone.HAND, playerA, "No Admittance");
		setChoice(playerA, "Jace");

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);

		setStopAt(1, PhaseStep.END_TURN);
		setStrictChooseMode(true);
		execute();

		assertPermanentCount(playerA, "Jace", 1);
		assertCounterCount(playerA, "Jace", CounterType.LOYALTY, 2);
	}

	@Test
	public void empowerJaceWithDoublingSeasonAndOathOfGideonCreatesMultipleTokensWithExtraStartingLoyalty() {
		// Oath of Gideon: Each planeswalker you control enters the battlefield with an
		// additional loyalty counter on it. Together with Doubling Season, both Jaces
		// survive with increased starting loyalty and the empower counter is doubled.
		addCard(Zone.BATTLEFIELD, playerA, "Doubling Season");
		addCard(Zone.BATTLEFIELD, playerA, "Oath of Gideon");
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
		addCard(Zone.HAND, playerA, "No Admittance");
		setChoice(playerA, "Jace");

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);

		setStopAt(1, PhaseStep.END_TURN);
		setStrictChooseMode(true);
		execute();

		assertPermanentCount(playerA, "Jace", 2);

		List<Permanent> jaces = currentGame.getBattlefield().getAllActivePermanents().stream()
				.filter(p -> p.getName().equals("Jace"))
				.collect(Collectors.toList());
		Assert.assertEquals(2, jaces.size());
		Assert.assertEquals(6, jaces.stream()
				.mapToInt(p -> p.getCounters(currentGame).getCount(CounterType.LOYALTY.getName()))
				.sum());
	}

	@Test
	public void empowerJaceFizzlesIfCraftyCutpurseRedirectsTokenCreationToOpponent() {
		// Crafty Cutpurse has flash. When it enters, each token that would be created
		// under an opponent's control this turn is created under its controller's control instead.
		// Empower Jace cannot put counters on an opponent's Jace, so that token remains at 0 loyalty.
		addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
		addCard(Zone.HAND, playerA, "No Admittance");
		addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
		addCard(Zone.HAND, playerB, "Crafty Cutpurse");

		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "No Admittance", playerB);
		// The redirected token has 0 loyalty and is removed by state-based actions.
		castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Crafty Cutpurse", true);

		setStopAt(1, PhaseStep.END_TURN);
		setStrictChooseMode(true);
		execute();

		assertPermanentCount(playerA, "Jace", 0);
		assertPermanentCount(playerB, "Jace", 0);
	}
}
