package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GarnetPrincessOfAlexandriaTest extends CardTestPlayerBase {

    @Test
    public void test_Targets_0() {
        // 2/2
        // Whenever Garnet attacks, you may remove a lore counter from each of any number of Sagas you control.
        // Put a +1/+1 counter on Garnet for each lore counter removed this way.
        addCard(Zone.BATTLEFIELD, playerA, "Garnet, Princess of Alexandria");

        // nothing to select after attack
        attack(1, playerA, "Garnet, Princess of Alexandria", playerB);
        //setChoice(playerA, TestPlayer.CHOICE_SKIP); // no valid choices, so do not show choose dialog

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Targets_1() {
        // 2/2
        // Whenever Garnet attacks, you may remove a lore counter from each of any number of Sagas you control.
        // Put a +1/+1 counter on Garnet for each lore counter removed this way.
        addCard(Zone.BATTLEFIELD, playerA, "Garnet, Princess of Alexandria");
        //
        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        // I, II, III, IV -- Stampede! -- Other creatures you control get +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Summon: Choco/Mog"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        // prepare x1 saga
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summon: Choco/Mog");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can select 1 target only
        attack(1, playerA, "Garnet, Princess of Alexandria", playerB);
        setChoice(playerA, "Summon: Choco/Mog");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 2/2 attack + 1/1 saga's boost + 1/0 counter remove boost
        assertCounterCount(playerA, "Summon: Choco/Mog", CounterType.LORE, 0);
        assertLife(playerB, 20 - 2 - 1 - 1);
    }

    @Test
    public void test_Targets_2() {
        // 2/2
        // Whenever Garnet attacks, you may remove a lore counter from each of any number of Sagas you control.
        // Put a +1/+1 counter on Garnet for each lore counter removed this way.
        addCard(Zone.BATTLEFIELD, playerA, "Garnet, Princess of Alexandria");
        //
        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        // I, II, III, IV -- Stampede! -- Other creatures you control get +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Summon: Choco/Mog", 2); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3 * 2);

        // prepare x2 sagas
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summon: Choco/Mog");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summon: Choco/Mog");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can select 2 targets
        attack(1, playerA, "Garnet, Princess of Alexandria", playerB);
        setChoice(playerA, "Summon: Choco/Mog", 2); // x2 targets

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 2/2 attack + x2 1/1 saga's boost + x2 1/0 counter remove boost
        assertCounterCount(playerA, "Summon: Choco/Mog", CounterType.LORE, 0);
        assertLife(playerB, 20 - 2 - 2 - 2);
    }
}
