package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * 118.9d
 * If an alternative cost is being paid to cast a spell, any additional costs, cost increases, and cost reductions that
 * affect that spell are applied to that alternative cost. (See rule 601.2f.)
 *
 * @author DarioBarrabinoDev
 */
public class AlternativeCastCardCostsAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Flashback_CardCostReduction_AI() {
        // Bargain
        // This spell costs {2} less to cast if it's bargained.
        // Return target nonland permanent to its owner's hand. Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Johann's Stopgap"); // {3}{U}
        //
        // Flash
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        // The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2); // 2 for Snapcaster Mage, {3}{U} - {2} for flashback
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic"); // artifact to sacrifice for bargain
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Bronze Dragon"); // nonland permanent to return

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        // the AI chooses the only card in the graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // the AI can see the flashback cast only with the card's own reduction
        aiPlayPriority(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Johann's Stopgap", 1); // cast with flashback
        assertHandCount(playerB, "Ancient Bronze Dragon", 1); // returned to hand
        assertPermanentCount(playerA, "Darksteel Relic", 0); // sacrificed for bargain
    }
}
