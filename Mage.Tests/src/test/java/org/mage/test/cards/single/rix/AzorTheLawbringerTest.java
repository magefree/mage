package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AzorTheLawbringerTest extends CardTestPlayerBase {

    @Test
    public void test_EntersOnYourTurn_CantCastDuringOpponentsNextTurn() {
        // When Azor enters, each opponent can't cast instant or sorcery spells during that player's next turn.
        addCard(Zone.HAND, playerA, "Azor, the Lawbringer"); // {2}{W}{W}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azor, the Lawbringer");

        checkPlayableAbility("next turn", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        checkPlayableAbility("later turn", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_EntersOnOpponentsTurn_CantCastDuringItsNextTurn() {
        addCustomEffect_TargetBlink(playerA);

        // When Azor enters, each opponent can't cast instant or sorcery spells during that player's next turn.
        addCard(Zone.BATTLEFIELD, playerA, "Azor, the Lawbringer");
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // turn 2 - enters on the opponent's turn, that turn is not its next turn
        activateAbility(2, PhaseStep.UPKEEP, playerA, "target blink", "Azor, the Lawbringer");
        checkPlayableAbility("this turn", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        // turn 4 - the next turn
        checkPlayableAbility("next turn", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);

        // turn 6 - free
        checkPlayableAbility("later turn", 6, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();
    }
}
