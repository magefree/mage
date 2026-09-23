package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author temmi
 */
public class RaffineSchemingSeerTest extends CardTestCommanderDuelBase {

    /**
     * https://github.com/magefree/mage/issues/15216
     * - Raffine, Scheming Seer as commander
     * - Attack and connive
     * - Discard Malevolent Hermit from hand
     * Malevolent Hermit must go to graveyard, not exile.
     */
    @Test
    public void testMalevolentHermitDiscardedByRaffineNotExiled() {
        addCard(Zone.COMMAND, playerA, "Raffine, Scheming Seer");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Malevolent Hermit");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raffine, Scheming Seer");
        attack(1, playerA, "Silvercoat Lion", playerB);
        addTarget(playerA, "Silvercoat Lion");
        setChoice(playerA, "Malevolent Hermit");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Malevolent Hermit", 1);
        assertExileCount(playerA, "Malevolent Hermit", 0);
    }
}