package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author GregorStocks
 */
public class MoonshadowTest extends CardTestPlayerBase {

    @Test
    public void testTriggerFromAllZones() {
        addCard(Zone.BATTLEFIELD, playerA, "Moonshadow");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Lightning Bolt {R} - Deal 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        // Thought Scour {U} - Target player mills two cards. Draw a card.
        addCard(Zone.HAND, playerA, "Thought Scour", 1);
        // One with Nothing {B} - Discard your hand.
        addCard(Zone.HAND, playerA, "One with Nothing", 1);

        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 3);
        skipInitShuffling();

        // Battlefield -> graveyard: kill Silvercoat Lion, trigger once (6 -> 5)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");
        // Library -> graveyard: mill 2 creatures, draw 1, trigger once (5 -> 4)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Thought Scour", playerA);
        // Hand -> graveyard: discard the drawn creature, trigger once (4 -> 3)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "One with Nothing");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 3);
        assertCounterCount(playerA, "Moonshadow", CounterType.M1M1, 3);
    }
}
