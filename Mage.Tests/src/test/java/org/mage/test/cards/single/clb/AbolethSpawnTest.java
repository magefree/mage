package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AbolethSpawnTest extends CardTestPlayerBase {

    private static final String aboleth = "Aboleth Spawn"; // 2/3 Flash, ward {2}
    // Whenever a creature entering the battlefield under an opponent's control
    // causes a triggered ability of that creature to trigger,
    // you may copy that ability. You may choose new targets for the copy.
    private static final String sparkmage = "Sparkmage Apprentice"; // 1/1
    // When Sparkmage Apprentice enters the battlefield, it deals 1 damage to any target.
    private static final String attendant = "Soul's Attendant"; // 1/1
    // Whenever another creature enters the battlefield, you may gain 1 life.
    private static final String hatchling = "Kraken Hatchling"; // 0/4
    private static final String ridgescale = "Ridgescale Tusker"; // 5/5
    // When Ridgescale Tusker enters the battlefield, put a +1/+1 counter on each other creature you control.


    @Test
    public void testTriggerCopies() {
        addCard(Zone.BATTLEFIELD, playerA, aboleth);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerB, attendant);
        addCard(Zone.HAND, playerB, sparkmage);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, sparkmage);
        setChoice(playerB, "Whenever another"); // order triggers
        addTarget(playerB, hatchling); // to deal 1 damage
        setChoice(playerA, true); // yes to copy sparkmage trigger
        setChoice(playerA, true); // yes to change targets
        addTarget(playerA, aboleth); // to deal 1 damage
        setChoice(playerB, true); // yes to gain 1 life

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertDamageReceived(playerA, hatchling, 1);
        assertDamageReceived(playerA, aboleth, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 21);
    }

    @Test
    public void testTriggerController() {
        addCard(Zone.BATTLEFIELD, playerA, aboleth);
        addCard(Zone.BATTLEFIELD, playerB, hatchling);
        addCard(Zone.BATTLEFIELD, playerA, attendant);
        addCard(Zone.HAND, playerB, ridgescale);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, ridgescale);
        setChoice(playerA, "Whenever another"); // order triggers
        setChoice(playerA, true); // yes to copy ridgescale trigger
        setChoice(playerA, true); // yes to gain 1 life

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, aboleth, CounterType.P1P1, 1);
        assertCounterCount(playerA, attendant, CounterType.P1P1, 1);
        assertCounterCount(playerB, hatchling, CounterType.P1P1, 1);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

}
