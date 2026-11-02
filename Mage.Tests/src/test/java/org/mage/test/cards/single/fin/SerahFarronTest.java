package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class SerahFarronTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SerahFarron Serah Farron // Crystallized Serah} {1}{G}{W}
     * Legendary Creature - Human Citizen 2/2
     * The first legendary creature spell you cast each turn costs {2} less to cast.
     * At the beginning of combat on your turn, if you control two or more other legendary creatures,
     * you may transform Serah Farron.
     * Crystallized Serah
     * Legendary Artifact
     * The first legendary creature spell you cast each turn costs {2} less to cast.
     * Legendary creatures you control get +2/+2.
     */
    private static final String serah = "Serah Farron";
    private static final String yargle = "Yargle, Glutton of Urborg"; // {4}{B}, legendary 9/3

    @Test
    public void test_FrontFaceReducesByTwo() {
        addCard(Zone.BATTLEFIELD, playerA, serah);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, yargle);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, yargle);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, yargle, 1);
        assertTappedCount("Swamp", true, 3);
    }

    @Test
    public void test_BackFaceReducesByTwo() {
        addCard(Zone.BATTLEFIELD, playerA, serah);
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");
        addCard(Zone.BATTLEFIELD, playerA, "Kira, Great Glass-Spinner");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, yargle);

        setChoice(playerA, true); // transform at the beginning of combat
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, yargle);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Crystallized Serah", 1);
        assertPowerToughness(playerA, yargle, 9 + 2, 3 + 2);
        assertTappedCount("Swamp", true, 3);
    }
}
