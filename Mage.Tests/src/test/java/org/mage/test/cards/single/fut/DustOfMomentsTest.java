package org.mage.test.cards.single.fut;

import java.util.List;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by glerman on 29/6/15.
 */
public class DustOfMomentsTest extends CardTestPlayerBase {

  @Test
  public void testRemoveCounters() throws Exception {
    initGame();
    setModeChoice(playerA, "1"); // Chose the remove 2 time counters option
    setStopAt(3, PhaseStep.END_TURN);
    execute();

    // Chronozoa should have duplicated
    final List<Permanent> activeCreatures = currentGame.getBattlefield().getAllActivePermanents(CardType.CREATURE, currentGame);
    Assert.assertEquals(2, activeCreatures.size());

    for (final Permanent creature : activeCreatures) {
      Assert.assertEquals("Chronozoa", creature.getName());
      Assert.assertEquals(3, creature.getCounters(currentGame).getCount(CounterType.TIME));
    }
    // Check time counters on kraken
    assertCounterOnExiledCardCount("Deep-Sea Kraken", CounterType.TIME, 6);
  }

  @Test
  public void testAddCounters() throws Exception {
    initGame();
    setModeChoice(playerA, "2"); // Chose the add 2 time counters option
    setStopAt(3, PhaseStep.END_TURN);
    execute();

    assertCounterCount("Chronozoa", CounterType.TIME, 4);
    assertCounterOnExiledCardCount("Deep-Sea Kraken", CounterType.TIME, 10);
  }

  private void initGame() {
    addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.HAND, playerA, "Chronozoa");
    addCard(Zone.HAND, playerA, "Deep-Sea Kraken");
    addCard(Zone.HAND, playerA, "Dust of Moments");

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa", true);
    activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend"); // Casts Deep-Sea Kraken as Suspend
    castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dust of Moments");
  }
}
