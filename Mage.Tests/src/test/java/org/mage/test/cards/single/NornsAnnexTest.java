package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by glerman on 23/6/15.
 * what is this test???
 */
@Ignore
public class NornsAnnexTest extends CardTestPlayerBase{
  @Test
  @Ignore
  public void testNornsAnnex() {
    addCard(Zone.BATTLEFIELD, playerA, "Norn's Annex");
    addCard(Zone.BATTLEFIELD, playerB, "Brindle Boar");
    attack(2, playerB, "Brindle Boar", playerA);
    setStopAt(2, PhaseStep.END_TURN);
    execute();



  }
}
