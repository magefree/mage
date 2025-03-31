package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class HarmonizeTest extends CardTestPlayerBase {

    private static final String bear = "Grizzly Bears";
    private static final String dragonfire = "Channeled Dragonfire";

    @Test
    public void testNoTap() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.GRAVEYARD, playerA, dragonfire);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonize", playerB);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(bear, false);
        assertLife(playerB, 20 - 2);
        assertExileCount(playerA, dragonfire, 1);
        assertTappedCount("Mountain", true, 7);
    }

    @Test
    public void testTap() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.GRAVEYARD, playerA, dragonfire);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonize", playerB);
        setChoice(playerA, true);
        setChoice(playerA, bear);
        setChoice(playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(bear, true);
        assertLife(playerB, 20 - 2);
        assertExileCount(playerA, dragonfire, 1);
        assertTappedCount("Mountain", true, 5);
    }
}
