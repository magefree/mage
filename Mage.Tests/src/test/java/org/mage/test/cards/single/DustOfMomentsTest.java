package org.mage.test.cards.single;

import java.util.List;

import mage.cards.Card;
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
  public void test() throws Exception {
    addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.HAND, playerA, "Chronozoa");
    addCard(Zone.HAND, playerA, "Deep-Sea Kraken");
    addCard(Zone.HAND, playerA, "Dust of Moments");

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa");
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep-Sea Kraken");
    castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dust Of Moments");

    setModeChoice(playerA, "1");

    setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
    execute();

    final List<Permanent> activeCreatures = currentGame.getBattlefield().getAllActivePermanents(CardType.CREATURE);
    Assert.assertEquals(2, activeCreatures.size());

    for (final Permanent creature : activeCreatures) {
      Assert.assertEquals("Chronozoa", creature.getName());
    }
    final List<Card> exiledCards = currentGame.getExile().getAllCards(currentGame);
    Assert.assertEquals(1, exiledCards.size());

    final Card kraken = exiledCards.get(0);
    final int krakenCounters = kraken.getCounters(currentGame).getCount(CounterType.TIME);
    Assert.assertEquals(6, krakenCounters);
  }
}
