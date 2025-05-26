package org.mage.test.cards.single.ltc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GandalfOfTheSecretFireTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GandalfOfTheSecretFire Gandalf of the Secret Fire} {1}{U}{R}{W}
     * Legendary Creature — Avatar Wizard
     * Whenever you cast an instant or sorcery spell from your hand during an opponent’s turn, exile that card with three time counters on it instead of putting it into your graveyard as it resolves. Then if the exiled card doesn’t have suspend, it gains suspend. (At the beginning of your upkeep, remove a time counter. When the last is removed, you may play it without paying its mana cost.)
     * 3/4
     */
    private static final String gandalf = "Gandalf of the Secret Fire";

    @Test
    public void test_yourturn() {
        addCard(Zone.BATTLEFIELD, playerA, gandalf, 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_oppturn_suspend() {
        addCard(Zone.BATTLEFIELD, playerA, gandalf, 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        checkExileCount("1: bolt in exile", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkCardCounters("1: bolt has 3 time counters", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", CounterType.TIME, 3);

        // turn 3: from 3 to 2 time counter
        checkCardCounters("2: bolt has 2 time counters", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", CounterType.TIME, 2);

        // turn 5: from 2 to 1 time counter
        checkCardCounters("3: bolt has 1 time counters", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", CounterType.TIME, 1);

        setChoice(playerA, true); // yes to cast from suspend removing last counter
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 6);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_split_suspend() {
        addCard(Zone.BATTLEFIELD, playerA, gandalf, 1);

        addCard(Zone.HAND, playerA, "Fire // Ice", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire", playerB);
        addTargetAmount(playerA, playerB, 2);

        checkExileCount("1: fire//ice in exile", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fire // Ice", 1);
        checkCardCounters("1: fire//ice has 3 time counters", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fire // Ice", CounterType.TIME, 3);

        // turn 3: from 3 to 2 time counter
        checkCardCounters("2: fire//ice has 2 time counters", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire // Ice", CounterType.TIME, 2);

        // turn 5: from 2 to 1 time counter
        checkCardCounters("3: fire//ice has 1 time counters", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire // Ice", CounterType.TIME, 1);

        setChoice(playerA, true); // yes to cast from suspend removing last counter
        setChoice(playerA, "Cast Fire"); // choose to cast Fire side
        addTargetAmount(playerA, playerB, 2);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4);
        assertGraveyardCount(playerA, "Fire // Ice", 1);
    }

    @Test
    public void test_oppturn_counterspell_suspend() {
        addCard(Zone.BATTLEFIELD, playerA, gandalf, 1);

        addCard(Zone.HAND, playerA, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertExileCount(playerA, "Counterspell", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void test_oppturn_countered_nosuspend() {
        addCard(Zone.BATTLEFIELD, playerB, gandalf, 1);

        addCard(Zone.HAND, playerA, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Lightning Bolt", "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Counterspell", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void test_adventure_nosuspend() {
        addCard(Zone.BATTLEFIELD, playerA, gandalf, 1);

        addCard(Zone.HAND, playerA, "Bonecrusher Giant", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Stomp", playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertExileCount(playerA, "Bonecrusher Giant", 1);
        // since an Adventure card is not going to the graveyard on resolve, Gandalf's trigger does not suspend it.
        assertCounterOnExiledCardCount("Bonecrusher Giant", CounterType.TIME, 0);
    }
}
