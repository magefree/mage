package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ExpeditedInheritanceTest extends CardTestPlayerBase {

    @Test
    public void test_OpponentsCreature_MayPlayUntilEndOfThatPlayersNextTurn() {
        skipInitShuffling();

        // Whenever a creature is dealt damage, its controller may exile that many cards from the top of their
        // library. They may play those cards until the end of their next turn.
        addCard(Zone.BATTLEFIELD, playerA, "Expedited Inheritance");
        //
        addCard(Zone.HAND, playerA, "Shock"); // {R}, 2 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt"); // top card, {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Hill Giant");
        setChoice(playerB, true); // exile

        // that player is playerB, its next turn is turn 2
        checkPlayableAbility("next turn", 2, PhaseStep.END_TURN, playerB, "Cast Lightning Bolt", true);
        checkPlayableAbility("after next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 2);
    }
}
