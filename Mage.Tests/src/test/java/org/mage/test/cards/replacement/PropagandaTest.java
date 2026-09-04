package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PropagandaTest extends CardTestPlayerBase {

    @Test
    public void test_Propaganda1() {
        setStrictChooseMode(true);

        // Creatures can’t attack you unless their controller pays {2} for each creature they control that’s attacking you.
        addCard(Zone.BATTLEFIELD, playerA, "Propaganda");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        attack(2, playerB, "Silvercoat Lion");
        setChoice(playerB, true);// Pay {2} to attack?

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);

        assertTappedCount("Silvercoat Lion", true, 1);
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void test_Propaganda2() {
        setStrictChooseMode(true);

        // Creatures can’t attack you unless their controller pays {2} for each creature they control that’s attacking you.
        addCard(Zone.BATTLEFIELD, playerA, "Propaganda", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        attack(2, playerB, "Silvercoat Lion");
        setChoice(playerB, "Propaganda"); // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.");
        setChoice(playerB, true);// Pay {2} to attack?
        setChoice(playerB, true);// Pay {2} to attack?

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);

        assertTappedCount("Silvercoat Lion", true, 1);
        assertTappedCount("Plains", true, 4);
    }

    // https://github.com/magefree/mage/issues/10372
    // https://github.com/magefree/mage/issues/14754
    @Test
    public void testClone() {
        addCard(Zone.BATTLEFIELD, playerB, "Archangel of Tithes");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes");
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Archangel of Tithes");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fell");
        addTarget(playerA, "Archangel of Tithes[no copy]");

        attack(2, playerB, "Balduvian Bears");
        setChoice(playerB, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 18);
        assertTappedCount("Balduvian Bears", true, 1);
    }

}
