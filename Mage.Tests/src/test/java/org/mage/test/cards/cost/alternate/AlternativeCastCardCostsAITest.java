package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

public class AlternativeCastCardCostsAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Flashback_CardCostReduction_AI() {
        // Bargain
        // This spell costs {2} less to cast if it's bargained.
        // Return target nonland permanent to its owner's hand. Draw a card.
        addCard(Zone.GRAVEYARD, playerA, "Johann's Stopgap"); // {3}{U}
        //
        // When Snapcaster Mage enters, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 2); // {3}{U} - {2}
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Relic"); // for bargain
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Bronze Dragon");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        // the AI chooses the only card in the graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // the AI can see the flashback cast only with the card's own reduction
        aiPlayPriority(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Johann's Stopgap", 1);
        assertHandCount(playerB, "Ancient Bronze Dragon", 1);
        assertPermanentCount(playerA, "Darksteel Relic", 0);
    }
}
