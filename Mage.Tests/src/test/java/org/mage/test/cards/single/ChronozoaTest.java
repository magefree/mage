package org.mage.test.cards.single;

import java.util.List;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by glerman on 22/6/15.
 */
public class ChronozoaTest extends CardTestPlayerBase {

  /**
   * Test that time counters are removed before the draw phase
   */
  @Test
  public void testVanishing() {
    addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
    // Flying
    // Vanishing 3
    // When Chronozoa dies, if it had no time counters on it, put two tokens that are copies of it onto the battlefield.
    addCard(Zone.HAND, playerA, "Chronozoa");

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa");

    setStopAt(5, PhaseStep.DRAW);
    execute();

    // Make sure one time counter was removed at beginning of playerA turn num 3
    assertCounterCount("Chronozoa", CounterType.TIME, 1);
  }

  /**
   * Test that the tokens are put to battlefield if the last time counter is removed
   */
  @Test
  public void testDuplicationEffect() {
    addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
    // Flying
    // Vanishing 3
    // When Chronozoa dies, if it had no time counters on it, put two tokens that are copies of it onto the battlefield.
    addCard(Zone.HAND, playerA, "Chronozoa");

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa");

    setStopAt(9, PhaseStep.PRECOMBAT_MAIN);
    execute();

    // The original Chronozoa card should be in graveyard
    assertGraveyardCount(playerA, 1);

    final List<Permanent> creatures = currentGame.getBattlefield().getAllActivePermanents(CardType.CREATURE);
    Assert.assertEquals(2, creatures.size());

    for (final Permanent creature : creatures) {
      // Make sure the creatures are Chronozoa tokens
      Assert.assertEquals("Chronozoa", creature.getName());
      Assert.assertEquals("Chronozoa has to be a token", true, creature instanceof PermanentToken);

      // Make sure each token has 2 time counters
      final Counters counters = creature.getCounters();
      Assert.assertEquals(1, counters.size());
      for(final Counter counter : counters.values()) {
        Assert.assertEquals(CounterType.TIME.getName(), counter.getName());
        Assert.assertEquals(2, counter.getCount());
      }
    }
  }
}
