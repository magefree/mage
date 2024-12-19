package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SurvivalTest extends CardTestPlayerBase {

    private static final String survivor = "Cautious Survivor";

    @Test
    public void testRegularNoAttack() {
        addCard(Zone.BATTLEFIELD, playerA, survivor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 0);
        assertLife(playerB, 20 - 0);
        assertTapped(survivor, false);
    }

    @Test
    public void testRegularAttack() {
        addCard(Zone.BATTLEFIELD, playerA, survivor);

        attack(1, playerA, survivor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 4);
        assertTapped(survivor, true);
    }

    private static final String assault = "Relentless Assault";

    @Test
    public void testExtraMainPhase() {
        addCard(Zone.HAND, playerA, assault);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, survivor);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, assault);
        attack(1, playerA, survivor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 4);
        assertTapped(survivor, true);
    }

    private static final String courage = "Ornamental Courage";

    @Test
    public void testUntap() {
        addCard(Zone.HAND, playerA, courage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, survivor);

        attack(1, playerA, survivor);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, courage, survivor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 0);
        assertLife(playerB, 20 - 4);
        assertTapped(survivor, false);
        assertPowerToughness(playerA, survivor, 4 + 1, 4 + 3);
    }
}
