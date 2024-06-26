package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author TheElk801
 */
public class FreerunningTest extends CardTestCommanderDuelBase {

    private static final String vision = "Eagle Vision";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, vision);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, vision);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertTappedCount("Island", true, 5);
    }

    private static final String poisoner = "Hired Poisoner";

    @Test
    public void testAssassin() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, poisoner);
        addCard(Zone.HAND, playerA, vision);

        attack(1, playerA, poisoner, playerB);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, vision);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
    }

    private static final String goblin = "Raging Goblin";

    @Test
    public void testCommander() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2 + 1);
        addCard(Zone.COMMAND, playerA, goblin);
        addCard(Zone.HAND, playerA, vision);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblin);

        attack(1, playerA, goblin, playerB);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, vision);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
    }

    @Test
    public void testNeither() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 5 + 1);
        addCard(Zone.HAND, playerA, goblin);
        addCard(Zone.HAND, playerA, vision);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goblin);

        attack(1, playerA, goblin, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, vision);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertTappedCount("Volcanic Island", true, 5 + 1);
    }
}
