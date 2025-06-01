package org.mage.test.cards.single.cns;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MuzzioVisionaryArchitectTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MuzzioVisionaryArchitect Muzzio, Visionary Architect} {1}{U}{U}
     * Legendary Creature — Human Artificer
     * {3}{U}, {T}: Look at the top X cards of your library, where X is the greatest mana value among artifacts you control. You may put an artifact card from among them onto the battlefield. Put the rest on the bottom of your library in any order.
     * 1/3
     */
    private static final String muzzio = "Muzzio, Visionary Architect";

    @Test
    public void test_no_artifact() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Mox Ruby");
        addCard(Zone.LIBRARY, playerA, "Black Lotus");

        addCard(Zone.BATTLEFIELD, playerA, muzzio);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(muzzio, true);
        assertPermanentCount(playerA, 5);
    }

    @Test
    public void test_mv2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Mox Ruby");
        addCard(Zone.LIBRARY, playerA, "Black Lotus");

        addCard(Zone.BATTLEFIELD, playerA, muzzio);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Ace's Baseball Bat"); // mv 2
        addCard(Zone.BATTLEFIELD, playerA, "Chrome Mox", 2); // mv 0

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}");
        setChoice(playerA, "Mox Ruby"); // chosen to put on battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(muzzio, true);
        assertPermanentCount(playerA, 9);
        assertPermanentCount(playerA, "Mox Ruby", 1);
    }
}
