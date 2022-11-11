package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author LevelX2
 */
public class OpalPalaceTest extends CardTestCommanderDuelBase {

    /**
     * I cast my commander with Opal Palace's second ability and it did not
     * receive a +1/+1 counter the first time it was cast (rulings say it should
     * on the first time cast).
     */
    @Test
    public void testFirstAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        // {T}: Add {C}.
        // {1}, {T}: Add one mana of any color in your commander's color identity.
        // If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it
        // equal to the number of times it's been cast from the command zone this game.
        addCard(Zone.BATTLEFIELD, playerA, "Opal Palace", 1);

        // showHand("hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showCommand("command", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // showAvailableAbilities("abi", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ob Nixilis of the Black Oath"); // {3}{B}{B}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertPermanentCount(playerA, "Ob Nixilis of the Black Oath", 1);
        assertCounterCount("Ob Nixilis of the Black Oath", CounterType.P1P1, 1);
    }
}
