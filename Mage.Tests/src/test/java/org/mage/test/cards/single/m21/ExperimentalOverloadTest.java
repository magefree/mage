package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExperimentalOverloadTest extends CardTestPlayerBase {

    @Test
    public void castAndReturnCard() {
        // Create an X/X blue and red Weird creature token, where X is the number of instant and sorcery cards in your graveyard.
        // Then you may return an instant or sorcery card from your graveyard to your hand. Exile Experimental Overload.
        addCard(Zone.HAND, playerA, "Experimental Overload");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.GRAVEYARD, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Experimental Overload");
       // setChoice(playerA, true);
        setChoice(playerA, "Shock");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertHandCount(playerA, "Shock",1);

    }
}
