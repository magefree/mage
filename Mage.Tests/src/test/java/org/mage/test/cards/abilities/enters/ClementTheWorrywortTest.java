package org.mage.test.cards.abilities.enters;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 *
 * @author earchip94
 */
public class ClementTheWorrywortTest extends CardTestPlayerBase{

   private final String frog = "Clement, the Worrywort";
   
   @Test
   public void castBounce() {
      addCard(Zone.HAND, playerA, frog);
      addCard(Zone.BATTLEFIELD, playerA, "Spore Frog");
      addCard(Zone.BATTLEFIELD, playerA, "Forest");
      addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

      castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, frog);
      addTarget(playerA, "Spore Frog");

      setStrictChooseMode(true);
      setStopAt(1, PhaseStep.BEGIN_COMBAT);
      execute();

      assertPermanentCount(playerA, 4);
      assertHandCount(playerA, 1);
   }

   @Test
   public void castNoTarget() {
      addCard(Zone.HAND, playerA, frog);
      addCard(Zone.BATTLEFIELD, playerA, "Haze Frog");
      addCard(Zone.BATTLEFIELD, playerA, "Forest");
      addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

      castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, frog);
      setStrictChooseMode(false);

      setStopAt(1, PhaseStep.BEGIN_COMBAT);
      execute();

      assertPermanentCount(playerA, 5);
      assertHandCount(playerA, 0);
   }

   @Test
   public void castOther() {
      addCard(Zone.HAND, playerA, "Haze Frog");
      addCard(Zone.BATTLEFIELD, playerA, frog);
      addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

      castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Haze Frog");
      setChoice(playerA, "Whenever"); // order triggers
      addTarget(playerA, frog);

      setStrictChooseMode(true);
      setStopAt(1, PhaseStep.BEGIN_COMBAT);
      execute();

      assertPermanentCount(playerA, 6);
      assertHandCount(playerA, 1);
   }

   @Test
   public void castOtherNoTarget() {
      addCard(Zone.HAND, playerA, "Spore Frog");
      addCard(Zone.BATTLEFIELD, playerA, frog);
      addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

      castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spore Frog");
      setStrictChooseMode(false);

      setStopAt(1, PhaseStep.BEGIN_COMBAT);
      execute();

      assertPermanentCount(playerA, 7);
      assertHandCount(playerA, 0);
   }

   @Test
   public void otherPlayerCast() {
      addCard(Zone.BATTLEFIELD, playerA, frog);

      addCard(Zone.HAND, playerB, "Llanowar Elves");
      addCard(Zone.BATTLEFIELD, playerB, "Forest");

      castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Llanowar Elves");
      // No choice required

      setStrictChooseMode(true);
      setStopAt(2, PhaseStep.BEGIN_COMBAT);
      execute();

      assertPermanentCount(playerA, 1);
      assertPermanentCount(playerB, 2);
      assertChoicesCount(playerA, 0);
      assertChoicesCount(playerB, 0);
   }
}
