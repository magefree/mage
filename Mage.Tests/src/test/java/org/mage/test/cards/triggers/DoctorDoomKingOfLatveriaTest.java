package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author muz
 */
public class DoctorDoomKingOfLatveriaTest extends CardTestPlayerBase {

    @Test
    public void testDoctorDoomKingOfLatveriaLandDiscardTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime");

        // Whenever you discard one or more land cards, each opponent loses 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Doctor Doom, King of Latveria", 1);
        addCard(Zone.HAND, playerB, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        // Let choice be autopicked since there is only one option

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertPermanentCount(playerB, "Doctor Doom, King of Latveria", 1);

        assertLife(playerA, 18); // -2 from Doctor Doom, King of Latveria's ability
        assertLife(playerB, 20);
    }

    @Test
    public void testDoctorDoomKingOfLatveriaNonLandDiscardTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime");

        // Whenever you discard one or more land cards, each opponent loses 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Doctor Doom, King of Latveria", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerB);
        // Let choice be autopicked since there is only one option

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertPermanentCount(playerB, "Doctor Doom, King of Latveria", 1);

        assertLife(playerA, 20); // No life lost since a non-land card was discarded
        assertLife(playerB, 20);
    }

}
