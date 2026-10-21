package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class HeroicReturnTest extends CardTestPlayerBase {

    // This spell costs {2} less to cast if a creature is attacking you.
    // Return target creature card from your graveyard to the battlefield.
    // If a Hero enters this way, it enters with two additional +1/+1 counters on it.
    private static final String heroicReturn = "Heroic Return";

    private static final String hero = "Agent of Atlas"; // 2/2 Human Spy Hero
    private static final String lion = "Silvercoat Lion"; // 2/2 Cat

    @Test
    public void test_NotAttacked_FullCost() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, heroicReturn);
        addCard(Zone.GRAVEYARD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        checkPlayableAbility("no attacker, full cost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + heroicReturn, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Attacked_CostReducedAndHeroGetsCounters() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, heroicReturn);
        addCard(Zone.GRAVEYARD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(2, playerB, "Grizzly Bears", playerA);
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, heroicReturn, hero);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, hero, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 2);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_Attacked_NonHeroGetsNoCounters() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, heroicReturn);
        addCard(Zone.GRAVEYARD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(2, playerB, "Grizzly Bears", playerA);
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, heroicReturn, lion);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertCounterCount(playerA, lion, CounterType.P1P1, 0);
    }
}
