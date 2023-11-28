package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TillerEngineTest extends CardTestPlayerBase {

    private static final String tiller = "Tiller Engine";
    /* Whenever a land enters the battlefield tapped and under your control, choose one —
     * Untap that land.
     * Tap target nonland permanent an opponent controls.
     */
    private static final String land = "Bloodfell Caves"; // enters tapped and gain 1 life
    private static final String centaur = "Centaur Courser";

    @Test
    public void testFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, tiller);
        addCard(Zone.BATTLEFIELD, playerB, centaur);
        addCard(Zone.HAND, playerA, land);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, land);
        setChoice(playerA, "Whenever a land enters"); // order triggers
        setModeChoice(playerA, "1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
        assertTapped(land, false);
        assertTapped(centaur, false);
    }

    @Test
    public void testSecondMode() {
        addCard(Zone.BATTLEFIELD, playerA, tiller);
        addCard(Zone.BATTLEFIELD, playerB, centaur);
        addCard(Zone.HAND, playerA, land);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, land);
        setChoice(playerA, "Whenever a land enters"); // order triggers
        setModeChoice(playerA, "2");
        addTarget(playerA, centaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
        assertTapped(land, true);
        assertTapped(centaur, true);
    }
}
