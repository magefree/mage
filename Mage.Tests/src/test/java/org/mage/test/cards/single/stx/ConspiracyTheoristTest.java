package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ConspiracyTheoristTest extends CardTestPlayerBase {

    @Test
    public void test_ExileOneOfDiscardedAndCast() {
        // Whenever you discard one or more nonland cards, you may exile one of them from your graveyard.
        // If you do, you may cast it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Conspiracy Theorist"); // 2/2
        //
        // As an additional cost to cast this spell, discard two cards.
        addCard(Zone.HAND, playerA, "Cathartic Reunion"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Shock"); // {R}, 2 damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Lightning Bolt^Shock"); // discard both by the cost
        setChoice(playerA, true); // use exile
        setChoice(playerA, "Lightning Bolt"); // exile one of them
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Shock", 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void test_CastOnlyThisTurn() {
        // Whenever you discard one or more nonland cards, you may exile one of them from your graveyard.
        // If you do, you may cast it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Conspiracy Theorist"); // 2/2
        //
        // As an additional cost to cast this spell, discard two cards.
        addCard(Zone.HAND, playerA, "Cathartic Reunion"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Shock"); // {R}, 2 damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cathartic Reunion");
        setChoice(playerA, "Lightning Bolt^Shock"); // discard both by the cost
        setChoice(playerA, true); // use exile
        setChoice(playerA, "Lightning Bolt"); // exile one of them
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Shock", 1);
    }
}
