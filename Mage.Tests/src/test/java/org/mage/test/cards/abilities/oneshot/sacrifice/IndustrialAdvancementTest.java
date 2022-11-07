package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.FileNotFoundException;

public class IndustrialAdvancementTest extends CardTestPlayerBase {

  private static final String CARD_INDUSTRIAL_ADVANCEMENT = "Industrial Advancement";
  private static final String CARD_RAMPANT_REJUVENATOR = "Rampant Rejuvenator";

  @Test
  public void sacrificeTriggersDiesAbilities() {
    // {3}{G}
    int rampantCost = 4;
    // should have 2 +1/+1 counters
    int rampantDiesDraw = 2;
    int draw = 1;

    // At the beginning of your end step, you may sacrifice a creature. If you do, look at the top X cards of your
    // library, where X is that creature's mana value. You may put a creature card from among them onto the battlefield.
    // Put the rest on the bottom of your library in a random order.
    addCard(Zone.BATTLEFIELD, playerA, CARD_INDUSTRIAL_ADVANCEMENT);
    // Rampant Rejuvenator enters the battlefield with two +1/+1 counters on it.
    // When Rampant Rejuvenator dies, search your library for up to X basic land cards, where X is Rampant
    // Rejuvenator's power, put them onto the battlefield, then shuffle.
    addCard(Zone.HAND, playerA, CARD_RAMPANT_REJUVENATOR);
    addCard(Zone.BATTLEFIELD, playerA, "Forest", rampantCost);

    setStopAt(1, PhaseStep.DRAW);
    execute();
    int librarySize = getLibraryCards(playerA).size();

    // Trigger adding counters
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CARD_RAMPANT_REJUVENATOR);
    setChoice(playerA, CARD_RAMPANT_REJUVENATOR);
    setStopAt(1, PhaseStep.CLEANUP);
    execute();

    assertLibraryCount(playerA, librarySize-rampantCost-rampantDiesDraw-draw);
  }
}
