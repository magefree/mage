package org.mage.test.cards.single.unf;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CometStellarPupTest extends CardTestPlayerBase {

    /**
     * Comet, Stellar Pup
     * {2}{R}{W}
     * Legendary Planeswalker — Comet
     * -
     * 0: Roll a six-sided die.
     * 1 or 2 — [+2], then create two 1/1 green Squirrel creature tokens. They gain haste until end of turn.
     * 3 — [−1], then return a card with mana value 2 or less from your graveyard to your hand.
     * 4 or 5 — Comet, Stellar Pup deals damage equal to the number of loyalty counters on him to a creature or player, then [−2].
     * 6 — [+1], and you may activate Comet, Stellar Pup’s loyalty ability two more times this turn.
     * -
     * Loyalty: 5
     */
    private final static String comet = "Comet, Stellar Pup";

    private final static String cometAbility = "0: Roll a six-sided die."
            + "<br>1 or 2 &mdash; [+2], then create two 1/1 green Squirrel creature tokens. They gain haste until end of turn."
            + "<br>3 &mdash; [-1], then return a card with mana value 2 or less from your graveyard to your hand."
            + "<br>4 or 5 &mdash; {this} deals damage equal to the number of loyalty counters on him to a creature or player, then [-2]."
            + "<br>6 &mdash; [+1], and you may activate Comet, Stellar Pup's loyalty ability two more times this turn.";

    @Test
    public void testRoll1() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);

        setDieRollResult(playerA, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 2);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 5 + 2);
    }

    @Test
    public void testRoll2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);

        setDieRollResult(playerA, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        attack(1, playerA, "Squirrel Token", playerB);
        attack(1, playerA, "Squirrel Token", playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 2);
        assertLife(playerB, 20 - 2);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 5 + 2);
    }

    @Test
    public void testRoll3() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);
        addCard(Zone.GRAVEYARD, playerA, "Memnite");

        setDieRollResult(playerA, 3);
        setChoice(playerA, "Memnite");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 0);
        assertHandCount(playerA, "Memnite", 1);
        assertLife(playerB, 20);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 5 - 1);
    }

    // TODO: Currently it is not possible to choose a player of a TargetCreatureOrPlayer
    //       the 4 roll is tested in testRoll6 on a permanent.
    @Ignore
    @Test
    public void testRoll4() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);

        setDieRollResult(playerA, 4);
        setChoice(playerA, "PlayerB");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 0);
        assertLife(playerB, 20 - 5);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 5 - 2);
    }

    @Test
    public void testRoll5() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon"); // 9/9

        setDieRollResult(playerA, 5);
        setChoice(playerA, "Ancient Brontodon");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 0);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, "Ancient Brontodon", 5);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 5 - 2);
    }

    @Test
    public void testRoll6() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);
        addCard(Zone.BATTLEFIELD, playerB, "Ghalta, Primal Hunger"); // 12/12

        setDieRollResult(playerA, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("6 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 6);

        setDieRollResult(playerA, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("7 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 7);

        setDieRollResult(playerA, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("9 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 9);

        setDieRollResult(playerA, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("9 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 11);

        setDieRollResult(playerA, 4);
        setChoice(playerA, "Ghalta, Primal Hunger");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 4);
        assertDamageReceived(playerB, "Ghalta, Primal Hunger", 11);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 9);
    }

    @Test
    public void testRoll6WithCarthTheLion() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);
        // Planeswalkers' loyalty abilities you activate cost an additional [+1] to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Carth the Lion");

        setDieRollResult(playerA, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("7 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 7);

        setDieRollResult(playerA, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("10 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 10);

        setDieRollResult(playerA, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 4);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 13);
    }

    @Test
    public void testRoll6AgainstEidolonOfObstruction() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comet);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Loyalty abilities of planeswalkers your opponents control cost {1} more to activate.
        addCard(Zone.BATTLEFIELD, playerB, "Eidolon of Obstruction");

        setDieRollResult(playerA, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, true);
        checkPermanentCounters("7 loyalty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, comet, CounterType.LOYALTY, 6);

        setDieRollResult(playerA, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can activate more", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cometAbility, false); // no mana to pay the tax one more time.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Squirrel Token", 2);
        assertTappedCount("Plains", true, 2);
        assertCounterCount(playerA, comet, CounterType.LOYALTY, 8);
    }
}
