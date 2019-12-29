package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */
public class CommanderAffinityTest extends CardTestCommanderDuelBase {

    /*
    Blinkmoth Infusion {12}{U}{U}
    Affinity for artifacts (This spell costs {1} less to cast for each artifact you control.)
    Untap all artifacts.
    */

    @Test
    public void test_AffinityNormal() {
        addCard(Zone.HAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 12);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        checkHandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // cast for UU (12 must be reduced)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AffinityCommanderNormalReduction() {
        addCard(Zone.COMMAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 12);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2 * 2);

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // first cast for 12UU (-12 by abzan, -UU by islands)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, "Yes"); // keep commander
        checkCommandCardCount("after 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 1);

        // second cast for 12UU + 2 (-12 by abzan, -UU by islands, -2 by islands)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, "No"); // remove commander to grave
        checkCommandCardCount("after 2", 1, PhaseStep.END_TURN, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AffinityCommanderAdditionalReduction() {
        addCard(Zone.COMMAND, playerA, "Blinkmoth Infusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2);

        checkCommandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion", 1);

        // first cast for 12UU (-12 by abzan, -UU by islands)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, "Yes"); // keep commander
        checkCommandCardCount("after 1", 1, PhaseStep.BEGIN_COMBAT, playerA, "Blinkmoth Infusion", 1);

        // second cast for 12UU + 2 (-12 by abzan, -UU by islands, -2 by abzan)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blinkmoth Infusion");
        setChoice(playerA, "No"); // remove commander to grave
        checkCommandCardCount("after 2", 1, PhaseStep.END_TURN, playerA, "Blinkmoth Infusion", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

}
