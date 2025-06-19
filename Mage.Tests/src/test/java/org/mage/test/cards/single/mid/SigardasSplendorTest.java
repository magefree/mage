package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SigardasSplendor Sigarda's Splendor}
 * {2}{W}{W}
 * Enchantment
 * As Sigarda’s Splendor enters the battlefield, note your life total.
 * At the beginning of your upkeep, draw a card if your life total is greater than or equal
 * to the last noted life total for Sigarda’s Splendor. Then note your life total.
 * Whenever you cast a white spell, you gain 1 life.
 *
 * @author notgreat
 */
public class SigardasSplendorTest extends CardTestPlayerBase {
    private static final String sigardasSplendor = "Sigarda's Splendor";

    //Original bug:  [BUG] Sigarda's Splendor always draws you a card the turn after it came into play #9872
    //Test added while changing abilities' zcc while entering the battlefield

    @Test
    public void sigardasSplendorTestBasic() {
        addCard(Zone.HAND, playerA, sigardasSplendor, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        setStrictChooseMode(true);

        checkHandCount("Initial hand size", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sigardasSplendor);
        checkHandCount("Initial hand size (2)", 1, PhaseStep.END_TURN, playerA, 1); //-1 sigarda
        checkLife("Initial life", 1, PhaseStep.END_TURN, playerA, 20);
        checkHandCount("Did not draw on 1st upkeep", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 3); //-1 sigarda, +1 natural draw, +1 trigger
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, sigardasSplendor);
        checkHandCount("Did not draw on 1st upkeep (2)", 3, PhaseStep.END_TURN, playerA, 2); //-2 sigarda, +1 natural draw, +1 trigger
        checkLife("Initial life", 3, PhaseStep.END_TURN, playerA, 21);

        setChoice(playerA, "At the beginning of your upkeep"); //stack triggers
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, sigardasSplendor, 2);
        assertHandCount(playerA, 5); //-2 sigardas, +2 natural draw, +3 trigger
    }

    @Test
    public void sigardasSplendorTestDamaged() {
        addCard(Zone.HAND, playerA, sigardasSplendor, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.HAND, playerB, "Scorching Spear", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        setStrictChooseMode(true);

        checkHandCount("Initial hand size", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sigardasSplendor);
        checkHandCount("Initial hand size (2)", 1, PhaseStep.END_TURN, playerA, 1); //-1 sigarda
        checkLife("Initial life", 1, PhaseStep.END_TURN, playerA, 20);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Scorching Spear", playerA);
        checkLife("Post-spear 1", 2, PhaseStep.END_TURN, playerA, 19);
        checkHandCount("Did not draw on 1st upkeep", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 2); //-1 sigarda, +1 natural draw
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, sigardasSplendor);
        checkHandCount("Did not draw on 1st upkeep (2)", 3, PhaseStep.END_TURN, playerA, 1); //-2 sigarda, +1 natural draw
        checkLife("Post-splendors", 3, PhaseStep.END_TURN, playerA, 20);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Scorching Spear", playerA);
        checkLife("Post-spear 2", 4, PhaseStep.END_TURN, playerA, 19);

        setChoice(playerA, "At the beginning of your upkeep"); //stack triggers
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, sigardasSplendor, 2);
        assertHandCount(playerA, 3); //-2 sigardas, +2 natural draw, +1 trigger
    }
}
