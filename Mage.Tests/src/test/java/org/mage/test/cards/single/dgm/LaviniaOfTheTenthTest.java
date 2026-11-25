package org.mage.test.cards.single.dgm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.35a
 * Certain spells and abilities can detain a permanent. Until the next turn of the controller 
 * of that spell or ability, that permanent can’t attack or block and its activated 
 * abilities can’t be activated.
 *
 * @author JayDi85
 */
public class LaviniaOfTheTenthTest extends CardTestPlayerBase {

    @Test
    public void test_DetainUntilYourNextTurn() {
        // When Lavinia enters, detain each nonland permanent your opponents control with mana value 4 or less.
        addCard(Zone.HAND, playerA, "Lavinia of the Tenth"); // {3}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        // {T}: This creature deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer"); // {2}{R}, 1/1
        // {R}: This creature gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Shivan Dragon"); // {4}{R}{R}, 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lavinia of the Tenth");

        // detained on the opponent's turn
        checkMayAttackDefender("detained", 2, playerB, "Prodigal Pyromancer", playerA, false);
        checkPlayableAbility("detained", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals", false);
        // mana value 6 is not detained
        checkMayAttackDefender("not detained", 2, playerB, "Shivan Dragon", playerA, true);
        checkPlayableAbility("not detained", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "{R}: {this} gets", true);

        // free after playerA's next turn
        checkMayAttackDefender("free", 4, playerB, "Prodigal Pyromancer", playerA, true);
        checkPlayableAbility("free", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals", true);
        activateAbility(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {this} deals", playerA);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
    }
}
