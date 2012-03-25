package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 *
 * Card: When Oblivion Ring enters the battlefield, exile another target nonland permanent.
 * When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
 */
public class OblivionRingTest extends CardTestPlayerBase {

    /**
     * When Oblivion Ring enters the battlefield, exile another target nonland permanent.
     */
    @Test
    public void testFirstTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 1);
        assertPermanentCount(playerB, "Frost Titan", 0);
    }

    /**
     * When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
     */
    @Test
    public void testSecondTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Constants.Zone.HAND, playerA, "Oblivion Ring");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Constants.Zone.HAND, playerB, "Naturalize");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Oblivion Ring");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Oblivion Ring", 0);
        assertPermanentCount(playerB, "Frost Titan", 1);
    }
}
