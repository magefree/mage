package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AmzuSwarmsHungerTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Amzu, Swarm's Hunger");
        addCard(Zone.GRAVEYARD, playerA, "Esper Sentinel");
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Raise the Past");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Past");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Insect Token", 3, 3);
    }

    @Test
    public void test_GreatestManaValueAmongYourCardsOnly() {
        // Whenever one or more cards leave your graveyard, you may create a 1/1 black and green Insect creature token,
        // then put a number of +1/+1 counters on it equal to the greatest mana value among those cards. Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Amzu, Swarm's Hunger");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // mv 2
        addCard(Zone.GRAVEYARD, playerB, "Colossal Dreadmaw"); // mv 6
        //
        // {1}, Exile Relic of Progenitus: Exile all graveyards. Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Relic of Progenitus");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // exile both graveyards at once
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Exile");
        setChoice(playerA, true); // create token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Insect Token", CounterType.P1P1, 2);
    }
}
