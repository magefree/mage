package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DoomsTimePlatformTest extends CardTestPlayerBase {

    @Test
    public void testTriggersWhenAnotherCreatureAttacks() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Doom's Time Platform");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        attack(1, playerA, "Grizzly Bears", playerB);
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertCounterOnExiledCardCount("Lightning Bolt", CounterType.TIME, 2);
    }
}
