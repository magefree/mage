
package org.mage.test.cards.protection;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FlickeringWardTest extends CardTestPlayerBase {

    @Test
    public void testDOesNotRemoveItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Enchant creature
        // As Flickering Ward enters the battlefield, choose a color.
        // Enchanted creature has protection from the chosen color. This effect doesn't remove Flickering Ward.
        // {W}: Return Flickering Ward to its owner's hand.
        addCard(Zone.HAND, playerA, "Flickering Ward"); // {W}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flickering Ward", "Silvercoat Lion");
        setChoice(playerA, "White");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Flickering Ward", 1);
    }

}
