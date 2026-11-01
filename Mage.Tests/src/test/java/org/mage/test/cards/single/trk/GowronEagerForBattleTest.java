package org.mage.test.cards.single.trk;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DejviFiala
 */
public class GowronEagerForBattleTest extends CardTestPlayerBase {

    /*
     * Gowron, Eager for Battle {2}{B}{R}
     * Legendary Creature — Klingon Warrior
     * Whenever another creature dies, put a +1/+1 counter on Gowron.
     * Sacrifice another creature: Gowron gains trample until end of turn.
     * If you sacrificed a Klingon this way, you draw a card and lose 1 life.
     * 3/3
     */
    private static final String gowron = "Gowron, Eager for Battle";
    private static final String sacAbility = "Sacrifice another creature";
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String aggressor = "Defense Force Aggressor"; // 2/1 Klingon Warrior
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testSacrificeNonKlingon() {
        addCard(Zone.BATTLEFIELD, playerA, gowron);
        addCard(Zone.BATTLEFIELD, playerA, bears);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, sacAbility);
        setChoice(playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("trample this turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, gowron, TrampleAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, bears, 1);
        assertCounterCount(playerA, gowron, CounterType.P1P1, 1);
        assertPowerToughness(playerA, gowron, 4, 4);
        assertAbility(playerA, gowron, TrampleAbility.getInstance(), false);
        assertHandCount(playerA, 0);
        assertLife(playerA, 20);
    }

    @Test
    public void testSacrificeKlingon() {
        addCard(Zone.BATTLEFIELD, playerA, gowron);
        addCard(Zone.BATTLEFIELD, playerA, aggressor);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, sacAbility);
        setChoice(playerA, aggressor);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, aggressor, 1);
        assertCounterCount(playerA, gowron, CounterType.P1P1, 1);
        assertAbility(playerA, gowron, TrampleAbility.getInstance(), true);
        assertHandCount(playerA, 1);
        assertLife(playerA, 19);
    }

    @Test
    public void testOpponentCreatureDies() {
        addCard(Zone.BATTLEFIELD, playerA, gowron);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bears, 1);
        assertCounterCount(playerA, gowron, CounterType.P1P1, 1);
        assertAbility(playerA, gowron, TrampleAbility.getInstance(), false);
    }

    @Test
    public void testCantSacrificeItself() {
        addCard(Zone.BATTLEFIELD, playerA, gowron);

        checkPlayableAbility("no other creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, sacAbility, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, gowron, 1);
    }
}
