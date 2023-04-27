
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MasterOfCrueltiesTest extends CardTestPlayerBase {

    /*
    The ability of an Alesha-resurrected Master of Cruelties triggered in an EDH game despite being blocked by a creature.
     */
    @Test
    public void testMasterWasAleshaAnimated() {
        // First strike
        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking.
        addCard(Zone.BATTLEFIELD, playerB, "Alesha, Who Smiles at Death"); // 3/2

        // First strike
        // Deathtouch
        // Master of Cruelties can only attack alone.
        // Whenever Master of Cruelties attacks a player and isn't blocked, that player's life total becomes 1. Master of Cruelties assigns no combat damage this combat.
        addCard(Zone.GRAVEYARD, playerB, "Master of Cruelties"); // 1/4

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        attack(2, playerB, "Alesha, Who Smiles at Death");
        setChoice(playerB, true);
        // Master of Cruelties is autochosen since only target

        block(2, playerA, "Silvercoat Lion", "Master of Cruelties");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Master of Cruelties", 1);
        assertTapped("Master of Cruelties", true);
        assertTapped("Alesha, Who Smiles at Death", true);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }
}
