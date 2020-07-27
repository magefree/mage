package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class AftermathTest extends CardTestPlayerBase {

    @Test
    public void testCastFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, "Spring // Mind", 1);
        /*
        Aftermath (Cast this spell only from your graveyard. Then exile it.)
        Draw two cards.
        */
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Spring // Mind", 1);  // card is exiled after casting from graveyard
        assertHandCount(playerA, 2);  // two cards drawn
        
    }
}
