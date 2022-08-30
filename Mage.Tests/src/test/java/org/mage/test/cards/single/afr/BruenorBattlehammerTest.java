package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class BruenorBattlehammerTest extends CardTestPlayerBase {

    private static final String bruenor = "Bruenor Battlehammer";
    private static final String lion = "Silvercoat Lion";
    private static final String bonesplitter = "Bonesplitter";
    private static final String morningstar = "Vulshok Morningstar";
    private static final String hauberk = "Demonmail Hauberk";

    @Test
    public void testModifyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bruenor);
        addCard(Zone.BATTLEFIELD, playerA, bonesplitter);
        addCard(Zone.BATTLEFIELD, playerA, morningstar);

        // activate {2} for free
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", lion);
        setChoice(playerA, true); // use for free
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // activate {1} for mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Plains", true);
        assertPermanentCount(playerA, bruenor, 1);
        assertPermanentCount(playerA, bonesplitter, 1);
        assertPermanentCount(playerA, morningstar, 1);
        // +2 from each equipment, +2x2 from Bruenor
        assertPowerToughness(playerA, lion, 2 + 2 + 2 + (2 * 2), 2 + 2);
    }

    @Test
    public void testHauberk() {
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bruenor);
        addCard(Zone.BATTLEFIELD, playerA, hauberk);

        setChoice(playerA, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        setStopAt(1, PhaseStep.END_TURN);
        assertPermanentCount(playerA, bruenor, 1);
        assertPowerToughness(playerA, lion, 2 + 4 + (1 * 2), 2 + 2);
    }
}
