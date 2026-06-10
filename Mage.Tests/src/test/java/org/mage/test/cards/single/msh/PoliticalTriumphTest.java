package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PoliticalTriumphTest extends CardTestPlayerBase {

    private static final String POLITICAL_TRIUMPH = "Political Triumph";

    @Test
    public void testSacrificeAndFollowupEffectsOnFourthPlanCounter() {
        skipInitShuffling();
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, POLITICAL_TRIUMPH);
        addCard(Zone.HAND, playerA, "Raise the Alarm", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, POLITICAL_TRIUMPH, 0);
        assertGraveyardCount(playerA, POLITICAL_TRIUMPH, 1);
        assertGraveyardCount(playerA, "Raise the Alarm", 2);
        assertPermanentCount(playerA, "Soldier Token", 4);
        assertPowerToughness(playerA, "Soldier Token", 2, 2, Filter.ComparisonScope.All);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testNoSacrificeBeforeFourthPlanCounter() {
        addCard(Zone.BATTLEFIELD, playerA, POLITICAL_TRIUMPH);
        addCard(Zone.HAND, playerA, "Raise the Alarm");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, POLITICAL_TRIUMPH, 1);
        assertCounterCount(playerA, POLITICAL_TRIUMPH, CounterType.PLAN, 3);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPowerToughness(playerA, "Soldier Token", 1, 1);
    }
}
