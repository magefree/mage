package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SphinxsDecreeTest extends CardTestPlayerBase {

    @Test
    public void test_CantCastDuringNextTurn() {
        // Each opponent can't cast instant or sorcery spells during that player's next turn.
        addCard(Zone.HAND, playerA, "Sphinx's Decree"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sphinx's Decree");

        // turn 2 - instant or sorcery can't be cast, other spells can
        checkPlayableAbility("next turn", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        checkPlayableAbility("next turn", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", true);

        // turn 4 - free
        checkPlayableAbility("later turn", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_NothingCastDuringNextTurn_LaterTurnFree() {
        // Each opponent can't cast instant or sorcery spells during that player's next turn.
        addCard(Zone.HAND, playerA, "Sphinx's Decree"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sphinx's Decree");

        // turn 2 - the next turn passes without any spell
        // turn 4 - free
        checkPlayableAbility("later turn", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 3);
    }
}
