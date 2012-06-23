package org.mage.test.cards.filters;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class MassAppealTest extends CardTestPlayerBase {

    @Test
    public void testNoDraw() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Constants.Zone.HAND, playerA, "Mass Appeal");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mass Appeal");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);
    }

    /**
     * Tests that cards will be drawn only for controller's permanents
     * Tests Human creatures with several subtypes (subtype comparison scope)
     */
    @Test
    public void testDrawingCards() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ana Disciple", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Alabaster Mage", 3);
        addCard(Constants.Zone.HAND, playerA, "Mass Appeal");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ana Disciple", 6);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mass Appeal");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 5);
        assertHandCount(playerB, 0);
    }

}
