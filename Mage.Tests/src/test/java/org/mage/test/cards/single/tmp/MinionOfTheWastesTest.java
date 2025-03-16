package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class MinionOfTheWastesTest extends CardTestPlayerBase {

    private static final String minion = "Minion of the Wastes";
    /*  Trample
     *  As this creature enters, pay any amount of life.
     *  Minion of the Wastes’s power and toughness are each equal to the life paid as it entered.
     */

    @Test
    public void testSimple() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, minion);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, minion);
        setChoice(playerA, "X=3");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertPowerToughness(playerA, minion, 3, 3);
    }

    @Test
    public void testFlicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        addCard(Zone.HAND, playerA, minion);
        addCard(Zone.HAND, playerA, "Cloudshift"); // flicker

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, minion);
        setChoice(playerA, "X=3");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", minion);
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
        assertPowerToughness(playerA, minion, 2, 2);
    }

    // Note similar cards: Wood Elemental, Nameless Race, Dracoplasm

}
