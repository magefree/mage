package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JRHerlehy
 *         Created on 3/10/17.
 */
public class BloodbondMarchTest extends CardTestPlayerBase {

    @Test
    public void testCastNoExtraCardsInGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodbond March");
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 10);
        addCard(Zone.GRAVEYARD, playerA, "Elvish Mystic", 2);
        addCard(Zone.HAND, playerA, "Elvish Mystic");

        addCard(Zone.GRAVEYARD, playerB, "Elvish Mystic", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Mystic");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertGraveyardCount(playerA, "Elvish Mystic", 0);
        assertGraveyardCount(playerB, "Elvish Mystic", 0);
        assertPermanentCount(playerA, "Elvish Mystic", 3);
        assertPermanentCount(playerB, "Elvish Mystic", 2);
    }

    @Test
    public void testCastExtraCardsInGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodbond March");
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 10);
        addCard(Zone.GRAVEYARD, playerA, "Elvish Mystic", 2);
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Elvish Mystic");

        addCard(Zone.GRAVEYARD, playerB, "Elvish Mystic", 2);
        addCard(Zone.GRAVEYARD, playerB, "Griselbrand", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Mystic");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertGraveyardCount(playerA, "Elvish Mystic", 0);
        assertGraveyardCount(playerB, "Elvish Mystic", 0);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerB, 2);
        assertPermanentCount(playerA, "Elvish Mystic", 3);
        assertPermanentCount(playerB, "Elvish Mystic", 2);
    }
}
