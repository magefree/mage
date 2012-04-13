package org.mage.test.cards.copy;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 *
 * Card: You may have {this} enter the battlefield as a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it gains "When this creature becomes the target of a spell or ability, sacrifice it."
 */
public class PhantasmalImageTest extends CardTestPlayerBase {

    @Test
    public void testCopyCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

}
