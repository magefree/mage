package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author DarioBarrabinoDev
 */
public class OrCostManaReductionAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AI_PaysReducedManaOption() {
        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        addCard(Zone.BATTLEFIELD, playerA, "Vadrik, Astral Archmage"); // 1/2
        //
        // As an additional cost to cast this spell, discard a card or pay {5}.
        // Lightning Axe deals 5 damage to target creature.
        addCard(Zone.HAND, playerA, "Lightning Axe"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // {R} + {5} - {1}
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel"); // 4/4

        // AI has no other card to discard, so it must use the reduced {5}
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Axe", 1);
        assertGraveyardCount(playerB, "Serra Angel", 1);
    }
}
