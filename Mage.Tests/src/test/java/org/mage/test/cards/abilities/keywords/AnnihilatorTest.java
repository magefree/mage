package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnnihilatorTest extends CardTestPlayerBase {

    @Test
    public void testCardsSacrificedToAnnihilatorTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Annihilator 6 (Whenever this creature attacks, defending player sacrifices two permanents.)
        addCard(Zone.BATTLEFIELD, playerB, "Emrakul, the Aeons Torn");

        attack(2, playerB, "Emrakul, the Aeons Torn");
        setChoice(playerA, "Island");
        setChoice(playerA, "Island");
        setChoice(playerA, "Island");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Mountain");
        setChoice(playerA, "Mountain");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 5);
        assertPermanentCount(playerA, 1);
    }


}
