package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by IGOUDT on 30-3-2017.
 */
public class KiraGreatGlassSpinnerTest extends CardTestPlayerBase {

    /*
       Kira, Great Glass-Spinner  {1}{U}{U}
       Legendary Creature - Spirit 2/2
       Flying
       Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability."
     */
    private final String kira = "Kira, Great Glass-Spinner";
    private final String ugin = "Ugin, the Spirit Dragon";

    @Test
    public void counterFirst() {

        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyality counters

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", kira); // Ugin ability

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
    }

    @Test
    public void counterFirstResolveSecond() {

        String ugin = "Ugin, the Spirit Dragon";
        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyality counters
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Unsummon", 1);

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage", kira); // Ugin ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", kira);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, kira, 0);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 9);
        assertGraveyardCount(playerA, "Unsummon", 1);
    }

    @Test
    public void counterFirstThisTurn_counterFirstOnNextTurn() {

        addCard(Zone.BATTLEFIELD, playerA, ugin); // starts with 7 Loyality counters

        addCard(Zone.BATTLEFIELD, playerA, kira);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage to", kira); // Ugin ability
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: {this} deals 3 damage to", kira); // Ugin ability

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kira, 1);
        assertCounterCount(playerA, ugin, CounterType.LOYALTY, 11);
    }

}
