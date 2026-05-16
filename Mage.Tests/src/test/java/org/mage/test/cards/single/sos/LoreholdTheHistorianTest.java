package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LoreholdTheHistorianTest extends CardTestPlayerBase {

    @Test
    public void testInstantDrawnFirstGetsMiracleTwo() {
        setStrictChooseMode(true);

        // Scenario: Lorehold grants miracle {2} to instant and sorcery cards in its controller's
        // hand. The first card player B draws this turn is Lightning Bolt, so it should be
        // revealable and castable during the miracle trigger even though player B has no red mana.

        // Step 1: Put Lorehold on player B's battlefield, two generic mana sources available, and
        // Lightning Bolt as the first card player B will draw for the turn.
        addCard(Zone.BATTLEFIELD, playerB, "Lorehold, the Historian");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        skipInitShuffling();

        // Step 2: Accept the granted miracle trigger to reveal Lightning Bolt, cast it for {2},
        // and target player A.
        setChoice(playerB, true);
        addTarget(playerB, playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Step 3: Lightning Bolt was cast for Lorehold's generic miracle cost, dealt 3 damage,
        // and ended in the graveyard instead of staying in hand.
        assertLife(playerA, 17);
        assertHandCount(playerB, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void testCreatureDrawnFirstDoesNotGainMiracle() {
        setStrictChooseMode(true);

        // Scenario: Lorehold grants miracle only to instant and sorcery cards in hand. A creature
        // drawn as the first card of the turn should not offer a miracle prompt or become castable
        // during the draw step.

        // Step 1: Put Lorehold on player B's battlefield and make Grizzly Bears the first card
        // player B will draw for the turn.
        addCard(Zone.BATTLEFIELD, playerB, "Lorehold, the Historian");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        skipInitShuffling();

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Step 2: The creature stayed in hand and no unexpected strict-mode miracle choice was
        // requested.
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void testOpponentUpkeepDiscardThenDraw() {
        setStrictChooseMode(true);

        // Scenario: Lorehold's rummage trigger happens at the beginning of each opponent's upkeep.
        // Its controller may discard a card; only if they do, they draw a card.

        // Step 1: Give player A Lorehold, one expendable hand card to discard, and a known card to
        // draw when player B's upkeep begins.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        skipInitShuffling();

        // Step 2: On player B's upkeep, accept the optional discard cost and choose Memnite.
        setChoice(playerA, true);
        setChoice(playerA, "Memnite");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        // Step 3: Player A discarded Memnite, then drew the known Grizzly Bears.
        assertGraveyardCount(playerA, "Memnite", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
