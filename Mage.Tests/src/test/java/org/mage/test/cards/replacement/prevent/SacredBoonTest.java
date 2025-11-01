
package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */

public class SacredBoonTest extends CardTestPlayerBase {

    @Test
    public void testSacredBoonBigDamage() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Sacred Boon");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Bombard");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacred Boon", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bombard", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertDamageReceived(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 5);
    }

    @Test
    public void testSacredBoonSmallDamage() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Sacred Boon");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Scattershot");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacred Boon", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Scattershot", "Silvercoat Lion");
        setChoice(playerB, false); // don't change scattershot targets
        setChoice(playerA, "At the"); // stack triggers - technically incorrect, should be a single trigger

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertDamageReceived(playerA, "Silvercoat Lion", 0);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 4);
    }
}
