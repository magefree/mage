package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author klayhamn
 */
public class ThrummingStoneTest extends CardTestPlayerBase {

  @Test
  public void testApplyForNoneRippleCardsWhenSingleRipple() throws Exception {

    removeAllCardsFromLibrary(playerA);

    addCard(Zone.BATTLEFIELD, playerA, "Thrumming Stone");
    addCard(Zone.BATTLEFIELD, playerA, "Swamp");
    addCard(Zone.HAND, playerA, "Shadowborn Apostle");

    addCard(Zone.LIBRARY, playerA, "Shadowborn Apostle", 1);
    addCard(Zone.LIBRARY, playerA, "Swamp", 3);

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadowborn Apostle");
    setChoice(playerA, true);

    setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
    execute();

    assertPermanentCount(playerA, "Shadowborn Apostle", 2);

  }

  @Test
  public void testApplyForNoneRippleCardsWhenMultiRipple() throws Exception {

    removeAllCardsFromLibrary(playerA);

    addCard(Zone.BATTLEFIELD, playerA, "Thrumming Stone");
    addCard(Zone.BATTLEFIELD, playerA, "Swamp");
    addCard(Zone.HAND, playerA, "Shadowborn Apostle");

    addCard(Zone.LIBRARY, playerA, "Shadowborn Apostle");
    addCard(Zone.LIBRARY, playerA, "Swamp", 3);
    addCard(Zone.LIBRARY, playerA, "Shadowborn Apostle");

    skipInitShuffling();

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadowborn Apostle");
    setChoice(playerA, true);

    setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
    execute();

    assertPermanentCount(playerA, "Shadowborn Apostle", 3);

  }
}
