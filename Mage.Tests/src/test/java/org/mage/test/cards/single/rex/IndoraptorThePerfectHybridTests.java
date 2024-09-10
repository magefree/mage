package org.mage.test.cards.single.rex;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jimga150
 */
public class IndoraptorThePerfectHybridTests extends CardTestPlayerBase {
  // {1}{B/G}{R} 3/1 Legendary Creature - Dinosaur Mutant
  // Bloodthirst X (This creature enters the battlefield with X +1/+1 counters on it, where X is the damage
  // dealt to your opponents this turn.)
  // Menace
  // Enrage -- Whenever Indoraptor, the Perfect Hybrid is dealt damage, choose an opponent at random.
  // Indoraptor deals damage equal to its power to that player unless they sacrifice a nontoken creature.

  private static final String indoraptorName = "Indoraptor, the Perfect Hybrid";

  @Test
  public void testNoDamageETB() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
    addCard(Zone.HAND, playerA, indoraptorName);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, indoraptorName);

    setStopAt(1, PhaseStep.END_TURN);
    setStrictChooseMode(true);
    execute();

    assertPowerToughness(playerA, indoraptorName, 3, 1);
    assertCounterCount(playerA, indoraptorName, CounterType.P1P1, 0);
  }

  @Test
  public void testDamageETB() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
    addCard(Zone.HAND, playerA, indoraptorName);
    addCard(Zone.HAND, playerA, "Lightning Bolt");

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, playerB);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, indoraptorName);

    setStopAt(1, PhaseStep.END_TURN);
    setStrictChooseMode(true);
    execute();

    assertPowerToughness(playerA, indoraptorName, 3 + 3, 1 + 3);
    assertCounterCount(playerA, indoraptorName, CounterType.P1P1, 3);
  }

  @Test
  public void testEnrageNoCreaturesToSac() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
    addCard(Zone.HAND, playerA, indoraptorName);
    addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, playerB);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, indoraptorName, true);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, indoraptorName);

    setStopAt(1, PhaseStep.END_TURN);
    setStrictChooseMode(true);
    execute();

    assertPowerToughness(playerA, indoraptorName, 3 + 3, 1 + 3);
    assertCounterCount(playerA, indoraptorName, CounterType.P1P1, 3);

    //bolt and indoraptor damage
    assertLife(playerB, currentGame.getStartingLife() - 3 - (3 + 3));
  }

  @Test
  public void testEnrageWithCreaturesToSac() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
    addCard(Zone.HAND, playerA, indoraptorName);
    addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

    addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, playerB);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, indoraptorName, true);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, indoraptorName);

    // Sac a creature?
    setChoice(playerB, "Yes");
    // Which one?
    setChoice(playerB, "Memnite");

    setStopAt(1, PhaseStep.END_TURN);
    setStrictChooseMode(true);
    execute();

    assertPowerToughness(playerA, indoraptorName, 3 + 3, 1 + 3);
    assertCounterCount(playerA, indoraptorName, CounterType.P1P1, 3);

    //bolt damage
    assertLife(playerB, currentGame.getStartingLife() - 3);
    assertGraveyardCount(playerB, "Memnite", 1);
  }

  @Test
  public void testEnrageWithCreaturesToSacChooseNo() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
    addCard(Zone.HAND, playerA, indoraptorName);
    addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

    addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, playerB);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, indoraptorName, true);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", true);
    addTarget(playerA, indoraptorName);

    // Sac a creature?
    setChoice(playerB, "No");

    setStopAt(1, PhaseStep.END_TURN);
    setStrictChooseMode(true);
    execute();

    assertPowerToughness(playerA, indoraptorName, 3 + 3, 1 + 3);
    assertCounterCount(playerA, indoraptorName, CounterType.P1P1, 3);

    //bolt and indoraptor damage
    assertLife(playerB, currentGame.getStartingLife() - 3 - (3 + 3));
    assertPermanentCount(playerB, "Memnite", 1);
  }

}
